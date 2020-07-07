package com.mrwind.windbase.bo;

import java.io.Serializable;

/**
 * @author wuyiming
 * Created by wuyiming on 2018/7/20.
 */
public class UserTeamRoleBO implements Serializable {
    /**用户id*/
    private String userId;
    /**用户名*/
    private String userName;
    /**用户姓名*/
    private String name;
    /**用户电话*/
    private String userTel;
    /**用户头像*/
    private String userAvatar;
    /**团队id*/
    private String teamId;
    /**团队名称*/
    private String teamName;
    /**团队头像*/
    private String teamAvatar;
    /**是否为管理员*/
    private Boolean isManager;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamAvatar() {
        return teamAvatar;
    }

    public void setTeamAvatar(String teamAvatar) {
        this.teamAvatar = teamAvatar;
    }

    public Boolean getIsManager() {
        return isManager;
    }

    public void setIsManager(Boolean manager) {
        isManager = manager;
    }
}
