package com.mrwind.windbase.vo;

import java.util.Date;

/**
 * Description
 *
 * @author hanjie
 * @date 2018-11-29
 */

public class FlCourierUserVO {


    /**
     * countryCode : 86
     * name : 周泽勇
     * userId : 000000006527e7ff01652823190e0000
     * avatar : http://static.123feng.com/rtwIW3eWqrABupGZozw8inK5v5j2Wg4rOIexCEn3f2jUhMbvW8KinsbYGyeps4.png
     * currentCountry : china
     * userName : 15067145796
     * tel : 15067145796
     * currentTeamId : 5afd5817cd0bd80ecf11e0dc
     */

    private int countryCode;
    private String name;
    private String userId;
    private String avatar;
    private String currentCountry;
    private String userName;
    private String tel;
    private String currentTeamId;

    private String homeAddress;
    private Double homeLat;
    private Double homeLng;
    private String deliverAreaAddress;
    private Double deliverAreaLat;
    private Double deliverAreaLng;
    private Integer receiveOrder;
    private Integer orderLimit;
    private Integer lowerBound;
    private Integer carryNum;

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCurrentCountry() {
        return currentCountry;
    }

    public void setCurrentCountry(String currentCountry) {
        this.currentCountry = currentCountry;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCurrentTeamId() {
        return currentTeamId;
    }

    public void setCurrentTeamId(String currentTeamId) {
        this.currentTeamId = currentTeamId;
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

    public String getDeliverAreaAddress() {
        return deliverAreaAddress;
    }

    public void setDeliverAreaAddress(String deliverAreaAddress) {
        this.deliverAreaAddress = deliverAreaAddress;
    }

    public Double getDeliverAreaLat() {
        return deliverAreaLat;
    }

    public void setDeliverAreaLat(Double deliverAreaLat) {
        this.deliverAreaLat = deliverAreaLat;
    }

    public Double getDeliverAreaLng() {
        return deliverAreaLng;
    }

    public void setDeliverAreaLng(Double deliverAreaLng) {
        this.deliverAreaLng = deliverAreaLng;
    }

    public Integer getReceiveOrder() {
        return receiveOrder;
    }

    public void setReceiveOrder(Integer receiveOrder) {
        this.receiveOrder = receiveOrder;
    }

    public Integer getOrderLimit() {
        return orderLimit;
    }

    public void setOrderLimit(Integer orderLimit) {
        this.orderLimit = orderLimit;
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
