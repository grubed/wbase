package com.mrwind.windbase.bo;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/8/28
 */

public class DeliveryShipmentStatusBO {

    private int todayOrder;

    private int totalOrder;

    private double totalPrice;

    public int getTodayOrder() {
        return todayOrder;
    }

    public void setTodayOrder(int todayOrder) {
        this.todayOrder = todayOrder;
    }

    public int getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(int totalOrder) {
        this.totalOrder = totalOrder;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
