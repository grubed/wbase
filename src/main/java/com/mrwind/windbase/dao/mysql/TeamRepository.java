package com.mrwind.windbase.dao.mysql;

import com.alibaba.fastjson.JSONObject;
import com.mrwind.windbase.entity.mysql.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by CL-J on 2018/7/18.
 */
public interface TeamRepository extends JpaRepository<Team, String>, JpaSpecificationExecutor<Team> {
    @Query(value = "SELECT t.* FROM team t " +
            "LEFT JOIN user_team_relation uttr ON t.teamId = uttr.teamId " +
            "WHERE uttr.userId = :userId AND t.rootId = :rootId", nativeQuery = true)
    List<Team> queryRootTeamByUserId(@Param("userId") String userId, @Param("rootId") String rootId);


    /**
     * 获取用户所在的所有团队
     *
     * @param userId 用户 id
     */
    @Query(value = "SELECT t.* FROM team t LEFT JOIN user_team_relation utr ON t.teamId = utr.teamId WHERE utr.userId = :userId", nativeQuery = true)
    List<Team> findUserAllTeams(@Param("userId") String userId);

    /**
     * 获取用户所在的所有根团队
     *
     * @param userId 用户 id
     */
    @Query(value = "SELECT t2.* FROM team t2 WHERE teamId IN (SELECT DISTINCT t.rootId FROM team t LEFT JOIN user_team_relation utr ON t.teamId = utr.teamId WHERE utr.userId = :userId)", nativeQuery = true)
    List<Team> findUserAllRoots(@Param("userId") String userId);

    /**
     * 根据团队流水号查询用户在指定团队及其子团队内的所有团队信息
     *
     * @param userId            用户 id
     * @param parentTeamNo      团队流水号例如（62）
     * @param childTeamNoPrefix 子团队模糊匹配前缀例如（62,）
     */
    @Query(value = "SELECT t.* " +
            "FROM team t " +
            "       LEFT JOIN user_team_relation utr ON t.teamId = utr.teamId " +
            "WHERE utr.userId = :userId " +
            "  AND utr.teamId IN (SELECT teamId FROM team WHERE parentIds = :parentTeamNo " +
            "                                                OR parentIds LIKE :childTeamNoPrefix%)", nativeQuery = true)
    List<Team> findUserAllTeamsInParentIds(@Param("userId") String userId,
                                           @Param("parentTeamNo") String parentTeamNo,
                                           @Param("childTeamNoPrefix") String childTeamNoPrefix);

    List<Team> findByParentIdsIn(Collection<String> parentIds);

    List<Team> findByTeamIdIn(Collection<String> teamIdList);

    @Query(value = "SELECT parentIds FROM team WHERE teamId = :teamId", nativeQuery = true)
    String queryParentIdsByTeamId(@Param("teamId") String teamId);

    Team findByTeamId(String teamId);

    // TODO 调用方可能有 bug
    List<Team> findByParentIdsStartingWith(String parentIds);

    List<Team> findByParentIdsStartingWithOrTeamId(String parentIds, String teamId);

    @Query(value = "select u.userId  from user u LEFT JOIN user_team_relation r ON u.userId = r.userId where " +
            " r.teamId IN :teamIdList order  by u.userId", nativeQuery = true)
    HashSet<String> findUserByTeamIdList(@Param("teamIdList") List<String> teamIdList);

    //根据仓库id和仓库分区(project)获取仓库下包括子孙仓库的所有配送员
    @Query(value = "select distinct(u.userid) as userId,u.userName,u.name,u.avatar,u.tel,u.currentCountry,u.currentTeamId,u.countryCode from user u " +
            "LEFT JOIN  user_team_relation r ON u.userId = r.userId " +
            "LEFT JOIN  team t ON r.teamId = t.teamId " +
            " where " +
            " ((t.teamId NOT IN (:teamIds)  and t.parentIds like :parentIds%) or t.teamId = :teamId) order by u.userId", nativeQuery = true)
    List<JSONObject> findAllDeliveryManByTeamIds(@Param("teamId") String teamId, @Param("parentIds") String parentIds, @Param("teamIds") List<String> teamIds);


    @Query(value = "select * from team t " +
            "LEFT JOIN team_role_relation r ON t.teamId = r.teamId " +
            " where r.roleId =:roleId and t.project = :project and  t.parentIds like :parentIds%", nativeQuery = true)
    HashSet<Team> findAllChildrenWareHouse(@Param("roleId") String roleId, @Param("project") String project, @Param("parentIds") String parentIds);


    Team findByParentIds(String parentIds);

    @Query(value = "SELECT COUNT(*) FROM team WHERE parentIds LIKE :like", nativeQuery = true)
    int countChildrens(@Param("like") String childrenParentIdsLike);

