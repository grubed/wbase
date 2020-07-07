package com.mrwind.windbase.vo;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/9/21
 */

public class FlExpressUserDataVO {

    private double distance;
    private double weight;
    private double amount;
    private long preemptionNum;
    private long printNum;
    private long unPrintNum;
    private long pickNum;
    private long transferNum;
    private long deliveredNum;
    private long undeliverNum;
    private long unPickNum;
    private long nowPickedNum;
    private TeamClientDataVO client = new TeamClientDataVO();

    public TeamClientDataVO getClient() {
        return client;
    }

    public void setClient(TeamClientDataVO client) {
        this.client = client;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getPreemptionNum() {
        return preemptionNum;
    }

    public void setPreemptionNum(long preemptionNum) {
        this.preemptionNum = preemptionNum;
    }

    public long getPrintNum() {
        return printNum;
    }

    public void setPrintNum(long printNum) {
        this.printNum = printNum;
    }

    public long getPickNum() {
        return pickNum;
    }

    public void setPickNum(long pickNum) {
        this.pickNum = pickNum;
    }

    public long getTransferNum() {
        return transferNum;
    }

    public void setTransferNum(long transferNum) {
        this.transferNum = transferNum;
    }

    public long getDeliveredNum() {
        return deliveredNum;
    }

    public void setDeliveredNum(long deliveredNum) {
        this.deliveredNum = deliveredNum;
    }

    public long getUndeliverNum() {
        return undeliverNum;
    }

    public void setUndeliverNum(long undeliverNum) {
        this.undeliverNum = undeliverNum;
    }

    public long getUnPickNum() {
        return unPickNum;
    }

    public void setUnPickNum(long unPickNum) {
        this.unPickNum = unPickNum;
    }

    public long getNowPickedNum() {
        return nowPickedNum;
    }

    public void setNowPickedNum(long nowPickedNum) {
        this.nowPickedNum = nowPickedNum;
    }

    public long getUnPrintNum() {
        return unPrintNum;
    }

    public void setUnPrintNum(long unPrintNum) {
        this.unPrintNum = unPrintNum;
    }

}
