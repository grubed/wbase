package com.mrwind.windbase.dto;

/**
 * 更新用户资料
 *
 * @author hanjie
 * @date 2018/7/24
 */

public class UpdateUserInfoDTO {

    private String name;
    private String avatar;
    private String windId;
    private String flType;

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

    public String getFlType() {
        return flType;
    }

    public void setFlType(String flType) {
        this.flType = flType;
    }

    public String getWindId() {
        return windId;
    }

    public void setWindId(String windId) {
        this.windId = windId;
    }

}
