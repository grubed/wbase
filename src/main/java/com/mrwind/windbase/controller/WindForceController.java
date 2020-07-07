package com.mrwind.windbase.controller;

import com.alibaba.fastjson.JSONArray;
import com.mrwind.windbase.common.annotation.SkipTokenAndRootTeamAuth;
import com.mrwind.windbase.common.annotation.WindAuthorization;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.common.util.TextUtils;
import com.mrwind.windbase.common.util.TimeUtil;
import com.mrwind.windbase.dto.GetUserByJobDTO;
import com.mrwind.windbase.dto.TeamPushDTO;
import com.mrwind.windbase.dto.TeamShopDTO;
import com.mrwind.windbase.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.Set;

/**
 * 提供给风力相关特殊接口
 *
 * @author hanjie
 * @date 2018/7/25
 */
@Api(description = "风力相关接口")
@RestController
@RequestMapping(value = "/flapi")
public class WindForceController extends BaseController {

    @Resource
    private WindForceService windForceService;

    @Resource
    private AccountService accountService;

    @Resource
    private TeamService teamService;

    @Resource
    private BusService busService;

    @Resource
    private UserService userService;

    /**
     * 根据手机号获取该人员标签
     */
    @ApiOperation("根据手机号获取该人员标签")
    @GetMapping(value = "/listUserJobByTel")
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    public Result listUserRolesByTel(@RequestParam String teamId, @RequestParam String tel) {
        return windForceService.listUserRolesByTel(teamId, tel);
    }

    /**
     * 获取指定团队下所有人手机号码(包括子团队)
     */
    @ApiOperation("获取指定团队下所有人手机号码(包括子团队)")
    @GetMapping(value = "/getAllTel")
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    public Result getAllTel(@RequestParam String rootteamid) {
        return windForceService.getAllTel(rootteamid);
    }

    /**
     * 通过标签来查找用户
     */
    @ApiOperation("通过标签来查找用户")
    @PostMapping(value = "/getUsersByJobs")
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    public Result getUserByJob(@RequestBody @Valid GetUserByJobDTO body, BindingResult bindingResult) {
        return windForceService.getUserByJob(body);
    }

    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @ApiOperation("获取风先生仓库地址列表")
    @Deprecated
    @RequestMapping(value = "/getstoreinfolist", method = RequestMethod.GET)
    public Result getStoreInfoList(@RequestParam @ApiParam("标签关键字，多个以\",\"分隔") String keyWords,
                                   @RequestParam @ApiParam("项目类别") String project) {
        return Result.getSuccess();
    }

    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @ApiOperation("根据经纬度坐标获取离得最近的仓库")
    @RequestMapping(value = "/getclosestteambygps", method = RequestMethod.GET)
    public Result getClosestTeamByGps(@RequestParam @ApiParam("查询纬度") double lat,
                                      @RequestParam @ApiParam("查询经度") double lng) {
        return windForceService.getClosestTeamByGps(lng, lat);
    }

    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @ApiOperation("userId获取该人员信息和所属部门信息（是否有部门领导和员工区分的字段）")
    @RequestMapping(value = "/getuserteaminfo", method = RequestMethod.GET)
    public Result getUserTeamInfo(@RequestParam(required = false) @ApiParam("项目类别") String project,
                                  @RequestParam @ApiParam("根团队ID") String rootTeamId,
                                  @RequestParam @ApiParam("用户ID") String userId,
                                  @RequestParam @ApiParam("数据类型，多个以逗号分隔。数据项：user-获取用户基本信息，userrole-获取用户标签，rootteam-获取根部门团队，warehouseteam-获取所在仓库团队，clientserverteam-获取客服团队(和获取所在仓库团队互斥)，parentteam-获取父团队，teamrole-获取团队标签，teamgps-获取团队经纬度坐标")
                                          String type) {
        return windForceService.getuserteaminfo(project, rootTeamId, userId, type);
    }

    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @ApiOperation("公司客户userId获取该人员信息和所属部门信息（是否有部门领导和员工区分的字段）")
    @RequestMapping(value = "/getclientuserteaminfo", method = RequestMethod.GET)
    public Result getClientUserTeamInfo(@ApiParam("根团队ID") String rootTeamId,
                                        @RequestParam @ApiParam("用户ID") String userId,
                                        @RequestParam @ApiParam("数据类型，多个以逗号分隔。数据项：user-获取用户基本信息，userrole-获取用户标签，rootteam-获取根部门团队，parentteam-获取父团队，teamrole-获取团队标签，teamgps-获取团队经纬度坐标")
                                                String type) {
        return windForceService.getclientuserteaminfo(rootTeamId, userId, type);
    }

    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @ApiOperation(value = "通过teamId批量获取 teamInfo + teamPosition", httpMethod = "POST")
    @PostMapping("/team/infoAndPosition")
    public Result getTeamInfoPosition(@RequestBody JSONArray array) {
        return windForceService.getTeamsInfoPosition(array);
    }

    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @RequestMapping(value = "/{id}/type/{type}", method = RequestMethod.GET)
    @ApiOperation(value = "获取仓库下的所有配送员详情", httpMethod = "GET", produces = MediaType
            .APPLICATION_JSON_VALUE)
    public Result getAllDeliveryManByTeam(@ApiParam(value = "id", required = true) @PathVariable("id") String id,
                                          @ApiParam(value = "type", required = true) @PathVariable("type") String type) {
        return windForceService.getUserByTeamId(id, type);
    }

    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @RequestMapping(value = "/push", method = RequestMethod.POST)
    @ApiOperation(value = "给仓库下的所有配送员推送抢单信息", httpMethod = "POST", produces = MediaType
            .APPLICATION_JSON_VALUE, notes = "给team中的用户推送消息,type为1时表示给team下的包含所有子孙用户推送，type为2时表示只给该仓库下的非子孙仓库成员推送,type为其他值时不推送")
    public Result grabOrderPush(@RequestBody @Valid TeamPushDTO req, BindingResult bindingResult) {
        windForceService.pushToDeliveryManByTeamId(req);
        return Result.getSuccess();
    }

