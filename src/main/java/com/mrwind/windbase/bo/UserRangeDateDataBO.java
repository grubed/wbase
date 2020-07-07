package com.mrwind.windbase.bo;

/**
 * 日期范围内考勤统计数据
 *
 * @author hanjie
 * @date 2018/9/18
 */

public class UserRangeDateDataBO {

    private int workDays;

    private double inWorkHours;

    private double outWorkHours;

    private double lateHours;

    private double leaveHours;

    public int getWorkDays() {
        return workDays;
    }

    public void setWorkDays(int workDays) {
        this.workDays = workDays;
    }

    public double getInWorkHours() {
        return inWorkHours;
    }

    public void setInWorkHours(double inWorkHours) {
        this.inWorkHours = inWorkHours;
    }

    public double getOutWorkHours() {
        return outWorkHours;
    }

    public void setOutWorkHours(double outWorkHours) {
        this.outWorkHours = outWorkHours;
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
