package com.mrwind.windbase.vo;

import java.util.List;

/**
 * Created by CL-J on 2018/7/24.
 */
public class TeamUserVO {
    private String userId;
    private String name;
    private String tel;
    private String avatar;
    private boolean manager;
    private List<RoleVO> roles;
    private TeamUserAttendanceVO attendance;

    public TeamUserVO() {
    }

    public TeamUserVO(String userId, String name, String tel, String avatar, boolean manager) {
        this.userId = userId;
        this.name = name;
        this.tel = tel;
        this.avatar = avatar;
        this.manager = manager;
    }

    public TeamUserAttendanceVO getAttendance() {
        return attendance;
    }

    public void setAttendance(TeamUserAttendanceVO attendance) {
        this.attendance = attendance;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<RoleVO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleVO> roles) {
        this.roles = roles;
    }
}
