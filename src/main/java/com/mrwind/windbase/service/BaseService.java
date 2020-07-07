package com.mrwind.windbase.service;

import com.mrwind.windbase.bo.UserLatestLocationBO;
import com.mrwind.windbase.common.api.attence.AttenceApi;
import com.mrwind.windbase.common.api.wallet.WalletApi;
import com.mrwind.windbase.common.api.windforce.WindForceApi;
import com.mrwind.windbase.common.constant.CommonConstants;
import com.mrwind.windbase.common.util.ServletUtil;
import com.mrwind.windbase.common.util.TextUtils;
import com.mrwind.windbase.dao.mongo.*;
import com.mrwind.windbase.dao.mysql.*;
import com.mrwind.windbase.entity.mysql.Team;
import com.mrwind.windbase.entity.mysql.User;
import com.mrwind.windbase.vo.UserLatestLocationVO;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by CL-J on 2018/8/16.
 */
public class BaseService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    protected WindIdLogRepository windIdLogRepository;

    @Resource
    protected TeamRepository teamRepository;

    @Resource
    protected UserRepository userRepository;

    @Resource
    protected UserTeamRelationRepository userTeamRelationRepository;

    @Resource
    protected RoleRepository roleRepository;

    @Resource
    protected TeamRoleRelationRepository teamRoleRelationRepository;

    @Resource
    protected StringRedisTemplate stringRedisTemplate;

    @Resource
    protected AccountService accountService;

    @Resource
    protected UserTeamRelationService userTeamRelationService;

    @Resource
    protected TeamExtentionRepository teamExtentionRepository;

    @Resource
    protected UserTeamRoleRelationRepository userTeamRoleRelationRepository;

    @Resource
    protected UserTeamService userTeamService;

    @Resource
    protected TeamService teamService;

    @Resource
    protected TeamPositionDao teamPositionDao;

    @Resource
    protected RoleTypeRepository roleTypeRepository;

    @Resource
    protected UserExtensionRepository userExtensionRepository;

    @Resource
    protected UserTokenRepository userTokenRepository;

    @Resource
    protected TeamResignationRepository teamResignationRepository;

    @Resource
    protected SmsDao smsDao;
    @Resource
    protected VoiceLogDao voiceLogDao;

    @Resource
    protected WindforceProjectDao windforceProjectApiDao;

    @Resource
    protected UserService userService;

    @Resource
    protected PushMsgService pushMsgService;

    @Resource
    protected PriceRuleRepository priceRuleRepository;

    @Resource
    protected CapitalDetailRepository capitalDetailRepository;

    @Resource
    protected TeamMemberSummaryDao teamMemberSummaryDao;

//    @Resource
//    protected WindChatApi windChatApi;

    @Resource
    protected UserLoginRecordRepository userLoginRecordRepository;

    @Resource
    protected RootTeamPriceRuleRepository rootTeamPriceRuleRepository;

    @Resource
    protected ExpressBillingModeRepository expressBillingModeRepository;

    @Resource
    protected SmsService smsService;

    @Resource
    protected UserAppLanguageRepository userAppLanguageRepository;

    @Resource
    protected CourierSettingsDao courierSettingsDao;

    @Resource(name = "redisTemplate")
    protected RedisTemplate<String, Map<String, String>> userLanguageRedisTemplate;

    @Resource
    protected CacheService cacheService;

    @Resource
    protected BusService busService;

    @Resource
    protected CourierSettingsLogDao courierSettingsLogDao;

    @Resource
    protected UserFriendRepository userFriendRepository;

//    @Resource
//    protected UserFriendService userFriendService;

    @Resource
    protected AttenceApi attenceApi;

    @Resource
    UserAddressRepository userAddressRepository;

    @Resource
    protected WalletApi walletApi;

    @Resource
    protected WindForceApi windForceApi;

    @Resource
    protected BlackListRepository blackListRepository;

