package com.mrwind.windbase.dto;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by CL-J on 2018/12/11.
 */
public class CreateUserAddressDTO {
    private Long id;

    private String userId;
    private String shopId;
    private long userAddrSort;
    private String userAddress;
    private String userAddressDetail;
    private BigDecimal userLat;
    private BigDecimal userLng;
    private String userCountry;
    private String userProvince;
    private String userCity;
    private String userDistrict;
    private String userLandMark;
    private int isDefault;


    public CreateUserAddressDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public long getUserAddrSort() {
        return userAddrSort;
    }

    public void setUserAddrSort(long userAddrSort) {
        this.userAddrSort = userAddrSort;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserAddressDetail() {
        return userAddressDetail;
    }

    public void setUserAddressDetail(String userAddressDetail) {
        this.userAddressDetail = userAddressDetail;
    }

    public BigDecimal getUserLat() {
        return userLat;
    }

    public void setUserLat(BigDecimal userLat) {
        this.userLat = userLat;
    }

    public BigDecimal getUserLng() {
        return userLng;
    }

    public void setUserLng(BigDecimal userLng) {
        this.userLng = userLng;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getUserProvince() {
        return userProvince;
    }

    public void setUserProvince(String userProvince) {
        this.userProvince = userProvince;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserDistrict() {
        return userDistrict;
    }

    public void setUserDistrict(String userDistrict) {
        this.userDistrict = userDistrict;
    }

    public String getUserLandMark() {
        return userLandMark;
    }

    public void setUserLandMark(String userLandMark) {
        this.userLandMark = userLandMark;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public String toString() {
        return "CreateUserAddressDTO{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", shopId='" + shopId + '\'' +
                ", userAddrSort=" + userAddrSort +
                ", userAddress='" + userAddress + '\'' +
                ", userAddressDetail='" + userAddressDetail + '\'' +
                ", userLat=" + userLat +
                ", userLng=" + userLng +
                ", userCountry='" + userCountry + '\'' +
                ", userProvince='" + userProvince + '\'' +
                ", userCity='" + userCity + '\'' +
                ", userDistrict='" + userDistrict + '\'' +
                ", userLandMark='" + userLandMark + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
