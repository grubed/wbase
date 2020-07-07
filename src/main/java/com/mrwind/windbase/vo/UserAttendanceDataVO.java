package com.mrwind.windbase.vo;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/10/16
 */

public class UserAttendanceDataVO {

    private int workDays;

    private double inHours;

    private double outHours;

    private double lateHours;

    private double leaveHours;

    public double getInHours() {
        return inHours;
    }

    public void setInHours(double inHours) {
        this.inHours = inHours;
    }

    public int getWorkDays() {
        return workDays;
    }

    public void setWorkDays(int workDays) {
        this.workDays = workDays;
    }

    public double getOutHours() {
        return outHours;
    }

    public void setOutHours(double outHours) {
        this.outHours = outHours;
    }

    public double getLateHours() {
        return lateHours;
    }

    public void setLateHours(double lateHours) {
        this.lateHours = lateHours;
    }

    public double getLeaveHours() {
        return leaveHours;
    }

    public void setLeaveHours(double leaveHours) {
        this.leaveHours = leaveHours;
    }

}
