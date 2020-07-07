package com.mrwind.windbase.controller;

import com.mrwind.windbase.common.annotation.SkipTokenAndRootTeamAuth;
import com.mrwind.windbase.common.api.MerchantApi;
import com.mrwind.windbase.common.constant.CommonConstants;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.dto.*;
import com.mrwind.windbase.service.AccountService;
import com.mrwind.windbase.service.TeamService;
import com.mrwind.windbase.service.UserTeamRelationService;
import com.mrwind.windbase.service.UserTeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * Created by CL-J on 2018/7/18.
 */
@Api(description = "团队所需接口")
@RestController
@RequestMapping(value = "/team")
public class TeamController extends BaseController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserTeamRelationService userTeamRelationService;

    @Autowired
    private UserTeamService userTeamService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/testt")
    @SkipTokenAndRootTeamAuth
    public Result dsd() {
        MerchantApi.createStore("99999");
        return Result.getSuccess();
    }

    @SkipTokenAndRootTeamAuth
    @ApiOperation(value = "创建团队", httpMethod = "POST", notes = "name:必填 avatar:选填 parentId:当要添加的team有父team时填写;当为根team时不填")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result createTeam(@RequestBody @Valid TeamDTO teamDTO) {

        return teamService.createTeam(teamDTO);
    }

    /**
     * 获取分组详情
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取分组详情", httpMethod = "GET", produces = MediaType
            .APPLICATION_JSON_VALUE)
    @ApiResponse(code = 200, message = "OK", response = Result.class)
    public Result getTeam(@PathVariable("id") String id) {
        return teamService.getTeam(id);
    }


    @ApiOperation(value = "邀请成员", httpMethod = "POST")
    @RequestMapping(value = "/invite", method = RequestMethod.POST)
    public Result inviteMember(@RequestBody InviteDTO inviteDTO) {
        return teamService.inviteMember(inviteDTO);
    }

    @ApiOperation(value = "获取所有客服人员", httpMethod = "GET", notes = "获取带有客服标签的团队下的所有人")
    @RequestMapping(value = "/service", method = RequestMethod.GET)
    // TODO 获取所有客服人员
    public Result getServices() {
        return null;
    }


    @ApiOperation("修改team属性")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Result editTeam(@RequestBody UpdateTeamDTO req) {
        return teamService.editTeam(req);
    }

    /**
     * 修改团队地址及GPS信息
     *
     * @param teamAddressGpsDTO team的地址和GPS信息
     * @return
     */
    @ApiOperation("修改team的地址和GPS信息")
    @RequestMapping(value = "/editteamaddressgps", method = RequestMethod.POST)
    public Result editTeamAddressGps(@RequestBody TeamAddressGpsDTO teamAddressGpsDTO) {
        return teamService.editTeamAddressGps(teamAddressGpsDTO);
    }

    // TODO 返回团队的标签 如果是仓库 需要返回仓库的地址
