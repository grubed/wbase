package com.mrwind.windbase.entity.mysql;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by CL-J on 2018/7/20.
 *
 * 根团队资金明细
 */

@Entity
@Table(name = "capital_detail")
public class CapitalDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long  id;

    private String rootTeamId;

    private String teamId;

    private String userId;

    //标题
    private String title;

    //类型，数据项：1-风力配送费
    private int type;

    //二级类型，数据项：1-收入，2-支出
    private int subType;

    //本次变动金额 单位 元
    private BigDecimal cost;
    //备注
    private String remark;
    //本次操作后余额
    private BigDecimal amount;

    //充值类型 1.微信支付  2.支付宝支付
    private int rechargeType;

    //第三方交易单号Id
    private String payId;

    @CreationTimestamp
    @Column(name="create_time")
    private Date createTime;

    @UpdateTimestamp
    @Column(name="update_time")
    private Date updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSubType() {
        return subType;
    }

    public void setSubType(int subType) {
        this.subType = subType;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRootTeamId() {
        return rootTeamId;
    }

    public void setRootTeamId(String rootTeamId) {
        this.rootTeamId = rootTeamId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(int rechargeType) {
        this.rechargeType = rechargeType;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
