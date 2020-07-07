package com.mrwind.windbase.entity.mysql;

import jdk.nashorn.internal.objects.annotations.Property;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by CL-J on 2018/12/10.
 */

@Entity
@Table(name = "user_address")
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date updateTime;
    private int isDefault;

    public UserAddress(String userId, long userAddrSort, String userAddress, String userAddressDetail, BigDecimal userLat, BigDecimal userLng, String userCountry, String userProvince, String userCity, String userDistrict, String userLandMark, int isDefault) {
        this.userId = userId;
        this.userAddrSort = userAddrSort;
        this.userAddress = userAddress;
        this.userAddressDetail = userAddressDetail;
        this.userLat = userLat;
        this.userLng = userLng;
        this.userCountry = userCountry;
        this.userProvince = userProvince;
        this.userCity = userCity;
        this.userDistrict = userDistrict;
        this.userLandMark = userLandMark;
        this.isDefault = isDefault;
    }

    public UserAddress() {
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
}
