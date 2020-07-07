package com.mrwind.windbase.vo;

import java.util.List;

/**
 * Created by CL-J on 2018/9/25.
 */
public class TeamTreeInfoVO {

    private String teamId;
    private String name;
    private List<TeamTreeTeamVO> parents;
    private List<TeamTreeTeamVO> children;
    private List<TeamTreeUserVO> users;

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

    public List<TeamTreeTeamVO> getParents() {
        return parents;
    }

    public void setParents(List<TeamTreeTeamVO> parents) {
        this.parents = parents;
    }

    public List<TeamTreeTeamVO> getChildren() {
        return children;
    }

    public void setChildren(List<TeamTreeTeamVO> children) {
        this.children = children;
    }

    public List<TeamTreeUserVO> getUsers() {
        return users;
    }

    public void setUsers(List<TeamTreeUserVO> users) {
        this.users = users;
    }
}
