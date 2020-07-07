package com.mrwind.windbase.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.mrwind.windbase.bo.TeamStatusChangeStatBO;
import com.mrwind.windbase.common.constant.AttenceSummaryType;
import com.mrwind.windbase.common.constant.TeamMemberSummaryType;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.common.util.ServletUtil;
import com.mrwind.windbase.common.util.TextUtils;
import com.mrwind.windbase.entity.mongo.Position;
import com.mrwind.windbase.entity.mongo.TeamMemberSummary;
import com.mrwind.windbase.entity.mongo.TeamPosition;
import com.mrwind.windbase.entity.mongo.attendance.AttenceSummary;
import com.mrwind.windbase.entity.mysql.Team;
import com.mrwind.windbase.entity.mysql.TeamExtention;
import com.mrwind.windbase.entity.mysql.User;
import com.mrwind.windbase.entity.mysql.UserTeamRelation;
import com.mrwind.windbase.vo.*;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据页相关
 */

@Service
public class DataService extends BaseService {

    /**
     * [套壳接口] 根据指定teamId或者userId获取所有人员各状态统计数据统计
     */
    public Result getPickDeliverOrderNumStat(JSONObject body, String project) {
        return windForceApi.getPickDeliverOrderNumStat(body);
    }

    /**
     * 获取个人数据
     *
     * @param userId    用户 id
     * @param startDate 起始时间
     * @param endDate   结束时间
     * @param project   BU project
     * @return Result
     */
    public Result getPersonalData(String userId, String startDate, String endDate, String project) {
        if (userRepository.findByUserId(userId) == null) {
            return Result.getFailI18N("error.user.not.found");
        }

        UserDataVO vo = new UserDataVO();

        // 调用风力统计数据
        Map<String, Object> body = new HashMap<>();
        body.put("startDate", startDate);
        body.put("endDate", endDate);
        body.put("userId", userId);
        String token = ServletUtil.getCurrentRequest().getHeader(HttpHeaders.AUTHORIZATION);
        vo.setExpress(windForceApi.getFlUserTeamData(body));

        return Result.getSuccess(vo);
    }

    /**
     * 获取团队数据
     *
     * @param teamId    团队 id
     * @param startDate 起始时间
     * @param endDate   结束时间
     * @param project   BU project
     * @return Result
     */
    public Result getTeamData(String teamId, String startDate, String endDate, String project) {
        Team team = teamRepository.findByTeamId(teamId);
        if (team == null) {
            return Result.getFailI18N("error.team.not.found");
        }
        String rootTeamId = team.getRootId();

        TeamDataVO vo = new TeamDataVO();

        // 此团队及其所有子团队所有人的id
        List<String> teamUserIds = userRepository.getTeamAndChildTeamUserIds(String.valueOf(team.getParentIds()));
        // 此团队及其所有子团队所有的id
        List<String> teamIds = teamRepository.getTeamAndChildTeamIds(String.valueOf(team.getParentIds()));

        // 新增人数
        Set<String> newMemberIds = getUserIdsFromTeamMemberSummaries(teamIds, teamMemberSummaryDao.findRangeTeamTypeSummary(rootTeamId, startDate, endDate, TeamMemberSummaryType.NEW));
        vo.getMember().setNewMember(newMemberIds.size());

        // 离职人数
        Set<String> quitMemberIds = getUserIdsFromTeamMemberSummaries(teamIds, teamMemberSummaryDao.findRangeTeamTypeSummary(rootTeamId, startDate, endDate, TeamMemberSummaryType.QUIT));
        vo.getMember().setQuitMember(quitMemberIds.size());

        // 总数 - 暂时不做，返回当前时刻人数
        vo.getMember().setTotalMember(teamRepository.countUserByTeam(team.getParentIds() + ",%", teamId));

        Map<AttenceSummaryType, List<AttenceSummary>> allTypeSummaries = attenceApi.getTeamRangeAllSummary(startDate, endDate, team.getRootId());

        // 工作人数
        Set<String> workMemberIds = getUserIdsFromAttenceSummariesByType(teamUserIds, AttenceSummaryType.ATTENCE, allTypeSummaries);
        vo.getAttendance().setWorkMembers(workMemberIds.size());

        // 内勤人数
        Set<String> inMemberIds = getUserIdsFromAttenceSummariesByType(teamUserIds, AttenceSummaryType.IN, allTypeSummaries);
        vo.getAttendance().setInMember(inMemberIds.size());

        // 外勤人数
        Set<String> outMemberIds = getUserIdsFromAttenceSummariesByType(teamUserIds, AttenceSummaryType.OUT, allTypeSummaries);
        vo.getAttendance().setOutMember(outMemberIds.size());

        // 迟到人数
        Set<String> lateMemberIds = getUserIdsFromAttenceSummariesByType(teamUserIds, AttenceSummaryType.LATE, allTypeSummaries);
        vo.getAttendance().setLateMember(lateMemberIds.size());

        // 请假人数
        Set<String> leaveMemberIds = getUserIdsFromAttenceSummariesByType(teamUserIds, AttenceSummaryType.LEAVE, allTypeSummaries);
        vo.getAttendance().setLeaveMember(leaveMemberIds.size());

        // 无班人数
        Set<String> dayOffMemberIds = getUserIdsFromAttenceSummariesByType(teamUserIds, AttenceSummaryType.DAY_OFF, allTypeSummaries);
        vo.getAttendance().setDayOffMember(dayOffMemberIds.size());

        // 调用风力统计数据
        Map<String, Object> body = new HashMap<>();
        body.put("startDate", startDate);
        body.put("endDate", endDate);
        body.put("teamId", teamId);
        String token = ServletUtil.getCurrentRequest().getHeader(HttpHeaders.AUTHORIZATION);
        vo.setExpress(windForceApi.getFlUserTeamData(body));

        return Result.getSuccess(vo);

    }

