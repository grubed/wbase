package com.mrwind.windbase.common.constant;

/**
 * 推送 Action type 标识
 *
 * @author hanjie
 * @date 2018/8/20
 */

public class PushActionType {

    /**
     * 给成员 - 成员被移除分组
     */
    public static final String TEAM_USER_DELETED = "team.user.deleted";

    /**
     * team用户移动给原来team的管理员推送标识
     */
    public static final String TEAM_MEMBER_MOVE_OLD_PUSH = "team.member.move.old.push";

    /**
     * team用户移动给新team的管理员推送标识
     */
    public static final String TEAM_MEMBER_MOVE_NEW_PUSH = "team.member.move.new.push";

    /**
     * team用户移动给被移动的用户推送标识
     */
    public static final String TEAM_MEMBER_MOVE_USER_PUSH = "team.member.move.user.push";

    /**
     * 合并分组推送标识
     */
    public static final String TEAM_MERGE_PUSH = "team.merge.push";

    /**
     * 修改组名称推送标识
     */
    public static final String TEAM_NAME_EDIT_PUSH = "team.name.edit.push";

    /**
     * 修改组地址推送标识
     */
    public static final String TEAM_ADDRESS_EDIT_PUSH = "team.address.edit.push";

    /**
     * 删除组推送标识
     */
    public static final String TEAM_DELETE_PUSH = "team.delete.push";

    /**
     * 账号在别处登录
     */
    public static final String ACCOUNT_ALREADY_LOGIN = "account.already.login";

}
