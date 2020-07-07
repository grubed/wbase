package com.mrwind.windbase.vo;

import java.math.BigDecimal;

/**
 * 发货端用户信息资料
 *
 * @author hanjie
 * @date 2018/8/1
 */

public class ShipmentUserInfoVO {

    private String userId;

    private String userName;

    private String name;

    private String avatar;

    private String tel;

    private Integer countryCode;

    /**
     * 前所在团队名字
     */
    private String teamName;

    /**
     * 前所在团队 id
     */
    private String teamId;

    private BigDecimal accountBalance;

    private int shipmentCountToday;

    private int shipmentCountTotal;

    private double shipmentAmoutTotal;

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public int getShipmentCountToday() {
        return shipmentCountToday;
    }

    public void setShipmentCountToday(int shipmentCountToday) {
        this.shipmentCountToday = shipmentCountToday;
    }

    public int getShipmentCountTotal() {
        return shipmentCountTotal;
    }

    public void setShipmentCountTotal(int shipmentCountTotal) {
        this.shipmentCountTotal = shipmentCountTotal;
    }

    public double getShipmentAmoutTotal() {
        return shipmentAmoutTotal;
    }

    public void setShipmentAmoutTotal(double shipmentAmoutTotal) {
        this.shipmentAmoutTotal = shipmentAmoutTotal;
    }

}
