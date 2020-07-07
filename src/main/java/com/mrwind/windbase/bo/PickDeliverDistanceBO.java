package com.mrwind.windbase.bo;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/8/27
 */

public class PickDeliverDistanceBO {


    /**
     * pick : 0
     * delivered : 0
     * pickDistance : 0
     * deliveredDistance : 0
     */

    private int pick;
    private int delivered;
    private double pickDistance;
    private double deliveredDistance;

    public int getPick() {
        return pick;
    }

    public void setPick(int pick) {
        this.pick = pick;
    }

    public int getDelivered() {
        return delivered;
    }

    public void setDelivered(int delivered) {
        this.delivered = delivered;
    }

    public double getPickDistance() {
        return pickDistance;
    }

    public void setPickDistance(double pickDistance) {
        this.pickDistance = pickDistance;
    }

    public double getDeliveredDistance() {
        return deliveredDistance;
    }

    public void setDeliveredDistance(double deliveredDistance) {
        this.deliveredDistance = deliveredDistance;
    }

}