    @RequestMapping(value = "/teldispatcher/{id}", method = RequestMethod.GET)
    @SkipTokenAndRootTeamAuth
    @ApiOperation(value = "电话调度获取被调度人员", httpMethod = "GET", produces = MediaType
            .APPLICATION_JSON_VALUE)
    public Result telephoneDispatcherUsers(@ApiParam(value = "id", required = true) @PathVariable("id") String id) {
        return windForceService.telephoneDispatcherUsers(id);
    }

    @RequestMapping(value = "/browser/user", method = RequestMethod.GET)
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @ApiOperation(value = "获取B端用户列表", httpMethod = "GET", produces = MediaType
            .APPLICATION_JSON_VALUE)
    public Result getBrowserUsers(@ApiParam(value = "page", required = true) @RequestParam("page") int page, @ApiParam(value = "limit", required = true) @RequestParam("limit") int limit) {
        return windForceService.getBrowserUsers(page, limit);
    }

    @RequestMapping(value = "/browser/userbycreatetime", method = RequestMethod.GET)
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @ApiOperation(value = "获取B端用户列表(通过用户创建时间过滤)", httpMethod = "GET", produces = MediaType
            .APPLICATION_JSON_VALUE)
    public Result getBrowserUsers(@RequestParam(value = "startTime", required = false) String startTime, @RequestParam(value = "endTime", required = false) String endTime) {
        if (TextUtils.isEmpty(startTime) && TextUtils.isEmpty(endTime)) {
            return windForceService.getAllBrowserUsers();
        }
        if (!TimeUtil.isFormat(startTime, TimeUtil.yyyyMMdd1) || !TimeUtil.isFormat(endTime, TimeUtil.yyyyMMdd1)) {
            return Result.getFail("error date format");
        }
        if (TimeUtil.compareDate(startTime, endTime, TimeUtil.yyyyMMdd1) > 0) {
            return Result.getFail("error date range");
        }
        endTime = endTime + " 23:59:59";
        return windForceService.getBrowserUsersByCreateTime(startTime, endTime);
    }

