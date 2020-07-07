package com.mrwind.windbase.dao.mysql;

import com.mrwind.windbase.entity.mysql.Team;
import com.mrwind.windbase.entity.mysql.UserTeamRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


/**
 * @author wuyiming
 * Created by wuyiming on 2018/7/19.
 */
public interface UserTeamRelationRepository extends JpaRepository<UserTeamRelation, String> {

    @Query(value = "SELECT utr.* FROM user_team_relation utr WHERE utr.teamId = :teamId AND utr.userId = :userId LIMIT 1", nativeQuery = true)
    UserTeamRelation findByTeamIdAndUserId(@Param("teamId") String teamId, @Param("userId") String userId);

    List<UserTeamRelation> findByUserId(String userId);

    int deleteAllByUserIdAndTeamId(String userId, String teamId);

    int deleteAllByTeamIdAndUserIdIn(String teamId, Collection<String> userId);

    List<UserTeamRelation> findByTeamIdAndMananger(String teamId, boolean isManager);

    @Modifying
    @Query(value = "UPDATE user_team_relation utrr SET mananger = :mananger " +
            "WHERE utrr.userId = :userId AND utrr.teamId = :teamId", nativeQuery = true)
    int updateRoleIdByUserIdAndTeamId(@Param("mananger") boolean mananger,
                                      @Param("userId") String userId,
                                      @Param("teamId") String teamId);

    @Query(value = "select * from user_team_relation ur where ur.teamId = :teamId order by field(ur.mananger, true, false), field(ur.id, 0 , 1)", nativeQuery = true)
    List<UserTeamRelation> findByTeamIdAndOrderByManangerDescIdAsc(@Param("teamId") String teamId);

    List<UserTeamRelation> findByTeamId(String teamId);

    List<UserTeamRelation> findByTeamIdIn(Collection<String> teamId);

    List<UserTeamRelation> findByTeamIdInAndUserId(List<String> teamIdList, String userId);

    /**
     * 根据teamIdList获取这些团队的管理员IdList
     *
     * @param teamIdList
     * @return
     */
    @Query(value = "select r.userId from user_team_relation r where r.teamId in (:teamIdList) and r.mananger = true", nativeQuery = true)
    List<String> findManagerIdListByTeamIdList(@Param("teamIdList") List<String> teamIdList);


    /**
     * 根据teamIdList获取这些团队的所有用户IdList
     *
     * @param teamIdList
     * @return
     */
    @Query(value = "select r.userId from user_team_relation r where r.teamId in :teamIdList and r.mananger = true", nativeQuery = true)
    List<String> findUserIdListByByTeamIdList(@Param("teamIdList") List<String> teamIdList);

    @Query(value = "SELECT mananger FROM user_team_relation utr WHERE utr.userId = :userId AND utr.teamId = :teamId", nativeQuery = true)
    Boolean isTeamManager(@Param("teamId") String teamId, @Param("userId") String userId);


    @Query(value = "select u.userId as userId,u.tel as tel  from user u left join " +
            "user_team_relation r on u.userId = r.userId left join " +
            "team t on r.teamId = t.teamId where t.teamIdNo in (:teamIdNos) and r.mananger = true order by length(t.parentIds) desc", nativeQuery = true)
    List<Map<String, Object>> getSuperiorTeamManagerListByTeamIdNoIn(@Param("teamIdNos") List<Long> teamIdNos);

    /**
     * 用来做用户在团队中的排他性校验
     *
     * @param userId
     * @return
     */
    HashSet<UserTeamRelation> findByUserIdInAndTeamId(Collection<String> userId, String teamId);


    @Query(value = "select t.* FROM team t LEFT JOIN user_team_relation ur ON ur.teamId = t.teamId AND t.rootId = :root WHERE ur.userId = :userId AND ur.mananger = 1", nativeQuery = true)
    Team findUserManagerTeam(@Param("root") String root, @Param("userId") String userId);

    @Query(value = "select * from user_team_relation utr join team t on t.teamId = utr.teamId where utr.userId in :userIds and t.rootId = :rootTeamId ", nativeQuery = true)
    List<UserTeamRelation> findByUserIdInAndInRootTeamId(@Param("userIds") Collection<String> userIds, @Param("rootTeamId") String rootTeamId);


}
