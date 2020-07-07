package com.mrwind.windbase.dao.mysql;

import com.mrwind.windbase.entity.mysql.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description
 *
 * @author hanjie
 */

public interface UserRepository extends JpaRepository<User, String> {

    User findByTel(String tel);

    List<User> findByTelIn(Collection<String> tel);

    User findByCountryCodeAndTel(Integer countryCode, String tel);

    @Query(value = "SELECT u.* FROM user u " +
            "LEFT JOIN user_team_relation utrr on u.userId = utrr.userId " +
            "WHERE 1 = 1 " +
            "AND utrr.teamId = :teamId", nativeQuery = true)
    List<User> queryUsersByTeamId(@Param("teamId") String teamId);


    @Query(value = "SELECT u.* FROM user u " +
            "LEFT JOIN user_team_relation utrr on u.userId = urr.userId " +
            "LEFT JOIN role r ON utrr.roleId = r.roleId " +
            "WHERE 1 = 1 " +
            "AND r.keyWord = 'manager' " +
            "AND utrr.teamId = :teamId " +
            "AND utrr.managerId = :managerId", nativeQuery = true)
    List<User> queryUsersByTeamIdAndManagerId(@Param("teamId") String teamId, @Param("managerId") String managerId);

    boolean existsByUserNameOrTel(String userName, String tel);

    boolean existsByTel(String tel);

    User findByUserId(String userId);

    User findByUserIdAndTel(String userId, String tel);

    User findByUserNameAndPassword(String userName, String password);


    @Query(value = "select u.* from user u " +
            "where (u.tel like concat('%', :query, '%') or u.name like concat('%', :query, '%'))", nativeQuery = true)
    List<User> findByTelOrName(@Param("query") String query);

    List<User> findByUserIdIn(Collection<String> userId);


    /**
     * 获取团队管理员 by teamId
     */
    @Query(value = "SELECT u.* " +
            "FROM user u " +
            "       LEFT JOIN user_team_relation utr ON u.userId = utr.userId " +
            "WHERE utr.teamId = :teamId " +
            "  AND utr.mananger = TRUE", nativeQuery = true)
    List<User> findTeamManagerByTeamId(@Param("teamId") String teamId);

    /**
     * 获取团队管理员 by teamNo
     */
    @Query(value = "SELECT u.* " +
            "FROM user u " +
            "       JOIN user_team_relation utr ON u.userId = utr.userId JOIN team t ON t.teamId = utr.teamId " +
            " WHERE t.teamIdNo = :teamNo " +
            "  AND utr.mananger = TRUE", nativeQuery = true)
    List<User> findTeamManagerByTeamNo(@Param("teamNo") String teamNo);

    /**
     * 获取一批用户的头像
     *
     * @param userIdList
     * @return
     */
    @Query(value = "select u.userId as userId,u.avatar as avatar from User u where u.userId in (:userIdList)")
    List<Map<String, Object>> findUserAvatarByUserIdIn(@Param("userIdList") Collection<String> userIdList);

    /**
     * 获取一批用户的名字
     *
     * @param userIdList
     * @return
     */
    @Query(value = "select u.userId as userId,u.name as name from User u where u.userId in (:userIdList)")
    List<Map<String, Object>> findNameByUserIdIn(@Param("userIdList") Collection<String> userIdList);

    /**
     * 获取一批用户的电话号码
     *
     * @param userIdList
     * @return
     */
    @Query(value = "select u.userId as userId,u.tel as tel from User u where u.userId in (:userIdList)")
    List<Map<String, Object>> findUserTelByUserIdIn(@Param("userIdList") Collection<String> userIdList);

    /**
     * 获取一个团队及其子团队的所有人的 id
     */
    @Query(value = "SELECT distinct u.userId " +
            "FROM user u " +
            "       LEFT JOIN user_team_relation utr ON u.userId = utr.userId " +
            "WHERE utr.teamId IN (SELECT t.teamId FROM team t WHERE t.parentIds = :teamParentIds " +
            "                                                    OR t.parentIds LIKE CONCAT(:teamParentIds,',%'))", nativeQuery = true)
    List<String> getTeamAndChildTeamUserIds(@Param("teamParentIds") String teamParentIds);

