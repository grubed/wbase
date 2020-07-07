package com.mrwind.windbase.dao.mysql;

import com.mrwind.windbase.entity.mysql.TeamRoleRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TeamRoleRelationRepository extends JpaRepository<TeamRoleRelation,Long> {

    List<TeamRoleRelation> findByTeamId(String teamId);

    List<TeamRoleRelation> findByTeamIdIn(List<String> teamIdList);

    int deleteAllByTeamIdAndRoleIdIn(String teamId, Collection<String> roleId);

    TeamRoleRelation findByRoleId(String roleId);

    List<TeamRoleRelation> findByRoleIdInAndTeamId(Collection<String> roleId, String teamId);
}
