package com.mrwind.windbase.entity.mongo;

import org.mongodb.morphia.annotations.Id;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-01-14
 */

public class TeamConfigSwitch {

    @Id
    private String id;
    private String teamId;
    private String key;
    private String status;

    public TeamConfigSwitch() {
    }

    public TeamConfigSwitch(String teamId, String key, String status) {
        this.teamId = teamId;
        this.key = key;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