    /**
     * 获取一个团队及其子团队的所有人
     */
    @Query(value = "SELECT u.* " +
            "FROM user u " +
            "       LEFT JOIN user_team_relation utr ON u.userId = utr.userId " +
            "WHERE utr.teamId IN (SELECT t.teamId FROM team t WHERE t.parentIds = :teamParentIds " +
            "                                                    OR t.parentIds LIKE CONCAT(:teamParentIds,',%') GROUP BY u.userId)", nativeQuery = true)
    List<User> getTeamAndChildTeamUsers(@Param("teamParentIds") String teamParentIds);

    /**
     * 获取所有B端客户列表（要排除风先生的账号，要排除rootTeamId是风先生,shop=false，其他需要根据rootTeamId来排序）
     *
     * @return
     */
    @Query(value = "SELECT u.userId  AS userId, " +
            "       u.name    AS userName, " +
            "       u.tel     AS userTel, " +
            "       u.avatar  AS userAvatar, " +
            "       rt.teamId AS rootTeamId, " +
            "       rt.name   as rootTeamName " +
            "FROM team t " +
            "       INNER JOIN team rt ON rt.teamId = t.rootId " +
            "       INNER JOIN user_team_relation utr ON utr.teamId = t.teamId " +
            "       INNER JOIN user u ON u.userId = utr.userId " +
            "WHERE rt.shop = 1 " +
            "  AND rt.teamId != '5afd5817cd0bd80ecf11e0dc' " +
            "ORDER BY t.name, u.name ", nativeQuery = true)
    List<Map<String, Object>> getBrowserUsers();

    /**
     * 分页获取B端客户列表（要排除风先生的账号，要排除rootTeamId是风先生，其他需要根据rootTeamId来排序）
     *
     * @return
     */
    @Query(value = "SELECT u.userId  AS userId, " +
            "       u.name    AS userName, " +
            "       u.tel     AS userTel, " +
            "       u.avatar  AS userAvatar, " +
            "       rt.teamId AS rootTeamId, " +
            "       rt.name   as rootTeamName " +
            "FROM team t " +
            "       INNER JOIN team rt ON rt.teamId = t.rootId " +
            "       INNER JOIN user_team_relation utr ON utr.teamId = t.teamId " +
            "       INNER JOIN user u ON u.userId = utr.userId " +
            "WHERE rt.shop = 1 " +
            "  AND rt.teamId != '5afd5817cd0bd80ecf11e0dc' " +
            "ORDER BY t.name, u.name ", countQuery = "SELECT count(*) " +
            "FROM team t " +
            "       INNER JOIN team rt ON rt.teamId = t.rootId " +
            "       INNER JOIN user_team_relation utr ON utr.teamId = t.teamId " +
            "       INNER JOIN user u ON u.userId = utr.userId " +
            "WHERE rt.shop = 1 " +
            "  AND rt.teamId != '5afd5817cd0bd80ecf11e0dc' " +
            "ORDER BY t.name, u.name", nativeQuery = true)
    Page<Map<String, Object>> getBrowserUsersByPage(Pageable pageable);

    /**
     * 根据teamId获取team下的所有直属用户电话,形如188***,187***,....
     *
     * @param teamId
     * @return
     */
    @Query(value = "select group_concat(u.tel) from user as u " +
            "left  join user_team_relation r on r.userId = u.userId where " +
            "r.teamId = :teamId", nativeQuery = true)
    String getUserTelByUserTeamIn(@Param("teamId") String teamId);

    /**
     * 获取所有B端客户列表,要排除rootTeamId是风先生和shop =false)
     *
     * @return
     */
    @Query(value = "SELECT u.userId  AS userId, " +
            "       u.name    AS userName, " +
            "       u.tel     AS tel, " +
            "       u.avatar  AS userAvatar, " +
            "       rt.teamId AS rootTeamId " +
            "FROM team t " +
            "       INNER JOIN team rt ON rt.teamId = t.rootId " +
            "       INNER JOIN user_team_relation utr ON utr.teamId = t.teamId " +
            "       INNER JOIN user u ON u.userId = utr.userId " +
            "WHERE rt.shop = 1 " +
            "  AND rt.teamId != '5afd5817cd0bd80ecf11e0dc' " +
            "ORDER BY t.name, u.name", nativeQuery = true)
    List<Map<String, Object>> getBrowserUserList();

