package com.mrwind.windbase.controller;

import com.alibaba.fastjson.JSONObject;
import com.mrwind.windbase.common.constant.WindForceProjectEnum;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.common.util.TimeUtil;
import com.mrwind.windbase.service.DataService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 数据页相关
 */
@RequestMapping("/data")
@RestController
public class DataController extends BaseController {

    @Resource
    private DataService dataService;

    /**
     * 获取个人数据
     *
     * @param userId    用户 id
     * @param startDate 起始时间
     * @param endDate   结束时间
     * @return Result
     */
    @GetMapping("/personal")
    public Result personalData(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("userId") String userId,
            @RequestParam("project") String project) {
        if (!TimeUtil.isFormat(startDate, TimeUtil.yyyyMMdd1) || !TimeUtil.isFormat(endDate, TimeUtil.yyyyMMdd1)) {
            return Result.getFail("error date format");
        }
        if (TimeUtil.compareDate(startDate, endDate, TimeUtil.yyyyMMdd1) > 0) {
            return Result.getFail("error date range");
        }
        return dataService.getPersonalData(userId, startDate, endDate, project);
    }

    /**
     * 获取团队数据
     *
     * @param teamId    团队 id
     * @param startDate 起始时间
     * @param endDate   结束时间
     * @return Result
     */
    @GetMapping("/team")
    public Result teamData(@RequestParam("startDate") String startDate,
                           @RequestParam("endDate") String endDate,
                           @RequestParam("teamId") String teamId,
                           @RequestParam("project") String project) {
        if (!TimeUtil.isFormat(startDate, TimeUtil.yyyyMMdd1) || !TimeUtil.isFormat(endDate, TimeUtil.yyyyMMdd1)) {
            return Result.getFail("error date format");
        }
        if (TimeUtil.compareDate(startDate, endDate, TimeUtil.yyyyMMdd1) > 0) {
            return Result.getFail("error date range");
        }
        return dataService.getTeamData(teamId, startDate, endDate, project);
    }

    /**
     * 获取团队数据页点击详情
     *
     * @param teamId    团队 id
     * @param startDate 起始时间
     * @param endDate   结束时间
     * @param type      类型
     * @return List<TeamDataDetailsItemVO>
     */
    @GetMapping("/team/details")
    public Result teamDataDetails(@RequestParam("startDate") String startDate,
                                  @RequestParam("endDate") String endDate,
                                  @RequestParam("teamId") String teamId,
                                  @RequestParam("type") String type) {
        if (!TimeUtil.isFormat(startDate, TimeUtil.yyyyMMdd1) || !TimeUtil.isFormat(endDate, TimeUtil.yyyyMMdd1)) {
            return Result.getFail("error date format");
        }
        if (TimeUtil.compareDate(startDate, endDate, TimeUtil.yyyyMMdd1) > 0) {
            return Result.getFail("error date range");
        }
        return dataService.getTeamDataDetails(teamId, startDate, endDate, type);
    }

    /**
     * [套壳接口] 根据指定teamId或者userId获取所有人员各状态统计数据统计
     */
    @PostMapping("/pickdeliverordernumstat")
    public Result getPickDeliverOrderNumStat(@RequestParam("project") String project, @RequestBody JSONObject body) {
        if (!WindForceProjectEnum.hasEnumDesc(project)) {
            return Result.getFail("error project");
        }
        return dataService.getPickDeliverOrderNumStat(body, project);
    }

    @GetMapping("/team_stat")
    public Result getTeamStat(@RequestParam("startDate") String startDate,
                              @RequestParam("endDate") String endDate,
                              @RequestParam(value = "teamId", required = false) String teamId) {
        if (!TimeUtil.isFormat(startDate, TimeUtil.yyyyMMdd1) || !TimeUtil.isFormat(endDate, TimeUtil.yyyyMMdd1)) {
            return Result.getFail("error date format");
        }
        if (TimeUtil.compareDate(startDate, endDate, TimeUtil.yyyyMMdd1) > 0) {
            return Result.getFail("error date range");
        }
        return dataService.getTeamStat(startDate, endDate, teamId);
    }

}
