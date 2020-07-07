package com.mrwind.windbase.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by CL-J on 2018/8/1.
 */
public class UserTeamIdDTO {
    @NotNull
    private String userId;
    @NotNull
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
