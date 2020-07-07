package com.mrwind.windbase.dto;

import javax.validation.constraints.NotBlank;

/**
 * Created by zjw on 2018/9/25  下午4:41
 */
public class TeamShopDTO {
    @NotBlank
    private String shop;
    @NotBlank
    private String rootTeamId;

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getRootTeamId() {
        return rootTeamId;
    }

    public void setRootTeamId(String rootTeamId) {
        this.rootTeamId = rootTeamId;
    }
}
