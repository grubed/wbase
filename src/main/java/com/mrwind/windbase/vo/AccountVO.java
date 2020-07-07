package com.mrwind.windbase.vo;

import java.math.BigDecimal;

/**
 * Description
 *
 * @author hanjie
 * @date 2018-12-21
 */

public class AccountVO {

    private String id;
    private BigDecimal amount = BigDecimal.valueOf(0.00);
    private Integer currency = 0;
    private BigDecimal cash = BigDecimal.valueOf(0.00);
    private BigDecimal debt = BigDecimal.valueOf(0.00);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public BigDecimal getDebt() {
        return debt;
    }

    public void setDebt(BigDecimal debt) {
        this.debt = debt;
    }

}
