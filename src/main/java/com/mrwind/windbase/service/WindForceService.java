package com.mrwind.windbase.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mrwind.windbase.bo.TeamExpressBillingModeBO;
import com.mrwind.windbase.common.constant.CommonConstants;
import com.mrwind.windbase.common.constant.DataTypeEnum;
import com.mrwind.windbase.common.constant.TeamType;
import com.mrwind.windbase.common.util.*;
import com.mrwind.windbase.dto.*;
import com.mrwind.windbase.entity.mongo.CourierSettings;
import com.mrwind.windbase.entity.mongo.Position;
import com.mrwind.windbase.entity.mongo.TeamPosition;
import com.mrwind.windbase.entity.mysql.*;
import com.mrwind.windbase.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/7/25
 */

@Service
public class WindForceService extends BaseService {

    /**
     * 根据手机号获取该人员标签
     */
    public Result listUserRolesByTel(String teamId, String tel) {
        Team team = teamRepository.findByTeamId(teamId);
        if (team == null) {
            return Result.getFailI18N("error.team.not.found");
        }
        String parentIds = team.getParentIds();
        List<Team> teams = teamRepository.findByParentIdsStartingWith(parentIds);
        List<String> teamIds = new ArrayList<>();
        for (Team t : teams) {
            teamIds.add(t.getTeamId());
        }
        List<UserJobListVO> userJobList = JsonUtil.parse(roleRepository.listRolesByUserTel(teamIds, tel), UserJobListVO.class);
        return Result.getSuccess(userJobList);
    }

