package com.mrwind.windbase.vo;

import java.util.List;

/**
 * Created by CL-J on 2018/7/24.
 */
public class TeamInfoVO {

    private String name;

    private String teamId;

    private String avatar;

    private Integer memberCount;

    private boolean rootManager;

    private boolean manageable;

    private List<RoleVO> roles;

    private List<TeamMemberCountVO> children;

    private List<TeamUserVO> users;

    private TeamPositionVO position;

    private String project;

    private String homeAddress;

    private Double homeLat;

    private Double homeLng;

    public List<RoleVO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleVO> roles) {
        this.roles = roles;
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

    public List<TeamMemberCountVO> getChildren() {
        return children;
    }

    public void setChildren(List<TeamMemberCountVO> children) {
        this.children = children;
    }

    public List<TeamUserVO> getUsers() {
        return users;
    }

    public void setUsers(List<TeamUserVO> users) {
        this.users = users;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public boolean isRootManager() {
        return rootManager;
    }

    public void setRootManager(boolean rootManager) {
        this.rootManager = rootManager;
    }

    public boolean isManageable() {
        return manageable;
    }

    public void setManageable(boolean manageable) {
        this.manageable = manageable;
    }

    public TeamPositionVO getPosition() {
        return position;
    }

    public void setPosition(TeamPositionVO position) {
        this.position = position;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Double getHomeLat() {
        return homeLat;
    }

    public void setHomeLat(Double homeLat) {
        this.homeLat = homeLat;
    }

    public Double getHomeLng() {
        return homeLng;
    }

    public void setHomeLng(Double homeLng) {
        this.homeLng = homeLng;
    }

}
