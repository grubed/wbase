package com.mrwind.windbase.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by CL-J on 2018/7/26.
 */
public class RemoveUserRoleDTO {
    @NotNull(message = "error.parameter")
    private String userId;
    @NotNull(message = "error.parameter")
    private String teamId;
    @NotNull(message = "error.parameter")
    private String roleId;

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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
