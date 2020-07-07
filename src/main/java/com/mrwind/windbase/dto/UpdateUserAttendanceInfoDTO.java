package com.mrwind.windbase.dto;

import javax.validation.constraints.NotNull;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/7/30
 */

public class UpdateUserAttendanceInfoDTO {


    /**
     * userId : 1234123421342134213
     * teamId : 5afd2842cd0bd83f00088cc9
     * address : 优迈科技园
     * addressDetails : 建业路与怀德街交叉口
     * startTime : 09:00
     * endTime : 18:30
     * lat : 30.18499
     * lng : 120.17955
     * currentStatus : Rest
     * workHours : 0.0
     */

    @NotNull(message = "error.parameter")
    private String userId;

    @NotNull(message = "error.parameter")
    private String teamId;
    
    private String address;
    private String addressDetails;
    private String startTime;
    private String endTime;
    private Double lat;
    private Double lng;
    private String currentStatus;
    private Double workHours;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Double getWorkHours() {
        return workHours;
    }

    public void setWorkHours(Double workHours) {
        this.workHours = workHours;
    }

}
