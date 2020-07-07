package com.mrwind.windbase.dto;

import javax.validation.constraints.NotBlank;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/7/24
 */

public class UpdateCurrentTeamIdDTO {

    @NotBlank(message = "missing param: currentTeamId")
    private String currentTeamId;
    @NotBlank(message = "missing param: userId")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrentTeamId() {
        return currentTeamId;
    }

    public void setCurrentTeamId(String currentTeamId) {
        this.currentTeamId = currentTeamId;
    }

    @Override
    public String toString() {
        return "UpdateCurrentTeamIdDTO{" +
                "currentTeamId='" + currentTeamId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
