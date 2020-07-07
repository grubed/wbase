package com.mrwind.windbase.vo;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/11/16
 */

public class TeamClientDataVO {

    private long newClientNum;
    private long activeClientNum;
    private long unActiveClientNum;
    private long clientOrderNum;

    public long getNewClientNum() {
        return newClientNum;
    }

    public void setNewClientNum(long newClientNum) {
        this.newClientNum = newClientNum;
    }

    public long getActiveClientNum() {
        return activeClientNum;
    }

    public void setActiveClientNum(long activeClientNum) {
        this.activeClientNum = activeClientNum;
    }

    public long getUnActiveClientNum() {
        return unActiveClientNum;
    }

    public void setUnActiveClientNum(long unActiveClientNum) {
        this.unActiveClientNum = unActiveClientNum;
    }

    public long getClientOrderNum() {
        return clientOrderNum;
    }

    public void setClientOrderNum(long clientOrderNum) {
        this.clientOrderNum = clientOrderNum;
    }
    
}
