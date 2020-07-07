package com.mrwind.windbase.dto;

import com.mrwind.windbase.entity.mysql.PriceRule;

import java.math.BigDecimal;

public class AccountUpdataDto {
    private String orderId;
    private Integer type;
    private String source;
    private Boolean method;
    private String describe;
    private BigDecimal amount;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getMethod() {
        return method;
    }

    public void setMethod(Boolean method) {
        this.method = method;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
