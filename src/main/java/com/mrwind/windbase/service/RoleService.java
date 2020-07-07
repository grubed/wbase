package com.mrwind.windbase.service;

import com.mrwind.windbase.common.constant.CommonConstants;
import com.mrwind.windbase.common.util.LocaleType;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.common.util.ServletUtil;
import com.mrwind.windbase.common.util.TextUtils;
import com.mrwind.windbase.dto.AddRoleForTeamDTO;
import com.mrwind.windbase.dto.RemoveTeamRoleDTO;
import com.mrwind.windbase.entity.mysql.*;
import com.mrwind.windbase.vo.UserRoleVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by michelshout on 2018/7/19.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleService extends BaseService {

    /**
     * 新增或编辑角色类型
     *
     * @param type roleType
     * @return
     */
    public Result editRoleType(RoleType type) {
        if (type == null || StringUtils.isBlank(type.getName())) {
            return Result.getFailI18N("error.parameter.empty");
        }
        if (roleTypeRepository.findByName(type.getName()) != null) {
            return Result.getFailI18N("error.already.exist.roleType");
        }
        return Result.getSuccess(roleTypeRepository.save(type));
    }

    /**
     * 删除角色类型
     *
     * @param roleTypeId 角色类型关键字
     * @return
     */
    public Result delRoleType(String roleTypeId) {
        if (TextUtils.equals(roleTypeId, CommonConstants.FENGLI_TYPE_ID) || TextUtils.equals(roleTypeId, CommonConstants.SERVICE_TYPE_ID)) {
            return Result.getFailI18N("error.system.role");
        }
        roleTypeRepository.deleteById(roleTypeId);
        roleRepository.deleteAllByRoleTypeIdIs(roleTypeId);
        return Result.getSuccess();
    }

    /**
     * 获取所有角色类型
     */
    public Result getAllRoleType() {
        List<String> typeArray = new ArrayList<>();
        typeArray.add(CommonConstants.FENGLI_TYPE_ID);
        typeArray.add(CommonConstants.SERVICE_TYPE_ID);
        return Result.getSuccess(roleTypeRepository.findAll().stream().filter(r -> typeArray.stream().noneMatch(tid -> TextUtils.equals(tid, r.getRoleTypeId()))));
    }

    /**
     * 新增或编辑角色
     *
     * @param role 角色实例
     * @return
     */
    public Result edit(Role role) {
        if (role == null || StringUtils.isBlank(role.getRoleTypeId()) || StringUtils.isBlank(role.getName())) {
            return Result.getFailI18N("error.parameter.empty");
        }
        if (roleRepository.findByName(role.getName()) != null) {
            return Result.getFailI18N("error.already.exist.role");
        }
        roleRepository.save(role);
        return Result.getSuccess(role);
    }

    /**
     * 删除角色
     *
     * @param roleId 角色关键字
     * @return
     */
    public Result delRole(String roleId) {
        if (TextUtils.equals(roleId, CommonConstants.FENGLI_ID) || TextUtils.equals(roleId, CommonConstants.SERVICE_ID)) {
            return Result.getFailI18N("error.system.role");
        }
        roleRepository.deleteById(roleId);
        return Result.getSuccess();
    }

    public Result getAllRoleByRoleType(String roleTypeId) {
        return Result.getSuccess(roleRepository.findAllByRoleTypeId(roleTypeId));
    }

    /**
     * 给团队添加标签
     *
     * @param dto 团队 标签 id
     * @return result
     */
    public Result addRoleForTeam(AddRoleForTeamDTO dto) {
        if (teamRoleRelationRepository.findByRoleIdInAndTeamId(dto.getRoleIds(), dto.getTeamId()).size() > 0) {
            return Result.getFailI18N("error.already.exist.role");
        }
        List<TeamRoleRelation> teamRoleRelations = new ArrayList<>();
        dto.getRoleIds().forEach(r -> teamRoleRelations.add(new TeamRoleRelation(dto.getTeamId(), r)));
        List<TeamRoleRelation> result = teamRoleRelationRepository.saveAll(teamRoleRelations);
        // 如果是设置仓库操作，则需要设置更新团队的 project 字段
        if (dto.getRoleIds().contains(CommonConstants.FENGLI_ID) && !TextUtils.isEmpty(dto.getProject())) {
            Team team = teamRepository.findByTeamId(dto.getTeamId());
            if (team != null) {
                team.setProject(dto.getProject());
                teamRepository.save(team);
            }
        }
        return Result.getSuccess(result);
    }

    /**
     * 删除团队标签
     *
     * @param dto 团队 标签 id
     * @return result
     */
    public Result removeTeamRole(RemoveTeamRoleDTO dto) {
        teamRoleRelationRepository.deleteAllByTeamIdAndRoleIdIn(dto.getTeamId(), dto.getRoleIds());
        // 如果是取消设置仓库操作，则需要删除团队的 project 字段
        if (dto.getRoleIds().contains(CommonConstants.FENGLI_ID)) {
            Team team = teamRepository.findByTeamId(dto.getTeamId());
            if (team != null) {
                team.setProject(null);
                teamRepository.save(team);
            }
        }
        return Result.getSuccess();
    }

    /**
     * 给个人添加标签
     *
     * @param vo userId teamId roleId
     * @return result
     */
    public Result addRoleForUser(UserRoleVO vo) {

        if (userTeamRoleRelationRepository.findByRoleIdInAndUserIdAndTeamId(vo.getRoleIds(), vo.getUserId(), vo.getTeamId()).size() > 0) {
            return Result.getFailI18N("error.already.exist.role");
        }
        List<UserTeamRoleRelation> userTeamRoleRelations = new ArrayList<>();
        vo.getRoleIds().forEach(r -> userTeamRoleRelations.add(new UserTeamRoleRelation(vo.getUserId(), vo.getTeamId(), r)));
        List<UserTeamRoleRelation> result = userTeamRoleRelationRepository.saveAll(userTeamRoleRelations);
        User me = (User) ServletUtil.getCurrentRequest().getAttribute(CommonConstants.USER_KEY);
        String name = me.getName();
        Team root = teamService.getOneTeam(me.getCurrentTeamId());
        String roleName = roleRepository.findRoleNameByRoleIdList(vo.getRoleIds());

        //会话通知
//        Map<String, String> content = LocaleType.getMessageMap("push.role.edit.push", name, roleName);
//        MessageConfig config = MessageConfig.buildConfig(
//                me.getUserId(),
//                Arrays.asList(vo.getUserId()),
//                getUserLanguageMap(),
//                content,
//                root.getTeamId()
//        );
//        windChatApi.sendText(config, content);
        return Result.getSuccess(result);
    }

    /**
     * 删除个人标签
     *
     * @param vo userId teamId roleId
     * @return result
     */
    public Result removeUserRole(UserRoleVO vo) {
        userTeamRoleRelationRepository.deleteAllByUserIdAndTeamIdAndRoleIdIn(vo.getUserId(), vo.getTeamId(), vo.getRoleIds());
        return Result.getSuccess();
    }


    /**
     * 模糊搜索标签
     */
    public Result searchRole(String name) {
        List<String> typeArray = new ArrayList<>();
        typeArray.add(CommonConstants.FENGLI_TYPE_ID);
        typeArray.add(CommonConstants.SERVICE_TYPE_ID);
        return Result.getSuccess(roleRepository.findAllByNameLike("%" + name + "%").stream().filter(r -> typeArray.stream().noneMatch(tid -> TextUtils.equals(tid, r.getRoleTypeId()))));
    }

}
