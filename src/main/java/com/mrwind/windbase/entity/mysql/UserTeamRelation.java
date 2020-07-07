package com.mrwind.windbase.entity.mysql;

import com.sun.org.apache.xpath.internal.operations.Bool;
import net.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by CL-J on 2018/7/19.
 */

@Entity
@Table(name = "user_team_relation")
public class UserTeamRelation implements Serializable {

    private static final long serialVersionUID = -8415038095352482279L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userId;

    private String teamId;
    //是否是管理员
    private boolean mananger = false;

    public UserTeamRelation() {
    }

    public UserTeamRelation(String userId, String teamId, boolean mananger) {
        this.userId = userId;
        this.teamId = teamId;
        this.mananger = mananger;
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

    public boolean isMananger() {
        return mananger;
    }

    public void setMananger(boolean mananger) {
        this.mananger = mananger;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