    /**
     * 获取指定团队下所有人手机号码(包括子团队)
     */
    public Result getAllTel(String teamId) {
        List<Map<String, Object>> maps = new ArrayList<>();
        Team team = teamRepository.findByTeamId(teamId);
        if (team == null) {
            return Result.getFailI18N("error.team.not.found");
        }
        String parentIds = team.getParentIds();
        List<Team> teams = teamRepository.findByParentIdsStartingWith(parentIds);
        List<String> teamIds = new ArrayList<>();
        for (Team t : teams) {
            teamIds.add(t.getTeamId());
        }
        Set<String> userIds = new HashSet<>();
        for (UserTeamRelation relation : userTeamRelationRepository.findByTeamIdIn(teamIds)) {
            userIds.add(relation.getUserId());
        }
        List<User> users = userRepository.findByUserIdIn(userIds);
        for (User user : users) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", user.getUserId());
            map.put("name", user.getName());
            map.put("tel", user.getTel());
            maps.add(map);
        }
        return Result.getSuccess(maps);
    }

    /**
     * 通过标签来查找用户
     */
    public Result getUserByJob(GetUserByJobDTO body) {
        List<Map<String, Object>> maps = new ArrayList<>();
        boolean isAnd = body.getIsand();
        Team team = teamRepository.findByTeamId(body.getTeamId());
        if (team == null) {
            return Result.getFailI18N("error.team.not.found");
        }
        String parentIds = team.getParentIds();
        List<Team> teams = teamRepository.findByParentIdsStartingWith(parentIds);
        List<String> teamIds = new ArrayList<>();
        for (Team t : teams) {
            teamIds.add(t.getTeamId());
        }

        Set<String> userIds = new HashSet<>();
        List<UserTeamRoleRelation> relations = userTeamRoleRelationRepository.findByTeamIdInAndRoleIdIn(teamIds, body.getJobIDs());
        for (UserTeamRoleRelation relation : relations) {
            userIds.add(relation.getUserId());
        }
        Set<String> finalUserIds = new HashSet<>();
        if (!isAnd) {
            // 每个用户要满足其中一个指定的标签就行
            finalUserIds = userIds;
        } else {
            // 每个用户要满足指定的所有标签才行
            for (String userId : userIds) {
                Set<String> userRoleIds = new HashSet<>();
                for (UserTeamRoleRelation relation : relations) {
                    if (TextUtils.equals(relation.getUserId(), userId)) {
                        userRoleIds.add(relation.getRoleId());
                    }
                }
                if (userRoleIds.size() == body.getJobIDs().size()) {
                    finalUserIds.add(userId);
                }
            }
        }
        List<User> users = userRepository.findByUserIdIn(finalUserIds);
        for (User user : users) {
            Map<String, Object> m = new HashMap<>();
            m.put("id", user.getUserId());
            m.put("tel", user.getTel());
            m.put("name", user.getName());
            maps.add(m);
        }
        return Result.getSuccess(maps);
    }


    /**
     * 根据经纬度坐标获取离得最近的仓库
     *
     * @param lng 经度
     * @param lat 纬度
     * @return 最近的仓库对象
     */
    public Result getClosestTeamByGps(double lng, double lat) {
        List<StoreInfoDTO> nearStores = getNearStore(lat, lng);
        if (nearStores != null && !nearStores.isEmpty()) {
            return Result.getSuccess(nearStores.get(0));
        } else {
            return Result.getSuccess();
        }
    }

    /**
     * userId获取该人员信息和所属部门信息（是否有部门领导和员工区分的字段）
     *
     * @param project    项目类别/城市简称
     * @param rootTeamId 根团队ID
     * @param userId     用户ID
     * @param type       数据类型，多个以逗号分隔。数据项：user-获取用户基本信息，userrole-获取用户标签(本参数必须要获取组织)
     *                   rootteam-获取根部门团队，warehouseteam-获取所在仓库团队(和获取客服团队互斥)，clientserverteam-获取客服团队(和获取所在仓库团队互斥)，parentteam-获取父团队
     *                   teamrole-获取团队标签（本参数必须要获取组织），teamgps-获取团队经纬度坐标（本参数必须要获取组织）
     * @return TeamUserInfoDTO
     */
    public Result getuserteaminfo(String project, String rootTeamId, String userId, String type) {
        if (type.indexOf(DataTypeEnum.WAREHOUSETEAM.getDesc()) > -1 &&
                type.indexOf(DataTypeEnum.CLIENTSERVERTEAM.getDesc()) > -1) {
            return Result.getFailI18N("error.parameter");
        }
        boolean success = true;
        TeamUserInfoDTO teamUserInfoDTO = new TeamUserInfoDTO();
        if (type.indexOf(DataTypeEnum.USER.getDesc()) > -1) {
            //1.获取用户基本信息
            User user = userRepository.findByUserId(userId);
            if (user == null) {
                success = false;
                System.out.println("[" + project + "]userid:" + userId + ";rootTeamId:" + rootTeamId + ". game over.");
                teamUserInfoDTO = null;
                return Result.getFailI18N("error.user.not.found");
            }

            if (StringUtils.isBlank(user.getName())) {
                user.setName("");
            }
            teamUserInfoDTO.setUserInfo(user);
        }

        if (type.indexOf(DataTypeEnum.USEREXTENTION.getDesc()) > -1) {
            //2.获取用户附加业务属性
            UserExtension userExtension = userExtensionRepository.findByUserId(userId);
            if (userExtension != null) {
                teamUserInfoDTO.setUserExtension(userExtension);
            }
        }

        // todo 1211
        if (type.indexOf(DataTypeEnum.ROOTTEAM.getDesc()) > -1) {
            Team onlyTeam = getOnlyTeam(userId, rootTeamId);
            if (onlyTeam != null) {
                Team rootTeam = teamRepository.findByTeamId(onlyTeam.getRootId());
                if (rootTeam != null) {
                    teamUserInfoDTO.setRootTeam(rootTeam);
                }
            }
        }

        String nowRootTeamId = "";
        if (type.indexOf(DataTypeEnum.ROOTTEAM.getDesc()) > -1 || type.indexOf(DataTypeEnum.PARENTTEAM.getDesc()) > -1 ||
                type.indexOf(DataTypeEnum.WAREHOUSETEAM.getDesc()) > -1 || type.indexOf(DataTypeEnum.CLIENTSERVERTEAM.getDesc()) > -1 ||
                type.indexOf(DataTypeEnum.TEAMROLE.getDesc()) > -1 || type.indexOf(DataTypeEnum.TEAMGPS.getDesc()) > -1 ||
                type.indexOf(DataTypeEnum.TEAMEXTENTION.getDesc()) > -1 || type.indexOf(DataTypeEnum.USERROLE.getDesc()) > -1) {
            //3.当前账号挂接哪些团队
            List<UserTeamRelation> userTeamRelationList = userTeamRelationRepository.findByUserId(userId);
            if (userTeamRelationList != null && userTeamRelationList.size() > 0) {
                boolean needClientServerTeam, needWareHouseTeam, needBreak = false;
                if (type.indexOf(DataTypeEnum.CLIENTSERVERTEAM.getDesc()) > -1) {
                    needClientServerTeam = true;
                } else {
                    needClientServerTeam = false;
                }
                if (type.indexOf(DataTypeEnum.WAREHOUSETEAM.getDesc()) > -1) {
                    needWareHouseTeam = true;
                } else {
                    needWareHouseTeam = false;
                }
                Team clientServerTeam = null;
                Team wareHouseTeam = null;
                for (UserTeamRelation userTeamRelation : userTeamRelationList) {
                    //4.获取父team
                    Team parentTeam = teamRepository.findByTeamId(userTeamRelation.getTeamId());

                    //5.判断出是当前rootTeamId下的团队
                    if ((StringUtils.isNotBlank(rootTeamId) && !parentTeam.getRootId().equals(rootTeamId))
                            || (StringUtils.isNotBlank(project) && !project.equals(parentTeam.getProject()))
                    ) {
                        success = false;
                        continue;
                    }

                    if (needWareHouseTeam && wareHouseTeam == null) {
                        Role role = roleRepository.findByName(DataTypeEnum.WAREHOUSETEAM.getDesc());
                        if (role == null) {
                            break;
                        }
                        //6.获取仓库团队
                        List<Team> teams = teamRepository.getTeamByRoleIdAndProject(role.getRoleId(), project, parentTeam.getParentIds().split(","));
                        if (teams != null && teams.size() > 0) {
                            wareHouseTeam = teams.get(0);
                            teamUserInfoDTO.setWareHouseTeam(wareHouseTeam);
                            if (type.indexOf(DataTypeEnum.TEAMGPS.getDesc()) > -1) {
                                //7.获取仓库团队经纬度坐标
                                TeamPosition teamPosition = teamPositionDao.findOne("teamId", wareHouseTeam.getTeamId());
                                teamUserInfoDTO.setWareHouseTeamPosition(teamPosition);
                            }
                            if (type.indexOf(DataTypeEnum.TEAMROLE.getDesc()) > -1) {
                                //8.获取仓库团队角色（标签）
                                teamUserInfoDTO.setWareHouseTeamRoleList(JSON.parseArray(JSON.toJSONString(roleRepository.listTeamRolesByTeamId(wareHouseTeam.getTeamId())), RoleVO.class));
                            }
                            if (type.indexOf(DataTypeEnum.TEAMEXTENTION.getDesc()) > -1) {
                                //9.获取仓库团队附加业务属性
                                TeamExtention teamExtention = teamExtentionRepository.findByTeamId(wareHouseTeam.getTeamId());
                                teamUserInfoDTO.setWareHouseTeamExtention(teamExtention);
                            }
                            if (!needClientServerTeam || clientServerTeam != null) {
                                needBreak = true;
                            }
                        }
                    } else if (needClientServerTeam && clientServerTeam == null) {
                        Role role = roleRepository.findByName(DataTypeEnum.CLIENTSERVERTEAM.getDesc());
                        if (role == null) {
                            break;
                        }
                        //10.获取客服仓库团队
                        List<Team> teams = teamRepository.getTeamByRoleIdAndProject(role.getRoleId(), project, parentTeam.getParentIds().split(","));
                        if (teams != null && teams.size() > 0) {
                            clientServerTeam = teams.get(0);
                            teamUserInfoDTO.setClientServerTeam(clientServerTeam);
                            if (type.indexOf(DataTypeEnum.TEAMGPS.getDesc()) > -1) {
                                //11.获取客服团队经纬度坐标
                                TeamPosition teamPosition = teamPositionDao.findOne("teamId", clientServerTeam.getTeamId());
                                teamUserInfoDTO.setClientServerTeamPosition(teamPosition);
                            }
                            if (type.indexOf(DataTypeEnum.TEAMROLE.getDesc()) > -1) {
                                //12.获取客服团队角色（标签）
                                teamUserInfoDTO.setClientServerTeamRoleList(JsonUtil.parse(roleRepository.listTeamRolesByTeamId(clientServerTeam.getTeamId()), RoleVO.class));
                            }
                            if (type.indexOf(DataTypeEnum.TEAMEXTENTION.getDesc()) > -1) {
                                //13.获取客服团队附加业务属性
                                TeamExtention teamExtention = teamExtentionRepository.findByTeamId(clientServerTeam.getTeamId());
                                teamUserInfoDTO.setClientServerTeamExtention(teamExtention);
                            }
                            if (!needWareHouseTeam || wareHouseTeam != null) {
                                needBreak = true;
                            }
                        }
                    } else {
                        needBreak = true;
                    }

                    //前提条件：一个用户在风先生只能有一个团队
                    if (type.indexOf(DataTypeEnum.ROOTTEAM.getDesc()) > -1) {
//                    if (needBreak && type.indexOf(DataTypeEnum.ROOTTEAM.getDesc()) > -1) {
                        //14.获取根部门团队
                        Team rootTeam = teamRepository.findByTeamId(parentTeam.getRootId());
                        teamUserInfoDTO.setRootTeam(rootTeam);
                        if (type.indexOf(DataTypeEnum.TEAMGPS.getDesc()) > -1) {
                            //15.获取根团队经纬度坐标
                            TeamPosition teamPosition = teamPositionDao.findOne("teamId", rootTeam.getTeamId());
                            teamUserInfoDTO.setRootTeamPosition(teamPosition);
                        }
                        if (type.indexOf(DataTypeEnum.TEAMROLE.getDesc()) > -1) {
                            //16.获取根团队角色（标签）
                            teamUserInfoDTO.setRootTeamRoleList(JsonUtil.parse(roleRepository.listTeamRolesByTeamId(rootTeam.getTeamId()), RoleVO.class));
                        }
                        if (type.indexOf(DataTypeEnum.TEAMEXTENTION.getDesc()) > -1) {
                            //17.获取根团队附加业务属性
                            TeamExtention teamExtention = teamExtentionRepository.findByTeamId(rootTeam.getTeamId());
                            teamUserInfoDTO.setRootTeamExtention(teamExtention);
                        }
                    }

                    //前提条件：一个用户在风先生只能有一个团队
                    if (type.indexOf(DataTypeEnum.PARENTTEAM.getDesc()) > -1) {
//                    if (needBreak && type.indexOf(DataTypeEnum.PARENTTEAM.getDesc()) > -1) {
                        teamUserInfoDTO.setParentTeam(parentTeam);
                        //18.设置父团队
                        if (type.indexOf(DataTypeEnum.TEAMGPS.getDesc()) > -1) {
                            //19.获取父团队经纬度坐标
                            TeamPosition teamPosition = teamPositionDao.findOne("teamId", parentTeam.getTeamId());
                            teamUserInfoDTO.setParentTeamPosition(teamPosition);
                        }
                        if (type.indexOf(DataTypeEnum.TEAMROLE.getDesc()) > -1) {
                            //20.获取父团队角色（标签）
                            teamUserInfoDTO.setParentTeamRoleList(JsonUtil.parse(roleRepository.listTeamRolesByTeamId(parentTeam.getTeamId()), RoleVO.class));
                        }
                        if (type.indexOf(DataTypeEnum.USERROLE.getDesc()) > -1) {
                            //21.获取父团队用户角色（标签）
                            teamUserInfoDTO.setParentTeamUserRoleList(JsonUtil.parse(roleRepository.listUserRolesByTeamIdUserId(userId, parentTeam.getTeamId()), RoleVO.class));
                        }
                        if (type.indexOf(DataTypeEnum.TEAMEXTENTION.getDesc()) > -1) {
                            //22.获取父团队附加业务属性
                            TeamExtention teamExtention = teamExtentionRepository.findByTeamId(parentTeam.getTeamId());
                            teamUserInfoDTO.setParentTeamExtention(teamExtention);
                        }
                    }

                    if (needBreak) {
                        nowRootTeamId = parentTeam.getRootId();
                        //23.设置父团队ID和是否该用户是管理员
                        teamUserInfoDTO.setParentTeamId(userTeamRelation.getTeamId());
                        teamUserInfoDTO.setManager(userTeamRelation.isMananger());
                        success = true;
                        break;
                    }
                }
            }
        }

        //获取多团队关联信息
        if (type.indexOf(DataTypeEnum.ROOTTEAMUSERRELATION.getDesc()) > -1) {
            teamUserInfoDTO.setRelationRootTeamList(teamRepository.findUserAllRoots(userId));
        }
        System.out.println("[" + project + "]userid:" + userId + ";rootTeamId:" + rootTeamId + ".teamUserInfoDTO:" + JSON.toJSONString(teamUserInfoDTO));
        //if(success){
        return Result.getSuccess(teamUserInfoDTO);
        //}else{
        //return Result.getFailI18N("error.parameter");
        //}
    }

    /**
     * 公司客户userId获取该人员信息和所属部门信息（是否有部门领导和员工区分的字段）
     *
     * @param rootTeamId 根团队ID，可以为空
     * @param userId     用户ID
     * @param type       数据类型，多个以逗号分隔。数据项：user-获取用户基本信息，userrole-获取用户标签(本参数必须要获取组织)
     *                   rootteam-获取根部门团队，parentteam-获取父团队
     *                   teamrole-获取团队标签（本参数必须要获取组织），teamgps-获取团队经纬度坐标（本参数必须要获取组织）
     * @return TeamUserInfoDTO
     */
    public Result getclientuserteaminfo(String rootTeamId, String userId, String type) {
        return getuserteaminfo(null, rootTeamId, userId, type);
    }

    /**
     * 通过teamId批量获取 teamInfo + teamPosition + teamextension
     */
    public Result getTeamsInfoPosition(JSONArray array) {
        if (array.size() == 0) {
            return Result.getSuccess();
        }
        List<String> teamIds = array.stream().map(Object::toString).collect(Collectors.toList());
        List<Team> teams = teamRepository.getTeamsInTeamIds(teamIds);
        List<TeamExtention> texs = teamExtentionRepository.getTeamExtentionsByTeamIds(teamIds);
        List<TeamPosition> teamPositions = teamPositionDao.findByTeamIdsIn(teamIds);
        List<TeamInfoPositionDTO> teamInfoPositionDTOS = new ArrayList<>();

        teams.forEach(team -> {
            TeamExtention teamExtention = texs.stream().filter(t -> TextUtils.equals(t.getTeamId(), team.getTeamId())).findFirst().orElse(null);
            TeamPosition teamPosition = teamPositions.stream().filter(t -> TextUtils.equals(t.getTeamId(), team.getTeamId())).findFirst().orElse(null);
            teamInfoPositionDTOS.add(new TeamInfoPositionDTO(team, teamExtention, teamPosition));
        });
        return Result.getSuccess(teamInfoPositionDTOS);
    }

    /**
     * isGetAllMember为1时，获取team下的所有子孙用户
     * isGetAllMember为2时，获取该仓库(team)下的除过子孙仓库下成员的所有成员，即可接收抢单推送的成员
     *
     * @param teamId
     * @param isGetAllMember
     * @return
     */
    public Result getUserByTeamId(String teamId, String isGetAllMember) {
        List<FlCourierUserVO> map = getDeliveryManByTeam(teamId, isGetAllMember);
        return Result.getSuccess(map);
    }

    /**
     * isGetAllMember为1时，获取team下的所有子孙用户
     * isGetAllMember为2时，获取该仓库(team)下的除过子孙仓库下成员的所有成员，即可接收抢单推送的成员
     *
     * @param teamId
     * @param isGetAllMember
     * @return
     */
    List<FlCourierUserVO> getDeliveryManByTeam(String teamId, String isGetAllMember) {
        List<FlCourierUserVO> vos = new ArrayList<>();
        Team targetTeam = getOneTeam(teamId);
        if (targetTeam == null) {
            return vos;
        } else {
            List<User> users = new ArrayList<>();
            if (TextUtils.equals("1", isGetAllMember)) {
                users = teamService.findTypeUserByTeamId(teamId, StringUtils.join(TeamType.ALL, ","));
            } else if (TextUtils.equals("2", isGetAllMember)) {
                users = teamService.findTypeUserByTeamId(teamId, String.valueOf(TeamType.STORE));
            }
            for (User user : users) {
                FlCourierUserVO vo = new FlCourierUserVO();
                vo.setCountryCode(user.getCountryCode());
                vo.setName(user.getName());
                vo.setUserId(user.getUserId());
                vo.setAvatar(user.getAvatar());
                vo.setCurrentCountry(user.getCurrentCountry());
                vo.setUserName(user.getUserName());
                vo.setTel(user.getTel());
                vo.setCurrentTeamId(user.getCurrentTeamId());
                CourierSettingsVO settings = busService.getCourierSettingsById(user.getUserId());
                vo.setHomeAddress(settings.getHomeAddress());
                vo.setHomeLat(settings.getHomeLat());
                vo.setHomeLng(settings.getHomeLng());
                vo.setDeliverAreaAddress(settings.getDeliverAreaAddress());
                vo.setDeliverAreaLat(settings.getDeliverAreaLat());
                vo.setDeliverAreaLng(settings.getDeliverAreaLng());
                vo.setReceiveOrder(settings.getReceiveOrder());
                vo.setOrderLimit(settings.getOrderLimit());
                vo.setLowerBound(settings.getLowerBound());
                vo.setCarryNum(settings.getCarryNum());
                vos.add(vo);
            }
            return vos;
        }
    }

    public Team getOneTeam(String teamId) {
        return teamRepository.findById(teamId).orElse(null);
    }

    /**
     * 对指定仓库下属所有配送员进行消息推送
     *
     * @param req
     */
    public void pushToDeliveryManByTeamId(TeamPushDTO req) {
        String teamId = req.getTeamId();
        String isGetAllMember = req.getTypeId();
        List<String> userIdList = new ArrayList<>();
        List<FlCourierUserVO> users = getDeliveryManByTeam(teamId, isGetAllMember);
        for (FlCourierUserVO user : users) {
            userIdList.add(user.getUserId());
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("data", LocaleType.getMessageMap(req.getBody()));
        //actionType:CommonConstants.WIND_FORCE_GRAB_ORDER_PUSH
//        new PushRequest.Builder<>(userIdList)
//                .setActionType(req.getActionType())
//                .setTitle(LocaleType.getMessageMap(req.getTitle()))
//                .setBody(LocaleType.getMessageMap(req.getBody()))
//                .setData(dataMap)
//                .build()
//                .push();
    }

    /**
     * 获取1.该team下的除过子孙仓库下成员的所有人员。2.该team的所有上级team的管理员,按父亲,爷爷,祖宗...团队的管理员顺序
     *
     * @param teamId
     * @return
     */
    public Result telephoneDispatcherUsers(String teamId) {
        Map<String, Object> result = new HashMap<>();

        //获取该team下的除过子孙仓库下成员的所有人员
        List<Map<String, Object>> childUserList = new ArrayList<>();
        List<FlCourierUserVO> list = getDeliveryManByTeam(teamId, "2");
        childUserList = list.stream().map(l -> {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userId", l.getUserId());
            map.put("tel", l.getTel());
            return map;
        }).collect(Collectors.toList());


        //获取该team的所有上级team的管理员,按父亲,爷爷,祖宗...团队的管理员顺序获取
        List<Long> superiorTeamIdNoList = teamService.getParentTeamIdNosByTeamId(teamId);
        List<Map<String, Object>> superiorManagerList = userTeamRelationRepository.getSuperiorTeamManagerListByTeamIdNoIn(superiorTeamIdNoList);


        result.put("childUserList", childUserList);
        result.put("superiorManagerList", superiorManagerList);
        return Result.getSuccess(result);
    }

    /**
     * 获取B端客户列表
     *
     * @param page  当前页码
     * @param limit 每页数据量，小于1是获取全部的特殊处理
     * @return
     */
    public Result getBrowserUsers(int page, int limit) {
        Map<String, Object> result = new HashMap<>();
        if (limit < 1) {
            List<Map<String, Object>> browserUserList = userRepository.getBrowserUsers();
            result.put("total", browserUserList.size());
            result.put("list", browserUserList);
            return Result.getSuccess(result);
        }
        PageRequest pageRequest = new PageRequest(page - 1, limit);
        Page<Map<String, Object>> browserUsers = userRepository.getBrowserUsersByPage(pageRequest);
        result.put("total", browserUsers.getTotalElements());
        result.put("list", browserUsers.getContent());
        return Result.getSuccess(result);

    }

    /**
     * 根据用户创建时间获取B端客户列表
     *
     * @param startTime 过滤起始时间 eg: 2018-05-20
     * @param endTime   过滤结束时间 eg: 2018-11-20
     * @return Result
     */
    public Result getBrowserUsersByCreateTime(String startTime, String endTime) {
        return Result.getSuccess(userRepository.getBrowserUsersByCreateTime(startTime, endTime));
    }

    public Result getAllBrowserUsers() {
        return Result.getSuccess(userRepository.getAllBrowserUsers());
    }

    /**
     * 认证或取消认证根团队（即修改team表的shop字段）
     *
     * @param teamShopDTO
     * @return
     */
    public Result changeClientTeamShop(TeamShopDTO teamShopDTO) {
        Team team = getOneTeam(teamShopDTO.getRootTeamId());
        //团队不存在
        if (team == null) {
            return Result.getFailI18N("error.team.not.exit");
        }
        //团队不是根团队
        if (!team.getTeamId().equals(team.getRootId())) {
            return Result.getFailI18N("error.team.not.root");
        }
        boolean shopStatus = false;
        if ("1".equals(teamShopDTO.getShop())) {
            shopStatus = true;
        }
        team.setShop(shopStatus);
        teamRepository.save(team);
        return Result.getSuccess("success");

    }

    /**
     * 获取所有非风先生的根团队列表
     *
     * @return
     */
    public Result getClientRootTeamList() {
        List<Map<String, Object>> result = teamRepository.getClientRootTeamList();
        return Result.getSuccess(result);
    }

    /**
     * 获取所有配送员 id
     */
    public Result getAllCourierIds() {
        // 获取所有仓库
        List<Team> stores = teamRepository.findByType(TeamType.STORE);
        if (stores.isEmpty()) {
            return Result.getSuccess(new ArrayList<>());
        }
        List<User> couriers = userRepository.getUserByTeamIdIn(stores.stream().map(Team::getTeamId).collect(Collectors.toList()));
        return Result.getSuccess(couriers.stream().map(User::getUserId).collect(Collectors.toSet()));
    }

    /**
     * 获取指定 project 下属所有配送员及接单状态列表
     */
    public List<StoreCouriersStatusVO> getCouriersStatusByProject() {
        List<StoreCouriersStatusVO> vos = new ArrayList<>();

        // 获取所有仓库
        List<Team> stores = teamRepository.findByType(TeamType.STORE);
        if (stores.isEmpty()) {
            return vos;
        }

        // 仓库 id 和对应的配送员的映射
        Map<String, List<User>> storeIdCouriersMap = new HashMap<>();
        for (Team store : stores) {
            storeIdCouriersMap.put(store.getTeamId(), userRepository.getUserByTeamIdIn(Collections.singletonList(store.getTeamId())));
        }
        // 所有仓库配送员的 id
        List<String> allCouriersIds = storeIdCouriersMap.values().stream()
                .flatMap((Function<List<User>, Stream<User>>) Collection::stream)
                .map(User::getUserId)
                .collect(Collectors.toList());

        List<String> storeIds = stores.stream().map(Team::getTeamId).collect(Collectors.toList());


        // 获取所有仓库的位置信息
        Map<String, TeamPosition> teamPositionMap = CollectionUtil.takeFieldsToMap(teamPositionDao.findByTeamIdsIn(storeIds), "teamId");

        Map<String, TeamExtention> teamExtentionMap = CollectionUtil.takeFieldsToMap(teamExtentionRepository.findByTeamIdIn(storeIds), "teamId");

        // 获取所有配送员的接单状态
        List<UserExtension> userExtensions = userExtensionRepository.findByUserIdIn(allCouriersIds);
        Map<String, UserExtension> userExtensionMap = CollectionUtil.takeFieldsToMap(userExtensions, "userId");

        vos = stores.stream()
                .map(store -> {
                    StoreCouriersStatusVO vo = new StoreCouriersStatusVO();
                    vo.setWarehouseTeamId(store.getTeamId());
                    vo.setWarehouseTeamName(store.getName());
                    String storeId = store.getTeamId();
                    vo.setWarehouseLat(Optional.ofNullable(teamPositionMap.get(storeId))
                            .map(TeamPosition::getGpsInfo)
                            .map(Position::getLat)
                            .orElse(null)
                    );
                    vo.setWarehouseLng(Optional.ofNullable(teamPositionMap.get(storeId))
                            .map(TeamPosition::getGpsInfo)
                            .map(Position::getLng)
                            .orElse(null)
                    );
                    vo.setExpressStartTime(Optional.ofNullable(teamExtentionMap.get(storeId))
                            .map(TeamExtention::getExpressStartTime)
                            .orElse(null)
                    );
                    vo.setExpressEndTime(Optional.ofNullable(teamExtentionMap.get(storeId))
                            .map(TeamExtention::getExpressEndTime)
                            .orElse(null)
                    );

                    List<User> couriers = storeIdCouriersMap.get(store.getTeamId());
                    if (couriers == null || couriers.isEmpty()) {
                        vo.setUserList(Lists.newArrayList());
                    } else {
                        vo.setUserList(couriers.stream()
                                .map(courier -> {
                                    StoreCouriersStatusVO.CourierStatus courierStatus = new StoreCouriersStatusVO.CourierStatus();
                                    courierStatus.setUserId(courier.getUserId());
                                    courierStatus.setUserAvatar(courier.getAvatar());
                                    courierStatus.setUserName(courier.getName());
                                    courierStatus.setTel(courier.getTel());
                                    CourierSettingsVO courierSettings = busService.getCourierSettingsById(courier.getUserId());
                                    courierStatus.setHomeAddress(courierSettings.getHomeAddress());
                                    courierStatus.setHomeLat(courierSettings.getHomeLat());
                                    courierStatus.setHomeLng(courierSettings.getHomeLng());
                                    courierStatus.setOrderLimit(courierSettings.getOrderLimit());
                                    courierStatus.setLowerBound(courierSettings.getLowerBound());
                                    courierStatus.setCarryNum(courierSettings.getCarryNum());
                                    courierStatus.setReceiveOrderStatus(Optional.ofNullable(userExtensionMap.get(courier.getUserId()))
                                            .map(UserExtension::getReceiveOrderStatus)
                                            .orElse(null)
                                    );
                                    courierStatus.setDeliverAreaAddress(courierSettings.getDeliverAreaAddress());
                                    courierStatus.setDeliverAreaLat(courierSettings.getDeliverAreaLat());
                                    courierStatus.setDeliverAreaLng(courierSettings.getDeliverAreaLng());
                                    return courierStatus;
                                })
                                .collect(Collectors.toList())
                        );
                    }
                    return vo;
                })
                .collect(Collectors.toList());
        return vos;
    }

    /**
     * 根据 userId 获取指定计费规则(自己没有，从团队向上寻找)
     */
    public Result userBillingMode(String userId) {
        return Result.getSuccess(busService.getUserExpressBillingModeWithFromTeam(userId));
    }

    /**
     * 获取所有有配送计费规则的人的 id 以及计费信息
     */
    public Result getAvailableBillingModeUsers() {
        UserExpressBillingModeVO result = new UserExpressBillingModeVO();
        List<ExpressBillingMode> allModes = expressBillingModeRepository.findAll();
        if (allModes.isEmpty()) {
            return Result.getSuccess(result);
        }
        // 所有计费规则信息
        result.setAllModes(allModes);

        List<UserExpressBillingModeVO.UserModeInfo> userModeInfos = new ArrayList<>();

        // 所有自己有配送规则的配送员配置
        List<CourierSettings> hasModeUserSettings = courierSettingsDao.findByHasExpressBillingMode();
        List<String> hasModeUsersIds = hasModeUserSettings.stream().map(CourierSettings::getUserId).collect(Collectors.toList());
        for (CourierSettings settings : hasModeUserSettings) {
            UserExpressBillingModeVO.UserModeInfo userModeInfo = new UserExpressBillingModeVO.UserModeInfo();
            userModeInfo.setUserId(settings.getUserId());
            userModeInfo.setModeId(settings.getExpress().getBillingModeId());
            userModeInfos.add(userModeInfo);
        }

        // 所有自己有配送规则的团队
        List<TeamExpressBillingModeBO> teamModes = JsonUtil.parse(teamRepository.findByHasExpressBillingMode(CommonConstants.MRWIND_ID), TeamExpressBillingModeBO.class);
        if (teamModes != null && !teamModes.isEmpty()) {
            Map<String, TeamExpressBillingModeBO> teamParentIdModesMap = CollectionUtil.takeFieldsToMap(teamModes, "parentIds");
            List<Team> hasModeTeams = teamRepository.findByTeamIdIn(teamModes.stream().map(TeamExpressBillingModeBO::getTeamId).collect(Collectors.toList()));

            // 所有自己有配送规则的团队及其包含的成员 id
            HashMap<String, List<String>> hasModeTeamsUserIdsMap = teamService.getFlagUsersMapByFlagTeams(hasModeTeams);
            for (Map.Entry<String, List<String>> entry : hasModeTeamsUserIdsMap.entrySet()) {
                TeamExpressBillingModeBO teamMode = teamParentIdModesMap.get(entry.getKey());
                if (teamMode != null) {
                    List<String> userIds = entry.getValue();
                    for (String userId : userIds) {
                        if (!hasModeUsersIds.contains(userId)) {
                            UserExpressBillingModeVO.UserModeInfo userModeInfo = new UserExpressBillingModeVO.UserModeInfo();
                            userModeInfo.setUserId(userId);
                            userModeInfo.setModeId(teamMode.getModeId());
                            userModeInfos.add(userModeInfo);
                        }
                    }
                }
            }
        }

        if (!userModeInfos.isEmpty()) {
            // 最后给用户头像、电话等字段赋值
            Set<String> allUserIds = userModeInfos.stream().map(UserExpressBillingModeVO.UserModeInfo::getUserId).collect(Collectors.toSet());
            Map<String, User> userMap = CollectionUtil.takeFieldsToMap(userRepository.findByUserIdIn(allUserIds), "userId");
            userModeInfos.forEach(userModeInfo -> {
                User user = userMap.get(userModeInfo.getUserId());
                if (user != null) {
                    userModeInfo.setUserName(user.getName());
                    userModeInfo.setTel(user.getTel());
                }
            });
        }

        result.setUserModeInfos(userModeInfos);

        return Result.getSuccess(result);
    }

    public Result getUserInfoByTels(Set<String> tels) {
        List<Map<String, Object>> maps = new ArrayList<>();
        if (tels == null || tels.isEmpty()) {
            return Result.getSuccess(maps);
        }
        List<User> users = userRepository.findByTelIn(tels);
        for (User user : users) {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", user.getUserId());
            map.put("tel", user.getTel());
            map.put("avatar", user.getAvatar());
            map.put("name", user.getName());
            maps.add(map);
        }
        return Result.getSuccess(maps);
    }

    /**
     * 批量获取人员的接单状态
     */
    public Result getCouriersReceiveOrderStatus(Set<String> userIds) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : busService.getCourierReceiveOrderStatus(userIds).entrySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", entry.getKey());
            map.put("status", entry.getValue());
            result.add(map);
        }
        return Result.getSuccess(result);
    }

    /**
     * 根据经纬度获取附近的仓
     */
    public List<StoreInfoDTO> getNearStore(double lat, double lng) {
        List<StoreInfoDTO> result = new ArrayList<>();
        List<Team> stores = teamRepository.findByTypeAndRootId(TeamType.STORE, CommonConstants.MRWIND_ID);
        List<String> storeIds = stores.stream().map(Team::getTeamId).collect(Collectors.toList());
        if (storeIds.isEmpty()) {
            return result;
        }

        Map<String, Team> storeMap = CollectionUtil.takeFieldsToMap(stores, "teamId");
        Map<String, TeamExtention> storeExtensionMap = CollectionUtil.takeFieldsToMap(teamExtentionRepository.findByTeamIdIn(storeIds), "teamId");

        List<DBObject> dbObjects = teamPositionDao.nearSphere("gpsInfo", lat, lng, storeIds);
        for (DBObject dbObject : dbObjects) {
            String storeId = (String) dbObject.get("_id");
            StoreInfoDTO dto = new StoreInfoDTO();
            Team store = storeMap.get(storeId);
            if (store != null) {
                dto.setStoreId(store.getTeamId());
                dto.setStoreNo(store.getRemark());
                dto.setStoreName(store.getName());
            }
            TeamExtention storeExtension = storeExtensionMap.get(storeId);
            if (storeExtension != null) {
                dto.setStoreAddress(storeExtension.getFlAdress());
                dto.setLinker(storeExtension.getFlLinker());
                dto.setLinkerTel(storeExtension.getFlTel());
            }
            BasicDBObject gpsInfoObject = (BasicDBObject) dbObject.get("gpsInfo");
            dto.setStoreLat(gpsInfoObject.getDouble("lat"));
            dto.setStoreLng(gpsInfoObject.getDouble("lng"));
            dto.setDistance(AlgReUtil.getDistance(lat, lng, dto.getStoreLat(), dto.getStoreLng()));
            result.add(dto);
        }
        return result;
    }

    /**
     * 批量获取人员基本信息
     *
     * @param userIds    人员 id 集合
     * @param rootTeamId 根团队 id
     */
    public List<UserBasicInfoVO> batchUserBasicInfo(Set<String> userIds, String rootTeamId) {
        List<UserBasicInfoVO> vos = new ArrayList<>();
        if (userIds == null || userIds.isEmpty()) {
            return vos;
        }
        Team rootTeam = teamRepository.findByTeamId(rootTeamId);
        List<UserTeamRelation> relations = userTeamRelationRepository.findByUserIdInAndInRootTeamId(userIds, rootTeamId);
        Map<String, UserTeamRelation> relationMap = CollectionUtil.takeFieldsToMap(relations, "userId");
        List<String> teamIds = relations.stream().map(UserTeamRelation::getTeamId).collect(Collectors.toList());
        Map<String, Team> teamMap = new HashMap<>();
        if (!teamIds.isEmpty()) {
            teamMap = CollectionUtil.takeFieldsToMap(teamRepository.findByTeamIdIn(teamIds), "teamId");
        }
        List<User> users = userRepository.findByUserIdIn(userIds);
        for (User user : users) {
            UserBasicInfoVO vo = new UserBasicInfoVO();
            vo.setUserId(user.getUserId());
            vo.setName(user.getName());
            vo.setAvatar(user.getAvatar());
            vo.setTel(user.getTel());
            vo.setCurrentCountry(user.getCurrentCountry());
            vo.setRootTeamId(Optional.ofNullable(rootTeam).map(Team::getTeamId).orElse(null));
            vo.setRootTeamName(Optional.ofNullable(rootTeam).map(Team::getName).orElse(null));
            UserTeamRelation relation = relationMap.get(user.getUserId());
            if (relation != null) {
                vo.setTeamId(Optional.ofNullable(teamMap.get(relation.getTeamId())).map(Team::getTeamId).orElse(null));
                vo.setTeamName(Optional.ofNullable(teamMap.get(relation.getTeamId())).map(Team::getName).orElse(null));
            }
            vos.add(vo);
        }
        return vos;
    }

    public Object checkIsMember(String leaderId, String userId, String rootTeamId) {
        return userTeamService.checkIsMember(leaderId, userId, rootTeamId);
    }
}
