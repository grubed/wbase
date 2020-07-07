package com.mrwind.windbase.entity.mongo;


import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * @author wuyiming
 * Created by CL-J on 2018/7/23.
 */
@Entity(noClassnameStored = true)
public class TeamPosition {
    @Id
    private String teamId;

    private Position gpsInfo;

    public TeamPosition() {
    }

    public TeamPosition(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Position getGpsInfo() {
        return gpsInfo;
    }

    public void setGpsInfo(Position gpsInfo) {
        this.gpsInfo = gpsInfo;
    }
}

