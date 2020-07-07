package com.mrwind.windbase.dto;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * Created by zjw on 2018/8/22  下午3:04
 * 新版的计费规则
 */
public class PriceRuleDTO {

    /**
     * 服务器选择，如（冰岛，台湾...），如果团队有特殊计费规则，本字段为空
     * */
    private String project;

    //计费规则方案名称(例：杭州A类计费规则)
    @NotBlank
    private String name;

    //计费规则描述
    @NotBlank
    private String description;

    //货币单位
    @NotBlank
    private String currency;

    //起步价格
    private BigDecimal basePrice;

    //是否为默认计费规则
    private boolean defaultMode;

    //即时达计费规则
    private FeeDTO immediatePriceRule;

    //今日达计费规则
    private FeeDTO todayPriceRule;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public boolean isDefaultMode() {
        return defaultMode;
    }

    public void setDefaultMode(boolean defaultMode) {
        this.defaultMode = defaultMode;
    }

    public FeeDTO getImmediatePriceRule() {
        return immediatePriceRule;
    }

    public void setImmediatePriceRule(FeeDTO immediatePriceRule) {
        this.immediatePriceRule = immediatePriceRule;
    }

    public FeeDTO getTodayPriceRule() {
        return todayPriceRule;
    }

    public void setTodayPriceRule(FeeDTO todayPriceRule) {
        this.todayPriceRule = todayPriceRule;
    }


}
