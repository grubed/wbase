package com.mrwind.windbase.service;

import com.alibaba.fastjson.JSONObject;
import com.mrwind.windbase.bo.CourierMissionStatusBO;
import com.mrwind.windbase.bo.TeamMemberLocationStatusBO;
import com.mrwind.windbase.common.api.MerchantApi;
import com.mrwind.windbase.common.constant.CommonConstants;
import com.mrwind.windbase.common.constant.TeamType;
import com.mrwind.windbase.common.exception.FailResultException;
import com.mrwind.windbase.common.util.*;
import com.mrwind.windbase.dto.*;
import com.mrwind.windbase.entity.mongo.CourierSettings;
import com.mrwind.windbase.entity.mongo.Position;
import com.mrwind.windbase.entity.mongo.TeamPosition;
import com.mrwind.windbase.entity.mysql.*;
import com.mrwind.windbase.vo.*;
import com.mrwind.windbase.vo.team.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by CL-J on 2018/7/18.
 */
@Service
public class TeamService extends BaseService {

    //获取team
    public Team getOneTeam(String teamId) {
        return teamRepository.findById(teamId).orElse(null);
    }

    /**
     * 创建根团队/公司
     * tips 创建公司需要设置管理员并且创建资金账户
     * 1. 判断公司名是否重复
     * 2. 把创建者加入到该团队 并设置为管理员
     * 3. 团队扩展表创建
     * 4. 团队经纬度表创建
     */
    public Team createRootTeam(String dependTeamId, String teamName, String teamAvatar, String project, User creator) {

        String teamId = TextUtils.isEmpty(dependTeamId) ? TextUtils.getUUID() : dependTeamId;

        String redisNo = getSerialNumber();
        //1. 创建团队
        Team newTeam = new Team(teamId, Long.valueOf(redisNo), 0, project, teamName, teamAvatar, teamId, redisNo);
        newTeam.setRemark(teamName);
        teamRepository.save(newTeam);
        //2. 设置创建者为管理员
        userTeamRelationService.setTeamManager(teamId, creator.getUserId(), true);
        //3. 创建团队扩展表
        teamExtentionRepository.save(new TeamExtention(teamId));
        //4. 团队位置表创建 Mongo
        teamPositionDao.save(new TeamPosition(teamId));

        return newTeam;
    }

    /**
     * 创建分组
     * tips 此处创建者不加入新建分组 新创建分组默认没有成员,无资金账户
     * 1. 判断分组名在公司下是否重复
     * 2. 创建团队 继承project字段 !!!
     * 3. 团队扩展表创建
     * 4. 团队经纬度表创建
     */
    public Team createChildTeam(String teamName, Integer type, String teamAvatar, String parentTeamId, String project) {
        String teamId = TextUtils.getUUID();
        String redisNo = getSerialNumber();
        //2. 创建团队
        Team parent = Optional.ofNullable(teamRepository.findByTeamId(parentTeamId)).orElseThrow(() -> FailResultException.getI18N("error.no.parent.team"));
        Team newTeam = new Team(teamId, Long.valueOf(redisNo), type, project, teamName, teamAvatar, parent.getRootId(), parent.getParentIds() + "," + redisNo);
        newTeam.setRemark(teamName);
        teamRepository.save(newTeam); //3. 团队扩展表创建
        teamExtentionRepository.save(new TeamExtention(teamId));
        //4. 团队位置表创建 Mongo
        teamPositionDao.save(new TeamPosition(teamId));
        //若是电商类型  则创建店铺
        if (type.equals(Integer.valueOf(2))) {
            MerchantApi.createStore(teamId);
        }

        return newTeam;
    }

    /**
     * 创建团队
     *
     * @param teamDTO 需创建的team信息
     * @return
     */
    public Result createTeam(TeamDTO teamDTO) {
        String parentTeamId = teamDTO.getParentId();
        String name = teamDTO.getName();
        String avatar = teamDTO.getAvatar();
        String project = teamDTO.getProject();
        User creator = userRepository.findByUserId(teamDTO.getUserId());
        int type = teamDTO.getType();
        //创建根团队(公司)
        if (TextUtils.isEmpty(parentTeamId)) {
            //校验要创建的根团队名称是否重名
            if (teamRepository.getAllRoots().stream().anyMatch(t -> TextUtils.equals(t.getName(), name))) {
                return Result.getFailI18N("error.already.exist.name");
            }
            //没有重名则创建根团队
            Team newTeam = createRootTeam(teamDTO.getTeamId(), name, avatar, project, creator);
            return Result.getSuccess(newTeam);
        }

        //创建子团队
        //先校验要创建的子团队在当前根团队下是否重名
        String currentTeamId = creator.getCurrentTeamId();
        if (TextUtils.isEmpty(currentTeamId)) {
            return Result.getFailI18N("error.no.current.team");
        }
        Team root = getOneTeam(currentTeamId);
        List<Team> teams = getTeamChildrensAndSelf(root.getParentIds(), root.getTeamId());
        if (teams.stream().anyMatch(t -> TextUtils.equals(name, t.getName()))) {
            return Result.getFailI18N("error.already.exist.name");
        }

        Team parent = getOneTeam(teamDTO.getParentId());
        //如果跟父团队类型不同 且不是普通团队 报错

        if (!TextUtils.equals(root.getTeamId(), CommonConstants.MRWIND_ID)) {
            type = 0;
        } else {
            //若有任何一个父团队类型和所选类型不一致 且所选类型不是0
            if (!TextUtils.equals(teamDTO.getParentId(), CommonConstants.MRWIND_ID)) {
                if (parent.getType() != teamDTO.getType() && teamDTO.getType() != 0) {
                    return Result.getFailI18N("error.team.type.must.equle.parent");
                }
            }
        }

        //如果没有重名则创建子团队
        Team newTeam = createChildTeam(name, type, avatar, parentTeamId, project);
        return Result.getSuccess(newTeam);
    }