    /**
     * 从一个多天根团队人员统计数据中筛选出特定的一些团队的人
     *
     * @param teamIds   特定的一些团队
     * @param summaries 多天根团队人员统计数据
     * @return Set<String>
     */
    private Set<String> getUserIdsFromTeamMemberSummaries(List<String> teamIds, List<TeamMemberSummary> summaries) {
        Set<String> memberIds = new HashSet<>();
        for (TeamMemberSummary summary : summaries) {
            for (TeamMemberSummary.Summary s : summary.getSummaries()) {
                if (teamIds.contains(s.getTeamId()) && s.getUserIds() != null) {
                    memberIds.addAll(s.getUserIds());
                }
            }
        }
        return memberIds;
    }

    /**
     * 给定特定考勤统计类型和特定的一些人，从多天考勤所有类型统计数据中筛选出有特定考勤类型的人
     *
     * @param userIds          特定的一些人
     * @param type             要筛选的特定考勤统计类型
     * @param allTypeSummaries 多天考勤所有类型统计数据
     * @return 有特定考勤类型的人
     */
    private Set<String> getUserIdsFromAttenceSummariesByType(List<String> userIds, AttenceSummaryType type, Map<AttenceSummaryType, List<AttenceSummary>> allTypeSummaries) {
        Set<String> memberIds = new HashSet<>();
        List<AttenceSummary> summaries = allTypeSummaries.get(type);
        for (AttenceSummary summary : summaries) {
            for (String userId : userIds) {
                if (summary.getUserIds().contains(userId)) {
                    memberIds.add(userId);
                }
            }
        }
        return memberIds;
    }

    // ---------------------------------------------------------------------------------------------------------

