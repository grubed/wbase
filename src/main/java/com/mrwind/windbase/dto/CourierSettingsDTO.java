package com.mrwind.windbase.dto;

import org.mongodb.morphia.annotations.Entity;

import java.util.List;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/11/20
 */
@Entity(noClassnameStored = true)
public class CourierSettingsDTO {

    private Integer receiveOrder;
    private Boolean autoReceiveOrder;
    private Integer orderLimit;
    private Integer lowerBound;
    private Integer carryNum;
    private String autoStartTime;
    private String autoEndTime;
    private String billingModeId;
    private Integer minDistance;
    private Integer maxDistance;
    private Integer carLoad;
    private Integer carType;
    private Integer carCubage;
    private String expectRestTime;
    private String homeAddress;
    private Double homeLat;
    private Double homeLng;

    private Integer age;
    private String idCardNo;
    private String frontIdCardImg;
    private String backIdCardImg;
    private String carNo;
    private List<String> carImg;
    private List<String> driverLicenseImg;

    private String deliverAreaAddress;
    private Double deliverAreaLat;
    private Double deliverAreaLng;

    private String userName;
    private String userAvatar;

    public CourierSettingsDTO() {
    }

    public CourierSettingsDTO(Integer receiveOrder) {
        this.receiveOrder = receiveOrder;
    }

    public Integer getReceiveOrder() {
        return receiveOrder;
    }

    public void setReceiveOrder(Integer receiveOrder) {
        this.receiveOrder = receiveOrder;
    }

    public Boolean getAutoReceiveOrder() {
        return autoReceiveOrder;
    }

    public void setAutoReceiveOrder(Boolean autoReceiveOrder) {
        this.autoReceiveOrder = autoReceiveOrder;
    }

    public Integer getOrderLimit() {
        return orderLimit;
    }

    public void setOrderLimit(Integer orderLimit) {
        this.orderLimit = orderLimit;
    }

    public String getAutoStartTime() {
        return autoStartTime;
    }

    public void setAutoStartTime(String autoStartTime) {
        this.autoStartTime = autoStartTime;
    }

    public String getAutoEndTime() {
        return autoEndTime;
    }

    public void setAutoEndTime(String autoEndTime) {
        this.autoEndTime = autoEndTime;
    }

    public String getBillingModeId() {
        return billingModeId;
    }

    public void setBillingModeId(String billingModeId) {
        this.billingModeId = billingModeId;
    }

    public Integer getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(Integer minDistance) {
        this.minDistance = minDistance;
    }

    public Integer getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(Integer maxDistance) {
        this.maxDistance = maxDistance;
    }

    public Integer getCarLoad() {
        return carLoad;
    }

    public void setCarLoad(Integer carLoad) {
        this.carLoad = carLoad;
    }

    public Integer getCarType() {
        return carType;
    }

    public void setCarType(Integer carType) {
        this.carType = carType;
    }

    public Integer getCarCubage() {
        return carCubage;
    }

    public void setCarCubage(Integer carCubage) {
        this.carCubage = carCubage;
    }

    public String getExpectRestTime() {
        return expectRestTime;
    }

    public void setExpectRestTime(String expectRestTime) {
        this.expectRestTime = expectRestTime;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getFrontIdCardImg() {
        return frontIdCardImg;
    }

    public void setFrontIdCardImg(String frontIdCardImg) {
        this.frontIdCardImg = frontIdCardImg;
    }

    public String getBackIdCardImg() {
        return backIdCardImg;
    }

    public void setBackIdCardImg(String backIdCardImg) {
        this.backIdCardImg = backIdCardImg;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public List<String> getCarImg() {
        return carImg;
    }

    public void setCarImg(List<String> carImg) {
        this.carImg = carImg;
    }

    public List<String> getDriverLicenseImg() {
        return driverLicenseImg;
    }

    public void setDriverLicenseImg(List<String> driverLicenseImg) {
        this.driverLicenseImg = driverLicenseImg;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
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
