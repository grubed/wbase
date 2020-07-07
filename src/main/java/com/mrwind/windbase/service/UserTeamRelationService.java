package com.mrwind.windbase.service;

import com.mrwind.windbase.dao.mysql.RoleRepository;
import com.mrwind.windbase.dao.mysql.UserTeamRelationRepository;
import com.mrwind.windbase.entity.mysql.Team;
import com.mrwind.windbase.entity.mysql.UserTeamRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CL-J on 2018/7/18.
 */
@Service
public class UserTeamRelationService extends BaseService{


    @Autowired
    private TeamService teamService;

    public List<Team> GetTeamList(String userId) {
        List<UserTeamRelation> userTeamRoleRelations
                = userTeamRelationRepository
                .findByUserId(userId);
        List<Team> teamList = new ArrayList<Team>();
        userTeamRoleRelations.forEach(item -> {
            Team team = teamService.getOneTeam(item.getTeamId());
            teamList.add(team);
        });
        return teamList;
    }


    /**
     * 判断该team内的某个用户是否为该team的管理员
     * @param userId
     * @param teamId
     * @return
     */
    public Boolean isTeamManger(String userId, String teamId) {
        UserTeamRelation userTeamRoleRelation
                = userTeamRelationRepository
                .findByTeamIdAndUserId(teamId, userId);
        if (userTeamRoleRelation != null && userTeamRoleRelation.isMananger()==true) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 给某一个team内人员设置管理员或成员权限
     * @param teamId
     * @param userId
     * @param isManager true表示给该用户设置管理员权限，false表示用户设置成员权限
     */
    public void setTeamManager(String teamId,String userId, boolean isManager){

        UserTeamRelation userTeamRelation=userTeamRelationRepository.findByTeamIdAndUserId(teamId,userId);
        if(userTeamRelation != null){
            userTeamRelation.setMananger(isManager);
        }else{
            userTeamRelation = new UserTeamRelation(userId,teamId,isManager);
        }
        userTeamRelationRepository.save(userTeamRelation);
    }
}
