package com.mrwind.windbase.dto;

import javax.validation.constraints.NotBlank;

/**
 * Created by CL-J on 2018/8/17.
 */
public class DeleteAndResignDTO {
    /**团队id*/
    @NotBlank(message = "error.team.id.null")
    private String teamId;

    @NotBlank(message = "error.param")
    private String userId;

    private String reason;

    private String lastSignTime;

    public DeleteAndResignDTO() {
    }

    public DeleteAndResignDTO(@NotBlank(message = "error.team.id.null") String teamId, @NotBlank(message = "error.param") String userId, String reason, String lastSignTime) {
        this.teamId = teamId;
        this.userId = userId;
        this.reason = reason;
        this.lastSignTime = lastSignTime;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLastSignTime() {
        return lastSignTime;
    }

    public void setLastSignTime(String lastSignTime) {
        this.lastSignTime = lastSignTime;
    }
}
