package com.mrwind.windbase.vo;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-01-14
 */

public class TeamSwitchVO {

    private String targetTeamId;

    private String status;

    public TeamSwitchVO() {
    }

    public TeamSwitchVO(String targetTeamId, String status) {
        this.targetTeamId = targetTeamId;
        this.status = status;
    }

    public String getTargetTeamId() {
        return targetTeamId;
    }

    public void setTargetTeamId(String targetTeamId) {
        this.targetTeamId = targetTeamId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
