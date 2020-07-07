package com.mrwind.windbase.vo;

import com.mrwind.windbase.entity.mysql.ExpressBillingMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/10/16
 */

public class UserDetailsInfoWithDataVO {

    private String userId;
    private String userName;
    private String name;
    private String avatar;
    private boolean member;
    private boolean manager;
    private String tel;
    private int countryCode;
    private String teamId;
    private String teamName;
    private Integer orderLimit;
    private Integer lowerBound;
    private Integer carryNum;
    private Integer receiveOrder;
    private UserAttendanceVO attendance;
    private UserLatestLocationVO latestLocation;
    private List<RoleVO> roles = new ArrayList<>();
    private UserData userData = new UserData();

    private String idCardNo;
    private Integer age;
    private String homeAddress;
    private Double homeLat;
    private Double homeLng;
    private BigDecimal totalIncome;
    private long incomeRecordNum;
    private ExpressBillingMode billingMode;

    public static class UserData {

        private UserAttendanceDataVO attendance = new UserAttendanceDataVO();
        private FlExpressUserDataVO express = new FlExpressUserDataVO();

        public UserAttendanceDataVO getAttendance() {
            return attendance;
        }

        public void setAttendance(UserAttendanceDataVO attendance) {
            this.attendance = attendance;
        }

        public FlExpressUserDataVO getExpress() {
            return express;
        }

        public void setExpress(FlExpressUserDataVO express) {
            this.express = express;
        }

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public UserAttendanceVO getAttendance() {
        return attendance;
    }

    public void setAttendance(UserAttendanceVO attendance) {
        this.attendance = attendance;
    }

    public UserLatestLocationVO getLatestLocation() {
        return latestLocation;
    }

    public void setLatestLocation(UserLatestLocationVO latestLocation) {
        this.latestLocation = latestLocation;
    }

    public List<RoleVO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleVO> roles) {
        this.roles = roles;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

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

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Double getHomeLat() {
        return homeLat;
    }

    public void setHomeLat(Double homeLat) {
        this.homeLat = homeLat;
    }

    public Double getHomeLng() {
        return homeLng;
    }

    public void setHomeLng(Double homeLng) {
        this.homeLng = homeLng;
    }

    public Integer getOrderLimit() {
        return orderLimit;
    }

    public void setOrderLimit(Integer orderLimit) {
        this.orderLimit = orderLimit;
    }

    public Integer getReceiveOrder() {
        return receiveOrder;
    }

    public void setReceiveOrder(Integer receiveOrder) {
        this.receiveOrder = receiveOrder;
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

    public ExpressBillingMode getBillingMode() {
        return billingMode;
    }

    public void setBillingMode(ExpressBillingMode billingMode) {
        this.billingMode = billingMode;
    }

    public Integer getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(Integer lowerBound) {
        this.lowerBound = lowerBound;
    }

    public Integer getCarryNum() {
        return carryNum;
    }

    public void setCarryNum(Integer carryNum) {
        this.carryNum = carryNum;
    }

}
