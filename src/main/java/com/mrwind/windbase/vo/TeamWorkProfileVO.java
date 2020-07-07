package com.mrwind.windbase.vo;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-01-11
 */

public class TeamWorkProfileVO {

    private String userId;
    private String avatar;
    private String tel;
    private String name;
    private Integer status;
    private int carryNumFree;
    private int carryNum;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getCarryNumFree() {
        return carryNumFree;
    }

    public void setCarryNumFree(int carryNumFree) {
        this.carryNumFree = carryNumFree;
    }

    public int getCarryNum() {
        return carryNum;
    }

    public void setCarryNum(int carryNum) {
        this.carryNum = carryNum;
    }

}
