package com.mrwind.windbase.vo;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/9/6
 */

public class BaseAttendanceInfoVO {

    private boolean isOpen;

    private double workLng;
    private double workLat;
    private String workPlaceDetail;
    private String workPlace;
    private String workStartTime;

    public boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean open) {
        isOpen = open;
    }

    public double getWorkLng() {
        return workLng;
    }

    public void setWorkLng(double workLng) {
        this.workLng = workLng;
    }

    public double getWorkLat() {
        return workLat;
    }

    public void setWorkLat(double workLat) {
        this.workLat = workLat;
    }

    public String getWorkPlaceDetail() {
        return workPlaceDetail;
    }

    public void setWorkPlaceDetail(String workPlaceDetail) {
        this.workPlaceDetail = workPlaceDetail;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(String workStartTime) {
        this.workStartTime = workStartTime;
    }

}
