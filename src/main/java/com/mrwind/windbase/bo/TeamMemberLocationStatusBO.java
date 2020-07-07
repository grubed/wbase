package com.mrwind.windbase.bo;

import java.util.Date;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/10/16
 */

public class TeamMemberLocationStatusBO {

    private String userId;
    private Date updateTime;
    private String address;
    private String addressDetail;
    private double lat;
    private double lng;
    private Date todayFirstSignTime;
    private Date restTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Date getTodayFirstSignTime() {
        return todayFirstSignTime;
    }

    public void setTodayFirstSignTime(Date todayFirstSignTime) {
        this.todayFirstSignTime = todayFirstSignTime;
    }

    public Date getRestTime() {
        return restTime;
    }

    public void setRestTime(Date restTime) {
        this.restTime = restTime;
    }
    
}
