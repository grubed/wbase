package com.mrwind.windbase.entity.mysql;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by CL-J on 2018/7/25.
 */
@Entity
@Table(name = "user_team_role_relation")
public class UserTeamRoleRelation implements Serializable {

    private static final long serialVersionUID = -4234206720488896726L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long  id;

    private String userId;

    private String teamId;

    private String roleId;


    public UserTeamRoleRelation(String userId, String teamId, String roleId) {
        this.userId = userId;
        this.teamId = teamId;
        this.roleId = roleId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public UserTeamRoleRelation(){

    }
}
