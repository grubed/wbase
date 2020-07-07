package com.mrwind.windbase.vo;

/**
 * Created by CL-J on 2018/7/24.
 */
public class TeamMemberCountVO {

    private String teamId;

    private String name;

    private int memberCount;

    private boolean hasChildrens;

    public TeamMemberCountVO() {
    }

    public TeamMemberCountVO(String teamId, String name, int memberCount) {
        this.teamId = teamId;
        this.name = name;
        this.memberCount = memberCount;
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

}