    /**
     * 获取团队数据页点击详情
     *
     * @param teamId    团队 id
     * @param startDate 起始时间
     * @param endDate   结束时间
     * @param type      类型
     * @return List<TeamDataDetailsItemVO>
     */
    public Result getTeamDataDetails(String teamId, String startDate, String endDate, String type) {
        Team team = teamRepository.findByTeamId(teamId);
        if (team == null) {
            return Result.getFailI18N("error.team.not.found");
        }
        String rootTeamId = team.getRootId();

        // 此团队及其所有子团队所有的id
        List<String> teamIds = teamRepository.getTeamAndChildTeamIds(String.valueOf(team.getParentIds()));
        // 此团队及其所有子团队所有人的id
        List<String> teamUserIds = userRepository.getTeamAndChildTeamUserIds(String.valueOf(team.getParentIds()));

        List<AttenceSummary> attenceSummaries;
        List<TeamMemberSummary> teamMemberSummaries;
        List<TeamDataDetailsItemVO> vos = new ArrayList<>();

        if (TextUtils.equals(type, TeamMemberSummaryType.NEW.name())) {
            teamMemberSummaries = teamMemberSummaryDao.findRangeTeamTypeSummary(rootTeamId, startDate, endDate, TeamMemberSummaryType.NEW);
            vos = extractUserInfoFromTeamSummary(teamIds, teamMemberSummaries, rootTeamId);
        } else if (TextUtils.equals(type, TeamMemberSummaryType.QUIT.name())) {
            teamMemberSummaries = teamMemberSummaryDao.findRangeTeamTypeSummary(rootTeamId, startDate, endDate, TeamMemberSummaryType.QUIT);
            vos = extractUserInfoFromTeamSummary(teamIds, teamMemberSummaries, rootTeamId);
        } else if (TextUtils.equals(type, TeamMemberSummaryType.TOTAL.name())) {
            vos = getTeamAllUsers(team);
        } else if (TextUtils.equals(type, AttenceSummaryType.ATTENCE.name())) {
            attenceSummaries = attenceApi.getTeamRangeSummary(startDate, endDate, team.getRootId(), AttenceSummaryType.ATTENCE);
            vos = extractUserInfoFromAttendanceSummary(teamUserIds, attenceSummaries, rootTeamId);
        } else if (TextUtils.equals(type, AttenceSummaryType.IN.name())) {
            attenceSummaries = attenceApi.getTeamRangeSummary(startDate, endDate, team.getRootId(), AttenceSummaryType.IN);
            vos = extractUserInfoFromAttendanceSummary(teamUserIds, attenceSummaries, rootTeamId);
        } else if (TextUtils.equals(type, AttenceSummaryType.OUT.name())) {
            attenceSummaries = attenceApi.getTeamRangeSummary(startDate, endDate, team.getRootId(), AttenceSummaryType.OUT);
            vos = extractUserInfoFromAttendanceSummary(teamUserIds, attenceSummaries, rootTeamId);
        } else if (TextUtils.equals(type, AttenceSummaryType.LATE.name())) {
            attenceSummaries = attenceApi.getTeamRangeSummary(startDate, endDate, team.getRootId(), AttenceSummaryType.LATE);
            vos = extractUserInfoFromAttendanceSummary(teamUserIds, attenceSummaries, rootTeamId);
        } else if (TextUtils.equals(type, AttenceSummaryType.LEAVE.name())) {
            attenceSummaries = attenceApi.getTeamRangeSummary(startDate, endDate, team.getRootId(), AttenceSummaryType.LEAVE);
            vos = extractUserInfoFromAttendanceSummary(teamUserIds, attenceSummaries, rootTeamId);
        } else if (TextUtils.equals(type, AttenceSummaryType.DAY_OFF.name())) {
            attenceSummaries = attenceApi.getTeamRangeSummary(startDate, endDate, team.getRootId(), AttenceSummaryType.DAY_OFF);
            vos = extractUserInfoFromAttendanceSummary(teamUserIds, attenceSummaries, rootTeamId);
        }
        return Result.getSuccess(vos);
    }

    /**
     * 获取一个团队下所有的用户，并组装 VO 数据（包括子团队用户）
     */
    private List<TeamDataDetailsItemVO> getTeamAllUsers(Team team) {
        List<User> users = userRepository.getTeamAndChildTeamUsers(team.getParentIds());
        List<String> userIds = users.stream().map(User::getUserId).collect(Collectors.toList());

        // 用户 id 与用户名字的 map 映射
        Map<String, String> userIdNameMaps = new HashMap<>();
        if (!userIds.isEmpty()) {
            for (Map<String, Object> map : userRepository.findNameByUserIdIn(userIds)) {
                userIdNameMaps.put(((String) map.get("userId")), ((String) map.get("name")));
            }
        }

        return users.stream().map(user -> {
            TeamDataDetailsItemVO vo = new TeamDataDetailsItemVO();
            vo.setUserId(user.getUserId());
            vo.setName(userIdNameMaps.get(user.getUserId()));
            vo.setAvatar(user.getAvatar());
            vo.setDates(Lists.newArrayList());
            return vo;
        }).collect(Collectors.toList());
    }

