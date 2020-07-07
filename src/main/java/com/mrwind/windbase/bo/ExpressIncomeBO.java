package com.mrwind.windbase.bo;

import java.math.BigDecimal;

/**
 * Description
 *
 * @author hanjie
 * @date 2018-12-01
 */

public class ExpressIncomeBO {

    private BigDecimal totalIncome = BigDecimal.valueOf(0.0);
    private int incomeRecordNum;

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public int getIncomeRecordNum() {
        return incomeRecordNum;
    }

    public void setIncomeRecordNum(int incomeRecordNum) {
        this.incomeRecordNum = incomeRecordNum;
    }
}
