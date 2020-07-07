package com.mrwind.windbase.controller;

import com.mrwind.windbase.common.constant.CommonConstants;
import com.mrwind.windbase.common.util.LocaleType;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.common.util.TextUtils;
import com.mrwind.windbase.dto.AddRoleForTeamDTO;
import com.mrwind.windbase.dto.RemoveTeamRoleDTO;
import com.mrwind.windbase.entity.mysql.Role;
import com.mrwind.windbase.entity.mysql.RoleType;
import com.mrwind.windbase.entity.mysql.TeamRoleRelation;
import com.mrwind.windbase.service.RoleService;
import com.mrwind.windbase.vo.UserRoleVO;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by michelshout on 2018/7/19.
 */
@RestController
public class RoleController extends BaseController {
    @Resource
    private RoleService roleService;

    /**
     * 新增或编辑角色类型
     * @param
     * */
    @PostMapping(value = "/roleType")
    public Result roleType(@RequestBody @Valid RoleType roleType) {
        return roleService.editRoleType(roleType);
    }

    /**
     * 新增或者编辑角色
     * @param role  角色实例
     * @return
     * 注意：角色名称中文、英文和阿拉伯文字段不能同时为空
     */
    @ApiOperation("新增或者编辑角色")
    @PostMapping(value = "/role")
    public Result edit(@RequestBody Role role) {
        return roleService.edit(role);
    }

    /**
     * 删除某个标签类型
     * */
    @DeleteMapping("/roleType/{id}")
    public Result deleRoleType(@PathVariable("id") String roleTypeId) {
        return roleService.delRoleType(roleTypeId);
    }

    /**
     * 删除某个标签
     * @param roleId  标签关键字
     * @return
     */
    @ApiOperation("删除角色")
    @DeleteMapping(value = "/role/{id}")
    public Result delRole(@PathVariable("id") String roleId) {
       return roleService.delRole(roleId);
    }

    /**
     * 获取所有标签类型
     * */
    @GetMapping("/roleType")
    public Result allRoleType() {
        return roleService.getAllRoleType();
    }

    /**
     * 获取某个标签类型下的所有标签
     * */
    @GetMapping("/roleType/{id}")
    public Result allRole(@PathVariable("id") String roleTypeId) {
        return roleService.getAllRoleByRoleType(roleTypeId);
    }

    @ApiOperation(value = "给团队添加标签", httpMethod = "POST")
    @PostMapping("/role/team")
    public Result addRoleForTeam(@RequestBody @Valid AddRoleForTeamDTO dto, BindingResult bindingResult) {
        return roleService.addRoleForTeam(dto);
    }

    @ApiOperation(value = "删除团队标签", httpMethod = "DELETE")
    @DeleteMapping("/role/team")
    public Result removeTeamRole(@RequestBody @Valid RemoveTeamRoleDTO dto, BindingResult bindingResult) {
        return roleService.removeTeamRole(dto);
    }

    @ApiOperation(value = "给个人添加标签", httpMethod = "POST")
    @PostMapping("/role/user")
    public Result addRoleForUser(@RequestBody @Valid UserRoleVO vo, BindingResult bindingResult) {
        return roleService.addRoleForUser(vo);
    }

    @ApiOperation(value = "删除个人标签", httpMethod = "DELETE")
    @DeleteMapping("/role/user")
    public Result removeUserRole(@RequestBody @Valid UserRoleVO vo, BindingResult bindingResult) {
        return roleService.removeUserRole(vo);
    }

    @ApiOperation(value = "模糊搜索标签")
    @GetMapping("/role/{name}")
    public Result searchRole(@PathVariable("name") String name) {
        return roleService.searchRole(name);
    }

}
