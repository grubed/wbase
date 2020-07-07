package com.mrwind.windbase.vo;

/**
 * 团队数据
 *
 * @author hanjie
 * @date 2018/9/18
 */

public class TeamDataVO {

    private Member member = new Member();

    private Attendance attendance = new Attendance();

    private FlExpressUserDataVO express = new FlExpressUserDataVO();

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public FlExpressUserDataVO getExpress() {
        return express;
    }

    public void setExpress(FlExpressUserDataVO express) {
        this.express = express;
    }

    public static class Member {

        /**
         * 该日新增了多少人
         */
        private int newMember;

        /**
         * 该日离职了多少人
         */
        private int quitMember;

        /**
         * 该日共有多少人
         */
        private int totalMember;

        public int getNewMember() {
            return newMember;
        }

        public void setNewMember(int newMember) {
            this.newMember = newMember;
        }

        public int getQuitMember() {
            return quitMember;
        }

        public void setQuitMember(int quitMember) {
            this.quitMember = quitMember;
        }

        public int getTotalMember() {
            return totalMember;
        }

        public void setTotalMember(int totalMember) {
            this.totalMember = totalMember;
        }
    }

    public static class Attendance {

        /**
         * 团队共计工作人数
         */
        private int workMembers;

        /**
         * 团队中出内勤人数
         */
        private int inMember;

        /**
         * 团队中出外勤人数
         */
        private int outMember;

        /**
         * 团队迟到人数
         */
        private int lateMember;

        /**
         * 团队请假人数
         */
        private int leaveMember;

        /**
         * 团队无班人数
         */
        private int dayOffMember;

        public int getWorkMembers() {
            return workMembers;
        }

        public void setWorkMembers(int workMembers) {
            this.workMembers = workMembers;
        }

        public int getInMember() {
            return inMember;
        }

        public void setInMember(int inMember) {
            this.inMember = inMember;
        }

        public int getOutMember() {
            return outMember;
        }

        public void setOutMember(int outMember) {
            this.outMember = outMember;
        }

        public int getLateMember() {
            return lateMember;
        }

        public void setLateMember(int lateMember) {
            this.lateMember = lateMember;
        }

        public int getLeaveMember() {
            return leaveMember;
        }

        public void setLeaveMember(int leaveMember) {
            this.leaveMember = leaveMember;
        }

        public int getDayOffMember() {
            return dayOffMember;
        }

        public void setDayOffMember(int dayOffMember) {
            this.dayOffMember = dayOffMember;
        }
    }

}
