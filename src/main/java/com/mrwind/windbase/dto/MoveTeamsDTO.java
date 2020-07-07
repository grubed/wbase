package com.mrwind.windbase.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class MoveTeamsDTO {
    @NotNull
    private  String targetTeamId;

    @NotNull
    private List<String> teamIdList;

    public String getTargetTeamId() {
        return targetTeamId;
    }

    public void setTargetTeamId(String targetTeamId) {
        this.targetTeamId = targetTeamId;
    }

    public List<String> getTeamIdList() {
        return teamIdList;
    }

    public void setTeamIdList(List<String> teamIdList) {
        this.teamIdList = teamIdList;
    }
}