//    @GetMapping(value = "/{rootId}/children")
//    @ApiOperation(value = "用户所在team列表", httpMethod = "GET")
//    public Result getTeamList(@RequestHeader("Authorization") String token,
//                              @PathVariable("rootId") String rootId) {
//        return teamService.getTeamsByRootIdAndUser(rootId);
//    }

    @PostMapping(value = "/isManger")
    @ApiOperation(value = "是否是团队管理员", httpMethod = "POST")
    public Result isTeamManage(@RequestBody @Valid UserTeamIdDTO dto, BindingResult bindingResult) {
        return Result.getSuccess(userTeamService.checkCanManageTeam(dto.getUserId(), dto.getTeamId()));

    }

    /**
     * 获取所在分组 Team 详情
     */
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "获取所在分组 Team 详情", httpMethod = "GET", produces = MediaType
            .APPLICATION_JSON_VALUE)
    @ApiResponse(code = 200, message = "OK", response = Result.class)
    public Result getOnlyTeam() {
        return teamService.getTeam(null);
    }


    //合并team
    @RequestMapping(value = "/merge", method = RequestMethod.POST)
    @ApiOperation(value = "合并多个team", httpMethod = "POST", produces = MediaType
            .APPLICATION_JSON_VALUE)
    public Result getTeam(@Validated @RequestBody OperationTeamDTO operationTeamDTO) {
        return teamService.mergeTeams(operationTeamDTO.getTeamIdList(), operationTeamDTO.getName(), operationTeamDTO.getProject());
    }

    //移动team
    @RequestMapping(value = "/move", method = RequestMethod.POST)
    @ApiOperation(value = "移动多个team", httpMethod = "POST", produces = MediaType
            .APPLICATION_JSON_VALUE)
    public Result batchMoveTeams(@Validated @RequestBody MoveTeamsDTO moveTeamsDTO) {
        return teamService.batchMoveTeams(moveTeamsDTO.getTeamIdList(), moveTeamsDTO.getTargetTeamId());
    }

    //删除team
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除多个team", httpMethod = "POST", produces = MediaType
            .APPLICATION_JSON_VALUE)
    public Result batchDeleteTeams(@Validated @RequestBody OperationTeamDTO operationTeamDTO) {
        return teamService.batchDeleteTeams(operationTeamDTO.getTeamIdList());
    }


    @SkipTokenAndRootTeamAuth
    @GetMapping("isManager2")
    public Result ismanager(@RequestParam String userId, @RequestParam String teamId) {
        return Result.getSuccess(userTeamService.checkCanManageTeam(userId, teamId));
    }

    @SkipTokenAndRootTeamAuth
    @GetMapping(value = "/managerlist")
    public Result getManagerList(@RequestParam("userId") String userId,
                                 @RequestParam(value = "currentTeamId", required = false) String currentTeamId) {
        return Result.getSuccess(teamService.getUserLeaders(userId, currentTeamId));
    }

    @GetMapping(value = "/getUserDepartmentForAttenceReport")
    @SkipTokenAndRootTeamAuth
    public Result getUserDepartmentForAttenceReport(@RequestParam("userId") String userId, @RequestParam("currentTeamId") String currentTeamId) {
        return Result.getSuccess(teamService.getUserDepartmentForAttenceReport(userId, currentTeamId));
    }

    @GetMapping(value = "/children")
    public Result getChildrenTeams(@RequestParam("teamId") String teamId) {
        return Result.getSuccess(teamService.getTeamNextChildren(teamId));
    }


    @GetMapping(value = "/treeInfo")
    public Result getTeamTreeInfo(@RequestParam("teamId") String id) {
        return teamService.getTeamTreeInfo(id);
    }

    @GetMapping(value = "/root/noWind")
    public Result getRootNotWind() {
        return teamService.rootNotWind();
    }

    @GetMapping("/info")
    public Result getTeamInfo(@RequestParam(value = "teamId", required = false) String teamId, @RequestParam("project") String project) {
        return teamService.getTeamInfo(teamId, project);
    }

    /**
     * 获取该团队下所有已离职的人员信息
     */
    @GetMapping("/leaved_users")
    public Result getLeavedUserInfoByTeamId(@RequestParam("teamId") String teamId) {
        return teamService.getLeavedUserInfoByTeamId(teamId);
    }

    /**
     * 获取一个团队下所有人的 id（包括子团队）
     */
    @GetMapping("/all_user_ids")
    public Result getTeamAllUserIds(@RequestParam("teamId") String teamId) {
        return teamService.getTeamAllUserIds(teamId);
    }

    /**
     * 获取一个团队下所有人(包括子团队）
     */
    @GetMapping("/all_user")
    public Result getTeamAllUser(@RequestParam("teamId") String teamId) {
        return teamService.getTeamAllUser(teamId);
    }

    /**
     * 获取风先生团队一个用户的可通知的 leader (当前层级和上一层管理员)
     *
     * @param userId 用户 id
     */
    @GetMapping("/notify_leaders")
    public Result getWindNotifyLeaders(@RequestParam("userId") String userId) {
        return Result.getSuccess(teamService.getUserAllLeaders(userId, CommonConstants.MRWIND_ID, 2, false));
    }

    @PostMapping("/check_level")
    @SkipTokenAndRootTeamAuth
    public Result checkLevel(@RequestBody @Valid CheckTeamLevelDTO dto, BindingResult bindingResult) {
        dto.setEffectiveLevel(Math.max(dto.getEffectiveLevel(), 0));
        dto.setNotificationLevel(Math.max(dto.getNotificationLevel(), 0));
        return Result.getSuccess(teamService.checkLevel(dto.getMemberId(), dto.getLeaderId(), dto.getEffectiveLevel(), dto.getNotificationLevel()));
    }

    @GetMapping("/allMember")
    public Result teamAllMember(@RequestParam("teamId") String teamId) {
        return Result.getSuccess(teamService.getTeamAllMemberProfile(teamId));
    }

    @GetMapping("/teamandallchildteamids")
    @SkipTokenAndRootTeamAuth
    public Result getTeamAndAllChildTeamIds(@RequestParam(value = "teamId", required = false) String teamId) {
        return Result.getSuccess(teamService.getTeamAndAllChildTeamIds(teamId));
    }

    @GetMapping("/type_user")
    @SkipTokenAndRootTeamAuth
    public Result getTeamAndAllChildTeamIds(@RequestParam("teamId") String teamId, @RequestParam("type") String type) {
        return Result.getSuccess(teamService.findTypeUserByTeamId(teamId, type));
    }

}
