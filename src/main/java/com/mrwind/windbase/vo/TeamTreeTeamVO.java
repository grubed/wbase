package com.mrwind.windbase.vo;

/**
 * Created by CL-J on 2018/9/25.
 */
public class TeamTreeTeamVO {
    private String teamId;
    private String name;

    public TeamTreeTeamVO(String teamId, String name) {
        this.teamId = teamId;
        this.name = name;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
