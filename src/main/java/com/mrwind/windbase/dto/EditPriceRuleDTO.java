package com.mrwind.windbase.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by zjw on 2018/9/5  下午8:04
 */
public class EditPriceRuleDTO {
    //费用名称
    @NotNull
    private String name;

    //费用金额，单位元
    @NotNull
    private BigDecimal price;

    //费用描述
    @NotNull
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
