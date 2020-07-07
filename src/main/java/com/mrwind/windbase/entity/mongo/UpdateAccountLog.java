package com.mrwind.windbase.entity.mongo;

import com.alibaba.fastjson.JSONObject;
import com.mrwind.windbase.entity.mysql.PriceRule;
import org.mongodb.morphia.annotations.*;
import org.mongodb.morphia.utils.IndexDirection;

import java.math.BigDecimal;
import java.util.Date;

import static org.mongodb.morphia.utils.IndexDirection.*;

@Entity(noClassnameStored = true)
@Indexes(@Index(name = "orderId_type", value = "orderId,-type", unique = true))
public class UpdateAccountLog {
    @Id
    private String id;
    //@Property("orderId")
    private String orderId;
    // @Property("type")
    private Integer type;
    private String source;
    private Boolean method;
    private String describe;
    @Property
    private BigDecimal amount;
    private String sourceUserId;
    private String targetUserId;
    private String jsonType;
    private JSONObject data;

    private Date createdTime;

    @PrePersist
    void prePersist() {
        createdTime = new Date();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getJsonType() {
        return jsonType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setJsonType(String jsonType) {
        this.jsonType = jsonType;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(String sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
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
