package com.mrwind.windbase.service;

import com.mrwind.windbase.common.constant.PushActionType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 推送
 *
 * @author hanjie
 * @date 2018/8/20
 */

@Service
public class PushMsgService extends BaseService {

    /**
     * 给成员 - 静默推送, 成员被移除分组
     */
    public void pushTeamMemberDeletedToMember(List<String> to, Map<String, Object> pushData, String teamName) {
//        new PushRequest.Builder<Map<String, Object>>(to, getUserLanguageMap())
//                .setActionType(PushActionType.TEAM_USER_DELETED)
//                .setData(pushData)
//                .setSilent(true)
//                .build()
//                .push();
    }

    /**
     * team用户移动给原来team的管理员推送
     */
    public void pushOldTeamManagerMoveMember(List<String> to, String userName, Map<String, Object> oldTeamMap) {
//        new PushRequest.Builder<>(to, getUserLanguageMap())
//                .setActionType(PushActionType.TEAM_MEMBER_MOVE_OLD_PUSH)
//                .setData(oldTeamMap)
//                .setSilent(true)
//                .build()
//                .push();
    }

    /**
     * team用户移动给新team的管理员推送
     */
    public void pushNewTeamManagerMoveMember(List<String> to, String userName, Map<String, Object> userMap) {
//        new PushRequest.Builder<>(to, getUserLanguageMap())
//                .setActionType(PushActionType.TEAM_MEMBER_MOVE_NEW_PUSH)
//                .setData(userMap)
//                .setSilent(true)
//                .build()
//                .push();
    }

    /**
     * team中用户移动给被移动的用户推送
     */
    public void pushMoveMember(List<String> to, Map<String, Object> newTeamMap) {
//        new PushRequest.Builder<>(to, getUserLanguageMap())
//                .setActionType(PushActionType.TEAM_MEMBER_MOVE_USER_PUSH)
//                .setData(newTeamMap)
//                .setSilent(true)
//                .build()
//                .push();
    }

    /**
     * 合并组给本组（包含子组）所有成员+直线组长（爸爸爷爷祖爷爷）推送
     */
    public void pushMergeTeam(List<String> to, String userName, String teamListName, String teamName, Map<String, Object> teamMap) {
//        new PushRequest.Builder<>(to, getUserLanguageMap())
//                .setActionType(PushActionType.TEAM_MERGE_PUSH)
//                .setData(teamMap)
//                .setSilent(true)
//                .build()
//                .push();
    }

    /**
     * 修改组名推送
     */
    public void pushEditTeamName(List<String> to, String userName, String oldTeamName, String newTeamName, Map<String, Object> teamMap) {
//        new PushRequest.Builder<>(to, getUserLanguageMap())
//                .setActionType(PushActionType.TEAM_NAME_EDIT_PUSH)
//                .setData(teamMap)
//                .setSilent(true)
//                .build()
//                .push();
    }

    /**
     * 修改组地址推送
     */
    public void pushEditTeamAddress(List<String> to, String userName, String teamName, String newTeamAddress, Map<String, Object> teamMap) {
//        new PushRequest.Builder<>(to, getUserLanguageMap())
//                .setActionType(PushActionType.TEAM_ADDRESS_EDIT_PUSH)
//                .setData(teamMap)
//                .setSilent(true)
//                .build()
//                .push();
    }

    /**
     * 删除组推送
     */
    public void pushDeleteTeam(List<String> to, String userName, String teamName, Map<String, Object> teamMap) {
//        new PushRequest.Builder<>(to, getUserLanguageMap())
//                .setActionType(PushActionType.TEAM_DELETE_PUSH)
//                .setData(teamMap)
//                .setSilent(true)
//                .build()
//                .push();
    }

    /**
     * 账号在别处登录推送
     */
    public void pushAlreadyLoginTeam(List<String> to) {
//        new PushRequest.Builder<>(to, getUserLanguageMap())
//                .setActionType(PushActionType.ACCOUNT_ALREADY_LOGIN)
//                .setSilent(true)
//                .build()
//                .push();
    }



}
