package com.mrwind.windbase.common.api.attence;

import com.alibaba.fastjson.JSONObject;
import com.mrwind.windbase.bo.TeamMemberLocationStatusBO;
import com.mrwind.windbase.bo.TeamStatusChangeStatBO;
import com.mrwind.windbase.bo.UserLatestLocationBO;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.entity.mongo.attendance.AttenceSummary;
import com.mrwind.windbase.vo.TeamAttendanceInfoVO;
import com.mrwind.windbase.vo.TeamUserStatusVO;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/11/7
 */
@Component
public class AttendanceClientFallback implements AttendanceClient {

    @Override
    public Result setWorkPlaceTime(JSONObject body) {
        return null;
    }

    @Override
    public Result<List<AttenceSummary>> getRangeSummary(String teamId, String type, String startDate, String endDate) {
        return null;
    }

    @Override
    public Result<UserLatestLocationBO> getUserLatestLocation(String userId) {
        return null;
    }

    @Override
    public Result<List<TeamMemberLocationStatusBO>> getUserLatestLocationStatus(Collection<String> userIds) {
        return null;
    }

    @Override
    public Result<TeamAttendanceInfoVO> getTeamWorkPlace(String teamId) {
        return null;
    }

    @Override
    public Result<List<TeamUserStatusVO>> getUserStatusForTeam(Collection<String> userIds, String rootTeamId) {
        return null;
    }

    @Override
    public Result<TeamStatusChangeStatBO> getTeamStatusChangeStat(String teamId, String startDate, String endDate) {
        return null;
    }

    @Override
    public Result userLogin(String userId, String rootTeamId) {
        return null;
    }

    @Override
    public Result userLogout(String userId, String rootTeamId) {
        return null;
    }

}