    /**
     * 新版获取团队详情
     *
     * @param teamId  团队 id，不传则获取当前所在分组
     * @param project app BU
     */
    public Result getTeamInfo(String teamId, String project) {

        User me = getCurrentUser();
        // 校验团队有效性
        Team team;
        if (!TextUtils.isEmpty(teamId)) {
            team = teamRepository.findByTeamId(teamId);
        } else {
            team = getOnlyTeam(me.getUserId(), me.getCurrentTeamId());
            teamId = team.getTeamId();
        }
        if (team == null) {
            return Result.getFailI18N("error.team.not.found");
        }
        // 获取团队信息
        TeamDetailVO vo = new TeamDetailVO();
        // 团队名
        vo.setName(team.getName());
        // 团队 id
        vo.setTeamId(team.getTeamId());
        // 团队头像
        vo.setAvatar(team.getAvatar());
        //团队类型
        vo.setType(team.getType());
        // 团队成员数（包括子团队）
        vo.setMemberCount(getTeamUserNum(teamId));
        // 自己是否为根团队管理员
        UserTeamRelation teamRelation = userTeamRelationRepository.findByTeamIdAndUserId(me.getCurrentTeamId(), me.getUserId());
        vo.setRootManager(Optional.ofNullable(teamRelation).map(UserTeamRelation::isMananger).orElse(false));
        // 自己能否管理当前查询的团队
        if (vo.getRootManager()) {
            // 如果已经是根团队管理员，那么肯定可以管理其子团队
            vo.setManageable(true);
        } else {
            // 如果不是根团队管理员，则还需要判断是否能管理此团队
            vo.setManageable(userTeamService.checkCanManageTeam(me.getUserId(), teamId));
        }
        // 组装团队标签
        vo.setRoles(JsonUtil.parse(roleRepository.listTeamRolesByTeamId(teamId), RoleVO.class));
        // 子团队信息
        List<Team> childrenTeams = getTeamChildrenStartingWith(team.getParentIds()).stream()
                .filter(t -> !(t.getParentIds().replaceFirst(team.getParentIds() + ",", "").contains(","))).collect(Collectors.toList());
        List<ChildTeam> childTeams = new ArrayList<>();
        childrenTeams.forEach(childrenTeam -> {
            ChildTeam ct = new ChildTeam();
            ct.setTeamId(childrenTeam.getTeamId());
            ct.setName(childrenTeam.getName());
            ct.setType(childrenTeam.getType());
            ct.setMemberCount(getTeamUserNum(childrenTeam.getTeamId()));
            ct.setHasChildrens(teamRepository.countChildrens(childrenTeam.getParentIds() + ",%") > 0);
            ct.setMemberNames(userRepository.getTeamMembersName(childrenTeam.getTeamId()));
            childTeams.add(ct);
        });
        //子团队按名称排序
        vo.setChildrens(childTeams.stream().sorted(Comparator.comparing(ChildTeam::getName)).collect(Collectors.toList()));

        // 团队成员
        List<UserTeamRelation> userTeamRelations = userTeamRelationRepository.findByTeamIdAndOrderByManangerDescIdAsc(teamId);
        List<String> memberIds = userTeamRelations.stream().map(UserTeamRelation::getUserId).collect(Collectors.toList());
        // 用户 id 和 User 对象映射
        Map<String, User> membersUserMap = CollectionUtil.takeFieldsToMap(userRepository.findByUserIdIn(memberIds), "userId");
        // 用户 id 和最新位置&今日第一次签到时间&休息时间映射
        Map<String, TeamMemberLocationStatusBO> latestLocationStatusMap = CollectionUtil.takeFieldsToMap(attenceApi.getTeamMemberLatestLocationStatus(memberIds), "userId");
        // 用户 id 和当前是否有任务映射
        Map<String, CourierMissionStatusBO> missionStatusMap = CollectionUtil.takeFieldsToMap(windForceApi.getCourierMissionStatus(memberIds), "userId");
        // 用户 id 和当前考勤状态映射
        Map<String, TeamUserStatusVO> userStatusMap = CollectionUtil.takeFieldsToMap(attenceApi.getUserStatusForTeam(memberIds, me.getCurrentTeamId()), "userId");
        vo.setMembers(userTeamRelations.stream()
                .filter(relation -> membersUserMap.containsKey(relation.getUserId()))
                .map(relation -> {
                    String userId = relation.getUserId();
                    TeamMember member = new TeamMember();
                    member.setUserId(relation.getUserId());
                    member.setName(membersUserMap.get(userId).getName());
                    member.setTel(membersUserMap.get(userId).getTel());
                    member.setAvatar(membersUserMap.get(userId).getAvatar());
                    member.setManager(relation.isMananger());
                    member.setRoles(JsonUtil.parse(roleRepository.listUserRolesByTeamIdUserId(userId, team.getTeamId()), RoleVO.class));

                    TeamMemberStatus status = new TeamMemberStatus();
                    status.setReceiveOrder(busService.getCourierSettingsById(userId).getReceiveOrder());
                    CourierMissionStatusBO courierMissionStatusBO = missionStatusMap.get(userId);
                    if (courierMissionStatusBO != null) {
                        status.setDelivery(courierMissionStatusBO.getHasMission());
                    }
                    TeamMemberLocationStatusBO statusBO = latestLocationStatusMap.get(userId);
                    if (statusBO != null) {
                        status.setSignTime(statusBO.getTodayFirstSignTime());
                        UserLatestLocationVO latestLocation = new UserLatestLocationVO();
                        latestLocation.setUserId(userId);
                        latestLocation.setUpdateTime(statusBO.getUpdateTime());
                        latestLocation.setAddress(statusBO.getAddress());
                        latestLocation.setAddressDetail(statusBO.getAddressDetail());
                        latestLocation.setLat(statusBO.getLat());
                        latestLocation.setLng(statusBO.getLng());
                        status.setLatestLocation(latestLocation);
                        status.setRestTime(statusBO.getRestTime());
                    }

                    TeamUserStatusVO statusVO = userStatusMap.get(userId);
                    if (statusVO != null) {
                        status.setUiType(statusVO.getStatus());
                        status.setUiValue(statusVO.getTime());
                    }

                    member.setStatus(status);

                    return member;
                }).collect(Collectors.toList()));

        // move Frank to the first
        TeamMember frank = null;
        for (int i = 0; i < vo.getMembers().size(); i++) {
            TeamMember member = vo.getMembers().get(i);
            if (TextUtils.equals(member.getUserId(), "00000000653b909701653c3a49b90007")) {
                frank = member;
                break;
            }
        }
        if (frank != null) {
            vo.getMembers().remove(frank);
            vo.getMembers().add(0, frank);
        }

        // - 团队（ 仓库 ）地址 / 接单区域地址
        TeamExtention teamExtention = teamExtentionRepository.findByTeamId(teamId);
        TeamPosition teamPosition = teamPositionDao.findByTeamId(teamId);
        TeamPositionVO tpVo = new TeamPositionVO();
        tpVo.setAddress(Optional.ofNullable(teamExtention).map(TeamExtention::getFlAdress).orElse(null));
        tpVo.setCountry(Optional.ofNullable(teamExtention).map(TeamExtention::getFlCountry).orElse(null));
        tpVo.setProvince(Optional.ofNullable(teamExtention).map(TeamExtention::getFlProvince).orElse(null));
        tpVo.setCity(Optional.ofNullable(teamExtention).map(TeamExtention::getFlCity).orElse(null));
        tpVo.setDistrict(Optional.ofNullable(teamExtention).map(TeamExtention::getFlDistrict).orElse(null));
        tpVo.setLandMark(Optional.ofNullable(teamExtention).map(TeamExtention::getFlLandMark).orElse(null));
        tpVo.setLat(Optional.ofNullable(teamPosition).map(TeamPosition::getGpsInfo).map(Position::getLat).orElse(null));
        tpVo.setLng(Optional.ofNullable(teamPosition).map(TeamPosition::getGpsInfo).map(Position::getLng).orElse(null));
        vo.setExpressStartTime(Optional.ofNullable(teamExtention).map(TeamExtention::getExpressStartTime).orElse(null));
        vo.setExpressEndTime(Optional.ofNullable(teamExtention).map(TeamExtention::getExpressEndTime).orElse(null));
        vo.setPosition(tpVo);

        // 团队接单区域数
        List<String> allTeamIds = teamRepository.getTeamAndChildTeamIds(team.getParentIds());
        if (!allTeamIds.isEmpty()) {
            List<TeamPosition> teamPositions = teamPositionDao.findByTeamIdsIn(allTeamIds);
            vo.setTeamPositionCount(teamPositions
                    .stream()
                    .filter(position -> position.getGpsInfo() != null && position.getGpsInfo().getLat() != 0f && position.getGpsInfo().getLng() != 0f)
                    .count());
        }
        // project
        vo.setProject(team.getProject());
        // 离职人员数量
        vo.setRemovedMemberCount(teamResignationRepository.distinctUserIdCountByTeamId(teamId));
        // 利润
        vo.setProfit(BigDecimal.ZERO);
        // 团队考勤位置
        TeamAttendanceInfoVO teamAttendanceInfo = attenceApi.getTeamAttendanceInfo(team.getTeamId());
        if (teamAttendanceInfo != null) {
            TeamAttendance ta = new TeamAttendance();
            ta.setAddress(teamAttendanceInfo.getWorkPlace());
            ta.setAddressDetails(teamAttendanceInfo.getWorkPlaceDetail());
            ta.setStartTime(teamAttendanceInfo.getWorkStartTime());
            ta.setLat(teamAttendanceInfo.getWorkLat());
            ta.setLng(teamAttendanceInfo.getWorkLng());
            ta.setIsOpen(teamAttendanceInfo.getIsOpen());
            vo.setTeamAttendance(ta);
        }
        // 优先分配客户数量
        vo.setClientNum(0);
        // 配送员计费规则
        TeamExtention te = teamExtentionRepository.findByTeamId(team.getTeamId());
        vo.setCourierExpressBillingMode(Optional.ofNullable(te)
                .map(TeamExtention::getExpressBillingModeId)
                .flatMap(modeId -> expressBillingModeRepository.findById(modeId))
                .orElse(null));
        // 客户发货计费规则
        vo.setClientPriceRule(null);
        return Result.getSuccess(vo);
    }


