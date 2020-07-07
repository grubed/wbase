package com.mrwind.windbase.vo;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by CL-J on 2018/8/5.
 */
public class UserRoleVO {
    @NotNull(message = "error.parameter")
    private String userId;
    @NotNull(message = "error.parameter")
    private String teamId;
    @NotNull(message = "error.parameter")
    private List<String> roleIds;

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

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }
}
