package com.mrwind.windbase.entity.mysql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by CL-J on 2018/11/20.
 */

@Entity
@Table(name = "express_billing_mode")
public class ExpressBillingMode implements Serializable {

    private static final long serialVersionUID = -8158148352390842155L;

    @Id
    @GeneratedValue(generator = "idStrategy")
    @GenericGenerator(name = "idStrategy", strategy = "uuid")
    private String id;

    /**
     * 模式描述
     */
    private String name;

    /**
     * 模式描述
     */
    private String description;

    /**
     * 配送员底薪
     */
    private BigDecimal basic = BigDecimal.valueOf(0.00);
    private BigDecimal startWork = BigDecimal.valueOf(0.00);    // 底薪 - 开始工作
    private BigDecimal endWork = BigDecimal.valueOf(0.00);      // 底薪 - 结束工作
    private BigDecimal signLate = BigDecimal.valueOf(0.00);     // 底薪 - 迟到

    /**
     * 里程补贴
     */
    private BigDecimal mileageSubsidy = BigDecimal.valueOf(0.00);

    /**
     * 收件补贴
     */
    private BigDecimal pickupSubsidy = BigDecimal.valueOf(0.00);

    /**
     * 派件补贴
     */
    private BigDecimal sendSubsidy = BigDecimal.valueOf(0.00);

    /**
     * 拒单
     */
    private BigDecimal refuseOrder = BigDecimal.valueOf(0.00);

    /**
     * 今日未投递
     */
    private BigDecimal unDelivery = BigDecimal.valueOf(0.00);

    /**
     * 过夜未取件
     */
    private BigDecimal unPick = BigDecimal.valueOf(0.00);

    /**
     * 货币类型
     */
    private String currency;

    /**
     * 是否为默认计费规则
     */
    private boolean defaultMode;

    @JsonIgnore
    @CreationTimestamp
    private Date createTime;

    @JsonIgnore
    @UpdateTimestamp
    private Date updateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExpressBillingMode() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBasic() {
        return basic;
    }

    public void setBasic(BigDecimal basic) {
        this.basic = basic;
    }

    public BigDecimal getMileageSubsidy() {
        return mileageSubsidy;
    }

    public void setMileageSubsidy(BigDecimal mileageSubsidy) {
        this.mileageSubsidy = mileageSubsidy;
    }

    public BigDecimal getPickupSubsidy() {
        return pickupSubsidy;
    }

    public void setPickupSubsidy(BigDecimal pickupSubsidy) {
        this.pickupSubsidy = pickupSubsidy;
    }

    public BigDecimal getSendSubsidy() {
        return sendSubsidy;
    }

    public void setSendSubsidy(BigDecimal sendSubsidy) {
        this.sendSubsidy = sendSubsidy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean getDefaultMode() {
        return defaultMode;
    }

    public void setDefaultMode(boolean defaultMode) {
        this.defaultMode = defaultMode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getRefuseOrder() {
        return refuseOrder;
    }

    public void setRefuseOrder(BigDecimal refuseOrder) {
        this.refuseOrder = refuseOrder;
    }

    public BigDecimal getUnDelivery() {
        return unDelivery;
    }

    public void setUnDelivery(BigDecimal unDelivery) {
        this.unDelivery = unDelivery;
    }

    public BigDecimal getUnPick() {
        return unPick;
    }

    public void setUnPick(BigDecimal unPick) {
        this.unPick = unPick;
    }

    public BigDecimal getStartWork() {
        return startWork;
    }

    public void setStartWork(BigDecimal startWork) {
        this.startWork = startWork;
    }

    public BigDecimal getEndWork() {
        return endWork;
    }

    public void setEndWork(BigDecimal endWork) {
        this.endWork = endWork;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getSignLate() {
        return signLate;
    }

    public void setSignLate(BigDecimal signLate) {
        this.signLate = signLate;
    }

}
