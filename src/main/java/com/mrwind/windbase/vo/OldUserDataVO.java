package com.mrwind.windbase.vo;

/**
 * 用户数据页
 *
 * @author hanjie
 * @date 2018/8/23
 */

public class OldUserDataVO {

    private Attence attence;

    private Delivery delivery;

    private Team team;

    public Attence getAttence() {
        return attence;
    }

    public void setAttence(Attence attence) {
        this.attence = attence;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public static class Attence {

        private int attenceDay;

        private int lateDay;

        private double workHours;

        private double outCount;

        public int getAttenceDay() {
            return attenceDay;
        }

        public void setAttenceDay(int attenceDay) {
            this.attenceDay = attenceDay;
        }

        public int getLateDay() {
            return lateDay;
        }

        public void setLateDay(int lateDay) {
            this.lateDay = lateDay;
        }

        public double getWorkHours() {
            return workHours;
        }

        public void setWorkHours(double workHours) {
            this.workHours = workHours;
        }

        public double getOutCount() {
            return outCount;
        }

        public void setOutCount(double outCount) {
            this.outCount = outCount;
        }
    }

    public static class Delivery {

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

    public static class Team {

        private int attenceMember;

        private int outMember;

        private int quitMember;

        private int newMember;

        public int getAttenceMember() {
            return attenceMember;
        }

        public void setAttenceMember(int attenceMember) {
            this.attenceMember = attenceMember;
        }

        public int getOutMember() {
            return outMember;
        }

        public void setOutMember(int outMember) {
            this.outMember = outMember;
        }

        public int getQuitMember() {
            return quitMember;
        }

        public void setQuitMember(int quitMember) {
            this.quitMember = quitMember;
        }

        public int getNewMember() {
            return newMember;
        }

        public void setNewMember(int newMember) {
            this.newMember = newMember;
        }

    }

}