    /**
     * 获取团队信息
     *
     * @param teamId 团队id
     * @return d
     */
    public Result getTeam(String teamId) {
        User me = getCurrentUser();
        // - 获取到该团队
        Team team;
        TeamInfoVO teamInfoVO = new TeamInfoVO();
        if (TextUtils.isEmpty(teamId)) {
            team = getOnlyTeam(me.getUserId(), me.getCurrentTeamId());
            teamId = team.getTeamId();
            // 当前用户的家庭住址(仅在teamId为空时做查询返回)
            CourierSettingsVO courierSettings = busService.getCourierSettingsById(me.getUserId());
            if (courierSettings != null) {
                teamInfoVO.setHomeAddress(courierSettings.getHomeAddress());
                teamInfoVO.setHomeLat(courierSettings.getHomeLat());
                teamInfoVO.setHomeLng(courierSettings.getHomeLng());
            }
        } else {
            team = OptionalUtil.checkOptionalAndThrew(teamRepository.findByTeamId(teamId), "error.team.not.found");
        }

        // - 组装team的详细信息返回数据
        teamInfoVO.setAvatar(team.getAvatar());
        teamInfoVO.setTeamId(team.getTeamId());
        teamInfoVO.setName(team.getName());
        teamInfoVO.setMemberCount(getTeamUserNum(teamId));
        teamInfoVO.setProject(team.getProject());

        // - 团队（ 仓库 ）地址
        TeamExtention teamExtention = teamExtentionRepository.findByTeamId(teamId);
        TeamPosition teamPosition = teamPositionDao.findByTeamId(teamId);
        TeamPositionVO tpVo = new TeamPositionVO();
        tpVo.setAddress(Optional.ofNullable(teamExtention).map(TeamExtention::getFlAdress).orElse(null));
        tpVo.setCountry(Optional.ofNullable(teamExtention).map(TeamExtention::getFlCountry).orElse(null));
        tpVo.setProvince(Optional.ofNullable(teamExtention).map(TeamExtention::getFlProvince).orElse(null));
        tpVo.setCity(Optional.ofNullable(teamExtention).map(TeamExtention::getFlCity).orElse(null));
        tpVo.setDistrict(Optional.ofNullable(teamExtention).map(TeamExtention::getFlDistrict).orElse(null));
        tpVo.setLandMark(Optional.ofNullable(teamExtention).map(TeamExtention::getFlLandMark).orElse(null));
        tpVo.setLat(Optional.ofNullable(teamPosition).map(TeamPosition::getGpsInfo).map(Position::getLat).orElse(null));
        tpVo.setLng(Optional.ofNullable(teamPosition).map(TeamPosition::getGpsInfo).map(Position::getLng).orElse(null));
        teamInfoVO.setPosition(tpVo);

        // - 是否为根团队管理员
        UserTeamRelation teamRelation = userTeamRelationRepository.findByTeamIdAndUserId(me.getCurrentTeamId(), me.getUserId());
        teamInfoVO.setRootManager(Optional.ofNullable(teamRelation).map(UserTeamRelation::isMananger).orElse(false));

        // - 能否管理当前查询的团队
        if (teamInfoVO.isRootManager()) {
            // 如果已经是根团队管理员，那么肯定可以管理其子团队
            teamInfoVO.setManageable(true);
        } else {
            // 如果不是根团队管理员，则还需要判断是否能管理此团队
            teamInfoVO.setManageable(userTeamService.checkCanManageTeam(getCurrentUser().getUserId(), teamId));
        }

        logger.info("查询出来的标签为", JsonUtil.parse(roleRepository.listTeamRolesByTeamId(teamId), RoleVO.class));
        // 组装团队标签
        teamInfoVO.setRoles(JsonUtil.parse(roleRepository.listTeamRolesByTeamId(teamId), RoleVO.class));

        // 遍历所有人员 组装成需要的数据
        List<UserTeamRelation> userTeamRelationList = userTeamRelationRepository.findByTeamIdAndOrderByManangerDescIdAsc(teamId);
        List<TeamUserVO> users = new ArrayList<>();
        //1. 找出当前团队的所有人员信息
        //2. 找到每一个人的考勤信息
        //3.
        for (UserTeamRelation relation : userTeamRelationList) {
            // TODO 查询次数优化
            User user = userRepository.findByUserId(relation.getUserId());
            if (user == null) {
                continue;
            }
            TeamUserVO userVO = new TeamUserVO(user.getUserId(), user.getName(), user.getTel(), user.getAvatar(), relation.isMananger());
            // 组装个人标签
            userVO.setRoles(JsonUtil.parse(roleRepository.listUserRolesByTeamIdUserId(user.getUserId(), teamId), RoleVO.class));

            userVO.setAttendance(null);
            users.add(userVO);
        }
        // FIXME doing...
        teamInfoVO.setUsers(users);
        List<Team> childrenTeams = getTeamChildrenStartingWith(team.getParentIds()).stream()
                .filter(t -> !(t.getParentIds().replaceFirst(team.getParentIds() + ",", "").contains(","))).collect(Collectors.toList());
        List<TeamMemberCountVO> teamMemberCountVOS = new ArrayList<>();
        // 遍历所有子团队 组装团队信息和团队人数z
        childrenTeams.forEach(childrenTeam -> {
            TeamMemberCountVO childrenCountVO = new TeamMemberCountVO(childrenTeam.getTeamId(), childrenTeam.getName(), getTeamUserNum(childrenTeam.getTeamId()));
            childrenCountVO.setHasChildrens(teamRepository.countChildrens(childrenTeam.getParentIds() + ",%") > 0);
            teamMemberCountVOS.add(childrenCountVO);
        });
        //子团队按名称排序
        teamInfoVO.setChildren(teamMemberCountVOS.stream().sorted(Comparator.comparing(TeamMemberCountVO::getName)).collect(Collectors.toList()));

        return Result.getSuccess(teamInfoVO);
    }

    /**
     * 修改team名称
     *
     * @param
     * @return
     */
    public Result editTeam(UpdateTeamDTO dto) {
        User me = (User) ServletUtil.getCurrentRequest().getAttribute(CommonConstants.USER_KEY);
        String teamId = dto.getTeamId();
        if (TextUtils.isEmpty(teamId)) {
            return Result.getFail("team id can not empty");
        }
        Team team = getOneTeam(teamId);
        if (team == null) {
            return Result.getFailI18N("error.team.not.exit");
        }
        String oldTeamName = team.getName();

        // 主表
        team.setName(Optional.ofNullable(dto.getTeamName()).orElse(team.getName()));
        team.setRemark(Optional.ofNullable(dto.getTeamName()).orElse(team.getRemark()));
        teamRepository.save(team);

        // 拓展表
        TeamExtention te = teamExtentionRepository.findByTeamId(teamId);
        if (te == null) {
            te = new TeamExtention(teamId);
        }
        te.setExpressBillingModeId(Optional.ofNullable(dto.getExpressBillingModeId()).orElse(te.getExpressBillingModeId()));
        te.setExpressStartTime(Optional.ofNullable(dto.getExpressStartTime()).orElse(te.getExpressStartTime()));
        te.setExpressEndTime(Optional.ofNullable(dto.getExpressEndTime()).orElse(te.getExpressEndTime()));
        teamExtentionRepository.save(te);


        if (!TextUtils.isEmpty(dto.getTeamName())) {
            //修改team名称完成后推送
            Map<String, Object> teamMap = new HashMap<>();
            teamMap.put("teamId", dto.getTeamId());
            teamMap.put("teamName", dto.getTeamName());
            List<String> userIdList = getUserForPush(team);
            String name = me.getName();
            //单纯的推送
            pushMsgService.pushEditTeamName(userIdList, name, oldTeamName, dto.getTeamName(), teamMap);
            //系统通知+内部自带的推送
//            Map<String, String> content = LocaleType.getMessageMap("push.team.name.edit.push", name, oldTeamName, dto.getTeamName());
//            MessageConfig config = MessageConfig.buildConfig(
//                    userIdList,
//                    getUserLanguageMap(),
//                    content,
//                    team.getRootId());
//            windChatApi.sendText(config, content);
        }
        return Result.getSuccess();
    }

