package com.mrwind.windbase.dto;

import com.mrwind.windbase.entity.mysql.Team;

import java.util.List;

public class TeamManageDto {
    private List<Team> teamList;
    private Boolean isManage;

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    public Boolean getManage() {
        return isManage;
    }

    public void setManage(Boolean manage) {
        isManage = manage;
    }
}
