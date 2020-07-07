package com.mrwind.windbase.service;

import com.google.common.collect.Lists;
import com.mrwind.windbase.common.util.CommUtil;
import com.mrwind.windbase.common.util.LocaleType;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.common.util.TextUtils;
import com.mrwind.windbase.dto.AddManagerDTO;
import com.mrwind.windbase.dto.DeleteAndResignDTO;
import com.mrwind.windbase.dto.DeleteMemberDTO;
import com.mrwind.windbase.dto.MoveMemberDTO;
import com.mrwind.windbase.entity.mysql.Team;
import com.mrwind.windbase.entity.mysql.TeamResignationRelation;
import com.mrwind.windbase.entity.mysql.User;
import com.mrwind.windbase.entity.mysql.UserTeamRelation;
import com.mrwind.windbase.vo.RootTeamListVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Description
 *
 * @author hanjie
 */

@Service
public class UserTeamService extends BaseService {

    /**
     * 获取所有下属
     *
     * @return 所有下属
     */
    public Result getAllMembers(String rootId) {
        List<User> userList = userRepository.queryUsersByTeamId(rootId);
        return Result.getSuccess(userList);
    }

    /**
     * 添加管理员
     *
     * @param addManagerDTO
     * @return 返回参数
     */
    @Transactional(rollbackFor = Exception.class)
    public Result addManager(AddManagerDTO addManagerDTO) {
        boolean userIdListCheck = userService.userParamCheck(Arrays.asList(addManagerDTO.getUserId()), addManagerDTO.getTeamId());
        if (!userIdListCheck) {
            return Result.getFailI18N("error.user.operation.refresh");
        }
        String uid = addManagerDTO.getUserId();
        String tid = addManagerDTO.getTeamId();
        User user = userRepository.findByUserId(uid);
        if (user == null) {
            return Result.getFailI18N("error.user.not.found");
        }

        String operatorId = getCurrentUser().getUserId();
        if (!checkCanManageTeam(operatorId, tid)) {
            return Result.getFailI18N("error.permission.denied");
        }
        //新增管理员
        userTeamRelationService.setTeamManager(tid, uid, true);

        return Result.getSuccess();
    }

