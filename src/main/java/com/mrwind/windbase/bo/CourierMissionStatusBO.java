package com.mrwind.windbase.bo;

/**
 * Description
 *
 * @author hanjie
 * @date 2018-12-08
 */

public class CourierMissionStatusBO {

    private String userId;
    private boolean hasMission;
    private int totalUnit;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean getHasMission() {
        return hasMission;
    }

    public void setHasMission(boolean hasMission) {
        this.hasMission = hasMission;
    }

    public int getTotalUnit() {
        return totalUnit;
    }

    public void setTotalUnit(int totalUnit) {
        this.totalUnit = totalUnit;
    }

}
