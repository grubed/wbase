package com.mrwind.windbase.entity.mongo;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;
import java.util.List;

/**
 * 配送员接单控制 & 配送设置
 *
 * @author hanjie
 * @date 2018/11/20
 */

@Entity(noClassnameStored = true)
public class CourierSettings {

    @Id
    private String userId;
    private Date createTime;
    private Date updateTime;
    private Receive receive;
    private Express express;
    private String homeAddress;
    private GpsInfo homeGps;

    private Integer age;
    private String idCardNo;
    private String frontIdCardImg;
    private String backIdCardImg;
    private String carNo;
    private List<String> carImg;
    private List<String> driverLicenseImg;

    private String deliverAreaAddress;
    private GpsInfo deliverAreaGps;

    public static class Receive {

        private Boolean autoReceiveOrder;
        // 单量上限
        private Integer orderLimit;
        // 单量下限
        private Integer lowerBound;
        // 运载单位个数
        private Integer carryNum;
        private String autoStartTime;
        private String autoEndTime;

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

    public static class Express {

        private String billingModeId;
        private Integer minDistance;
        private Integer maxDistance;
        private Integer carLoad;
        private Integer carType;
        private Integer carCubage;
        private String expectRestTime;

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

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Receive getReceive() {
        return receive;
    }

    public void setReceive(Receive receive) {
        this.receive = receive;
    }

    public Express getExpress() {
        return express;
    }

    public void setExpress(Express express) {
        this.express = express;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public GpsInfo getHomeGps() {
        return homeGps;
    }

    public void setHomeGps(GpsInfo homeGps) {
        this.homeGps = homeGps;
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

    public GpsInfo getDeliverAreaGps() {
        return deliverAreaGps;
    }

    public void setDeliverAreaGps(GpsInfo deliverAreaGps) {
        this.deliverAreaGps = deliverAreaGps;
    }

}