    /**
     * 批量移动团队成员或管理员
     *
     * @param moveMemberDTO 请求参数
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public Result batchMoveMembers(MoveMemberDTO moveMemberDTO) {
        boolean userIdListCheck = userService.userParamCheck(moveMemberDTO.getUserIds(), moveMemberDTO.getFromTeamId());
        if (!userIdListCheck) {
            return Result.getFailI18N("error.user.operation.refresh");
        }

        String operatorId = getCurrentUser().getUserId();
        boolean isManager = false;
        if (checkCanManageTeam(operatorId, moveMemberDTO.getFromTeamId())) {
            isManager = checkCanManageTeam(operatorId, moveMemberDTO.getToTeamId());
        }
        if (!isManager) {
            return Result.getFailI18N("error.permission.denied");
        }

        List<UserTeamRelation> saveList = new ArrayList<>();
        for (String userId : moveMemberDTO.getUserIds()) {
            UserTeamRelation aRelation = new UserTeamRelation();
            aRelation.setUserId(userId);
            aRelation.setTeamId(moveMemberDTO.getToTeamId());
            aRelation.setMananger(false);
            saveList.add(aRelation);

            //删除原团队关系
            userTeamRelationRepository.deleteAllByUserIdAndTeamId(userId, moveMemberDTO.getFromTeamId());
        }

        //添加新团队关系
        userTeamRelationRepository.saveAll(saveList);

        //获取team的rootTeamId
        String rootTeamId = teamRepository.findByTeamId(moveMemberDTO.getFromTeamId()).getRootId();
        //获取需要被移动的用户名字
        String name = userRepository.findNameByUserIdList(moveMemberDTO.getUserIds());
        List<String> teamIdList = new ArrayList<>();
        //给原来组管理员推送
        teamIdList.add(moveMemberDTO.getFromTeamId());
        List<String> oldTeamManagerList = userTeamRelationRepository.findManagerIdListByTeamIdList(teamIdList);

        Map<String, Object> oldTeamMap = new HashMap<>();
        oldTeamMap.put("oldTeamId", moveMemberDTO.getFromTeamId());
        //单纯的推送
        pushMsgService.pushOldTeamManagerMoveMember(oldTeamManagerList, name, oldTeamMap);

        //系统通知+内部自带的推送
//        Map<String, String> pushContentToOldManager = LocaleType.getMessageMap("push.team.member.move.old.push", name);
//        MessageConfig config = MessageConfig.buildConfig(
//                oldTeamManagerList,
//                getUserLanguageMap(),
//                pushContentToOldManager,
//                rootTeamId
//        );
//        windChatApi.sendText(config, pushContentToOldManager);


        //给新组的管理员推送
        teamIdList.clear();
        teamIdList.add(moveMemberDTO.getToTeamId());
        List<String> newTeamManagerList = userTeamRelationRepository.findManagerIdListByTeamIdList(teamIdList);
        List<User> users = userRepository.findByUserIdIn(moveMemberDTO.getUserIds());
        Map<String, Object> userMap;
        //应给新的管理员逐个发送每个被移动成员的卡片,即一个管理员收到多个用户被移动的推送及系统通知
        for (int i = 0, length = users.size(); i < length; i++) {

            String userId = users.get(i).getUserId();
            String userName = users.get(i).getName();
            String userAvatar = userRepository.findByUserId(userId).getAvatar();

            userMap = new HashMap<>();
            userMap.put("userId", userId);
            userMap.put("userName", userName);
            userMap.put("newTeamId", moveMemberDTO.getToTeamId());
            //单纯的推送
            pushMsgService.pushNewTeamManagerMoveMember(newTeamManagerList, users.get(i).getName(), userMap);

            //系统通知+内部自带的推送
//            Map<String, String> pushContentToNewManager = LocaleType.getMessageMap("push.team.member.move.new.push", userName);
//            MessageConfig textMsgConfig = MessageConfig.buildConfig(
//                    newTeamManagerList,
//                    getUserLanguageMap(),
//                    pushContentToNewManager,
//                    rootTeamId
//            );
//            windChatApi.sendText(textMsgConfig, pushContentToNewManager);
//            MessageConfig cardMsgConfig = MessageConfig.buildConfig(
//                    newTeamManagerList,
//                    getUserLanguageMap(),
//                    LocaleType.getMessageMap("chat.msg.type.card"),
//                    rootTeamId
//            );
//            windChatApi.sendCard(cardMsgConfig, userId, userName, userAvatar);
        }

        // 添加操作记录
        for (String userId : moveMemberDTO.getUserIds()) {
            teamMemberSummaryDao.addNewMember(userId, moveMemberDTO.getToTeamId(), rootTeamId);
        }

        //给被移动的用户发送通知
        Map<String, Object> newTeamMap = new HashMap<>();
        Team newTeam = teamRepository.findByTeamId(moveMemberDTO.getToTeamId());

        newTeamMap.put("newTeamId", newTeam.getTeamId());
        newTeamMap.put("newTeamName", newTeam.getName());
        newTeamMap.put("oldTeamId", moveMemberDTO.getFromTeamId());
        //单纯的推送
        pushMsgService.pushMoveMember(moveMemberDTO.getUserIds(), newTeamMap);

        //系统通知+内部自带的推送
//        Map<String, String> pushContentToMoveMember = LocaleType.getMessageMap("push.team.member.move.user.push", newTeam.getName());
//        MessageConfig config2 = MessageConfig.buildConfig(
//                moveMemberDTO.getUserIds(),
//                getUserLanguageMap(),
//                pushContentToMoveMember,
//                rootTeamId
//        );
//
//        windChatApi.sendText(config2, pushContentToMoveMember);
        return Result.getSuccess();
    }

    /**
     * 删除某个成员
     *
     * @param deleteMemberDTO 删除的成员对象
     * @return 操作结果
     */
    @Transactional(rollbackFor = Exception.class)
    public Result deleteAndResignMembers(DeleteAndResignDTO deleteMemberDTO) {
        User deleteUser = userRepository.findByUserId(deleteMemberDTO.getUserId());
        if (deleteUser == null) {
            return Result.getFailI18N("error.user.not.found");
        }
        String currentTeamId = getCurrentUser().getCurrentTeamId();
        String teamId = deleteMemberDTO.getTeamId();
        Team team = teamRepository.findByTeamId(deleteMemberDTO.getTeamId());
        Team root = teamRepository.findByTeamId(team.getRootId());
        String name = deleteUser.getName();
        TeamResignationRelation teamResignationRelation = new TeamResignationRelation(name + "(已离职)", teamId, team.getName(), root.getTeamId(), root.getName(), deleteMemberDTO.getUserId(), deleteMemberDTO.getReason(), deleteMemberDTO.getLastSignTime());
        //1. 删除团队关系
        userTeamRelationRepository.deleteAllByUserIdAndTeamId(deleteMemberDTO.getUserId(), teamId);
        //2 . 离职表中添加这些人
        teamResignationRepository.save(teamResignationRelation);

        // 添加每日离职人员统计
        teamMemberSummaryDao.addQuitMember(deleteMemberDTO.getUserId(), teamId, teamRepository.getRootTeamId(teamId));

        // 推送给成员通知其被移除
        List<String> pushToIds = getUserLeaders(deleteMemberDTO.getUserId(), currentTeamId).stream().map(User::getUserId).collect(Collectors.toList());
        Map<String, String> msgContent = LocaleType.getMessageMap("push.team.member.delete.push", deleteUser.getName(), currentTeamId);
//        MessageConfig config = MessageConfig.buildConfig(
//                pushToIds,
//                getUserLanguageMap(),
//                msgContent,
//                getCurrentUser().getCurrentTeamId()
//        );
//        windChatApi.sendText(config, msgContent);

        Map<String, Object> pushData = new HashMap<>();
        pushData.put("teamId", deleteMemberDTO.getTeamId());
        pushMsgService.pushTeamMemberDeletedToMember(Arrays.asList(deleteMemberDTO.getUserId()), pushData, team.getName());

        return Result.getSuccess();
    }

