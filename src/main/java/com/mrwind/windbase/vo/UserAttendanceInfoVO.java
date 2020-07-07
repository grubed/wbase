package com.mrwind.windbase.vo;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/9/6
 */

public class UserAttendanceInfoVO extends BaseAttendanceInfoVO {

    private String userId;
    private boolean follow;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean getFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

}
