package com.mrwind.windbase.entity.mongo.attendance;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;


/**
 * Created by CL-J on 29/03/2018.
 */
@Entity(noClassnameStored = true,value = "AttenceHourData")
public class AttenceHourData {

    @Id
    private String id;

    private String userid;
    private String date; //当天日期 年月日
    private String hour;//时间  09:00


    private String address;
    private String addressDetail;
    private double lat;
    private double lng;
    private String statu;
    private boolean userAction = false;

    /**
     * 当前团队 id
     */
    private String teamId;


    private String timeStamp;

    public AttenceHourData() {
    }

    public AttenceHourData(String userid, String date, String hour, String address, String addressDetail, double lat, double lng, String statu, boolean userAction, String teamId, String timeStamp) {
        this.userid = userid;
        this.date = date;
        this.hour = hour;
        this.address = address;
        this.addressDetail = addressDetail;
        this.lat = lat;
        this.lng = lng;
        this.statu = statu;
        this.userAction = userAction;
        this.teamId = teamId;
        this.timeStamp = timeStamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isUserAction() {
        return userAction;
    }

    public void setUserAction(boolean userAction) {
        this.userAction = userAction;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return "AttenceHourData{" +
                "userid='" + userid + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", address='" + address + '\'' +
                ", addressDetail='" + addressDetail + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", statu='" + statu + '\'' +
                ", userAction=" + userAction +
                ", teamId='" + teamId + '\'' +
                '}';
    }


}
