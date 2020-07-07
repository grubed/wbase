package com.mrwind.windbase.entity.mysql;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by CL-J on 2018/7/23.
 */
@Entity
@Table(name = "team_extention")
public class TeamExtention {
    @Id
    private String teamId;

    private String flAdress;

    private String flLinker;

    private String flTel;

    // TODO 默认值为多少 ?  目前先设置15
    private BigDecimal flCost = BigDecimal.valueOf(0.0);

    private String flCountry;
    private String flProvince;
    private String flCity;
    private String flDistrict;
    private String flLandMark;
    private String expressBillingModeId;
    /**
     * 团队取派开始时间
     */
    private String expressStartTime;
    /**
     * 团队取派结束时间
     */
    private String expressEndTime;

    public TeamExtention() {
    }

    public TeamExtention(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getFlAdress() {
        return flAdress;
    }

    public void setFlAdress(String flAdress) {
        this.flAdress = flAdress;
    }

    public String getFlLinker() {
        return flLinker;
    }

    public void setFlLinker(String flLinker) {
        this.flLinker = flLinker;
    }

    public String getFlTel() {
        return flTel;
    }

    public void setFlTel(String flTel) {
        this.flTel = flTel;
    }

    public BigDecimal getFlCost() {
        return flCost;
    }

    public void setFlCost(BigDecimal flCost) {
        this.flCost = flCost;
    }

    public String getFlCountry() {
        return flCountry;
    }

    public void setFlCountry(String flCountry) {
        this.flCountry = flCountry;
    }

    public String getFlProvince() {
        return flProvince;
    }

    public void setFlProvince(String flProvince) {
        this.flProvince = flProvince;
    }

    public String getFlCity() {
        return flCity;
    }

    public void setFlCity(String flCity) {
        this.flCity = flCity;
    }

    public String getFlDistrict() {
        return flDistrict;
    }

    public void setFlDistrict(String flDistrict) {
        this.flDistrict = flDistrict;
    }

    public String getFlLandMark() {
        return flLandMark;
    }

    public void setFlLandMark(String flLandMark) {
        this.flLandMark = flLandMark;
    }

    public String getExpressBillingModeId() {
        return expressBillingModeId;
    }

    public void setExpressBillingModeId(String expressBillingModeId) {
        this.expressBillingModeId = expressBillingModeId;
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
    
}
