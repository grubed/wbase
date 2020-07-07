package com.mrwind.windbase.dto;


import com.mrwind.windbase.entity.mongo.TeamPosition;
import com.mrwind.windbase.entity.mysql.Team;
import com.mrwind.windbase.entity.mysql.TeamExtention;

/**
 * Created by CL-J on 2018/8/2.
 */
public class TeamInfoPositionDTO {

    private Team team;

    private TeamExtention teamExtention;

    private TeamPosition teamPosition;

    public TeamInfoPositionDTO(Team team, TeamExtention teamExtention, TeamPosition teamPosition) {
        this.team = team;
        this.teamExtention = teamExtention;
        this.teamPosition = teamPosition;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public TeamExtention getTeamExtention() {
        return teamExtention;
    }

    public void setTeamExtention(TeamExtention teamExtention) {
        this.teamExtention = teamExtention;
    }

    public TeamPosition getTeamPosition() {
        return teamPosition;
    }

    public void setTeamPosition(TeamPosition teamPosition) {
        this.teamPosition = teamPosition;
    }


}
