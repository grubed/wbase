package com.mrwind.windbase.vo.team;

import com.mrwind.windbase.vo.UserLatestLocationVO;

import java.util.Date;

/**
 * Description
 *
 * @author hanjie
 * @date 2018-12-08
 */

public class TeamMemberStatus {

    private boolean delivery;
    private Date signTime;
    private Integer receiveOrder;
    private UserLatestLocationVO latestLocation;
    private Date restTime;
    private String uiType;
    private Object uiValue;

    public boolean getDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Integer getReceiveOrder() {
        return receiveOrder;
    }

    public void setReceiveOrder(Integer receiveOrder) {
        this.receiveOrder = receiveOrder;
    }

    public UserLatestLocationVO getLatestLocation() {
        return latestLocation;
    }

    public void setLatestLocation(UserLatestLocationVO latestLocation) {
        this.latestLocation = latestLocation;
    }

    public Date getRestTime() {
        return restTime;
    }

    public void setRestTime(Date restTime) {
        this.restTime = restTime;
    }

    public String getUiType() {
        return uiType;
    }

    public void setUiType(String uiType) {
        this.uiType = uiType;
    }

    public Object getUiValue() {
        return uiValue;
    }

    public void setUiValue(Object uiValue) {
        this.uiValue = uiValue;
    }

}
