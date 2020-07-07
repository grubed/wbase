package com.mrwind.windbase.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by zjw on 2018/9/5  上午10:42
 */
public class RootTeamPriceRuleDTO {
    @NotNull
    private  String rootTeamId;

    //即时达计费规则Id
    private Long immediateRuleId;

    //今日达计费规则Id
    private Long todayRuleId;

    public String getRootTeamId() {
        return rootTeamId;
    }

    public void setRootTeamId(String rootTeamId) {
        this.rootTeamId = rootTeamId;
    }

    public Long getImmediateRuleId() {
        return immediateRuleId;
    }

    public void setImmediateRuleId(Long immediateRuleId) {
        this.immediateRuleId = immediateRuleId;
    }

    public Long getTodayRuleId() {
        return todayRuleId;
    }

    public void setTodayRuleId(Long todayRuleId) {
        this.todayRuleId = todayRuleId;
    }
}
