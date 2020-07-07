package com.mrwind.windbase.vo;

import java.math.BigDecimal;

/**
 * Description
 *
 * @author hanjie
 * @date 2018-12-17
 */

public class UserWorkSettingsInfoVO {

    private UserLatestLocationVO latestLocation;
    private BigDecimal totalIncome;
    private long incomeRecordNum;
    private int expressTotalNum;
    private CourierSettingsVO settings;
    private UserAttendanceInfoVO attendance;

    public UserLatestLocationVO getLatestLocation() {
        return latestLocation;
    }

    public void setLatestLocation(UserLatestLocationVO latestLocation) {
        this.latestLocation = latestLocation;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public long getIncomeRecordNum() {
        return incomeRecordNum;
    }

    public void setIncomeRecordNum(long incomeRecordNum) {
        this.incomeRecordNum = incomeRecordNum;
    }

    public CourierSettingsVO getSettings() {
        return settings;
    }

    public void setSettings(CourierSettingsVO settings) {
        this.settings = settings;
    }

    public UserAttendanceInfoVO getAttendance() {
        return attendance;
    }

    public void setAttendance(UserAttendanceInfoVO attendance) {
        this.attendance = attendance;
    }

    public int getExpressTotalNum() {
        return expressTotalNum;
    }

    public void setExpressTotalNum(int expressTotalNum) {
        this.expressTotalNum = expressTotalNum;
    }
}