    /**
     * 修改team的地址和GPS信息
     *
     * @param teamAddressGpsDTO team的地址和GPS信息
     * @return
     */
    public Result editTeamAddressGps(TeamAddressGpsDTO teamAddressGpsDTO) {
        if (StringUtils.isBlank(teamAddressGpsDTO.getTeamId())) {
            return Result.getFailI18N("error.team.id.null");
        }
        if (StringUtils.isBlank(teamAddressGpsDTO.getAddress())) {
            return Result.getFailI18N("error.parameter.empty");
        }
        Team team = getOneTeam(teamAddressGpsDTO.getTeamId());
        TeamExtention teamExtention = teamExtentionRepository.findByTeamId(teamAddressGpsDTO.getTeamId());
        if (teamExtention == null) {
            teamExtention = new TeamExtention();
            teamExtention.setTeamId(teamAddressGpsDTO.getTeamId());
        }
        teamExtention.setFlAdress(teamAddressGpsDTO.getAddress());
        teamExtention.setFlCountry(teamAddressGpsDTO.getCountry());
        teamExtention.setFlProvince(teamAddressGpsDTO.getProvince());
        teamExtention.setFlCity(teamAddressGpsDTO.getCity());
        teamExtention.setFlDistrict(teamAddressGpsDTO.getDistrict());
        teamExtention.setFlLandMark(teamAddressGpsDTO.getLandMark());

        teamExtentionRepository.save(teamExtention);

        if (teamAddressGpsDTO.getLng() != 0 || teamAddressGpsDTO.getLat() != 0) {
            TeamPosition teamPosition = teamPositionDao.findOne("teamId", teamAddressGpsDTO.getTeamId());
            if (teamPosition == null) {
                teamPosition = new TeamPosition(teamAddressGpsDTO.getTeamId());
            }
            Position position = new Position();
            position.setLng(teamAddressGpsDTO.getLng());
            position.setLat(teamAddressGpsDTO.getLat());
            teamPosition.setGpsInfo(position);
            teamPositionDao.save(teamPosition);
        }

        //修改team地址完成后推送
        User me = (User) ServletUtil.getCurrentRequest().getAttribute(CommonConstants.USER_KEY);

        Map<String, Object> teamMap = new HashMap<>();
        teamMap.put("teamId", teamAddressGpsDTO.getTeamId());
        List<String> userIdList = getUserForPush(team);
        String name = me.getName();
        //单纯的推送
        pushMsgService.pushEditTeamAddress(userIdList, name, team.getName(), teamAddressGpsDTO.getAddress(), teamMap);

        //系统通知+内部自带的推送
//        Map<String, String> content = LocaleType.getMessageMap("push.team.address.edit.push", name, team.getName(), teamAddressGpsDTO.getAddress());
//        MessageConfig config = MessageConfig.buildConfig(userIdList, getUserLanguageMap(), content, team.getRootId());
//        windChatApi.sendText(config, content);
        return Result.getSuccess();
    }

    /**
     * 合并同一父team下同一层级的多个team
     *
     * @param teamIdList 要合并的teamId列表
     */
    public Result mergeTeams(List<String> teamIdList, String name, String project) {

        //1.判断team的数量，若<2，则无需合并
        if (teamIdList.size() < 2) {
            return Result.getFail("the number of teams is less than 2,don't need to merge");
        }

        //2.新建team

        //获取父部门的parentIds
        String parentTeamParentIds = getParentTeamParentIds(getOneTeam(teamIdList.get(0)).getParentIds());
        Team parenTeam = teamRepository.findByParentIds(parentTeamParentIds);


        //先校验要创建的子团队在当前根团队下是否重名
        User me = (User) ServletUtil.getCurrentRequest().getAttribute(CommonConstants.USER_KEY);
        Team root = getOneTeam(me.getCurrentTeamId());
        List<Team> teams = getTeamChildrensAndSelf(root.getParentIds(), root.getTeamId());
        if (teams.stream().anyMatch(t -> TextUtils.equals(name, t.getName()))) {
            return Result.getFailI18N("error.already.exist.name");
        }

        //调用统一创建team方法创建team
        Team newTeam = createChildTeam(name, parenTeam.getType(), "", parenTeam.getTeamId(), project);

        List<Team> teamList = teamRepository.findByTeamIdIn(teamIdList);

        //要合并的多个团队的名称
        String teamListName = teamRepository.findTeamNameByTeamIdList(teamIdList);


        //新team的人员是要合并的多个team的人员总和去重
        HashSet<String> userSet = new HashSet<>();

        //获取要被合并的多个team的所有子孙team,以便后面修改这些team的parentIds
        List<Team> allChildrensTeams = new ArrayList<>();


        for (int i = 0, length = teamList.size(); i < length; i++) {

            List<Team> getTeamChildrens = getTeamChildrenStartingWith(teamList.get(i).getParentIds());

            allChildrensTeams.addAll(modifyTeamParentIds(getTeamChildrens, teamList.get(i).getParentIds(), newTeam.getParentIds()));

        }

        //多个team的人员去重合并到新的team中
        HashSet<String> userIdHashSeta = teamRepository.findUserByTeamIdList(teamIdList);
        userTeamRelationRepository.saveAll(mergeUserToTeam(userIdHashSeta, false, newTeam.getTeamId()));

        //修改多个Team的子孙的parentIds为新team的parentIds
        teamRepository.saveAll(allChildrensTeams);

        //删除这多个team的role标签
        List<TeamRoleRelation> teamRoleRelationList = teamRoleRelationRepository.findByTeamIdIn(teamIdList);
        teamRoleRelationRepository.deleteAll(teamRoleRelationList);

        //删除这多个team中的所有用户
        List<UserTeamRelation> list = userTeamRelationRepository.findByTeamIdIn(teamIdList);
        userTeamRelationRepository.deleteAll(list);

        //todo 删除多个team的扩展表
        List<TeamExtention> teamExtentionList = teamExtentionRepository.findByTeamIdIn(teamIdList);
        teamExtentionRepository.deleteAll(teamExtentionList);

        //todo 删除多个team的position表
        teamPositionDao.deleteByQuery(teamPositionDao.createQuery().field("teamId").in(teamIdList));

        //删除同一层级被合并的多个team
        teamRepository.deleteAll(teamList);

        int memberCount = getTeamUserNum(newTeam.getTeamId());

        boolean hasChildrens = teamRepository.countChildrens(newTeam.getParentIds() + ",%") > 0;

        //给本组（包含子组）所有成员+直线组长（爸爸爷爷祖爷爷）发送推送消息
        List<String> userIdList = getUserForPush(newTeam);

        Map<String, Object> teamMap = new HashMap<>();
        teamMap.put("teamIdList", teamIdList);
        teamMap.put("newTeamId", newTeam.getTeamId());
        teamMap.put("newTeamName", newTeam.getName());
        String nameOfUser = me.getName();
        //单纯的推送
        pushMsgService.pushMergeTeam(userIdList, nameOfUser, teamListName, newTeam.getName(), teamMap);

        //系统通知+内部自带的推送
//        Map<String, String> content = LocaleType.getMessageMap("push.team.merge.push", nameOfUser, teamListName, newTeam.getName());
//        MessageConfig config = MessageConfig.buildConfig(userIdList, getUserLanguageMap(), content, root.getTeamId());
//        windChatApi.sendText(config, content);

        return Result.getSuccess(new MegerTeamDTO(newTeam.getTeamId(), newTeam.getName(), newTeam.getAvatar(), memberCount, hasChildrens));
    }


    /**
     * 批量删除同一父team下同一层级的多个team
     *
     * @param teamIdList
     */
    public Result batchDeleteTeams(List<String> teamIdList) {


        //根据teamIdList找到teamList
        List<Team> teamList = teamRepository.findByTeamIdIn(teamIdList);

        //找到要删除的team的直线管理员
        List<String> parentTeamsList = getParentTeamsByTeamId(teamIdList.get(0));

        User me = (User) ServletUtil.getCurrentRequest().getAttribute(CommonConstants.USER_KEY);

        //获取要删除的teamIdList的name
        String teamIdListName = teamRepository.findTeamNameByTeamIdList(teamIdList);

        //
        List<Team> teams = getTeamsByTeamIds(teamList);
        teams.addAll(teamList);

        //获取自身及所有子孙的teamIdList
        List<String> allTeamIdList = getTeamIdListByTeamList(teams);

        //找到所有team下的所有用户关系
        List<UserTeamRelation> userTeamRelationList = userTeamRelationRepository.findByTeamIdIn(allTeamIdList);

        //找到所有team的所有标签
        List<TeamRoleRelation> teamRoleRelationList = teamRoleRelationRepository.findByTeamIdIn(allTeamIdList);

        //找到所有team的teamExtention
        List<TeamExtention> teamExtentionList = teamExtentionRepository.findByTeamIdIn(allTeamIdList);


        //若team列表下存在用户,则删除失败
        if (userTeamRelationList.size() > 0) {
            return Result.getFailI18N("error.cannot.delete.team");
        } else {
            //删除Team的所有标签
            teamRoleRelationRepository.deleteAll(teamRoleRelationList);

            // todo 删除所有team的position表
            teamPositionDao.deleteByQuery(teamPositionDao.createQuery().field("teamId").in(allTeamIdList));

            //删除所有的team
            teamRepository.deleteAll(teams);
            //删除所有的teamExtention
            teamExtentionRepository.deleteAll(teamExtentionList);


            List<String> userIdList = new ArrayList<>();
            //添加所有直线组长
            userIdList.addAll(userTeamRelationRepository.findManagerIdListByTeamIdList(parentTeamsList));
            Map<String, Object> teamMap = new HashMap<>();
            teamMap.put("teamIdList", teamIdList);
            String name = me.getName();
            pushMsgService.pushDeleteTeam(userIdList, name, teamIdListName, teamMap);

        }
        return Result.getSuccess();
    }

