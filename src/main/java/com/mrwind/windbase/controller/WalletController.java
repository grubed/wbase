package com.mrwind.windbase.controller;

import com.mrwind.windbase.common.annotation.SkipTokenAndRootTeamAuth;
import com.mrwind.windbase.common.annotation.WindAuthorization;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.service.TeamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by zjw on 2018/12/18  下午8:18
 */
@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Resource
    private TeamService teamService;

    @GetMapping(value = "/teammanager")
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    public Result getTeamManagersByTeamId(@RequestParam("teamId") String teamId) {
        return teamService.getTeamManagerListByTeamId(teamId);
    }

    @GetMapping(value = "/parentteamsbyuser")
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    public Result getParentTeamsByUserId(@RequestParam("userId") String userId, @RequestParam("rootTeamId") String rootTeamId) {
        return teamService.getParentTeamsByUserId(userId, rootTeamId);
    }

    @GetMapping(value = "/parentteamsbyteam")
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    public Result getParentTeamByTeamId(@RequestParam("teamId") String teamId) {
        return Result.getSuccess(teamService.getParentTeamByTeamId(teamId));
    }

    @GetMapping(value = "/team")
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    public Result getOneTeamInfo(@RequestParam("teamId") String teamId) {
        return Result.getSuccess(teamService.getOneTeam(teamId));
    }

    @GetMapping(value = "/teamofuser")
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    public Result getOnlyTeamByUserIdAndRootTeamId(@RequestParam("userId") String userId, @RequestParam("rootTeamId") String rootTeamId) {
        return Result.getSuccess(teamService.getOnlyTeam(userId, rootTeamId));
    }
}
