package com.mrwind.windbase.entity.mysql;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by CL-J on 2018/7/19.
 */
@Entity
@Table(name = "team_role_relation")
public class TeamRoleRelation implements Serializable {

    private static final long serialVersionUID = 1597778990336777626L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamId;
    //团队整体权限
    private String roleId;

    public TeamRoleRelation(String teamId, String roleId) {
        this.teamId = teamId;
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public TeamRoleRelation() {
    }
}
