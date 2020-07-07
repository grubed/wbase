package com.mrwind.windbase.entity.mongo.attendance;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Set;

/**
 * 考勤人数总结（包括出勤人数，外出人数）,不写入，数据维护方在考勤项目
 *
 * @author hanjie
 * @date 2018/8/23
 */
@Entity(noClassnameStored = true, value = "AttenceSummary")
public class AttenceSummary {

    @Id
    private String id;

    private String date;

    private String currentTeamId;

    private String type;

    private Set<String> userIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCurrentTeamId() {
        return currentTeamId;
    }

    public void setCurrentTeamId(String currentTeamId) {
        this.currentTeamId = currentTeamId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<String> userIds) {
        this.userIds = userIds;
    }

}
