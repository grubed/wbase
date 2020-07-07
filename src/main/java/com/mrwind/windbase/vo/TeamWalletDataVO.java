package com.mrwind.windbase.vo;

import java.math.BigDecimal;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-01-02
 */

public class TeamWalletDataVO {


    /**
     * income : 1786
     * cost : 479.42
     * profit : 1306.58
     * date : 2019-01-01 00:00:00 ~ 2019-01-01 23:59:59
     * countorder : 46
     */

    private BigDecimal income;
    private BigDecimal cost;
    private BigDecimal profit;
    private String date;
    private long countorder;

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getCountorder() {
        return countorder;
    }

    public void setCountorder(long countorder) {
        this.countorder = countorder;
    }

}