    private List<TeamDataDetailsItemVO> extractUserInfoFromTeamSummary(List<String> teamIds, List<TeamMemberSummary> summaries, String rootTeamId) {
        List<TeamDataDetailsItemVO> vos = new ArrayList<>();
        Map<String, List<String>> userDatesMap = new HashMap<>();
        for (TeamMemberSummary summary : summaries) {
            for (TeamMemberSummary.Summary s : summary.getSummaries()) {
                if (teamIds.contains(s.getTeamId()) && s.getUserIds() != null) {
                    for (String userId : s.getUserIds()) {
                        userDatesMap.computeIfAbsent(userId, k -> new ArrayList<>());
                        List<String> dates = userDatesMap.get(userId);
                        if (!dates.contains(summary.getDate())) {
                            userDatesMap.get(userId).add(summary.getDate());
                        }
                    }
                }
            }
        }

        if (userDatesMap.isEmpty()) {
            return vos;
        }

        // 用户 id 与用户头像的 map 映射
        Map<String, String> userIdAvatarMaps = new HashMap<>();
        if (!userDatesMap.keySet().isEmpty()) {
            for (Map<String, Object> map : userRepository.findUserAvatarByUserIdIn(userDatesMap.keySet())) {
                userIdAvatarMaps.put(((String) map.get("userId")), ((String) map.get("avatar")));
            }
        }

        // 用户 id 与用户名字的 map 映射
        Map<String, String> userIdNameMaps = new HashMap<>();
        if (!userDatesMap.keySet().isEmpty()) {
            for (Map<String, Object> map : userRepository.findNameByUserIdIn(userDatesMap.keySet())) {
                userIdNameMaps.put(((String) map.get("userId")), ((String) map.get("name")));
            }
        }

        // 组装数据
        for (Map.Entry<String, List<String>> entry : userDatesMap.entrySet()) {
            TeamDataDetailsItemVO vo = new TeamDataDetailsItemVO();
            vo.setAvatar(userIdAvatarMaps.get(entry.getKey()));
            vo.setUserId(entry.getKey());
            vo.setDates(entry.getValue());
            vo.setName(userIdNameMaps.get(entry.getKey()));
            vos.add(vo);
        }
        return vos;
    }


    private List<TeamDataDetailsItemVO> extractUserInfoFromAttendanceSummary(List<String> userIds, List<AttenceSummary> summaries, String rootTeamId) {
        List<TeamDataDetailsItemVO> vos = new ArrayList<>();
        Map<String, List<String>> userDatesMap = new HashMap<>();
        for (AttenceSummary summary : summaries) {
            for (String userId : userIds) {
                if (summary.getUserIds().contains(userId)) {
                    userDatesMap.computeIfAbsent(userId, k -> new ArrayList<>());
                    List<String> dates = userDatesMap.get(userId);
                    if (!dates.contains(summary.getDate())) {
                        userDatesMap.get(userId).add(summary.getDate());
                    }
                }
            }
        }

        if (userDatesMap.isEmpty()) {
            return vos;
        }

        // 用户 id 与用户头像的 map 映射
        Map<String, String> userIdAvatarMaps = new HashMap<>();
        if (!userDatesMap.keySet().isEmpty()) {
            for (Map<String, Object> map : userRepository.findUserAvatarByUserIdIn(userDatesMap.keySet())) {
                userIdAvatarMaps.put(((String) map.get("userId")), ((String) map.get("avatar")));
            }
        }

        // 用户 id 与用户名字的 map 映射
        Map<String, String> userIdNameMaps = new HashMap<>();
        if (!userDatesMap.keySet().isEmpty()) {
            for (Map<String, Object> map : userRepository.findNameByUserIdIn(userDatesMap.keySet())) {
                userIdNameMaps.put(((String) map.get("userId")), ((String) map.get("name")));
            }
        }

        // 组装数据
        for (Map.Entry<String, List<String>> entry : userDatesMap.entrySet()) {
            TeamDataDetailsItemVO vo = new TeamDataDetailsItemVO();
            vo.setUserId(entry.getKey());
            vo.setAvatar(userIdAvatarMaps.get(entry.getKey()));
            vo.setDates(entry.getValue());
            vo.setName(userIdNameMaps.get(entry.getKey()));
            vos.add(vo);
        }
        return vos;
    }