    @RequestMapping(value = "/accountbalance/{id}", method = RequestMethod.GET)
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @ApiOperation(value = "根据rootTeamId获取账户余额", httpMethod = "GET", produces = MediaType
            .APPLICATION_JSON_VALUE)
    public Result getClientTeamBalance(@ApiParam(value = "id", required = true) @PathVariable("id") String id) {
        return accountService.getAccountBalance(id);
    }

    @RequestMapping(value = "/changeclientteamshop", method = RequestMethod.POST)
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @ApiOperation(value = "认证或取消认证根团队", httpMethod = "POST", produces = MediaType
            .APPLICATION_JSON_VALUE)
    public Result changeClientTeamShop(@RequestBody @Valid TeamShopDTO req) {
        return windForceService.changeClientTeamShop(req);
    }

    @RequestMapping(value = "/clientteamlist", method = RequestMethod.GET)
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @ApiOperation(value = "获取所有非风先生的根团队列表", httpMethod = "GET", produces = MediaType
            .APPLICATION_JSON_VALUE)
    public Result getClientRootTeamList() {
        return windForceService.getClientRootTeamList();
    }

    /**
     * 获取所有配送员id
     */
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @GetMapping(value = "/courier_ids")
    public Result getAllCourierIds() {
        return windForceService.getAllCourierIds();
    }

    /**
     * 获取指定 project 下属所有配送员及接单状态列表
     */
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @GetMapping(value = "/couriers_status")
    public Result getCouriersStatusByProject(@RequestParam(value = "project", required = false) String project) {
        return Result.getSuccess(windForceService.getCouriersStatusByProject());
    }

    /**
     * 根据 userId 获取指定计费规则(自己没有，从团队向上寻找)
     */
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @GetMapping(value = "/billingMode")
    public Result userBillingMode(@RequestParam("userId") String userId) {
        return windForceService.userBillingMode(userId);
    }

    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @GetMapping(value = "/managerlist")
    public Result getManagerList(@RequestParam("userId") String userId,
                                 @RequestParam(value = "currentTeamId", required = false) String currentTeamId) {
        return Result.getSuccess(teamService.getUserLeaders(userId, currentTeamId));
    }

    /**
     * 通过teamId获取商户根团队下的所有成员和商户信息
     */
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @GetMapping(value = "/shopDetail")
    public Result shopDetail(@RequestParam("teamId") String teamId) {
        return teamService.shopDetail(teamId);
    }

    /**
     * 通过团队id查找上一层级的leader
     */
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @GetMapping(value = "/teamTopLeader")
    public Result teamTopLeader(@RequestParam("teamId") String teamId) {
        return teamService.teamTopLeader(teamId);
    }

    /**
     * 获取所有有配送计费规则的人
     */
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @GetMapping(value = "/getavailablebillingmodeusers")
    public Result getAvailableBillingModeUsers() {
        return windForceService.getAvailableBillingModeUsers();
    }

    /**
     * 获取配送员相关配置
     */
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @GetMapping("/courier_settings/{id}")
    public Result getCourierSettings(@PathVariable("id") String userId) {
        return Result.getSuccess(busService.getCourierSettingsById(userId));
    }

    /**
     * 根据手机号码获取用户信息
     */
    @ApiOperation("根据手机号码获取用户信息")
    @WindAuthorization
    @SkipTokenAndRootTeamAuth
    @PostMapping(value = "/user/byTels")
    public Result getUserInfoByTel(@RequestBody Set<String> tels) {
        return windForceService.getUserInfoByTels(tels);
    }

    /**
     * 获取人员管理员（可定制）
     *
     * @param userId 用户 id
     */
    @WindAuthorization
    @SkipTokenAndRootTeamAuth
    @GetMapping("/user_leaders")
    public Result getUserLeaders(@RequestParam("userId") String userId, @RequestParam("rootTeamId") String rootTeamId, @RequestParam("maxLevel") int maxLevel, @RequestParam("includeCurrentLevelWhenIsManager") boolean includeCurrentLevelWhenIsManager) {
        return Result.getSuccess(teamService.getUserAllLeaders(userId, rootTeamId, maxLevel, includeCurrentLevelWhenIsManager));
    }

