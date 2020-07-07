package com.mrwind.windbase.vo;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/8/3
 */

public class UserAttendanceVO {

    private String address;
    private String addressDetails;
    private String startTime;
    private double lat;
    private double lng;
    private String currentStatus;
    private int attenceCount;
    private float lateCount;
    private String lastSign;

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

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public int getAttenceCount() {
        return attenceCount;
    }

    public void setAttenceCount(int attenceCount) {
        this.attenceCount = attenceCount;
    }

    public float getLateCount() {
        return lateCount;
    }

    public void setLateCount(float lateCount) {
        this.lateCount = lateCount;
    }

    public String getLastSign() {
        return lastSign;
    }

    public void setLastSign(String lastSign) {
        this.lastSign = lastSign;
    }
}
