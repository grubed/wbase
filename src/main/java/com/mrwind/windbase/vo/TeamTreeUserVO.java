package com.mrwind.windbase.vo;

/**
 * Created by CL-J on 2018/9/25.
 */
public class TeamTreeUserVO {
    private String userId;
    private String name;

    public TeamTreeUserVO(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

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
}
