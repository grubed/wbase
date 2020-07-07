package com.mrwind.windbase.dao.mysql;

import com.mrwind.windbase.entity.mysql.TeamExtention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by CL-J on 2018/7/25.
 */
public interface TeamExtentionRepository extends JpaRepository<TeamExtention, String> {

    List<TeamExtention> findByTeamIdIn(List<String> teamIdList);

    /**
     * 通过团队ID获取该团队附加业务属性
     * @param teamId    团队ID
     * @return
     */
    TeamExtention findByTeamId(String teamId);

    @Query(value = "select * from team_extention where teamId in :teamIds",nativeQuery = true)
    List<TeamExtention> getTeamExtentionsByTeamIds(@Param("teamIds") List<String> teamIds);
}