    /**
     * 获取团队管理员（可定制）
     */
    @WindAuthorization
    @SkipTokenAndRootTeamAuth
    @GetMapping("/user_leaders_by_team")
    public Result getLeadersByTeamId(@RequestParam("teamId") String teamId, @RequestParam("maxLevel") int maxLevel) {
        return Result.getSuccess(teamService.getTeamLeaders(teamId, maxLevel));
    }

    /**
     * 获取一个人当前所在分组成员以及指定多少层下的人员
     */
    @WindAuthorization
    @SkipTokenAndRootTeamAuth
    @GetMapping("/up_team_user")
    public Result getTeamUserAndUpByLevel(@RequestParam("userId") String userId,
                                          @RequestParam("rootTeamId") String rootTeamId,
                                          @RequestParam("upLevel") int upLevel,
                                          @RequestParam("includeSelf") boolean includeSelf) {
        // 确保 upLevel 大于等于 1
        upLevel = Math.max(1, upLevel);
        return Result.getSuccess(teamService.getTeamUserAndUpByLevel(userId, rootTeamId, upLevel, includeSelf));
    }


    /**
     * 获取一个团队的的人员（当前层）
     */
    @WindAuthorization
    @SkipTokenAndRootTeamAuth
    @GetMapping("/team_users")
    public Result getTeamUsers(@RequestParam("teamId") String teamId) {
        return Result.getSuccess(teamService.getTeamUsers(teamId));
    }

    @WindAuthorization
    @SkipTokenAndRootTeamAuth
    @PostMapping("/user_receive_order_status")
    public Result getCouriersReceiveOrderStatus(@RequestBody Set<String> userIds) {
        return windForceService.getCouriersReceiveOrderStatus(userIds);
    }

    @WindAuthorization
    @SkipTokenAndRootTeamAuth
    @GetMapping("/type_user")
    public Result getTeamAndAllChildTeamIds(@RequestParam("teamId") String teamId, @RequestParam("type") String type) {
        return Result.getSuccess(teamService.findTypeUserByTeamId(teamId, type));
    }

    @WindAuthorization
    @SkipTokenAndRootTeamAuth
    @GetMapping("/near_store")
    public Result getNearStore(@RequestParam("lat") double lat, @RequestParam("lng") double lng) {
        return Result.getSuccess(windForceService.getNearStore(lat, lng));
    }

    @WindAuthorization
    @SkipTokenAndRootTeamAuth
    @PostMapping("/batch_user_basic_info")
    public Result batchUserBasicInfo(@RequestBody Set<String> userIds, @RequestParam("rootTeamId") String rootTeamId) {
        return Result.getSuccess(windForceService.batchUserBasicInfo(userIds, rootTeamId));
    }

    @WindAuthorization
    @SkipTokenAndRootTeamAuth
    @GetMapping("/check_member")
    public Result checkMember(@RequestParam("leaderId") String leaderId, @RequestParam("userId") String userId, @RequestParam("rootTeamId") String rootTeamId) {
        return Result.getSuccess(windForceService.checkIsMember(leaderId, userId, rootTeamId));
    }

    /**
     * 根据用户注册时间获取人员
     */
    @WindAuthorization
    @SkipTokenAndRootTeamAuth
    @GetMapping(value = "/users_by_create_time")
    public Result getUsersByCreateTime(@RequestParam("startTime") Date startTime, @RequestParam("endTime") Date endTime) {
        return Result.getSuccess(userService.getUsersByCreateTime(startTime, endTime));
    }

    /**
     * 获取团队的直接下级子团队列表
     * @param teamId
     * @return
     */
    @WindAuthorization
    @SkipTokenAndRootTeamAuth
    @GetMapping(value = "/directchildteam")
    public Result getDirectChildTeamByTeamId(@RequestParam("teamId") String teamId){
        return Result.getSuccess(teamService.getDirectChildTeamByTeamId(teamId));
    }

}
