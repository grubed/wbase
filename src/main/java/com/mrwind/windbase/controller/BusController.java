package com.mrwind.windbase.controller;

import com.mrwind.windbase.common.annotation.SkipRootTeamAuth;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.dto.CourierSettingsDTO;
import com.mrwind.windbase.service.BusService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 业务
 *
 * @author hanjie
 * @date 2018/11/20
 */

@RestController
@RequestMapping("/bus")
public class BusController extends BaseController {

    @Resource
    private BusService busService;

    /**
     * 更新 / 保存配送员相关配置
     */
    @PutMapping("/courier_settings")
    public Result updateCourierSettings(@RequestBody @Valid CourierSettingsDTO dto, BindingResult bindingResult) {
        busService.updateCourierSettings(dto);
        return Result.getSuccess();
    }

    /**
     * 更新 / 保存配送员相关配置
     */
    @PutMapping("/courier_settings/{id}")
    public Result updateCourierSettingsById(@PathVariable("id") String userId, @RequestBody @Valid CourierSettingsDTO dto, BindingResult bindingResult) {
        busService.updateCourierSettingsById(userId, dto);
        return Result.getSuccess();
    }

    /**
     * 获取配送员相关配置
     */
    @GetMapping("/courier_settings")
    public Result getCourierSettings() {
        return Result.getSuccess(busService.getCourierSettings());
    }

    /**
     * 获取配送员相关配置 by id
     */
    @GetMapping("/courier_settings/{id}")
    public Result getCourierSettings(@PathVariable("id") String userId) {
        return Result.getSuccess(busService.getCourierSettingsById(userId));
    }

    /**
     * 每次打开风信需要获取用户的一些状态值
     */
    @GetMapping("/user_app_status")
    public Result getUserAppStatus() {
        return busService.getUserAppStatus();
    }

    @GetMapping("/billingModes")
    public Result getAllExpressBillingMode() {
        return busService.getAllExpressBillingMode();
    }

    /**
     * App 更多页数据
     */
    @GetMapping("/app_more_data")
    @SkipRootTeamAuth
    public Result getAppMoreData(@RequestParam("project") String project) {
        return busService.getAppMoreData(project);
    }


    /**
     * 获取用户余额
     */
    @GetMapping("/account/balance")
    @SkipRootTeamAuth
    public Result getUserAccountBalance() {
        return busService.getUserAccountBalance();
    }

}
