package com.mrwind.windbase.controller;

import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.service.StatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by michelshout on 2018/8/6.
 */
@Api(description = "统计相关接口")
@RestController
@RequestMapping("/stat")
public class StatController extends BaseController{
    @Resource
    private StatService statService;

    @ApiOperation(value = "获取取件单数、取件距离、妥投单数、妥投距离和重复投递单数统计", httpMethod = "GET")
    @GetMapping("/pickdeliverdistance")
    public Result pickDeliverDistance(@RequestHeader("Authorization") String token,@RequestParam String project,@RequestParam String startDate,@RequestParam String endDate){
        return statService.pickDeliverDistance(token, project, startDate, endDate);
    }
}
