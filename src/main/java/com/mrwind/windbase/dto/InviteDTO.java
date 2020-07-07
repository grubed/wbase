package com.mrwind.windbase.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by CL-J on 2018/7/24.
 */
public class InviteDTO {

    @NotNull(message = "error param: userId")
    private String userId;

    @NotNull(message = "error param: teamId")
    private String teamId;

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
    
}
