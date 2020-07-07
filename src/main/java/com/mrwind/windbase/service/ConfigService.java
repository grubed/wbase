package com.mrwind.windbase.service;

import com.mrwind.windbase.common.constant.TeamConfigSwitchStatus;
import com.mrwind.windbase.common.exception.FailResultException;
import com.mrwind.windbase.common.util.CollectionUtil;
import com.mrwind.windbase.common.util.TextUtils;
import com.mrwind.windbase.dao.mongo.TeamConfigSwitchDao;
import com.mrwind.windbase.entity.mongo.TeamConfigSwitch;
import com.mrwind.windbase.entity.mysql.Team;
import com.mrwind.windbase.vo.TeamSwitchVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-01-14
 */

@Service
public class ConfigService extends BaseService {

    @Resource
    private TeamConfigSwitchDao teamConfigSwitchDao;

    /**
     * 设置团队的跟随配置
     *
     * @param teamId 团队 id
     * @param key    约定配置的 key
     * @param status 状态
     */
    public TeamSwitchVO setTeamSwitch(String teamId, String key, String status) {
        checkTeamSwitchStatus(status);
        synchronized ((teamId + key).intern()) {
            TeamConfigSwitch config = teamConfigSwitchDao.findBy(teamId, key);
            if (config == null) {
                config = new TeamConfigSwitch(teamId, key, status);
            } else {
                config.setStatus(status);
            }
            teamConfigSwitchDao.insertOrUpdateById(config);
            return getTeamSwitch(teamId, key);
        }
    }

    /**
     * 检验团队的跟随状态
     */
    private void checkTeamSwitchStatus(String status) {
        if (!TextUtils.equals(status, TeamConfigSwitchStatus.FOLLOW)
                && !TextUtils.equals(status, TeamConfigSwitchStatus.OPEN)
                && !TextUtils.equals(status, TeamConfigSwitchStatus.CLOSE)) {
            throw FailResultException.get("error status");
        }
    }

    /**
     * 根据 userId 和 rootTeamId 获取其所在团队的跟随配置
     *
     * @param userId     用户 id
     * @param rootTeamId 根团队 id
     * @param key        约定的配置 key
     */
    public TeamSwitchVO getTeamSwitch(String userId, String rootTeamId, String key) {
        Team onlyTeam = getOnlyTeam(userId, rootTeamId);
        if (onlyTeam == null) {
            return new TeamSwitchVO();
        }
        return getTeamSwitch(onlyTeam.getTeamId(), key);
    }

    /**
     * 获取团队的跟随配置
     *
     * @param teamId 团队 id
     * @param key    约定的配置 key
     */
    public TeamSwitchVO getTeamSwitch(String teamId, String key) {
        Team team = teamRepository.findByTeamId(teamId);
        if (team == null) {
            return new TeamSwitchVO();
        }
        String[] teamNos = team.getParentIds().split(",");
        List<Team> teams = teamRepository.findByTeamIdNoIn(Arrays.stream(teamNos).map(Long::valueOf).collect(Collectors.toList()));
        Map<Long, Team> teamIdNoMap = CollectionUtil.takeFieldsToMap(teams, "teamIdNo");
        List<String> teamIds = teams.stream().map(Team::getTeamId).collect(Collectors.toList());
        Map<String, TeamConfigSwitch> configSwitchMap = CollectionUtil.takeFieldsToMap(teamConfigSwitchDao.findBy(teamIds, key), "teamId");
        for (int i = teamNos.length - 1; i >= 0; i--) {
            Long teamIdNo = Long.valueOf(teamNos[i]);
            String tid = teamIdNoMap.get(teamIdNo).getTeamId();
            TeamConfigSwitch config = configSwitchMap.get(tid);
            if (config == null) {
                return new TeamSwitchVO();
            }
            switch (config.getStatus()) {
                case TeamConfigSwitchStatus.FOLLOW:
                    break;
                case TeamConfigSwitchStatus.OPEN:
                    return new TeamSwitchVO(tid, i == (teamNos.length - 1) ? TeamConfigSwitchStatus.OPEN : TeamConfigSwitchStatus.FOLLOW_OPEN);
                case TeamConfigSwitchStatus.CLOSE:
                    return new TeamSwitchVO(tid, i == (teamNos.length - 1) ? TeamConfigSwitchStatus.CLOSE : TeamConfigSwitchStatus.FOLLOW_CLOSE);
                default:
                    return new TeamSwitchVO();
            }
        }
        return new TeamSwitchVO();
    }

}
