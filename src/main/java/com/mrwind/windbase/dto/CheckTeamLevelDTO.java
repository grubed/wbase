package com.mrwind.windbase.dto;

import javax.validation.constraints.NotEmpty;

/**
 * Description
 *
 * @author hanjie
 * @date 2018-12-29
 */

public class CheckTeamLevelDTO {

    @NotEmpty(message = "missing param: memberId")
    private String memberId;

    private String leaderId;

    private int notificationLevel;

    private int effectiveLevel;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public int getNotificationLevel() {
        return notificationLevel;
    }

    public void setNotificationLevel(int notificationLevel) {
        this.notificationLevel = notificationLevel;
    }

    public int getEffectiveLevel() {
        return effectiveLevel;
    }

    public void setEffectiveLevel(int effectiveLevel) {
        this.effectiveLevel = effectiveLevel;
    }

}
