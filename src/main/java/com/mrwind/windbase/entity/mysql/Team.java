package com.mrwind.windbase.entity.mysql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by CL-J on 2018/7/18.
 */

@Entity
@Table(name = "team")
public class Team implements Serializable {

    private static final long serialVersionUID = -7429915738852269843L;
    @Id
    private String teamId;
    //流水号，parentIds就是使用本参数拼接
    @JsonIgnore
    @Column(name = "teamIdNo", unique = true)
    private long teamIdNo;
    //
    private String project;

    private String name;

    private String avatar;

    private String rootId;

    private boolean shop = false;

    private BigDecimal perPrice;
    //仅风先生可用 0 普通团队 1 物流团队 2 电商团队
    private int type;
    @JsonIgnore
    private String parentIds;

    private String remark;

    @JsonIgnore
    @CreationTimestamp
    private Date createTime;

    @JsonIgnore
    @UpdateTimestamp
    private Date updateTime;

    public Team() {
    }

    /**
     * 创建根团队初始化字段
     */
    public Team(String teamId, long teamIdNo, String name, String avatar, String rootId, String parentIds) {
        this.teamId = teamId;
        this.teamIdNo = teamIdNo;
        this.name = name;
        this.avatar = avatar;
        this.rootId = rootId;
        this.parentIds = parentIds;
    }

    /**
     * 创建子团队时初始化字段
     */
    public Team(String teamId, long teamIdNo, int type, String project, String name, String avatar, String rootId, String parentIds) {
        this.teamId = teamId;
        this.teamIdNo = teamIdNo;
        this.project = project;
        this.name = name;
        this.avatar = avatar;
        this.rootId = rootId;
        this.parentIds = parentIds;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public long getTeamIdNo() {
        return teamIdNo;
    }

    public void setTeamIdNo(long teamIdNo) {
        this.teamIdNo = teamIdNo;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getPerPrice() {
        return perPrice;
    }

    public void setPerPrice(BigDecimal perPrice) {
        this.perPrice = perPrice;
    }

    public boolean isShop() {
        return shop;
    }

    public void setShop(boolean shop) {
        this.shop = shop;
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamId='" + teamId + '\'' +
                ", teamIdNo=" + teamIdNo +
                ", project='" + project + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", rootId='" + rootId + '\'' +
                ", perPrice=" + perPrice +
                ", parentIds='" + parentIds + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}