//    @Resource
//    protected ChatService chatService;

    @Resource
    protected PermissionService permissionService;

    // 获取当前用户
    public User getCurrentUser() {
        return (User) ServletUtil.getCurrentRequest().getAttribute(CommonConstants.USER_KEY);
    }

    // 获取当前根团队
    public Team getCurrentRootTeam() {
        return (Team) ServletUtil.getCurrentRequest().getAttribute(CommonConstants.USER_ROOT_TEAM);
    }


    /**
     * 获取用户在一个公司团队的唯一所在分组
     *
     * @param userId     用户 id
     * @param rootTeamId 公司根团队 id
     * @return 所在分组
     */
    public Team getOnlyTeam(String userId, String rootTeamId) {
        Team rootTeam = teamRepository.findByTeamId(rootTeamId);
        if (rootTeam != null) {
            return teamRepository.findOnlyTeam(userId, rootTeam.getParentIds(), rootTeam.getParentIds() + ",");
        } else {
            return null;
        }
    }

    /**
     * 获取用户的 Leader，如果当前分组没有或者自己也是管理员，则向父分组寻找，直至找到
     * ps: 如果此人是根团队管理员，则获取到的是自己
     *
     * @param userId        用户 id
     * @param currentTeamId 当前根团队 id
     * @return
     */
    public List<User> getUserLeaders(String userId, String currentTeamId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return new ArrayList<>();
        }
        List<User> leaders = new ArrayList<>();

        // 获取所在分组
        Team team = getOnlyTeam(user.getUserId(), TextUtils.isEmpty(currentTeamId) ? CommonConstants.MRWIND_ID : currentTeamId);
        if (team == null) {
            return new ArrayList<>();
        }

        // 如果此人在跟团队，则不管其是否为管理员直接返回根团队的所有管理员
        if (TextUtils.equals(team.getTeamId(), team.getRootId())) {
            leaders.addAll(getTeamManagersByTeamId(team.getTeamId()));
            return leaders;
        }

        while (leaders.isEmpty()) {
            if (team == null) {
                return new ArrayList<>();
            }
            List<User> teamManagers = getTeamManagersByTeamId(team.getTeamId());
            if (!teamManagers.isEmpty() && !teamManagers.stream().map(User::getUserId).collect(Collectors.toList()).contains(userId)) {
                // 当前分组有管理员
                leaders.addAll(teamManagers);
            } else {
                // 当前分组没有管理员获取自己也是管理员，则向上查找
                String parentIds = team.getParentIds();
                if (parentIds.contains(",")) {
                    team = teamRepository.findByParentIds(parentIds.substring(0, parentIds.lastIndexOf(",")));
                } else {
                    break;
                }
            }
        }

        return leaders;
    }

    /**
     * 获取一个团队的管理员 by teamId
     *
     * @param teamId 团队 id
     * @return 管理员
     */
    public List<User> getTeamManagersByTeamId(String teamId) {
        return userRepository.findTeamManagerByTeamId(teamId);
    }

    /**
     * 获取一个团队的管理员 by teamNo
     *
     * @param teamNo 团队 id
     * @return 管理员
     */
    public List<User> getTeamManagersByTeamNo(String teamNo) {
        return userRepository.findTeamManagerByTeamNo(teamNo);
    }

    /**
     * 为考勤获取一个人的部门信息
     *
     * @param userId        用户 id
     * @param currentTeamId 当前根团队 id
     * @return result
     */
    public Map<String, String> getUserDepartmentForAttenceReport(String userId, String currentTeamId) {
        String department = null;
        String unit = null;
        User user = userRepository.findByUserId(userId);
        Team currentTeam = teamRepository.findByTeamId(currentTeamId);
        if (user != null && currentTeam != null) {
            // 部门
            Team onlyTeam = getOnlyTeam(userId, currentTeamId);
            if (onlyTeam != null) {
                department = onlyTeam.getName();
                // 事业单元
                if (onlyTeam.getParentIds().contains(",")) {
                    String[] split = onlyTeam.getParentIds().split(",");
                    if (split.length <= 2) {
                        unit = department;
                    } else {
                        Team team = teamRepository.findByParentIds(split[0] + "," + split[1]);
                        if (team != null) {
                            unit = team.getName();
                        }
                    }
                } else {
                    unit = onlyTeam.getName();
                }
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("department", department == null ? "" : department);
        map.put("unit", unit == null ? "" : unit);
        return map;
    }

    /**
     * 是否为团队管理员
     *
     * @param userId 用户 id
     * @param teamId 团队 id
     */
    public boolean isManager(String userId, String teamId) {
        Boolean b = userTeamRelationRepository.isTeamManager(teamId, userId);
        return b == null ? false : b;
    }

    /**
     * 从 redis 中获取用户和其语言 map
     */
    public Map<String, String> getUserLanguageMap() {
        return cacheService.getUserLanguageMap();
    }

    /**
     * 获取用户最新位置
     *
     * @param userId 用户 id
     */
    protected UserLatestLocationVO getUserLatestLocation(String userId) {
        UserLatestLocationBO userLatestLocationBO = attenceApi.getUserLatestLocation(userId);
        UserLatestLocationVO userLatestLocationVO = null;
        if (userLatestLocationBO != null) {
            try {
                userLatestLocationVO = new UserLatestLocationVO();
                BeanUtils.copyProperties(userLatestLocationVO, userLatestLocationBO);
            } catch (Exception e) {
                userLatestLocationVO = null;
            }
        }
        return userLatestLocationVO;
    }

}
