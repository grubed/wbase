package com.mrwind.windbase.vo;

import java.util.Date;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-01-09
 */

public class TeamUserStatusVO {

    private String userId;
    private String status;
    private Date time;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    
}
