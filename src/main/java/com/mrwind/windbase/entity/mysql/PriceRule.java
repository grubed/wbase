package com.mrwind.windbase.entity.mysql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by CL-J on 2018/8/22.
 */
@Entity
@Table(name = "price_rule")
public class PriceRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long  id;


    /**
     * 服务器选择，如（冰岛，台湾...），如果团队有特殊计费规则，本字段为空
     * */
    private String project="";

    //计费规则方案名称(例：杭州A类计费规则)
    private String name;

    //计费类型（目前分两种：1.即时达 immediate 2.当日达 today）
    private String type;

    //计费规则描述
    private String description;

    //货币单位
    private String currency;

    //起步价格
    private BigDecimal basePrice=BigDecimal.valueOf(0.00);

    //免费配送里程(km)
    private BigDecimal  freeMileage=BigDecimal.valueOf(0.00);

    //超出免费配送里程的计费价格（xx/每公里）
    private BigDecimal  extraMileagePrice=BigDecimal.valueOf(0.00);

    //免费配送重量(kg)
    private BigDecimal freeWeight=BigDecimal.valueOf(0.00);

    //超出免费配送重量的计费价格（xx/每千克）
    private BigDecimal extraWeightPrice=BigDecimal.valueOf(0.00);

    //免费配送体积(立方米)
    private BigDecimal freeBulk=BigDecimal.valueOf(0.00);

    //超出免费配送体积的计费价格(xx/每立方米)
    private BigDecimal extraBulkPrice=BigDecimal.valueOf(0.00);

    //提成费
    private BigDecimal commission=BigDecimal.valueOf(0.00);

    //保价费
    private BigDecimal insurance=BigDecimal.valueOf(0.00);

    //其他费用
    private BigDecimal otherFees=BigDecimal.valueOf(0.00);

    //其他费用说明
    private String otherFeesDescription;

    //单个计费单位的价格(元/个)
    private BigDecimal  unitPrice=BigDecimal.valueOf(0.00);

    //免费的计费单位个数
    private int freeBillingUnit=0;

    //特殊时段服务开始时间
    private String specialServiceStartTime="18:00";

    //特殊时段服务结束时间
    private String  specialServiceEndTime="23:00";

    //特殊时段服务费率
    private BigDecimal specialServiceRate=BigDecimal.valueOf(1.00);

    //地点聚合优惠距离(米)
    private BigDecimal addressDiscount=BigDecimal.valueOf(0.00);

    //优惠返现
    private BigDecimal discountFee=BigDecimal.valueOf(0.00);

    //单个计费单位重量（千克）
    private BigDecimal unitWeight=BigDecimal.valueOf(0.00);

    //单个计费单位体积（边长，厘米）
    private BigDecimal unitBulk=BigDecimal.valueOf(0.00);


    //是否为默认计费规则
    private boolean defaultMode;

    @CreationTimestamp
    @JsonIgnore
    private Date creatTime;

    @UpdateTimestamp
    @JsonIgnore
    private Date updateTime;

    /**先废弃*/
//    /**
//     * 费用类型，数据项：base 起步点位费,base-delivery 基础投递费,
//     * exceeding-mileage 超出部分里程费,exceeding-weight 超出部分重量费,
//     * exceeding-bulk 超出部分体积费,commission 提成费,insurance 保价费
//     * */
//    private String  type;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

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

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public boolean isDefaultMode() {
        return defaultMode;
    }

    public void setDefaultMode(boolean defaultMode) {
        this.defaultMode = defaultMode;
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
