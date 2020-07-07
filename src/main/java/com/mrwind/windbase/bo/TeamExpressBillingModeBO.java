package com.mrwind.windbase.bo;

/**
 * Description
 *
 * @author hanjie
 * @date 2018-12-13
 */

public class TeamExpressBillingModeBO {

    private String teamId;
    private String parentIds;
    private String modeId;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getModeId() {
        return modeId;
    }

    public void setModeId(String modeId) {
        this.modeId = modeId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }
}
