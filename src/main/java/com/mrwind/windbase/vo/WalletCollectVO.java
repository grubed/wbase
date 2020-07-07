package com.mrwind.windbase.vo;

import java.math.BigDecimal;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-01-11
 */

public class WalletCollectVO {

    private BigDecimal income = BigDecimal.valueOf(0.00);
    private BigDecimal cost = BigDecimal.valueOf(0.00);
    private BigDecimal profit = BigDecimal.valueOf(0.00);

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

}
