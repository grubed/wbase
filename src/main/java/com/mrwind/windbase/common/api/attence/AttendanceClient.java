package com.mrwind.windbase.common.api.attence;

import com.alibaba.fastjson.JSONObject;
import com.mrwind.windbase.bo.TeamMemberLocationStatusBO;
import com.mrwind.windbase.bo.TeamStatusChangeStatBO;
import com.mrwind.windbase.bo.UserLatestLocationBO;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.entity.mongo.attendance.AttenceSummary;
import com.mrwind.windbase.vo.TeamAttendanceInfoVO;
import com.mrwind.windbase.vo.TeamUserStatusVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

//@FeignClient(name = "attence", fallback = AttendanceClientFallback.class, url = "http://hk.wisready.com")
@FeignClient(name = "attence", fallback = AttendanceClientFallback.class)
public interface AttendanceClient {

    @PutMapping("/Attence/setWorkPlaceTime")
    Result setWorkPlaceTime(@RequestBody JSONObject body);

    @GetMapping("/Attence/stat/rangeSummary")
    Result<List<AttenceSummary>> getRangeSummary(@RequestParam("teamId") String teamId, @RequestParam(value = "type", required = false) String type, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate);

    @GetMapping("/Attence/location/user_latest")
    Result<UserLatestLocationBO> getUserLatestLocation(@RequestParam("userId") String userId);

    @PostMapping("/Attence/location/latest_location_status")
    Result<List<TeamMemberLocationStatusBO>> getUserLatestLocationStatus(@RequestBody Collection<String> userIds);

    @GetMapping("/Attence/teamWorkPlace")
    Result<TeamAttendanceInfoVO> getTeamWorkPlace(@RequestParam("teamId") String teamId);

    @PostMapping("Attence/open/team_user_status")
    Result<List<TeamUserStatusVO>> getUserStatusForTeam(@RequestBody Collection<String> userIds,
                                                        @RequestParam("rootTeamId") String rootTeamId);

    @GetMapping("Attence/stat/team_status_change_stat")
    Result<TeamStatusChangeStatBO> getTeamStatusChangeStat(@RequestParam("teamId") String teamId,
                                                           @RequestParam("startDate") String startDate,
                                                           @RequestParam("endDate") String endDate);

    @GetMapping("/Attence/user_login")
    Result userLogin(@RequestParam("userId") String userId, @RequestParam("rootTeamId") String rootTeamId);

    @GetMapping("/Attence/user_logout")
    Result userLogout(@RequestParam("userId") String userId, @RequestParam("rootTeamId") String rootTeamId);


}