package com.mrwind.windbase.controller;

import com.mrwind.windbase.common.annotation.SkipTokenAndRootTeamAuth;
import com.mrwind.windbase.common.annotation.WindAuthorization;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.dto.TeamSwitchDTO;
import com.mrwind.windbase.service.ConfigService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-01-14
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Resource
    private ConfigService configService;

    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @PostMapping("/team_switch/{id}")
    public Result setTeamSwitch(@PathVariable("id") String teamId, @RequestBody @Valid TeamSwitchDTO dto, BindingResult bindingResult) {
        return Result.getSuccess(configService.setTeamSwitch(teamId, dto.getKey(), dto.getStatus()));
    }

    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @GetMapping("/team_switch/{id}")
    public Result getTeamSwitch(@PathVariable("id") String teamId, @RequestParam("configKey") String configKey) {
        return Result.getSuccess(configService.getTeamSwitch(teamId, configKey));
    }

    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @GetMapping("/team_switch")
    public Result getTeamSwitch(@RequestParam("userId") String userId, @RequestParam("rootTeamId") String rootTeamId, @RequestParam("configKey") String configKey) {
        return Result.getSuccess(configService.getTeamSwitch(userId, rootTeamId, configKey));
    }

}
