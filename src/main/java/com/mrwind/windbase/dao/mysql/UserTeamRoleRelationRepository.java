package com.mrwind.windbase.dao.mysql;

import com.mrwind.windbase.entity.mysql.Role;
import com.mrwind.windbase.entity.mysql.UserTeamRoleRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/7/26
 */

public interface UserTeamRoleRelationRepository extends JpaRepository<UserTeamRoleRelation, Long> {

    List<UserTeamRoleRelation> findByTeamIdInAndRoleIdIn(List<String> teamId, List<String> roleId);

    int deleteAllByUserIdAndTeamIdAndRoleId(String userId, String teamId, String roleId);

    int deleteAllByUserIdAndTeamIdAndRoleIdIn(String userId, String teamId, Collection<String> roleId);

    List<UserTeamRoleRelation> findByRoleIdInAndUserIdAndTeamId(Collection<String> roleId, String userId, String teamId);
}
