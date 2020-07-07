package com.mrwind.windbase.dto;

import com.mrwind.windbase.entity.mongo.TeamPosition;
import com.mrwind.windbase.entity.mysql.Team;
import com.mrwind.windbase.entity.mysql.TeamExtention;
import com.mrwind.windbase.entity.mysql.User;
import com.mrwind.windbase.entity.mysql.UserExtension;
import com.mrwind.windbase.vo.RoleVO;

import java.util.List;

/**
 * Created by michelshout on 2018/7/26.
 */
public class TeamUserInfoDTO {
    //当前用户信息
    private User userInfo = null;
    private UserExtension userExtension = null;

    //父团队ID
    private String parentTeamId = null;

    //当前用户在父团队是否是管理员
    private boolean manager = false;

    //根团队
    private Team rootTeam = null;
    private TeamExtention rootTeamExtention = null;
    private TeamPosition rootTeamPosition = null;
    private List<RoleVO> rootTeamRoleList = null;

    //仓库团队
    private Team wareHouseTeam = null;
    private TeamExtention wareHouseTeamExtention = null;
    private TeamPosition wareHouseTeamPosition = null;
    private List<RoleVO> wareHouseTeamRoleList = null;

    //客服团队
    private Team clientServerTeam = null;
    private TeamExtention clientServerTeamExtention = null;
    private TeamPosition clientServerTeamPosition = null;
    private List<RoleVO> clientServerTeamRoleList = null;

    //父团队
    private Team parentTeam = null;
    private TeamExtention parentTeamExtention = null;
    private TeamPosition parentTeamPosition = null;
    private List<RoleVO> parentTeamRoleList = null;
    private List<RoleVO> parentTeamUserRoleList = null;

    //所属多个根团队信息
    private List<Team> relationRootTeamList = null;

    //////////////////////////////////////////////////////////////////////////////////
    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public UserExtension getUserExtension() {
        return userExtension;
    }

    public void setUserExtension(UserExtension userExtension) {
        this.userExtension = userExtension;
    }

    public String getParentTeamId() {
        return parentTeamId;
    }

    public void setParentTeamId(String parentTeamId) {
        this.parentTeamId = parentTeamId;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public Team getRootTeam() {
        return rootTeam;
    }

    public void setRootTeam(Team rootTeam) {
        this.rootTeam = rootTeam;
    }

    public TeamExtention getRootTeamExtention() {
        return rootTeamExtention;
    }

    public void setRootTeamExtention(TeamExtention rootTeamExtention) {
        this.rootTeamExtention = rootTeamExtention;
    }

    public TeamPosition getRootTeamPosition() {
        return rootTeamPosition;
    }

    public void setRootTeamPosition(TeamPosition rootTeamPosition) {
        this.rootTeamPosition = rootTeamPosition;
    }

    public List<RoleVO> getRootTeamRoleList() {
        return rootTeamRoleList;
    }

    public void setRootTeamRoleList(List<RoleVO> rootTeamRoleList) {
        this.rootTeamRoleList = rootTeamRoleList;
    }

    public Team getWareHouseTeam() {
        return wareHouseTeam;
    }

    public void setWareHouseTeam(Team wareHouseTeam) {
        this.wareHouseTeam = wareHouseTeam;
    }

    public TeamExtention getWareHouseTeamExtention() {
        return wareHouseTeamExtention;
    }

    public void setWareHouseTeamExtention(TeamExtention wareHouseTeamExtention) {
        this.wareHouseTeamExtention = wareHouseTeamExtention;
    }

    public TeamPosition getWareHouseTeamPosition() {
        return wareHouseTeamPosition;
    }

    public void setWareHouseTeamPosition(TeamPosition wareHouseTeamPosition) {
        this.wareHouseTeamPosition = wareHouseTeamPosition;
    }

    public List<RoleVO> getWareHouseTeamRoleList() {
        return wareHouseTeamRoleList;
    }

    public void setWareHouseTeamRoleList(List<RoleVO> wareHouseTeamRoleList) {
        this.wareHouseTeamRoleList = wareHouseTeamRoleList;
    }

    public Team getClientServerTeam() {
        return clientServerTeam;
    }

    public void setClientServerTeam(Team clientServerTeam) {
        this.clientServerTeam = clientServerTeam;
    }

    public TeamExtention getClientServerTeamExtention() {
        return clientServerTeamExtention;
    }

    public void setClientServerTeamExtention(TeamExtention clientServerTeamExtention) {
        this.clientServerTeamExtention = clientServerTeamExtention;
    }

    public TeamPosition getClientServerTeamPosition() {
        return clientServerTeamPosition;
    }

    public void setClientServerTeamPosition(TeamPosition clientServerTeamPosition) {
        this.clientServerTeamPosition = clientServerTeamPosition;
    }

    public List<RoleVO> getClientServerTeamRoleList() {
        return clientServerTeamRoleList;
    }

    public void setClientServerTeamRoleList(List<RoleVO> clientServerTeamRoleList) {
        this.clientServerTeamRoleList = clientServerTeamRoleList;
    }

    public Team getParentTeam() {
        return parentTeam;
    }

    public void setParentTeam(Team parentTeam) {
        this.parentTeam = parentTeam;
    }

    public TeamExtention getParentTeamExtention() {
        return parentTeamExtention;
    }

    public void setParentTeamExtention(TeamExtention parentTeamExtention) {
        this.parentTeamExtention = parentTeamExtention;
    }

    public TeamPosition getParentTeamPosition() {
        return parentTeamPosition;
    }

    public void setParentTeamPosition(TeamPosition parentTeamPosition) {
        this.parentTeamPosition = parentTeamPosition;
    }

    public List<RoleVO> getParentTeamRoleList() {
        return parentTeamRoleList;
    }

    public void setParentTeamRoleList(List<RoleVO> parentTeamRoleList) {
        this.parentTeamRoleList = parentTeamRoleList;
    }

    public List<RoleVO> getParentTeamUserRoleList() {
        return parentTeamUserRoleList;
    }

    public void setParentTeamUserRoleList(List<RoleVO> parentTeamUserRoleList) {
        this.parentTeamUserRoleList = parentTeamUserRoleList;
    }

    public List<Team> getRelationRootTeamList() {
        return relationRootTeamList;
    }

    public void setRelationRootTeamList(List<Team> relationRootTeamList) {
        this.relationRootTeamList = relationRootTeamList;
    }
}
