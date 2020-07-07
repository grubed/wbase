package com.mrwind.windbase.dao.mysql;

import com.alibaba.fastjson.JSONObject;
import com.mrwind.windbase.entity.mysql.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wuyiming
 * Created by michelshout on 2018/7/19.
 */
public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {


    List<Role> findAllByRoleTypeId(String roleTypeId);

    Role findByName(String name);

    /**
     * 根据手机号获取该人员标签
     *
     * @param userTel 手机号码
     * @return role 集合
     */
    @Query(value = "SELECT r.*,utrr.teamId FROM role r " +
            "LEFT JOIN user_team_role_relation utrr ON r.roleId = utrr.roleId " +
            "LEFT JOIN user u ON utrr.userId = u.userId " +
            "WHERE u.tel = :tel AND utrr.teamId IN :teamIds", nativeQuery = true)
    List<JSONObject> listRolesByUserTel(@Param("teamIds") List<String> teamIds, @Param("tel") String userTel);


    /**
     * 根据用户ID获取该人员角色（标签）
     *
     * @param userId 用户ID
     * @param teamId 团队ID
     * @return
     */
    @Query(value = "SELECT r.*, rt.color, rt.bgColor FROM role r, role_type rt where r.roleTypeId = rt.roleTypeId and roleId in (select roleId from user_team_role_relation where userId = :userId and teamId = :teamId)", nativeQuery = true)
    List<JSONObject> listUserRolesByTeamIdUserId(@Param("userId") String userId, @Param("teamId") String teamId);

    /**
     * 根据用户ID获取该团队角色（标签）
     *
     * @param teamId 团队ID
     * @return
     */
    @Query(value = "SELECT r.*, rt.color, rt.bgColor FROM role r, role_type rt where r.roleTypeId = rt.roleTypeId and roleId in (select roleId from team_role_relation where teamId = :teamId)", nativeQuery = true)
    List<JSONObject> listTeamRolesByTeamId(@Param("teamId") String teamId);


    /**
     * 获取一个团队下此人所有的角色（包括子团队）
     */
    @Query(value = "SELECT r.* ,rt.bgColor, rt.color FROM role r " +
            "LEFT JOIN role_type rt ON r.roleTypeId = rt.roleTypeId " +
            "LEFT JOIN user_team_role_relation utrr ON r.roleId = utrr.roleId " +
            "LEFT JOIN team t ON t.teamId = utrr.teamId " +
            "WHERE utrr.userId = :userId AND (t.parentIds = :parentNo OR t.parentIds LIKE :childTeamNo)", nativeQuery = true)
    List<JSONObject> listTeamAllRoleByParentId(@Param("userId") String userId, @Param("parentNo") String parentNo, @Param("childTeamNo") String childTeamNo);

    List<Role> findAllByNameLike(@NotNull String name);

    int deleteAllByRoleTypeIdIs(@NotNull String roleTypeId);


    /**
     * 根据roleIdList获取roleName字符串，形如"XXX,YYY,ZZZ..."
     *
     * @param roleIdList
     * @return
     */
    @Query(value = "select group_concat(r.name) from role r where r.roleId in (:roleIdList)", nativeQuery = true)
    String findRoleNameByRoleIdList(@Param("roleIdList") List<String> roleIdList);

}