    @Query(value = "SELECT * FROM team " +
            "where teamId in (select teamId from team_role_relation where roleId = :roleId) " +
            "and project = :project and teamIdNo IN :teamIdNos order by parentIds desc", nativeQuery = true)
    List<Team> getTeamByRoleIdAndProject(@Param("roleId") String roleId, @Param("project") String project, @Param("teamIdNos") String[] teamIdNos);


    //根据teamId和parentIds获取包含team在内的子孙team的所有用户
    @Query(value = "select distinct(u.userid) as userId,u.userName,u.avatar,u.name,u.tel,u.currentCountry,u.currentTeamId,u.countryCode from user u " +
            "LEFT JOIN  user_team_relation r ON u.userId = r.userId " +
            "LEFT JOIN  team t ON r.teamId = t.teamId " +
            " where" +
            "  (t.parentIds like :parentIds% or t.teamId = :teamId) order by u.userId", nativeQuery = true)
    List<JSONObject> findAllUserByTeamId(@Param("teamId") String teamId, @Param("parentIds") String parentIds);


    @Query(value = "select count(*) from user_team_relation r where r.teamId IN (select t.teamId from team t where  t.parentIds like :parentIds  or t.teamId =:teamId) ", nativeQuery = true)
    int countUserByTeam(@Param("parentIds") String parentIds, @Param("teamId") String teamId);

    @Query(value = "select * from team where teamId In :teamIds", nativeQuery = true)
    List<Team> getTeamsInTeamIds(@Param("teamIds") List<String> teamIds);

    /**
     * 根据团队流水号查询用户在指定团队及其子团队内为管理员的所有团队信息
     *
     * @param userId            用户 id
     * @param parentTeamNo      团队流水号例如（62）
     * @param childTeamNoPrefix 子团队模糊匹配前缀例如（62,）
     * @return
     */
    @Query(value = "SELECT * " +
            "FROM team " +
            "WHERE teamId IN (SELECT utr.teamId " +
            "                 FROM user_team_relation utr " +
            "                 WHERE mananger = TRUE " +
            "                   AND userId = :userId" +
            "                   AND teamId IN (SELECT teamId FROM team WHERE parentIds = :parentTeamNo" +
            "                                                             OR parentIds LIKE :childTeamNoPrefix%))", nativeQuery = true)
    List<Team> findManageTeamsByTeamId(@Param("userId") String userId,
                                       @Param("parentTeamNo") String parentTeamNo,
                                       @Param("childTeamNoPrefix") String childTeamNoPrefix);


    @Query(value = "select * from team where teamId = rootId", nativeQuery = true)
    List<Team> getAllRoots();

    List<Team> findByTeamIdNoIn(Collection<Long> teamIdNoList);

    Team findByTeamIdNo(long teamIdNo);

    /**
     * 获取用户在一个公司团队的唯一所在分组
     */
    @Query(value = "SELECT t.* " +
            "FROM team t " +
            "WHERE t.teamId IN (SELECT utr.teamId FROM user_team_relation utr WHERE utr.userId = :userId) " +
            "  AND (t.parentIds = :parentTeamNo OR t.parentIds LIKE :childTeamNoPrefix%) LIMIT 1;", nativeQuery = true)
    Team findOnlyTeam(@Param("userId") String userId,
                      @Param("parentTeamNo") String parentTeamNo,
                      @Param("childTeamNoPrefix") String childTeamNoPrefix);


    /**
     * 根据teamInNos获取teamIdList
     *
     * @param teamIdNos
     * @return
     */
    @Query(value = "select t.teamId from team t where t.teamIdNo in :teamIdNos", nativeQuery = true)
    List<String> getTeamIdListByTeamIdNosIn(@Param("teamIdNos") List<Long> teamIdNos);

    /**
     * 根据teamIdList获取teamName字符串，形如"XXX,YYY,ZZZ..."
     *
     * @param teamIdList
     * @return
     */
    @Query(value = "select group_concat(t.name) from team t where t.teamId in :teamIdList", nativeQuery = true)
    String findTeamNameByTeamIdList(@Param("teamIdList") List<String> teamIdList);

    @Query(value = "SELECT rootId FROM team WHERE teamId = :teamId", nativeQuery = true)
    String getRootTeamId(@Param("teamId") String teamId);

    /**
     * 获取一个团队及其子团队的团队 id
     */
    @Query(value = "SELECT t.teamId " +
            "FROM team t " +
            "WHERE t.parentIds = :teamParentIds " +
            "   OR t.parentIds LIKE CONCAT(:teamParentIds,',%');", nativeQuery = true)
    List<String> getTeamAndChildTeamIds(@Param("teamParentIds") String teamParentIds);

