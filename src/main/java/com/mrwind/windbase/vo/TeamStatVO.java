package com.mrwind.windbase.vo;

import java.math.BigDecimal;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-01-02
 */

public class TeamStatVO {

    /**
     * teamId : 123
     * teamName : 北部中队
     * memberCount : 23 人数
     * rootManager : true
     * manager : true
     * manageable : false
     * teamPositionNum : 13 团队接单区域
     * attendanceStatusNum : 17 签到记录
     * totalOrderNum : 34 订单数
     * profit : 38745   利润
     * customerConsumption: 38745 客户消费
     * expressMapNum: 123  取派分派地图
     * userWorkSetting 3/5启动接单  配置个人工作
     * type 团队type
     */

    private String teamId;
    private String teamName;
    private int memberCount;
    private boolean rootManager;
    private boolean manager;
    private boolean manageable;
    private long teamPositionNum;
    private long attendanceStatusNum;
    private int totalOrderNum;
    private BigDecimal profit = BigDecimal.ZERO;
    private BigDecimal customerConsumption;
    private int expressMapNum;
    private int type;
    /**
     * 团队取派开始时间
     */
    private String expressStartTime;
    /**
     * 团队取派结束时间
     */
    private String expressEndTime;

    private TeamPositionVO position;

    private long userWorking;

    private long userWorks;

    private double carryLeave;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public boolean getRootManager() {
        return rootManager;
    }

    public void setRootManager(boolean rootManager) {
        this.rootManager = rootManager;
    }

    public boolean getManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public boolean getManageable() {
        return manageable;
    }

    public void setManageable(boolean manageable) {
        this.manageable = manageable;
    }

    public long getTeamPositionNum() {
        return teamPositionNum;
    }

    public void setTeamPositionNum(long teamPositionNum) {
        this.teamPositionNum = teamPositionNum;
    }

    public long getAttendanceStatusNum() {
        return attendanceStatusNum;
    }

    public void setAttendanceStatusNum(long attendanceStatusNum) {
        this.attendanceStatusNum = attendanceStatusNum;
    }

    public int getTotalOrderNum() {
        return totalOrderNum;
    }

    public void setTotalOrderNum(int totalOrderNum) {
        this.totalOrderNum = totalOrderNum;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getCustomerConsumption() {
        return customerConsumption;
    }

    public void setCustomerConsumption(BigDecimal customerConsumption) {
        this.customerConsumption = customerConsumption;
    }

    public int getExpressMapNum() {
        return expressMapNum;
    }

    public void setExpressMapNum(int expressMapNum) {
        this.expressMapNum = expressMapNum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getExpressStartTime() {
        return expressStartTime;
    }

    public void setExpressStartTime(String expressStartTime) {
        this.expressStartTime = expressStartTime;
    }

    public String getExpressEndTime() {
        return expressEndTime;
    }

    public void setExpressEndTime(String expressEndTime) {
        this.expressEndTime = expressEndTime;
    }

    public TeamPositionVO getPosition() {
        return position;
    }

    public void setPosition(TeamPositionVO position) {
        this.position = position;
    }

    public long getUserWorking() {
        return userWorking;
    }

    public void setUserWorking(long userWorking) {
        this.userWorking = userWorking;
    }

    public long getUserWorks() {
        return userWorks;
    }

    public void setUserWorks(long userWorks) {
        this.userWorks = userWorks;
    }

    public double getCarryLeave() {
        return carryLeave;
    }

    public void setCarryLeave(double carryLeave) {
        this.carryLeave = carryLeave;
    }

}
