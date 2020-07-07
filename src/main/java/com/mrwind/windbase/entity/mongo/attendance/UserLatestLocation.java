package com.mrwind.windbase.entity.mongo.attendance;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * 用户最近位置
 *
 * @author hanjie
 * @date 2018/10/16
 */

@Entity(value = "user_latest_location")
public class UserLatestLocation {

    @Id
    private String userId;
    private Date updateTime;
    private String address;
    private String addressDetail;
    private Position position;

    public static class Position {

        private double lng;

        private double lat;

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "lng=" + lng +
                    ", lat=" + lat +
                    '}';
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "UserLatestLocation{" +
                "userId='" + userId + '\'' +
                ", updateTime=" + updateTime +
                ", address='" + address + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", position=" + position +
                '}';
    }
}
