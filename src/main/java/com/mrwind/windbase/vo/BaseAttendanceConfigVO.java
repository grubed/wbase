package com.mrwind.windbase.vo;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-01-03
 */

public class BaseAttendanceConfigVO {

    private String plan;
    private double workLng;
    private double workLat;
    private String workPlaceDetail;
    private String workPlace;
    private String workStartTime;

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
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
