package com.mrwind.windbase.entity.mongo;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wuyiming
 * Created by wuyiming on 2018/6/4.
 */
@Entity(noClassnameStored = true)
public class UserActionLog {
    @Id
    private String userActionId;
    private String controllerName;
    private String functionName;
    private String keyId;
    private String paramentValue;
    private String responseValue;
    private Long spendTime;
    private String actionRemark;
    private Date createTime;
    private String creatorId;
    private List<String> headers = new ArrayList<>();

    public String getUserActionId() {
        return userActionId;
    }

    public void setUserActionId(String userActionId) {
        this.userActionId = userActionId;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getParamentValue() {
        return paramentValue;
    }

    public void setParamentValue(String paramentValue) {
        this.paramentValue = paramentValue;
    }

    public String getActionRemark() {
        return actionRemark;
    }

    public void setActionRemark(String actionRemark) {
        this.actionRemark = actionRemark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(String responseValue) {
        this.responseValue = responseValue;
    }

    public Long getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(Long spendTime) {
        this.spendTime = spendTime;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        return "UserActionLog{" +
                "userActionId='" + userActionId + '\'' +
                ", controllerName='" + controllerName + '\'' +
                ", functionName='" + functionName + '\'' +
                ", keyId='" + keyId + '\'' +
                ", paramentValue='" + paramentValue + '\'' +
                ", responseValue='" + responseValue + '\'' +
                ", spendTime=" + spendTime +
                ", actionRemark='" + actionRemark + '\'' +
                ", createTime=" + createTime +
                ", creatorId='" + creatorId + '\'' +
                ", headers=" + headers +
                '}';
    }
}