    /**
     * 批量移动同一父team下同一层级的多个team到某个team下
     *
     * @param teamIdList   待移动的团队
     * @param targetTeamId 目标team
     */
    @Transactional(rollbackFor = Exception.class)
    public Result batchMoveTeams(List<String> teamIdList, String targetTeamId) {

        //获取目标team的parentIds
        Team targetTeam = teamRepository.findByTeamId(targetTeamId);
        if (targetTeam == null) {
            return Result.getFail("target team not exit");
        }

        List<Team> teamList = teamRepository.findByTeamIdIn(teamIdList);
        if (teamIdList.isEmpty()) {
            return Result.getSuccess();
        }
        // 校验 1. teamIdList 内的所有团队必须是兄弟团队，即其直接父团队必须是同一个
        if (!isBrotherTeams(teamList)) {
            return Result.getFailI18N("error.operation.failed", "teams not same level");
        }
        // 所有待移动的分组树
        List<Team> teamsTree = getTeamsByTeamIds(teamList);
        teamsTree.addAll(teamList);

        // 校验 2. targetTeamId 不能是 teamIdList 的子团队，防止断层
        for (Team team : teamsTree) {
            if (TextUtils.equals(team.getTeamId(), targetTeamId)) {
                return Result.getFailI18N("error.operation.failed", "target team not allowed");
            }
        }

        String targetParentIds = targetTeam.getParentIds();
        //获取这多个team的父team的parentIds
        String parentParenIds = getParentTeamParentIds(teamList.get(0).getParentIds());
        for (int i = 0, length = teamsTree.size(); i < length; i++) {
            String oldParentIds = teamsTree.get(i).getParentIds();
            //更新team列表的parentIds
            teamsTree.get(i).setParentIds(targetParentIds + oldParentIds.substring(parentParenIds.length()));
        }
        teamRepository.saveAll(teamsTree);
        return Result.getSuccess();
    }

    //在redis中设置计数器，每新增一个team加1,值作为每个team的流水号
    public String getSerialNumber() {
        long teamKey = stringRedisTemplate.opsForValue().increment(CommonConstants.Redis.TEAM_KEY, 1);
        return String.valueOf(teamKey);
    }


    //根据一个team的parentIds获取其父亲节点的parentIds，若该team为根team,则返回""
    public String getParentTeamParentIds(String parentIds) {
        String result = "";
        String[] idsArray = parentIds.split(",");
        if (idsArray.length < 2) {
            return result;
        } else {
            String parentSerialNumber = idsArray[idsArray.length - 1];
            int plength = parentSerialNumber.length() + 1;
            result = parentIds.substring(0, parentIds.length() - plength);
            return result;
        }
    }


    public List<String> getChildTeamsParentIds() {
        return null;
    }


    /**
     * 将要合并的多个team的用户去重合并到新team中，默认所有用户isManege值为false
     *
     * @param userIdSet 去重的用户集合
     * @param isManager 集合用户的角色(是否为管理员)
     * @param teamId    合并后的新teamId
     */
    public List<UserTeamRelation> mergeUserToTeam(HashSet<String> userIdSet, boolean isManager, String teamId) {
        List<UserTeamRelation> userTeamRelationList = new ArrayList<>();
        UserTeamRelation userTeamRelation = null;
        for (String userId : userIdSet) {
            userTeamRelation = new UserTeamRelation();
            userTeamRelation.setMananger(isManager);
            userTeamRelation.setTeamId(teamId);
            userTeamRelation.setUserId(userId);
            userTeamRelationList.add(userTeamRelation);
        }
        return userTeamRelationList;
    }

    /**
     * 根据team的id列表获取所有的子孙team列表
     * 根据team列表获取所有的子孙team列表
     *
     * @return
     */
    public List<Team> getTeamsByTeamIds(List<Team> teamList) {
        List<Team> teams = new ArrayList<>();
        //先找到每个team下的所有子孙team
        for (int i = 0, length = teamList.size(); i < length; i++) {
            teams.addAll(getTeamChildrenStartingWith(teamList.get(i).getParentIds()));
        }
        return teams;
    }

    public List<String> getTeamIdListByTeamList(List<Team> teamList) {
        List<String> teamIdList = new ArrayList<>();
        for (Team team : teamList) {
            teamIdList.add(team.getTeamId());

        }
        return teamIdList;

    }

    /**
     * 将这些所有的team的parentIds更新为targetParentId
     * 即修改这些team的父亲节点的parentIds
     *
     * @param teams           team列表
     * @param parentParentIds 需要被替换掉的parentIds
     * @param targetParentIds 目标team的parentIds
     * @return
     */
    public List<Team> modifyTeamParentIds(List<Team> teams, String parentParentIds, String targetParentIds) {
        for (int i = 0, length = teams.size(); i < length; i++) {
            String oldParentIds = teams.get(i).getParentIds();
            //更新team的parentIds
            teams.get(i).setParentIds(targetParentIds + oldParentIds.substring(parentParentIds.length(), oldParentIds.length()));
        }
        return teams;
    }

    /**
     * 拉人进团队
     */
    public Result inviteMember(InviteDTO inviteDTO) {
        OptionalUtil.checkOptionalAndThrew(teamRepository.findByTeamId(inviteDTO.getTeamId()), "error.team");

        String userId = inviteDTO.getUserId();
        String teamId = inviteDTO.getTeamId();
        String rootTeamId = getCurrentUser().getCurrentTeamId();

        // 如果用户已加入其他团队 则返回
        if (getOnlyTeam(userId, rootTeamId) != null) {
            return Result.getFailI18N("error.already.joined.other.team");
        }

        //把用户加入团队
        userTeamRelationService.setTeamManager(teamId, userId, false);

        // 添加操作记录
        teamMemberSummaryDao.addNewMember(userId, teamId, rootTeamId);

        return Result.getSuccess();
    }

    /**
     * 获取所有子团队,不包含自己
     */
    public List<Team> getTeamChildrenStartingWith(String parentIds) {
        String param = parentIds + ",";
        return teamRepository.findByParentIdsStartingWith(param);
    }

    /**
     * 获取所有子团队,包含自己
     * TIPS 此处加逗号是为了区分有类似于 1,2  10,21 两个根团队 一个根团队的redisNo 为 1  一个为 10  但两个都是以 1开头
     */
    public List<Team> getTeamChildrensAndSelf(String parentIds, String teamId) {
        String param = parentIds + ",";
        return teamRepository.findByParentIdsStartingWithOrTeamId(param, teamId);
    }

    public List<Team> getTeamAndAllChildTeam(Team team) {
        return teamRepository.getTeamAndAllChildTeam(team.getParentIds());
    }

    /**
     * 根据团队Id获取该团队及所有子团队的人数
     * */


    /**
     * 获取团队人数
     *
     * @param teamId 团队 id
     * @return 人数
     */
    public int getTeamUserNum(String teamId) {
        Team team = teamRepository.findByTeamId(teamId);
        if (team == null) {
            return 0;
        }
        return teamRepository.countUserByTeam(team.getParentIds() + ",%", teamId);
    }

    /**
     * 根据teamId获取team的所有父亲，爷爷..祖宗teamId
     *
     * @param teamId
     * @return
     */
    public List<String> getParentTeamsByTeamId(String teamId) {
        List<String> teamIdList = new ArrayList<>();
        List<Long> teamIdNoList = getParentTeamIdNosByTeamId(teamId);
        if (teamIdNoList.size() > 0) {
            teamIdList = teamRepository.getTeamIdListByTeamIdNosIn(teamIdNoList);
        }
        return teamIdList;
    }

