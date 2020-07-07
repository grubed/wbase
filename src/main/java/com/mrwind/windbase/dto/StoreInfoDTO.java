package com.mrwind.windbase.dto;

import com.alibaba.fastjson.JSON;

/**
 * @author wuyiming
 * Created by wuyiming on 2018/7/25.
 */
public class StoreInfoDTO {
    /**仓库id*/
    private String storeId;
    /**仓库编号*/
    private String storeNo;
    /**仓库名字*/
    private String storeName;
    /**仓库地址*/
    private String storeAddress;
    /**仓库纬度*/
    private Double storeLat;
    /**仓库经度*/
    private Double storeLng;
    /**仓库联系人*/
    private String linker;
    /**仓库联系方式*/
    private String linkerTel;
    /**距当前查询点的距离，单位米*/
    private double distance;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public Double getStoreLat() {
        return storeLat;
    }

    public void setStoreLat(Double storeLat) {
        this.storeLat = storeLat;
    }

    public Double getStoreLng() {
        return storeLng;
    }

    public void setStoreLng(Double storeLng) {
        this.storeLng = storeLng;
    }

    public String getLinker() {
        return linker;
    }

    public void setLinker(String linker) {
        this.linker = linker;
    }

    public String getLinkerTel() {
        return linkerTel;
    }

    public void setLinkerTel(String linkerTel) {
        this.linkerTel = linkerTel;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