    /**
     * 新版获取团队相关数据
     *
     * @param startDate 起始日期
     * @param endDate   结束日期
     * @param teamId    团队 id
     */
    public Result getTeamStat(String startDate, String endDate, String teamId) {
        User me = getCurrentUser();
        TeamStatVO vo = new TeamStatVO();
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
        // 团队 id
        vo.setTeamId(team.getTeamId());
        // 团队名
        vo.setTeamName(team.getName());
        // 团队type
        vo.setType(team.getType());
        // 团队成员数（包括子团队）
        vo.setMemberCount(teamService.getTeamUserNum(teamId));
        // 自己是否为根团队管理员
        UserTeamRelation teamRelation = userTeamRelationRepository.findByTeamIdAndUserId(me.getCurrentTeamId(), me.getUserId());
        vo.setRootManager(Optional.ofNullable(teamRelation).map(UserTeamRelation::isMananger).orElse(false));
        // 自己是否为当前团队管理员
        vo.setManager(Optional.ofNullable(userTeamRelationRepository.findByTeamIdAndUserId(team.getTeamId(), me.getUserId())).map(UserTeamRelation::isMananger).orElse(false));
        // 自己能否管理当前查询的团队
        if (vo.getRootManager()) {
            // 如果已经是根团队管理员，那么肯定可以管理其子团队
            vo.setManageable(true);
        } else {
            // 如果不是根团队管理员，则还需要判断是否能管理此团队
            vo.setManageable(userTeamService.checkCanManageTeam(me.getUserId(), teamId));
        }
        // 团队接单区域数
        List<String> allTeamIds = teamRepository.getTeamAndChildTeamIds(team.getParentIds());
        if (!allTeamIds.isEmpty()) {
            List<TeamPosition> teamPositions = teamPositionDao.findByTeamIdsIn(allTeamIds);
            vo.setTeamPositionNum(teamPositions
                    .stream()
                    .filter(position -> position.getGpsInfo() != null && position.getGpsInfo().getLat() != 0f && position.getGpsInfo().getLng() != 0f)
                    .count());
        }
        // 团队考勤数据
        TeamStatusChangeStatBO teamStatusChangeStatBO = attenceApi.getTeamStatusChangeStat(teamId, startDate, endDate);
        vo.setAttendanceStatusNum(teamStatusChangeStatBO.getTotalNum());
        // 订单
        vo.setTotalOrderNum(windForceApi.getOrderTeamStat(teamId, startDate, endDate));
        // 团队财务利润
        WalletCollectVO walletCollectVO = walletApi.deliveryTeamTotals(team.getTeamId(), startDate + " 00:00:00", endDate + " 23:59:59");
        vo.setProfit(walletCollectVO.getProfit());
        vo.setCustomerConsumption(walletCollectVO.getCost());
        // 团队取派时间段
        TeamExtention te = teamExtentionRepository.findByTeamId(teamId);
        if (te != null) {
            vo.setExpressStartTime(te.getExpressStartTime());
            vo.setExpressEndTime(te.getExpressEndTime());
        }
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
        vo.setPosition(tpVo);

        // 配置人员工作状态
        List<TeamWorkProfileVO> workProfiles = teamService.getTeamAllMemberProfile(teamId);
        vo.setUserWorks(workProfiles.size());
        vo.setUserWorking(workProfiles.stream().filter(profile -> profile.getStatus() != null && profile.getStatus() == 1).count());
        int totalCarry = 0;
        int totalFree = 0;
        for (TeamWorkProfileVO workProfile : workProfiles) {
            totalCarry += workProfile.getCarryNum();
            totalFree += workProfile.getCarryNumFree();
        }
        if (totalCarry != 0) {
            vo.setCarryLeave(Double.parseDouble(String.format("%.02f", totalFree * 1.0f / totalCarry)));
        } else {
            vo.setCarryLeave(0f);
        }
        return Result.getSuccess(vo);
    }

}