    /**
     * 获取B端用户数量
     *
     * @return
     */
    @Query(value = "SELECT count(*) " +
            "FROM team t " +
            "       INNER JOIN team rt ON rt.teamId = t.rootId " +
            "       INNER JOIN user_team_relation utr ON utr.teamId = t.teamId " +
            "       INNER JOIN user u ON u.userId = utr.userId " +
            "WHERE rt.shop = 1 " +
            "  AND rt.teamId != '5afd5817cd0bd80ecf11e0dc' " +
            "ORDER BY t.name, u.name", nativeQuery = true)
    int getBrowserUserCount();

    @Query(value = "SELECT u.*" +
            "FROM user u" +
            "       JOIN user_team_relation utr ON u.userId = utr.userId " +
            "WHERE utr.teamId IN (SELECT teamId " +
            "                     FROM team " +
            "                     WHERE rootId = '5afd5817cd0bd80ecf11e0dc' " +
            "                       AND teamId IN " +
            "                           (SELECT teamId FROM team_role_relation WHERE roleId = :storeRoleId) " +
            "                       and project = :project " +
            "                     ORDER BY parentIds)", nativeQuery = true)
    List<User> getCourierByProject(@Param("storeRoleId") String storeRoleId, @Param("project") String project);

    @Query(value = "SELECT u.userId  AS userId, " +
            "       u.name    AS userName, " +
            "       u.tel     AS userTel, " +
            "       u.avatar  AS userAvatar, " +
            "       rt.teamId AS rootTeamId, " +
            "       rt.name   as rootTeamName " +
            "FROM team t " +
            "       INNER JOIN team rt ON rt.teamId = t.rootId " +
            "       INNER JOIN user_team_relation utr ON utr.teamId = t.teamId " +
            "       INNER JOIN user u ON u.userId = utr.userId " +
            "WHERE rt.shop = 1 " +
            "  AND rt.teamId != '5afd5817cd0bd80ecf11e0dc' " +
            "  AND u.createTime >= :startTime and u.createTime <= :endTime " +
            "ORDER BY t.name, u.name", nativeQuery = true)
    List<Map<String, Object>> getBrowserUsersByCreateTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    @Query(value = "SELECT u.userId  AS userId, " +
            "       u.name    AS userName, " +
            "       u.tel     AS userTel, " +
            "       u.avatar  AS userAvatar, " +
            "       rt.teamId AS rootTeamId, " +
            "       rt.name   as rootTeamName " +
            "FROM team t " +
            "       INNER JOIN team rt ON rt.teamId = t.rootId " +
            "       INNER JOIN user_team_relation utr ON utr.teamId = t.teamId " +
            "       INNER JOIN user u ON u.userId = utr.userId " +
            "WHERE rt.shop = 1 " +
            "  AND rt.teamId != '5afd5817cd0bd80ecf11e0dc' " +
            "ORDER BY t.name, u.name", nativeQuery = true)
    List<Map<String, Object>> getAllBrowserUsers();

    /**
     * 根据用户Id列表获取用户name字符串，形如"XXX,YYY,ZZZ..."
     */
    @Query(value = "select group_concat(u.name) from user u where u.userId in :userIdList", nativeQuery = true)
    String findNameByUserIdList(@Param("userIdList") List<String> userIdList);

    @Query(value = "select u.name " +
            "from user u " +
            "       left join user_team_relation utr on u.userId = utr.userId " +
            "where utr.teamId = :teamId", nativeQuery = true)
    List<String> getTeamMembersName(@Param("teamId") String teamId);

    @Query(value = "select u.* " +
            "from user u " +
            "       join user_team_relation utr on utr.userId = u.userId " +
            "where utr.teamId IN (select t.teamId " +
            "                     from team t " +
            "                     where t.parentIds like concat(:parentIds, ',%') " +
            "                        or t.teamId = :teamId )", nativeQuery = true)
    List<User> getAllUsersByTeam(@Param("teamId") String teamId, @Param("parentIds") String parentIds);

    /**
     * 获取一个团队分组的成员
     */
    @Query(value = "select u.* " +
            "from user u " +
            "       join user_team_relation utr on utr.userId = u.userId " +
            "       join team t on t.teamId = utr.teamId " +
            "where t.teamId in :teamIds ", nativeQuery = true)
    List<User> getUserByTeamIdIn(@Param("teamIds") Collection<String> teamIds);

    List<User> findByCreateTimeGreaterThanEqualAndCreateTimeLessThanEqual(Date startCreateTime, Date endCreateTime);

}
