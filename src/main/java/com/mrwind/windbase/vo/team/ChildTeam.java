package com.mrwind.windbase.vo.team;

import java.util.List;

/**
 * Description
 *
 * @author hanjie
 * @date 2018-12-08
 */

public class ChildTeam {

    private String teamId;
    private String name;
    private Integer type;
    private int memberCount;
    private boolean hasChildrens;
    private List<String> memberNames;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public boolean isHasChildrens() {
        return hasChildrens;
    }

    public void setHasChildrens(boolean hasChildrens) {
        this.hasChildrens = hasChildrens;
    }

    public List<String> getMemberNames() {
        return memberNames;
    }

    public void setMemberNames(List<String> memberNames) {
        this.memberNames = memberNames;
    }

}
