package com.mrwind.windbase.entity.mysql;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by CL-J on 2018/8/16.
 *
 * 离职表
 */
@Entity
@Table(name = "team_resignation_relation")
public class TeamResignationRelation implements Serializable {

    private static final long serialVersionUID = 6935187606359475115L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String teamId;

    private String teamName;

    private String rootTeamId;

    private String rootTeamName;

    private String userId;

    @CreationTimestamp
    private Date creatTime;

    private String reason;

    private String lastSignTime;

    public TeamResignationRelation() {
    }

    public TeamResignationRelation(String name, String teamId, String teamName, String rootTeamId, String rootTeamName, String userId, String reason, String lastSignTime) {
        this.name = name;
        this.teamId = teamId;
        this.teamName = teamName;
        this.rootTeamId = rootTeamId;
        this.rootTeamName = rootTeamName;
        this.userId = userId;
        this.reason = reason;
        this.lastSignTime = lastSignTime;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getRootTeamId() {
        return rootTeamId;
    }

    public void setRootTeamId(String rootTeamId) {
        this.rootTeamId = rootTeamId;
    }

    public String getRootTeamName() {
        return rootTeamName;
    }

    public void setRootTeamName(String rootTeamName) {
        this.rootTeamName = rootTeamName;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLastSignTime() {
        return lastSignTime;
    }

    public void setLastSignTime(String lastSignTime) {
        this.lastSignTime = lastSignTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
