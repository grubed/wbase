package com.mrwind.windbase.dto;

import javax.validation.constraints.NotNull;

public class UpdateTeamDTO {
    @NotNull
    private String teamId;

    private String teamName;

    private String expressBillingModeId;
    /**
     * 团队取派开始时间
     */
    private String expressStartTime;
    /**
     * 团队取派结束时间
     */
    private String expressEndTime;

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

    public String getExpressBillingModeId() {
        return expressBillingModeId;
    }

    public void setExpressBillingModeId(String expressBillingModeId) {
        this.expressBillingModeId = expressBillingModeId;
    }

    public String getExpressStartTime() {
        return expressStartTime;
    }

    public void setExpressStartTime(String expressStartTime) {
        this.expressStartTime = expressStartTime;
    }

    public String getExpressEndTime() {
        return expressEndTime;
    }

    public void setExpressEndTime(String expressEndTime) {
        this.expressEndTime = expressEndTime;
    }
    
}
