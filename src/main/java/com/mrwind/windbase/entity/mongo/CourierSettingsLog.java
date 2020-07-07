package com.mrwind.windbase.entity.mongo;

import com.mrwind.windbase.common.util.TextUtils;
import com.mrwind.windbase.dto.CourierSettingsDTO;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Description
 *
 * @author hanjie
 * @date 2018-11-28
 */

@Entity(noClassnameStored = true)
public class CourierSettingsLog {

    @Id
    private String id;

    private String operatorUserId;
    private String targetUserId;
    private boolean bySelf;
    private Date createTime;
    private CourierSettingsDTO settings;

    public CourierSettingsLog(String operatorUserId, String targetUserId) {
        this.operatorUserId = operatorUserId;
        this.targetUserId = targetUserId;
        this.bySelf = TextUtils.equals(operatorUserId, targetUserId);
        this.createTime = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(String operatorUserId) {
        this.operatorUserId = operatorUserId;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public boolean isBySelf() {
        return bySelf;
    }

    public void setBySelf(boolean bySelf) {
        this.bySelf = bySelf;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public CourierSettingsDTO getSettings() {
        return settings;
    }

    public void setSettings(CourierSettingsDTO settings) {
        this.settings = settings;
    }

}
