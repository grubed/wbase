package com.mrwind.windbase.dao.mysql;

import com.mrwind.windbase.entity.mysql.TeamResignationRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by CL-J on 2018/8/16.
 */
public interface TeamResignationRepository extends JpaRepository<TeamResignationRelation, Long> {

    @Query(value = "select count(distinct userId) " +
            "from team_resignation_relation urr " +
            "where teamId = :teamId ", nativeQuery = true)
    int distinctUserIdCountByTeamId(@Param("teamId") String teamId);

    @Query(value = "select distinct u.userId as userId, u.avatar as avatar, u.tel as tel, trr.name as name " +
            "from user u " +
            "       left join team_resignation_relation trr on trr.userId = u.userId " +
            "where trr.teamId = :teamId", nativeQuery = true)
    List<Map<String, Object>> findLeavedUserInfoByTeamId(@Param("teamId") String teamId);

}
