package com.mrwind.windbase.common.api.attence;

import com.alibaba.fastjson.JSONObject;
import com.mrwind.windbase.bo.TeamMemberLocationStatusBO;
import com.mrwind.windbase.bo.TeamStatusChangeStatBO;
import com.mrwind.windbase.bo.UserLatestLocationBO;
import com.mrwind.windbase.common.constant.AttenceSummaryType;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.common.util.TextUtils;
import com.mrwind.windbase.dao.mongo.FeignErrorLogDao;
import com.mrwind.windbase.entity.mongo.FeignErrorLog;
import com.mrwind.windbase.entity.mongo.attendance.AttenceSummary;
import com.mrwind.windbase.vo.TeamAttendanceInfoVO;
import com.mrwind.windbase.vo.TeamUserStatusVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/8/29
 */
@Component
public class AttenceApi {

    @Resource
    private FeignErrorLogDao feignErrorLogDao;

    @Resource
    private AttendanceClient attendanceClient;

    public void updateUserAttence(String userId, String workStartTime, String workPlace, String workPlaceDetail, double lat, double lng, boolean follow, boolean isOpen) {
        //保存用户的工作信息
        JSONObject body = new JSONObject();
        body.put("userId", userId);
        body.put("workStartTime", workStartTime);
        body.put("workPlace", workPlace);
        body.put("workPlaceDetail", workPlaceDetail);
        body.put("lat", lat);
        body.put("lng", lng);
        body.put("follow", follow);
        body.put("isOpen", isOpen);
        try {
            attendanceClient.setWorkPlaceTime(body);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
    }

    /**
     * 获取用户最新位置信息
     *
     * @param userId 用户 id
     * @return UserLatestLocationBO
     */
    public UserLatestLocationBO getUserLatestLocation(String userId) {
        UserLatestLocationBO bo = null;
        try {
            Result<UserLatestLocationBO> result = attendanceClient.getUserLatestLocation(userId);
            bo = Optional.ofNullable(result).map(Result::getData).orElse(bo);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        return bo;
    }

    /**
     * 获取指定范围内的团队考勤总结
     *
     * @param startDate     起始时间
     * @param endDate       结束时间
     * @param currentTeamId 根团队 id
     * @param type          类型
     * @return 团队考勤总结
     */
    public List<AttenceSummary> getTeamRangeSummary(String startDate, String endDate, String currentTeamId, AttenceSummaryType type) {
        List<AttenceSummary> summaries = new ArrayList<>();
        try {
            Result<List<AttenceSummary>> result = attendanceClient.getRangeSummary(currentTeamId, type.name(), startDate, endDate);
            summaries = Optional.ofNullable(result).map(Result::getData).orElse(summaries);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        return summaries;
    }

    /**
     * 获取最新位置&今日第一次签到时间&休息时间
     */
    public List<TeamMemberLocationStatusBO> getTeamMemberLatestLocationStatus(Collection<String> userIds) {
        List<TeamMemberLocationStatusBO> bos = new ArrayList<>();
        try {
            Result<List<TeamMemberLocationStatusBO>> result = attendanceClient.getUserLatestLocationStatus(userIds);
            bos = Optional.ofNullable(result).map(Result::getData).orElse(bos);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        return bos;
    }

    /**
     * 获取指定范围内的团队考勤总结(所有类型)
     *
     * @param startDate     起始时间
     * @param endDate       结束时间
     * @param currentTeamId 根团队 id
     * @return 团队考勤总结
     */
    public Map<AttenceSummaryType, List<AttenceSummary>> getTeamRangeAllSummary(String startDate, String endDate, String currentTeamId) {
        Map<AttenceSummaryType, List<AttenceSummary>> summaryMaps = new HashMap<>();
        summaryMaps.put(AttenceSummaryType.ATTENCE, new ArrayList<>());
        summaryMaps.put(AttenceSummaryType.IN, new ArrayList<>());
        summaryMaps.put(AttenceSummaryType.OUT, new ArrayList<>());
        summaryMaps.put(AttenceSummaryType.LATE, new ArrayList<>());
        summaryMaps.put(AttenceSummaryType.LEAVE, new ArrayList<>());
        summaryMaps.put(AttenceSummaryType.DAY_OFF, new ArrayList<>());
        List<AttenceSummary> summaries = new ArrayList<>();
        try {
            Result<List<AttenceSummary>> result = attendanceClient.getRangeSummary(currentTeamId, null, startDate, endDate);
            summaries = Optional.ofNullable(result).map(Result::getData).orElse(summaries);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        if (summaries.isEmpty()) {
            return summaryMaps;
        }
        for (AttenceSummary summary : summaries) {
            String type = summary.getType();
            if (TextUtils.equals(type, AttenceSummaryType.ATTENCE.name())) {
                summaryMaps.get(AttenceSummaryType.ATTENCE).add(summary);
            } else if (TextUtils.equals(type, AttenceSummaryType.IN.name())) {
                summaryMaps.get(AttenceSummaryType.IN).add(summary);
            } else if (TextUtils.equals(type, AttenceSummaryType.OUT.name())) {
                summaryMaps.get(AttenceSummaryType.OUT).add(summary);
            } else if (TextUtils.equals(type, AttenceSummaryType.LATE.name())) {
                summaryMaps.get(AttenceSummaryType.LATE).add(summary);
            } else if (TextUtils.equals(type, AttenceSummaryType.LEAVE.name())) {
                summaryMaps.get(AttenceSummaryType.LEAVE).add(summary);
            } else if (TextUtils.equals(type, AttenceSummaryType.DAY_OFF.name())) {
                summaryMaps.get(AttenceSummaryType.DAY_OFF).add(summary);
            }
        }
        return summaryMaps;
    }

    /**
     * 获取团队考勤信息
     */
    public TeamAttendanceInfoVO getTeamAttendanceInfo(String teamId) {
        TeamAttendanceInfoVO bo = null;
        try {
            Result<TeamAttendanceInfoVO> result = attendanceClient.getTeamWorkPlace(teamId);
            bo = Optional.ofNullable(result).map(Result::getData).orElse(bo);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        return bo;
    }

    /**
     * 批量获取用户的当前状态 For Team
     */
    public List<TeamUserStatusVO> getUserStatusForTeam(Collection<String> userIds, String rootTeamId) {
        List<TeamUserStatusVO> vos = new ArrayList<>();
        try {
            Result<List<TeamUserStatusVO>> result = attendanceClient.getUserStatusForTeam(userIds, rootTeamId);
            vos = Optional.ofNullable(result).map(Result::getData).orElse(vos);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        return vos;
    }

    /**
     * 获取一个团队的签到记录数据
     */
    public TeamStatusChangeStatBO getTeamStatusChangeStat(String teamId, String startDate, String endDate) {
        TeamStatusChangeStatBO bo = new TeamStatusChangeStatBO();
        try {
            Result<TeamStatusChangeStatBO> result = attendanceClient.getTeamStatusChangeStat(teamId, startDate, endDate);
            bo = Optional.ofNullable(result).map(Result::getData).orElse(bo);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        return bo;
    }

    /**
     * 考勤登录
     */
    public void userLogin(String userId, String rootTeamId) {
        try {
            attendanceClient.userLogin(userId, rootTeamId);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
    }

    /**
     * 考勤登出
     */
    public void userLogout(String userId, String rootTeamId) {
        try {
            attendanceClient.userLogout(userId, rootTeamId);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
    }

}
