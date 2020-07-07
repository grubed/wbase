package com.mrwind.windbase.entity.mongo.fengchat;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * 风信项目中的团队云信 id 对应实体，在本项目中只读，不写入，数据维护方在风信项目
 *
 * @author hanjie
 * @date 2018/6/11
 */

@Entity(noClassnameStored = true, value = "team_im_ids")
public class TeamIMId {

    @Id
    private String id;

    private String userId;
    private String teamId;
    private String imId;

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

    public String getImId() {
        return imId;
    }

    public void setImId(String imId) {
        this.imId = imId;
    }

}
