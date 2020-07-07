package com.mrwind.windbase.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UserOperateTeamDTO {
    @NotNull
    private String userId;

    @NotNull
    private List<IdDTO> teamIdList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<IdDTO> getTeamIdList() {
        return teamIdList;
    }

    public void setTeamIdList(List<IdDTO> teamIdList) {
        this.teamIdList = teamIdList;
    }
}
