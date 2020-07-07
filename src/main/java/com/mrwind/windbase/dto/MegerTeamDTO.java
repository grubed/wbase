package com.mrwind.windbase.dto;

/**
 * Created by CL-J on 2018/8/6.
 */
public class MegerTeamDTO {
    private String teamId;
    private String name;
    private String avatar;
    private int memberCount;
    private boolean hasChildrens;

    public MegerTeamDTO(String teamId, String name, String avatar, int memberCount, boolean hasChildrens) {
        this.teamId = teamId;
        this.name = name;
        this.avatar = avatar;
        this.memberCount = memberCount;
        this.hasChildrens = hasChildrens;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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
