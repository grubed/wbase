package com.mrwind.windbase.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zjw on 2018/11/28  下午2:07
 */
public class FeeDTO {

    //免费配送里程(km)
    private BigDecimal freeMileage;

    //超出免费配送里程的计费价格（xx/每公里）
    private BigDecimal  extraMileagePrice;

    //免费配送重量(kg)
    private BigDecimal freeWeight;

    //超出免费配送重量的计费价格（xx/每千克）
    private BigDecimal extraWeightPrice;

    //免费配送体积(立方米)
    private BigDecimal freeBulk;

    //超出免费配送体积的计费价格(xx/每立方米)
    private BigDecimal extraBulkPrice;

    //提成费
    private BigDecimal commission;

    //保价费
    private BigDecimal insurance;

    //其他费用
    private BigDecimal otherFees;

    //其他费用说明
    private String otherFeesDescription;

    //单个计费单位的价格(元/个)
    private BigDecimal  unitPrice=BigDecimal.valueOf(0.00);

    //免费的计费单位个数
    private int freeBillingUnit=0;

    //特殊时段服务开始时间
    private String  specialServiceStartTime;

    //特殊时段服务结束时间
    private String specialServiceEndTime;

    //特殊时段服务费率
    private BigDecimal specialServiceRate;

    //地点聚合优惠距离(米)
    private BigDecimal addressDiscount;

    //优惠返现
    private BigDecimal discountFee;

    //单个计费单位重量（千克）
    private BigDecimal unitWeight;

    //单个计费单位体积（边长，厘米）
    private BigDecimal unitBulk;

    public BigDecimal getFreeMileage() {
        return freeMileage;
    }

    public void setFreeMileage(BigDecimal freeMileage) {
        this.freeMileage = freeMileage;
    }

    public BigDecimal getExtraMileagePrice() {
        return extraMileagePrice;
    }

    public void setExtraMileagePrice(BigDecimal extraMileagePrice) {
        this.extraMileagePrice = extraMileagePrice;
    }

    public BigDecimal getFreeWeight() {
        return freeWeight;
    }

    public void setFreeWeight(BigDecimal freeWeight) {
        this.freeWeight = freeWeight;
    }

    public BigDecimal getExtraWeightPrice() {
        return extraWeightPrice;
    }

    public void setExtraWeightPrice(BigDecimal extraWeightPrice) {
        this.extraWeightPrice = extraWeightPrice;
    }

    public BigDecimal getFreeBulk() {
        return freeBulk;
    }

    public void setFreeBulk(BigDecimal freeBulk) {
        this.freeBulk = freeBulk;
    }

    public BigDecimal getExtraBulkPrice() {
        return extraBulkPrice;
    }

    public void setExtraBulkPrice(BigDecimal extraBulkPrice) {
        this.extraBulkPrice = extraBulkPrice;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getInsurance() {
        return insurance;
    }

    public void setInsurance(BigDecimal insurance) {
        this.insurance = insurance;
    }

    public BigDecimal getOtherFees() {
        return otherFees;
    }

    public void setOtherFees(BigDecimal otherFees) {
        this.otherFees = otherFees;
    }

    public String getOtherFeesDescription() {
        return otherFeesDescription;
    }

    public void setOtherFeesDescription(String otherFeesDescription) {
        this.otherFeesDescription = otherFeesDescription;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getFreeBillingUnit() {
        return freeBillingUnit;
    }

    public void setFreeBillingUnit(int freeBillingUnit) {
        this.freeBillingUnit = freeBillingUnit;
    }

    public String getSpecialServiceStartTime() {
        return specialServiceStartTime;
    }

    public void setSpecialServiceStartTime(String specialServiceStartTime) {
        this.specialServiceStartTime = specialServiceStartTime;
    }

    public String getSpecialServiceEndTime() {
        return specialServiceEndTime;
    }

    public void setSpecialServiceEndTime(String specialServiceEndTime) {
        this.specialServiceEndTime = specialServiceEndTime;
    }

    public BigDecimal getSpecialServiceRate() {
        return specialServiceRate;
    }

    public void setSpecialServiceRate(BigDecimal specialServiceRate) {
        this.specialServiceRate = specialServiceRate;
    }

    public BigDecimal getAddressDiscount() {
        return addressDiscount;
    }

    public void setAddressDiscount(BigDecimal addressDiscount) {
        this.addressDiscount = addressDiscount;
    }

    public BigDecimal getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(BigDecimal discountFee) {
        this.discountFee = discountFee;
    }

    public BigDecimal getUnitWeight() {
        return unitWeight;
    }

    public void setUnitWeight(BigDecimal unitWeight) {
        this.unitWeight = unitWeight;
    }

    public BigDecimal getUnitBulk() {
        return unitBulk;
    }

    public void setUnitBulk(BigDecimal unitBulk) {
        this.unitBulk = unitBulk;
    }
}
