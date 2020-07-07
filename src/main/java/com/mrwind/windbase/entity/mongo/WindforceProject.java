package com.mrwind.windbase.entity.mongo;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Created by michelshout on 2018/8/6.
 */
@Entity(noClassnameStored = true)
public class WindforceProject {
    @Id
    private String wfProjectApiId;
    private String projectType;
    private String project;
    private String baseUrl;
    private String key;
    private String remark;
    private Date createTime;

    public String getWfProjectApiId() {
        return wfProjectApiId;
    }

    public void setWfProjectApiId(String wfProjectApiId) {
        this.wfProjectApiId = wfProjectApiId;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
