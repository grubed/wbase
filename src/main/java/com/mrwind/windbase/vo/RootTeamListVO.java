package com.mrwind.windbase.vo;

import java.math.BigDecimal;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/8/22
 */

public class RootTeamListVO {

    private String teamId;

    private String project;

    private String name;

    private String avatar;

    private String rootId;

    private BigDecimal perPrice;

    private String remark;

    /**
     * 用户在该团队的名字
     */
    private String userTeamName;

    /**
     * 是否为根团队管理员
     */
    private boolean manager;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public BigDecimal getPerPrice() {
        return perPrice;
    }

    public void setPerPrice(BigDecimal perPrice) {
        this.perPrice = perPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserTeamName() {
        return userTeamName;
    }

    public void setUserTeamName(String userTeamName) {
        this.userTeamName = userTeamName;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

}