    /**
     * 获取一个团队及其子团队
     */
    @Query(value = "SELECT t.* " +
            "FROM team t " +
            "WHERE t.parentIds = :teamParentIds " +
            "   OR t.parentIds LIKE CONCAT(:teamParentIds,',%');", nativeQuery = true)
    List<Team> getTeamAndChildTeam(@Param("teamParentIds") String teamParentIds);


    @Query(value = "SELECT t.* FROM team t WHERE t.parentIds = concat ((SELECT t2.parentIds FROM team t2 WHERE t2.teamId = :teamId),',',t.teamIdNo)", nativeQuery = true)
    List<Team> getChildrenTeamsJustOne(@Param("teamId") String teamId);

    /**
     * 获取所有非风先生的根团队列表
     *
     * @return
     */
    @Query(value = "select t.teamId  as rootTeamId,t.name as rootTeamName,t.project as project," +
            "t.shop as shop from team t where t.teamId = t.rootId and t.teamId not in ('5afd5817cd0bd80ecf11e0dc') ", nativeQuery = true)
    List<Map<String, Object>> getClientRootTeamList();

    /**
     * 获取所有B端的根团队列表
     *
     * @return
     */
    @Query(value = "select t.teamId  as rootTeamId,t.name as rootTeamName," +
            " a.amount as accountBalance from team t " +
            " left join account as a on a.rootTeamId=t.teamId " +
            "where t.teamId = t.rootId and t.teamId not in ('5afd5817cd0bd80ecf11e0dc') and t.shop = 1 order by t.name", nativeQuery = true)
    List<Map<String, Object>> getBrowserRootTeamList();

    /**
     * 获取指定 project 的所有配送团队
     *
     * @param storeRoleId 仓库 id
     * @param project     project
     * @return List<Team>
     */
    @Query(value = "SELECT * " +
            "FROM team " +
            "WHERE rootId = '5afd5817cd0bd80ecf11e0dc' " +
            "AND teamId IN (SELECT teamId FROM team_role_relation WHERE roleId = :storeRoleId) " +
            "and project = :project " +
            "ORDER BY parentIds", nativeQuery = true)
    List<Team> getStoreTeamListByProject(@Param("storeRoleId") String storeRoleId, @Param("project") String project);

    /**
     * 获取所有配送团队
     *
     * @param storeRoleId 仓库 id
     * @return List<Team>
     */
    @Query(value = "SELECT * " +
            "FROM team " +
            "WHERE rootId = '5afd5817cd0bd80ecf11e0dc' " +
            "AND teamId IN (SELECT teamId FROM team_role_relation WHERE roleId = :storeRoleId) " +
            "ORDER BY parentIds", nativeQuery = true)
    List<Team> getStoreTeamList(@Param("storeRoleId") String storeRoleId);

    /**
     * 判断
     *
     * @param teamId 团队 id
     * @param roleId 角色 id
     * @return List<Team>
     */
    @Query(value = "select t.* " +
            "from team t " +
            "       join team_role_relation trr on trr.teamId = t.teamId " +
            "where t.teamId = :teamId " +
            "  and trr.roleId = :roleId ", nativeQuery = true)
    Team findByTeamIdAndRoleId(@Param("teamId") String teamId, @Param("roleId") String roleId);

    @Query(value = "select t.* " +
            "from team t " +
            "       join team_role_relation trr on trr.teamId = t.teamId " +
            "where trr.roleId = :roleId and t.teamIdNo in (:teamIdNos)", nativeQuery = true)
    List<Team> findByTeamIdNosAndRoleId(@Param("teamIdNos") Collection<String> teamIdNos, @Param("roleId") String roleId);

    @Query(value = "select t.teamId as teamId, t.parentIds as parentIds, te.expressBillingModeId as modeId " +
            "from team t " +
            "       join team_extention te on te.teamId = t.teamId " +
            "where te.expressBillingModeId is not null and t.rootId = :rootTeamId ", nativeQuery = true)
    List<JSONObject> findByHasExpressBillingMode(@Param("rootTeamId") String rootTeamId);


    @Query(value = "select t.* " +
            "from team t " +
            "where t.parentIds = :parentIds " +
            "   or t.parentIds like concat(:parentIds, ',%')", nativeQuery = true)
    List<Team> getTeamAndAllChildTeam(@Param("parentIds") String parentIds);

    List<Team> findByType(int type);

    List<Team> findByTypeAndRootId(int type, String rootId);

    @Query(value = "select t.* " +
            "from team t " +
            "where t.type in :types " +
            "  and (t.parentIds = :parentIds " +
            "         or t.parentIds like concat(:parentIds, ',%'))", nativeQuery = true)
    List<Team> findTeamByTypeInWithChild(@Param("types") Collection types, @Param("parentIds") String parentIds);

}













