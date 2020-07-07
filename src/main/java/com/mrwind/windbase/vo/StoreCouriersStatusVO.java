package com.mrwind.windbase.vo;

import java.util.List;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/11/5
 */

public class StoreCouriersStatusVO {

    private String warehouseTeamId;
    private String warehouseTeamName;
    private Double warehouseLat;
    private Double warehouseLng;
    private String expressStartTime;
    private String expressEndTime;
    private List<CourierStatus> userList;

    public String getWarehouseTeamId() {
        return warehouseTeamId;
    }

    public void setWarehouseTeamId(String warehouseTeamId) {
        this.warehouseTeamId = warehouseTeamId;
    }

    public String getWarehouseTeamName() {
        return warehouseTeamName;
    }

    public void setWarehouseTeamName(String warehouseTeamName) {
        this.warehouseTeamName = warehouseTeamName;
    }

    public Double getWarehouseLat() {
        return warehouseLat;
    }

    public void setWarehouseLat(Double warehouseLat) {
        this.warehouseLat = warehouseLat;
    }

    public Double getWarehouseLng() {
        return warehouseLng;
    }

    public void setWarehouseLng(Double warehouseLng) {
        this.warehouseLng = warehouseLng;
    }

    public List<CourierStatus> getUserList() {
        return userList;
    }

    public void setUserList(List<CourierStatus> userList) {
        this.userList = userList;
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

    public static class CourierStatus {
        private String userId;
        private String userName;
        private String userAvatar;
        private String tel;
        private Integer receiveOrderStatus;
        private Integer orderLimit;
        private Integer lowerBound;
        private Integer carryNum;
        private String deliverAreaAddress;
        private Double deliverAreaLng;
        private Double deliverAreaLat;
        private String homeAddress;
        private Double homeLat;
        private Double homeLng;

        public Integer getCarryNum() {
            return carryNum;
        }

        public void setCarryNum(Integer carryNum) {
            this.carryNum = carryNum;
        }

        public Integer getLowerBound() {
            return lowerBound;
        }

        public void setLowerBound(Integer lowerBound) {
            this.lowerBound = lowerBound;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
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

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public Integer getReceiveOrderStatus() {
            return receiveOrderStatus;
        }

        public void setReceiveOrderStatus(Integer receiveOrderStatus) {
            this.receiveOrderStatus = receiveOrderStatus;
        }

        public Integer getOrderLimit() {
            return orderLimit;
        }

        public void setOrderLimit(Integer orderLimit) {
            this.orderLimit = orderLimit;
        }

        public String getDeliverAreaAddress() {
            return deliverAreaAddress;
        }

        public void setDeliverAreaAddress(String deliverAreaAddress) {
            this.deliverAreaAddress = deliverAreaAddress;
        }

        public Double getDeliverAreaLng() {
            return deliverAreaLng;
        }

        public void setDeliverAreaLng(Double deliverAreaLng) {
            this.deliverAreaLng = deliverAreaLng;
        }

        public Double getDeliverAreaLat() {
            return deliverAreaLat;
        }

        public void setDeliverAreaLat(Double deliverAreaLat) {
            this.deliverAreaLat = deliverAreaLat;
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

    }

}
