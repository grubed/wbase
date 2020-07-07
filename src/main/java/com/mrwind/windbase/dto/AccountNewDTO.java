package com.mrwind.windbase.dto;

/**
 * Created by CL-J on 2019/3/22.
 */
public class AccountNewDTO {

    private String userId;
    private Integer currency;

    public AccountNewDTO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }
}
