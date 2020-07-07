package com.mrwind.windbase.vo;

/**
 * 个人数据
 *
 * @author hanjie
 * @date 2018/9/18
 */

public class UserDataVO {

    private UserAttendanceDataVO attendance = new UserAttendanceDataVO();

    private FlExpressUserDataVO express = new FlExpressUserDataVO();

    public UserAttendanceDataVO getAttendance() {
        return attendance;
    }

    public void setAttendance(UserAttendanceDataVO attendance) {
        this.attendance = attendance;
    }

    public FlExpressUserDataVO getExpress() {
        return express;
    }

    public void setExpress(FlExpressUserDataVO express) {
        this.express = express;
    }

}
