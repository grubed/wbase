package com.mrwind.windbase.controller;

import com.mrwind.windbase.common.annotation.SkipTokenAndRootTeamAuth;
import com.mrwind.windbase.common.constant.CommonConstants;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.common.util.ServletUtil;
import com.mrwind.windbase.common.util.TextUtils;
import com.mrwind.windbase.dto.AddManagerDTO;
import com.mrwind.windbase.dto.DeleteAndResignDTO;
import com.mrwind.windbase.dto.DeleteMemberDTO;
import com.mrwind.windbase.dto.MoveMemberDTO;
import com.mrwind.windbase.entity.mysql.User;
import com.mrwind.windbase.service.UserTeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Description
 *
 * @author hanjie
 */

@Api(description = "团队下的用户相关接口")
@RestController
@RequestMapping("/team")
public class UserTeamController extends BaseController {

    @Resource
    private UserTeamService userTeamService;

    @ApiOperation("获取所有下属")
    @GetMapping(value = "/getAllMembers")
    public Result getAllMembers(@RequestParam @ApiParam("团队id") String rootId) {
        return userTeamService.getAllMembers(rootId);
    }

    @ApiOperation("添加管理员")
    @RequestMapping(value = "/manager", method = RequestMethod.POST)
    public Result addManager(@RequestBody @Valid AddManagerDTO addManagerDTO, BindingResult bindingResult) {
        return userTeamService.addManager(addManagerDTO);
    }

    @ApiOperation("批量移动团队成员或管理员")
    @RequestMapping(value = "/move/members", method = RequestMethod.POST)
    public Result batchMoveMembers(@RequestBody @Valid MoveMemberDTO moveMemberDTO, BindingResult bindingResult) {
        return userTeamService.batchMoveMembers(moveMemberDTO);
    }

    @ApiOperation("批量删除成员")
    @RequestMapping(value = "/delete/members", method = RequestMethod.POST)
    public Result deleteMembers(@RequestBody @Valid DeleteMemberDTO deleteMemberDTO, BindingResult bindingResult) {
        return userTeamService.deleteMembers(deleteMemberDTO);
    }

    @ApiOperation("删除并离职成员")
    @RequestMapping(value = "/delete/member", method = RequestMethod.POST)
    public Result deleteMembers(@RequestBody @Valid DeleteAndResignDTO deleteMemberDTO, BindingResult bindingResult) {
        return userTeamService.deleteAndResignMembers(deleteMemberDTO);
    }

    @ApiOperation("通过用户 id 获取该用户所有公司列表")
    @GetMapping(value = "/{userId}/roots")
    @SkipTokenAndRootTeamAuth
    public Result getTeamListByUserId(@PathVariable String userId) {
        return userTeamService.getRootTeamListByUserId(userId);
    }

    @ApiOperation("删除管理员")
    @DeleteMapping(value = "/manager")
    public Result deleteManager(@RequestParam @ApiParam("团队id") String teamId,
                                @RequestParam @ApiParam("人员id") String userId) {
        return userTeamService.deleteManager(teamId, userId);
    }

    @ApiOperation("查询用户是否为你的下属")
    @GetMapping(value = "/isMember")
    public Result checkIsMember(@RequestParam("targetUserId") String targetUserId,
                                @RequestParam(value = "teamId", required = false) String teamId) {
        User me = (User) ServletUtil.getCurrentRequest().getAttribute(CommonConstants.USER_KEY);
        boolean isMember;
        if (TextUtils.isEmpty(teamId)) {
            // 调用方没传 teamId，默认查询范围在当前根团队
            isMember = userTeamService.checkIsMember(me.getUserId(), targetUserId, me.getCurrentTeamId());
        } else {
            isMember = userTeamService.checkIsMember(me.getUserId(), targetUserId, teamId);
        }
        return Result.getSuccess(isMember);
    }

    @GetMapping(value = "/userOnlyManagerTeam")
    public Result getUserOnlyTeam(@RequestParam("userId") String userId,
                                  @RequestParam("root") String root) {
        return userTeamService.getUserOnlyTeam(userId,root);
    }

    @GetMapping(value = "/isMrWind")
    public Result isMrWind(@RequestParam("userId") String userId) {
        return userTeamService.isMrWind(userId);
    }

}
