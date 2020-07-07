package com.mrwind.windbase.dto;

import com.mrwind.windbase.common.util.LocaleType;

import javax.validation.constraints.NotNull;

/**
 * Created by zjw on 2018/7/23.
 */
public class TeamDTO {
    private String teamId;

    @NotNull(message = "名称不能为空")
    private String name;

    private String avatar;

    private String parentId;

    private String userId;

    private int type;

    private String project;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public TeamDTO(){

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
