package com.mrwind.windbase.controller;

import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 权限
 *
 * @author hanjie
 * @date 2019-02-25
 */

@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController {

    @Resource
    private PermissionService permissionService;

    @PostMapping("/{userId}")
    public Result setPermission(@PathVariable String userId) {
        return Result.getSuccess();
    }

    @DeleteMapping("/{userId}")
    public Result deletePermission(@PathVariable String userId) {
        return Result.getSuccess();
    }

}