    /**
     * 根据teamId获取该team的父亲,爷爷....祖宗的teamIdNo
     *
     * @return
     */
    public List<Long> getParentTeamIdNosByTeamId(String teamId) {
        Team team = getOneTeam(teamId);
        String[] teamIdNos = team.getParentIds().split(",");

        //过滤掉该team本身自己的teamIdNo
        int length = teamIdNos.length - 1;
        List<Long> teamIdNoList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            teamIdNoList.add(Long.valueOf(teamIdNos[i]));
        }
        return teamIdNoList;
    }


    /**
     * 获取本组（包含子组）所有成员+直线组长即管理员（爸爸爷爷祖爷爷....）
     *
     * @return
     */
    public List<String> getUserForPush(Team team) {
        List<String> userIdList = new ArrayList<>();

        //获取爸爸,爷爷,祖爷爷...组列表
        List<String> parentTeamsList = getParentTeamsByTeamId(team.getTeamId());
        //添加所有直线组长
        if (parentTeamsList.size() > 0) {
            userIdList.addAll(userTeamRelationRepository.findManagerIdListByTeamIdList(parentTeamsList));
        }

        //添加所有本组（包含子组）所有成员
        List<FlCourierUserVO> users = JsonUtil.parse(teamRepository.findAllUserByTeamId(team.getTeamId(), team.getParentIds()), FlCourierUserVO.class);
        List<String> userIds = users.stream().map(FlCourierUserVO::getUserId).collect(Collectors.toList());
        userIdList.addAll(userIds);

        return userIdList;
    }


    //获取团队的子团队列表
    public Result getTeamNextChildren(String teamId) {
        return Result.getSuccess(teamRepository.getChildrenTeamsJustOne(teamId));
    }

    /**
     * @param teamId 团队id 如果teamId为空 teamId为用户当前所在团队
     *               获取该团队父团队树以及下一级的所有子团队；获取该团队下的所有人
     *               获取该团队下的所有人
     */
    public Result getTeamTreeInfo(String teamId) {

        String root = getCurrentRootTeam().getRootId();

        if (StringUtils.isEmpty(teamId)) {

            teamId = getOnlyTeam(getCurrentUser().getUserId(), root).getTeamId();
            getTeamTreeInfo(teamId);
        }

        Team team = teamRepository.findByTeamId(teamId);

        TeamTreeInfoVO result = new TeamTreeInfoVO();
        result.setTeamId(teamId);
        result.setName(team.getName());
        //children
        List<TeamTreeTeamVO> children = teamRepository.getChildrenTeamsJustOne(teamId).stream().map(t ->
                new TeamTreeTeamVO(t.getTeamId(), t.getName())).collect(Collectors.toList());

        result.setChildren(children);

        //parents
        if (!TextUtils.equals(team.getRootId(), teamId)) {
            String[] temp = team.getParentIds().split(String.format(",%s", team.getTeamIdNo()));
            List<Long> parentTreeIds = Arrays.asList(temp[0].split(",")).stream().map(Long::valueOf).collect(Collectors.toList());

            List<TeamTreeTeamVO> parents = teamRepository.findByTeamIdNoIn(parentTreeIds).stream().map(t ->
                    new TeamTreeTeamVO(t.getTeamId(), t.getName())).collect(Collectors.toList());

            result.setParents(parents);
        }

        //users
        List<String> userIds = userTeamRelationRepository.findByTeamId(teamId).stream().map(UserTeamRelation::getUserId).collect(Collectors.toList());
        List<TeamTreeUserVO> users = userRepository.findByUserIdIn(userIds).stream().map(u ->
                new TeamTreeUserVO(u.getUserId(), u.getName())).collect(Collectors.toList());

        result.setUsers(users);

        return Result.getSuccess(result);

    }

    /**
     * 获取非风先生外的根团队
     */
    public Result rootNotWind() {
        List<Team> roots = teamRepository.getAllRoots();
        List<Team> result = roots.stream().filter(t -> !StringUtils.equals(t.getRootId(), CommonConstants.MRWIND_ID)).collect(Collectors.toList());
        return Result.getSuccess(result);
    }

    /**
     * 获取根团队下所有用户和根团队信息
     */
    public Result shopDetail(String teamId) {
        Team root = teamRepository.findByTeamId(teamId);
        if (!StringUtils.equals(root.getTeamId(), root.getRootId())) {
            return Result.getFail("error.not.root");
        }

        List<String> uIds = userTeamRelationRepository.findByTeamId(teamId).stream().map(UserTeamRelation::getUserId).collect(Collectors.toList());

        List<User> users = userRepository.findByUserIdIn(uIds);

        JSONObject result = new JSONObject();
        result.put("team", root);
        result.put("users", users);
        return Result.getSuccess(result);
    }


    /**
     * 通过teamId查找上一层级的所有leader
     */
    public Result teamTopLeader(String teamId) {
        Team t = teamRepository.findByTeamId(teamId);
        String[] pIds = t.getParentIds().split(",");
        String pId;
        if (pIds.length > 1) {
            pId = pIds[pIds.length - 2];
        } else {
            pId = pIds[pIds.length - 1];
        }
        Team parent = teamRepository.findByTeamIdNo(Long.valueOf(pId).longValue());

        List<String> uIds = userTeamRelationRepository.findByTeamIdAndMananger(t.getTeamId(), true).stream().map(e -> e.getUserId()).collect(Collectors.toList());
        List<String> puIds = userTeamRelationRepository.findByTeamIdAndMananger(parent.getTeamId(), true).stream().map(e -> e.getUserId()).collect(Collectors.toList());
        puIds.forEach(e -> {
            uIds.add(e);
        });
        List<User> users = userRepository.findByUserIdIn(uIds);
        return Result.getSuccess(users);
    }

    /**
     * 通过teamid找到该层及以下的team中的所有人
     */
    public List<User> allUser(String teamId) {
        Team team = teamRepository.findByTeamId(teamId);
        List<String> children = teamRepository.getTeamAndChildTeam(team.getParentIds()).stream().map(t -> t.getTeamId()).collect(Collectors.toList());
        List<String> userIds = userTeamRelationRepository.findByTeamIdIn(children).stream().map(t -> t.getUserId()).collect(Collectors.toList());
        List<User> users = userRepository.findByUserIdIn(userIds);
        return users;
    }

    /**
     * 获取该团队下所有已离职的人员信息
     */
    public Result getLeavedUserInfoByTeamId(String teamId) {
        return Result.getSuccess(teamResignationRepository.findLeavedUserInfoByTeamId(teamId));
    }

    /**
     * 传入一个团队集合，假设称作这些团队为"标记团队"，返回每个"标记团队"的所有下属人员（包含下属团队）
     * 返回值: key 为团队 parentIds，value 是对应的成员id集合
     */
    public HashMap<String, List<String>> getFlagUsersMapByFlagTeams(List<Team> flagTeams) {
        if (flagTeams.isEmpty()) {
            return new HashMap<>();
        }
        // 所有"标记团队"的 id
        List<String> flagTeamIds = flagTeams.stream().map(Team::getTeamId).collect(Collectors.toList());
        // 根据这些"标记团队"，找到所有顶层"标记团队"
        List<Team> topFlagTeams = new ArrayList<>();
        flagTeams.sort(Comparator.comparingInt(o -> o.getParentIds().split(",").length));
        for (Team flagTeam : flagTeams) {
            boolean exist = false;
            for (Team topFlagTeam : topFlagTeams) {
                if (flagTeam.getParentIds().startsWith(topFlagTeam.getParentIds() + ",")) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                topFlagTeams.add(flagTeam);
            }
        }
        HashMap<String, List<String>> resultMap = new HashMap<>();
        for (Team topFlagTeam : topFlagTeams) {
            /*
              1. 找到顶层"标记团队"下所有的团队(包括自己)
              2. 分离出该顶层"标记团队"下的所有的"标记团队"和所有的"非标记团队"
              3. 生成所有"标记团队"的 HashMap<teamParentIds,teamMemberIds> flagTeamUserIdsMap
              4. 生成所有"非标记团队"的 HashMap<teamParentIds,teamMemberIds> nonFlagTeamUserIdsMap
              5. 遍历 nonFlagTeamUserIdsMap, 每个"非标记团队"都能上溯到一个"标记团队"，将其人员合并到这个"标记团队"
             */
            // 1. 找到顶层"标记团队"下所有的团队
            List<Team> teams = teamRepository.getTeamAndChildTeam(topFlagTeam.getParentIds());
            // 2.  分离出该顶层"标记团队"下的所有的"标记团队"和所有的"非标记团队"
            List<Team> flags = new ArrayList<>();
            List<Team> nonFlags = new ArrayList<>();
            for (Team team : teams) {
                if (flagTeamIds.contains(team.getTeamId())) {
                    flags.add(team);
                } else {
                    nonFlags.add(team);
                }
            }
            // 3. 生成所有"标记团队"的 HashMap<teamParentIds,teamMemberIds> flagTeamUserIdsMap
            HashMap<String, List<String>> flagTeamUserIdsMap = new HashMap<>();
            for (Team flag : flags) {
                flagTeamUserIdsMap.put(flag.getParentIds(), userTeamRelationRepository.findByTeamId(flag.getTeamId()).stream().map(UserTeamRelation::getUserId).collect(Collectors.toList()));
            }
            // 4. 生成所有"非标记团队"的 HashMap<teamParentIds,teamMemberIds> nonFlagTeamUserIdsMap
            HashMap<String, List<String>> nonFlagTeamUserIdsMap = new HashMap<>();
            for (Team nonFlag : nonFlags) {
                nonFlagTeamUserIdsMap.put(nonFlag.getParentIds(), userTeamRelationRepository.findByTeamId(nonFlag.getTeamId()).stream().map(UserTeamRelation::getUserId).collect(Collectors.toList()));
            }
            // 5. 遍历 nonFlagTeamUserIdsMap, 每个"非标记团队"都能上溯到一个"标记团队"，将其人员合并到这个"标记团队"
            for (Map.Entry<String, List<String>> entry : nonFlagTeamUserIdsMap.entrySet()) {
                String parentIds = entry.getKey();
                while (parentIds != null) {
                    if (flagTeamUserIdsMap.containsKey(parentIds)) {
                        flagTeamUserIdsMap.get(parentIds).addAll(entry.getValue());
                        break;
                    }
                    parentIds = getLastTeamParentIds(parentIds);
                }
            }
            resultMap.putAll(flagTeamUserIdsMap);
        }
        return resultMap;
    }

    private String getLastTeamParentIds(String parentIds) {
        if (parentIds.contains(",")) {
            parentIds = parentIds.substring(0, parentIds.lastIndexOf(","));
        } else {
            parentIds = null;
        }
        return parentIds;
    }

    /**
     * 获取一个团队下所有人的 id（包括子团队）
     */
    public Result getTeamAllUserIds(String teamId) {
        List<String> userIds = new ArrayList<>();
        Team team = teamRepository.findByTeamId(teamId);
        if (team == null) {
            return Result.getSuccess(userIds);
        }
        userIds = userRepository.getAllUsersByTeam(team.getTeamId(), team.getParentIds()).stream().map(User::getUserId).collect(Collectors.toList());
        return Result.getSuccess(userIds);
    }

    /**
     * 获取一个团队下所有人（包括子团队）
     */
    public Result getTeamAllUser(String teamId) {
        List<User> users = new ArrayList<>();
        Team team = teamRepository.findByTeamId(teamId);
        if (team == null) {
            return Result.getSuccess(users);
        }
        users = userRepository.getAllUsersByTeam(team.getTeamId(), team.getParentIds());
        return Result.getSuccess(users);
    }

    /**
     * 获取一个用户所有的 leaders（最大层级内）
     *
     * @param userId                           用户 id
     * @param rootTeamId                       根团队 id
     * @param maxLevel                         向上寻找的最大层级，-1 代表所有层级
     * @param includeCurrentLevelWhenIsManager 当自己为管理员时，是否将当前自己所在分组的管理员算进去
     */
    public List<User> getUserAllLeaders(String userId, String rootTeamId, int maxLevel, boolean includeCurrentLevelWhenIsManager) {
        List<User> leaders = new ArrayList<>();
        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(rootTeamId)) {
            return leaders;
        }
        if (maxLevel < 0) {
            maxLevel = Integer.MAX_VALUE;
        }
        Team onlyTeam = getOnlyTeam(userId, rootTeamId);
        if (onlyTeam == null) {
            return leaders;
        }
        boolean isManager = userTeamRelationService.isManager(userId, onlyTeam.getTeamId());
        String[] split = onlyTeam.getParentIds().split(",");
        int currentLevel = 0;
        for (int i = split.length - 1; i >= 0; i--) {
            if (++currentLevel > maxLevel) {
                break;
            }
            if (currentLevel == 1 && isManager && !includeCurrentLevelWhenIsManager) {
                continue;
            }
            leaders.addAll(userRepository.findTeamManagerByTeamNo(split[i]));
        }
        // 过滤掉自己
        return leaders.stream().filter(user -> !TextUtils.equals(user.getUserId(), userId)).collect(Collectors.toList());
    }

    /**
     * 获取一个团队 leaders（可向上找）
     *
     * @param teamId   teamId
     * @param maxLevel 向上寻找的最大层级，-1 代表所有层级
     */
    public List<User> getTeamLeaders(String teamId, int maxLevel) {
        List<User> leaders = new ArrayList<>();
        if (TextUtils.isEmpty(teamId)) {
            return leaders;
        }
        if (maxLevel < 0) {
            maxLevel = Integer.MAX_VALUE;
        }
        Team team = teamRepository.findByTeamId(teamId);
        if (team == null) {
            return leaders;
        }
        String[] split = team.getParentIds().split(",");
        int currentLevel = 0;
        for (int i = split.length - 1; i >= 0; i--) {
            if (++currentLevel > maxLevel) {
                break;
            }
            leaders.addAll(userRepository.findTeamManagerByTeamNo(split[i]));
        }
        return leaders;
    }

    public Result getTeamManagerListByTeamId(String teamId) {
        List<User> userList = new ArrayList<>();
        Team team = getOneTeam(teamId);
        if (team == null) {
            return Result.getFail("error.team.not.exit");
        }
        userList = getTeamManagersByTeamId(teamId);
        return Result.getSuccess(userList);
    }

    /**
     * 找出某个用户在某个根团队下的逐级向上的所属团队
     *
     * @param userId
     * @param rootTeamId
     * @return
     */

    public Result getParentTeamsByUserId(String userId, String rootTeamId) {
        List<Team> teamList = new ArrayList<>();
        Team currentTeam = getOnlyTeam(userId, rootTeamId);
        if (currentTeam == null) {
            return Result.getSuccess(teamList);
        }
        String[] teamIdNos = currentTeam.getParentIds().split(",");
        for (int i = teamIdNos.length - 1; i >= 0; i--) {
            Team t = teamRepository.findByTeamIdNo(Long.valueOf(teamIdNos[i]));
            teamList.add(t);
        }
        return Result.getSuccess(teamList);
    }

    /**
     * 根据teamId获取该团队的所有上级团队列表
     *
     * @param teamId
     * @return
     */
    public List<Team> getParentTeamByTeamId(String teamId) {
        List<Team> teamList = new ArrayList<>();
        Team team = getOneTeam(teamId);
        if (team == null) {
            return teamList;
        }
        String[] teamIdNos = team.getParentIds().split(",");
        for (int i = teamIdNos.length - 1; i >= 0; i--) {
            Team t = teamRepository.findByTeamIdNo(Long.valueOf(teamIdNos[i]));
            teamList.add(t);
        }
        return teamList;
    }


    /**
     * 判断集合内的团队是否为兄弟团队
     */
    public boolean isBrotherTeams(List<Team> teams) {
        if (teams == null || teams.isEmpty()) {
            return false;
        }
        try {
            boolean isBrotherTeams = true;
            Team firstTeam = teams.get(0);
            String firstTeamParentTeamParentIds = firstTeam.getParentIds().substring(0, firstTeam.getParentIds().lastIndexOf(","));
            for (Team team : teams) {
                String teamParentTeamParentIds = team.getParentIds().substring(0, team.getParentIds().lastIndexOf(","));
                if (!TextUtils.equals(teamParentTeamParentIds, firstTeamParentTeamParentIds)) {
                    isBrotherTeams = false;
                    break;
                }
            }
            return isBrotherTeams;
        } catch (Exception e) {
            // 出现异常，传入了根团队，直接返回 false
            return false;
        }
    }

    /**
     * 检查两个用户是否在指定的层级之间
     *
     * @param memberId          下属 id
     * @param leaderId          leader id
     * @param effectiveLevel    生效层级
     * @param notificationLevel 通知层级
     * @return 在返回 true，反之 false
     */
    public WindChatLevelVO checkLevel(String memberId, String leaderId, int effectiveLevel, int notificationLevel) {
        try {
            WindChatLevelVO vo = new WindChatLevelVO();

            Team memberTeam = getOnlyTeam(memberId, CommonConstants.MRWIND_ID);
            if (memberTeam == null) {
                return vo;
            }
            boolean memberIsLeader = Optional.ofNullable(userTeamRelationRepository.findByTeamIdAndUserId(memberTeam.getTeamId(), memberId))
                    .map(UserTeamRelation::isMananger).orElse(false);
            vo.setMemberIsManager(memberIsLeader);

            Team leaderTeam = null;
            if (!TextUtils.isEmpty(leaderId)) {
                leaderTeam = getOnlyTeam(leaderId, CommonConstants.MRWIND_ID);
                if (leaderTeam == null) {
                    return vo;
                }
                boolean leaderIsLeader = Optional.ofNullable(userTeamRelationRepository.findByTeamIdAndUserId(leaderTeam.getTeamId(), leaderId))
                        .map(UserTeamRelation::isMananger).orElse(false);
                if (!leaderIsLeader) {
                    return vo;
                }
            }

            String memberTeamParentIds = memberTeam.getParentIds();

            // member 和 leader 相差的层级数，没传 leaderId 即 leaderTeam == null 则不处理
            int intervalLevel = 0;
            if (leaderTeam != null) {
                String leaderTeamParentIds = leaderTeam.getParentIds();
                if (!TextUtils.equals(memberTeamParentIds, leaderTeamParentIds) && !memberTeamParentIds.startsWith(leaderTeamParentIds + ",")) {
                    // 不是一条团队线上的
                    return vo;
                }
                intervalLevel = memberTeamParentIds.split(",").length - leaderTeamParentIds.split(",").length;
                vo.setValid(intervalLevel + 1 >= effectiveLevel || leaderTeamParentIds.split(",").length == 1);
            }

            String[] split = memberTeamParentIds.split(",");
            int loopLevel = 1;
            for (int i = split.length - 1; i >= 0; i--) {
                if (leaderTeam == null || loopLevel <= intervalLevel + 1) {
                    // 没传 leaderId 或者当前来到了 leader 层及一下层 放进 down 里面
                    vo.getDown().addAll(
                            userRepository.findTeamManagerByTeamNo(split[i])
                                    .stream()
                                    .filter(user -> !TextUtils.equals(user.getUserId(), leaderId) && !TextUtils.equals(user.getUserId(), memberId))
                                    .map(user -> {
                                        BasicUserVO wu = new BasicUserVO();
                                        wu.setId(user.getUserId());
                                        wu.setName(user.getName());
                                        wu.setTel(user.getTel());
                                        wu.setAvatar(user.getAvatar());
                                        return wu;
                                    }).collect(Collectors.toList())
                    );
                } else {
                    // 当前来到了 leader 之上的层级
                    vo.getUp().addAll(
                            userRepository.findTeamManagerByTeamNo(split[i])
                                    .stream()
                                    .map(user -> {
                                        BasicUserVO wu = new BasicUserVO();
                                        wu.setId(user.getUserId());
                                        wu.setName(user.getName());
                                        wu.setTel(user.getTel());
                                        wu.setAvatar(user.getAvatar());
                                        return wu;
                                    }).collect(Collectors.toList())
                    );
                }
                if (loopLevel++ == notificationLevel) {
                    // 执行完通知层级，退出
                    break;
                }
            }
            return vo;
        } catch (Exception e) {
            return new WindChatLevelVO();
        }
    }

    /**
     * [无 TOKEN 接口]获取一个人当前所在分组成员以及指定多少层下的人员
     *
     * @param userId      用户 id
     * @param rootTeamId  根团队 id
     * @param upLevel     从当前层到目标层数，例如 1 代表当前层，2 代表当前层以及下一层
     * @param includeSelf 是否包括自己(userId), true 包括, false 排除
     */
    public List<User> getTeamUserAndUpByLevel(String userId, String rootTeamId, int upLevel, boolean includeSelf) {
        List<User> users = new ArrayList<>();
        Team onlyTeam = getOnlyTeam(userId, rootTeamId);
        if (onlyTeam == null) {
            return users;
        }

        List<String> teamIds = getTeamAndAllChildTeam(onlyTeam).stream().filter(team -> {
            int onlyTeamParentIdsLength = onlyTeam.getParentIds().split(",").length;
            return team.getParentIds().split(",").length - onlyTeamParentIdsLength < upLevel;
        }).map(Team::getTeamId).collect(Collectors.toList());

        if (teamIds.isEmpty()) {
            return users;
        }

        users = userRepository.getUserByTeamIdIn(teamIds)
                .stream()
                .filter(user -> includeSelf || !TextUtils.equals(userId, user.getUserId()))
                .collect(Collectors.toList());

        return users;
    }

    /**
     * 获取团队工作资料
     */
    public List<TeamWorkProfileVO> getTeamAllMemberProfile(String teamId) {
        Team team = getOneTeam(teamId);
        List<TeamWorkProfileVO> vos = new ArrayList<>();
        if (team == null) {
            return vos;
        }
        List<User> users = findTypeUserByTeamId(teamId, String.valueOf(TeamType.STORE));
        if (users.isEmpty()) {
            return vos;
        }
        List<String> userIds = users.stream().map(User::getUserId).collect(Collectors.toList());
        Map<String, CourierSettings> settingsMap = CollectionUtil.takeFieldsToMap(courierSettingsDao.findByUserIdIn(userIds), "userId");
        Map<String, Integer> receiveOrderStatusMap = busService.getCourierReceiveOrderStatus(userIds);
        Map<String, CourierMissionStatusBO> hasMissionMap = CollectionUtil.takeFieldsToMap(windForceApi.getCourierMissionStatus(userIds), "userId");

        for (User user : users) {
            TeamWorkProfileVO vo = new TeamWorkProfileVO();
            vo.setUserId(user.getUserId());
            vo.setAvatar(user.getAvatar());
            vo.setTel(user.getTel());
            vo.setName(user.getName());
            vo.setStatus(Optional.ofNullable(receiveOrderStatusMap.get(user.getUserId())).orElse(null));
            int carryNum = Optional.ofNullable(settingsMap.get(user.getUserId())).map(CourierSettings::getReceive).map(CourierSettings.Receive::getCarryNum).orElse(0);
            vo.setCarryNum(carryNum);
            vo.setCarryNumFree(carryNum - Optional.ofNullable(hasMissionMap.get(user.getUserId())).map(CourierMissionStatusBO::getTotalUnit).orElse(0));
            vos.add(vo);
        }
        return vos;
    }

    /**
     * 获取一个团队以及所有子团队的 id 集合
     */
    public List<String> getTeamAndAllChildTeamIds(String teamId) {
        Team team;
        if (TextUtils.isEmpty(teamId)) {
            User me = getCurrentUser();
            team = getOnlyTeam(me.getUserId(), me.getCurrentTeamId());
        } else {
            team = teamRepository.findByTeamId(teamId);
        }
        if (team == null) {
            return new ArrayList<>();
        }
        return getTeamAndAllChildTeam(team).stream().map(Team::getTeamId).collect(Collectors.toList());
    }

    /**
     * 无TOKEN 获取一个团队下（包括子团队）的所有指定 type 分组的人
     */
    public List<User> findTypeUserByTeamId(String teamId, String type) {
        Team team = teamRepository.findByTeamId(teamId);
        if (team == null) {
            return new ArrayList<>();
        }
        List<Team> teams = teamRepository.findTeamByTypeInWithChild(Arrays.asList(type.split(",")), team.getParentIds());
        if (teams.isEmpty()) {
            return new ArrayList<>();
        } else {
            return userRepository.getUserByTeamIdIn(teams.stream().map(Team::getTeamId).collect(Collectors.toList()));
        }
    }

    /**
     * 获取一个团队的的人员（当前层）
     */
    public List<User> getTeamUsers(String teamId) {
        return userRepository.getUserByTeamIdIn(Collections.singletonList(teamId));
    }

    /**
     * 根据团队id和层级数获取直接子团队列表
     * @param teamId
     * @return
     */
    public List<Team> getDirectChildTeamByTeamId(String teamId){
        List<Team> teamList = new ArrayList<>();
        Team team = teamRepository.findByTeamId(teamId);
        if (team == null) {
            return teamList;
        }
        teamList = teamRepository.getChildrenTeamsJustOne(teamId);
        return teamList;
    }
}
