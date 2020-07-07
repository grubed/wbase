package com.mrwind.windbase.entity.mysql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by CL-J on 2018/7/23.
 */
@Entity
@Table(name = "user_extension")
public class UserExtension implements Serializable {

    private static final long serialVersionUID = 3135934634063503033L;

    @Id
    private String userId;

    private String flType = "2";

    private Integer workStatus;

    private Date workStatusTodayFirstTime;

    private Date workStatusUpdateTime;

    private Integer receiveOrderStatus;

    private Date receiveOrderStatusUpdateTime;

    @UpdateTimestamp
    @JsonIgnore
    private Date updateTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFlType() {
        return flType;
    }

    public void setFlType(String flType) {
        this.flType = flType;
    }

    public Integer getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(Integer workStatus) {
        this.workStatus = workStatus;
    }

    public Date getWorkStatusTodayFirstTime() {
        return workStatusTodayFirstTime;
    }

    public void setWorkStatusTodayFirstTime(Date workStatusTodayFirstTime) {
        this.workStatusTodayFirstTime = workStatusTodayFirstTime;
    }

    public Date getWorkStatusUpdateTime() {
        return workStatusUpdateTime;
    }

    public void setWorkStatusUpdateTime(Date workStatusUpdateTime) {
        this.workStatusUpdateTime = workStatusUpdateTime;
    }

    public Integer getReceiveOrderStatus() {
        return receiveOrderStatus;
    }

    public void setReceiveOrderStatus(Integer receiveOrderStatus) {
        this.receiveOrderStatus = receiveOrderStatus;
    }

    public Date getReceiveOrderStatusUpdateTime() {
        return receiveOrderStatusUpdateTime;
    }

    public void setReceiveOrderStatusUpdateTime(Date receiveOrderStatusUpdateTime) {
        this.receiveOrderStatusUpdateTime = receiveOrderStatusUpdateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
