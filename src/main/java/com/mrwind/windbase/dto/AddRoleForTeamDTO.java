package com.mrwind.windbase.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by CL-J on 2018/7/26.
 */
public class AddRoleForTeamDTO {

    @NotNull(message = "error.parameter")
    private String teamId;
    @NotNull(message = "error.parameter")
    private List<String> roleIds;
    /**
     * 如果是将团队设置为仓库操作则传，否则不需要传
     */
    private String project;

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

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
    
}
