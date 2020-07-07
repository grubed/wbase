package com.mrwind.windbase.vo.team;

import com.mrwind.windbase.vo.RoleVO;
import com.mrwind.windbase.vo.TeamUserAttendanceVO;

import java.util.List;

/**
 * Description
 *
 * @author hanjie
 * @date 2018-12-08
 */

public class TeamMember {

    private String userId;
    private String name;
    private String tel;
    private String avatar;
    private boolean manager;
    private List<RoleVO> roles;
    private TeamMemberStatus status;

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

    public boolean getManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public List<RoleVO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleVO> roles) {
        this.roles = roles;
    }

    public TeamMemberStatus getStatus() {
        return status;
    }

    public void setStatus(TeamMemberStatus status) {
        this.status = status;
    }

}
