package com.mrwind.windbase.controller;

import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.service.WindBusinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 风力、等其他项目有关的逻辑
 *
 * @author hanjie
 * @date 2018/10/17
 */

@RestController
@RequestMapping("/bus")
public class WindBusinessController {

    @Resource
    WindBusinessService windBusinessService;

    @GetMapping(value = "/getnearcourierpagelist")
    public Result getNearCouriers(
            @RequestParam("lat") double lat,
            @RequestParam("lng") double lng,
            @RequestParam("project") String project,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit) {
        return windBusinessService.getNearCouriers(lat, lng, project, page, limit);
    }

}
