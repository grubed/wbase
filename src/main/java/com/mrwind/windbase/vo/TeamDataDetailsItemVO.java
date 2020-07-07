package com.mrwind.windbase.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/9/20
 */

public class TeamDataDetailsItemVO {

    private String userId;

    private String name;

    private String avatar;

    private List<String> dates = new ArrayList<>();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

}
