package com.mrwind.windbase.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by CL-J on 2018/7/26.
 */
public class RemoveTeamRoleDTO {
    @NotNull(message = "error.parameter")
    private String teamId;
    @NotNull(message = "error.parameter")
    private List<String> roleIds;

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
