package com.mrwind.windbase.vo.team;

import com.mrwind.windbase.entity.mysql.ExpressBillingMode;
import com.mrwind.windbase.vo.RoleVO;
import com.mrwind.windbase.vo.TeamPositionVO;

import java.math.BigDecimal;
import java.util.List;

public class TeamDetailVO {

    private String name;
    private String teamId;
    private String avatar;
    private int type;
    private int memberCount;
    private long teamPositionCount;
    private boolean rootManager;
    private boolean manageable;
    private List<RoleVO> roles;
    private List<ChildTeam> childrens;
    private List<TeamMember> members;
    private TeamPositionVO position;
    private String project;
    private int removedMemberCount;
    private BigDecimal profit;
    private TeamAttendance teamAttendance;
    private int clientNum;
    private ExpressBillingMode courierExpressBillingMode;
    private Object clientPriceRule;
    private String expressStartTime;
    private String expressEndTime;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
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

    public boolean getRootManager() {
        return rootManager;
    }

    public void setRootManager(boolean rootManager) {
        this.rootManager = rootManager;
    }

    public boolean getManageable() {
        return manageable;
    }

    public void setManageable(boolean manageable) {
        this.manageable = manageable;
    }

    public List<RoleVO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleVO> roles) {
        this.roles = roles;
    }

    public List<ChildTeam> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<ChildTeam> childrens) {
        this.childrens = childrens;
    }

    public List<TeamMember> getMembers() {
        return members;
    }

    public void setMembers(List<TeamMember> members) {
        this.members = members;
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

    public int getRemovedMemberCount() {
        return removedMemberCount;
    }

    public void setRemovedMemberCount(int removedMemberCount) {
        this.removedMemberCount = removedMemberCount;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public TeamAttendance getTeamAttendance() {
        return teamAttendance;
    }

    public void setTeamAttendance(TeamAttendance teamAttendance) {
        this.teamAttendance = teamAttendance;
    }

    public int getClientNum() {
        return clientNum;
    }

    public void setClientNum(int clientNum) {
        this.clientNum = clientNum;
    }

    public ExpressBillingMode getCourierExpressBillingMode() {
        return courierExpressBillingMode;
    }

    public void setCourierExpressBillingMode(ExpressBillingMode courierExpressBillingMode) {
        this.courierExpressBillingMode = courierExpressBillingMode;
    }

    public Object getClientPriceRule() {
        return clientPriceRule;
    }

    public void setClientPriceRule(Object clientPriceRule) {
        this.clientPriceRule = clientPriceRule;
    }

    public long getTeamPositionCount() {
        return teamPositionCount;
    }

    public void setTeamPositionCount(long teamPositionCount) {
        this.teamPositionCount = teamPositionCount;
    }

    public String getExpressStartTime() {
        return expressStartTime;
    }

    public void setExpressStartTime(String expressStartTime) {
        this.expressStartTime = expressStartTime;
    }

    public String getExpressEndTime() {
        return expressEndTime;
    }

    public void setExpressEndTime(String expressEndTime) {
        this.expressEndTime = expressEndTime;
    }

}