    @Transactional(rollbackFor = Exception.class)
    public Result deleteMembers(DeleteMemberDTO deleteMemberDTO) {
        boolean userIdListCheck = userService.userParamCheck(deleteMemberDTO.getUserIds(), deleteMemberDTO.getTeamId());
        if (!userIdListCheck) {
            return Result.getFailI18N("error.user.operation.refresh");
        }
        String currentTeamId = getCurrentUser().getCurrentTeamId();
        String teamId = deleteMemberDTO.getTeamId();
        Team team = teamRepository.findByTeamId(teamId);
        if (team == null) {
            return Result.getFailI18N("error.team.not.found");
        }
        List<String> pushToIds = null;
        for (String userId : deleteMemberDTO.getUserIds()) {
            User user = userRepository.findByUserId(userId);
            if (user == null) {
                continue;
            }
            userTeamRelationRepository.deleteAllByUserIdAndTeamId(userId, teamId);
            // 发送系统通知给直属 leader
            if (pushToIds == null) {
                pushToIds = getUserLeaders(userId, currentTeamId).stream().map(User::getUserId).collect(Collectors.toList());
            }
            Map<String, String> msgContent = LocaleType.getMessageMap("push.team.member.delete.push", user.getName());
//            MessageConfig config = MessageConfig.buildConfig(
//                    pushToIds,
//                    getUserLanguageMap(),
//                    msgContent,
//                    getCurrentUser().getCurrentTeamId()
//            );
//            windChatApi.sendText(config, msgContent);
        }

        // 推送给成员通知其被移除
        Map<String, Object> pushData = new HashMap<>();
        pushData.put("teamId", deleteMemberDTO.getTeamId());
        pushMsgService.pushTeamMemberDeletedToMember(deleteMemberDTO.getUserIds(), pushData, team.getName());

        return Result.getSuccess();
    }


    /**
     * 获取团队列表
     *
     * @return 团队信息
     */
    public Result getRootTeamListByUserId(String userId) {
        if (TextUtils.isEmpty(userId)) {
            userId = getCurrentUser().getUserId();
        }
        User user = userRepository.findByUserId(userId);
        List<Team> teams = teamRepository.findUserAllRoots(userId);
        List<RootTeamListVO> resultTeams = new ArrayList<>();
        for (Team team : teams) {
            RootTeamListVO vo = new RootTeamListVO();
            CommUtil.updateField(team, vo);
            vo.setUserTeamName(Optional.ofNullable(user).map(User::getName).orElse(null));
            vo.setManager(isManager(userId, team.getTeamId()));
            resultTeams.add(vo);
        }
        return Result.getSuccess(resultTeams);
    }

    /**
     * 删除管理员
     *
     * @param tid 团队id
     * @param uid 用户id
     * @return 团队信息
     */
    @Transactional(rollbackFor = Exception.class)
    public Result deleteManager(String tid, String uid) {
        if (!checkCanManageTeam(getCurrentUser().getUserId(), tid)) {
            return Result.getFailI18N("error.permission.denied");
        }

        userTeamRelationRepository.updateRoleIdByUserIdAndTeamId(false, uid, tid);

        return Result.getSuccess();
    }


    /**
     * 校验当前请求人员对某团队是否有操作权限
     *
     * @param teamId 团队id
     * @return 是否可以管理该团队
     */
    public boolean checkCanManageTeam(String userId, String teamId) {

        String parentIds = teamRepository.queryParentIdsByTeamId(teamId);

        if (StringUtils.isEmpty(parentIds)) {
            return false;
        }

        List<String> pNos = Lists.newArrayList(parentIds.split(","));

        List<String> pIds = teamRepository.findByTeamIdNoIn(pNos.stream().map(Long::valueOf).collect(Collectors.toList())).stream().map(Team::getTeamId).collect(Collectors.toList());

        List<UserTeamRelation> userTeamRelations = userTeamRelationRepository.findByTeamIdInAndUserId(pIds, userId);

        return userTeamRelations.stream().anyMatch(UserTeamRelation::isMananger);
    }

    /**
     * 通过userId获取到对应所有的团队ID
     *
     * @param userId 用户ID
     * @return
     */
    public List<UserTeamRelation> getUserTeamRelationWithUserId(String userId) {
        return userTeamRelationRepository.findByUserId(userId);
    }

    /**
     * 判断在一个目标团队范围内 user2 是否为 user1 的下属，即只要 user1 在目标团队以及任意子团队内为 user2 的管理员
     *
     * @param leaderId     上级 id
     * @param userId       下属 id
     * @param targetTeamId 目标团队 id
     * @return user2 为 user1 下属返回 true，反之 false
     */
    public boolean checkIsMember(String leaderId, String userId, String targetTeamId) {
        if (TextUtils.equals(leaderId, userId)) {
            return false;
        }
        // 1. 查询 user1 担任管理员的所有团队
        Team targetTeam = teamRepository.findByTeamId(targetTeamId);
        if (targetTeam == null) {
            return false;
        }
        boolean isMember = false;
        List<Team> manageTeams = teamRepository.findManageTeamsByTeamId(leaderId, targetTeam.getParentIds(),
                targetTeam.getParentIds() + ",");
        //  leader 所有管理团队的流水号 62 - 62,45
        Set<String> manageTeamNos = new HashSet<>();
        for (Team team : manageTeams) {
            manageTeamNos.add(team.getParentIds());
        }
        // 2. 查询 user2 是否在这些团队内
        List<Team> teams = teamRepository.findUserAllTeamsInParentIds(userId, targetTeam.getParentIds(),
                targetTeam.getParentIds() + ",");
        for (Team team : teams) {
            for (String manageTeamNo : manageTeamNos) {
                if (team.getParentIds().startsWith(manageTeamNo)) {
                    isMember = true;
                    break;
                }
            }
        }
        return isMember;
    }

    public Result getUserOnlyTeam(String userId, String root) {
        return Result.getSuccess(userTeamRelationRepository.findUserManagerTeam(root, userId));
    }

    public Result isMrWind(String userId) {
        List<UserTeamRelation> userTeamRelations = userTeamRelationRepository.findByUserId(userId);
        boolean mrwind = userTeamRelations.stream().map(e -> teamRepository.findByTeamId(e.getTeamId())).anyMatch(t ->
                TextUtils.equals(t.getRootId(), "5afd5817cd0bd80ecf11e0dc")
        );

        return Result.getSuccess(mrwind);
    }

}
