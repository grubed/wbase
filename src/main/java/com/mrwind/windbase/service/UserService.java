package com.mrwind.windbase.service;

import com.alibaba.fastjson.JSONObject;
import com.mrwind.windbase.bo.DeliveryShipmentStatusBO;
import com.mrwind.windbase.bo.PickDeliverDistanceBO;
import com.mrwind.windbase.common.api.WindPushApi;
import com.mrwind.windbase.common.constant.*;
import com.mrwind.windbase.common.exception.FailResultException;
import com.mrwind.windbase.common.threadpool.APIThreadPool;
import com.mrwind.windbase.common.util.*;
import com.mrwind.windbase.dto.*;
import com.mrwind.windbase.entity.mongo.CourierSettingsLog;
import com.mrwind.windbase.entity.mongo.TeamMemberSummary;
import com.mrwind.windbase.entity.mongo.attendance.AttenceSummary;
import com.mrwind.windbase.entity.mongo.fengchat.TeamIMId;
import com.mrwind.windbase.entity.mysql.*;
import com.mrwind.windbase.mq.MultiIOSource;
import com.mrwind.windbase.vo.*;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.security.Key;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户相关
 *
 * @author hanjie
 */

@Service
@EnableBinding(MultiIOSource.class)
public class UserService extends BaseService {

    @Resource
    private TeamService teamService;

    @Resource
    private SmsService smsService;

    @Resource
    private MultiIOSource multiIOSource;



    /**
     * 验证码有效时间（秒）
     */
    private static final int VER_CODE_EXPIRED_TIME = 10 * 60;

    /**
     * FIXME 暂时预留的万能验证码
     */
    private static final String GOD_KEY = "1325";

    private static final List<String> TELS = Arrays.asList("18668126683", "15678991635", "18368893301");

    public User findUserbyId(String userId) {
        return userRepository.findByUserId(userId);
    }

    /**
     * 根据 id 获取用户信息
     */
    public Result getUserInfoById(String userId) {
        User me = getCurrentUser();
        String rootTeamId = me.getCurrentTeamId();
        User targetUser = userRepository.findByUserId(userId);
        if (targetUser == null) {
            return Result.getFailI18N("error.user.not.found");
        }
        CourierSettingsVO courierSettings = busService.getCourierSettingsById(userId);
        UserInfoVO vo = new UserInfoVO(targetUser, courierSettings);
        vo.setWindIdUpdateCount(windIdLogRepository.countByUserId(userId));
        vo.setIsCourier(isCourier(userId, rootTeamId));
        vo.setMember(userTeamService.checkIsMember(me.getUserId(), targetUser.getUserId(), me.getCurrentTeamId()));

        // 接单状态
        Map<String, Integer> receiveOrderStatusMap = busService.getCourierReceiveOrderStatus(Collections.singletonList(userId));
        vo.setReceiveOrder(Optional.ofNullable(receiveOrderStatusMap.get(userId)).orElse(null));

        // 是否有支付密码
        vo.setHasPayPwd(walletApi.hasPayPwd(userId));

        Team onlyTeam = getOnlyTeam(targetUser.getUserId(), rootTeamId);
        if (onlyTeam != null) {
            Team rootTeam = teamRepository.findByTeamId(onlyTeam.getRootId());
            vo.setRootTeamId(Optional.ofNullable(rootTeam).map(Team::getTeamId).orElse(null));
            vo.setRootTeamName(Optional.ofNullable(rootTeam).map(Team::getName).orElse(null));
            vo.setTeamName(onlyTeam.getName());
            vo.setTeamId(onlyTeam.getTeamId());
            UserTeamRelation onlyTeamRelation = userTeamRelationRepository.findByTeamIdAndUserId(onlyTeam.getTeamId(), targetUser.getUserId());
            vo.setManager(onlyTeamRelation != null && onlyTeamRelation.isMananger());
            // 如果自己查看自己，member 字段为自己是否为管理员，即只有管理员才可以对自己有管理权限，普通用户对自己没有管理权限
            if (TextUtils.equals(me.getUserId(), targetUser.getUserId())) {
                vo.setMember(vo.isManager());
            }
        }
        vo.setAccounts(walletApi.getUserAccounts(userId));
        return Result.getSuccess(vo);
    }

    public Result getUserInfoAndRelation(String from, String target) {
        User targetUser = userRepository.findByUserId(target);
        UserFriend friend = userFriendRepository.get2UserRelation(from, target);
        int relation = 2;
        if (friend == null) {
            List<String> userTeamIds = userTeamRelationRepository.findByUserId(from).stream().map(t -> t.getTeamId()).collect(Collectors.toList());
            for (String tid : userTeamIds) {
                if (teamService.allUser(tid).stream().anyMatch(t -> t.getUserId() == target)) {
                    relation = 1;
                    break;
                }
            }
        } else {
            relation = friend.getIsFriend();
        }
        UserAddress address = userAddressRepository.findAllByUserId(target).stream().findFirst().orElse(null);
        Map<String, Object> result = new HashMap<>();
        result.put("userId", targetUser.getUserId());
        result.put("avatar", targetUser.getAvatar());
        result.put("tel", targetUser.getTel());
        result.put("name", targetUser.getName());
        if (address != null) {
            result.put("address", address.getUserAddressDetail());
        }
        result.put("relation", relation);

        return Result.getSuccess(result);

    }

    public Result getUserInfoByIdd(String userId, String root) {
        User targetUser = userRepository.findByUserId(userId);
        if (targetUser == null) {
            return Result.getFailI18N("error.user.not.found");
        }
        Map<String, String> map = new HashMap<>();
        map.put("userId", targetUser.getUserId());
        map.put("name", targetUser.getName());
        map.put("avatar", targetUser.getAvatar());
        map.put("tel", targetUser.getTel());
        return Result.getSuccess(map);

    }

    /**
     * 更新用户信息
     */
    @Transactional(rollbackFor = Exception.class)
    public Result updateUserInfo(String userId, UpdateUserInfoDTO body) {
        User updater = getCurrentUser();
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return Result.getFailI18N("error.user.not.found");
        }
        // 风信号
        if (!TextUtils.isEmpty(body.getWindId())) {
            if (windIdLogRepository.countByUserId(userId) != 0) {
                return Result.getFailI18N("error.update.wind.id");
            }
            if (!TextUtils.equals(body.getWindId(), user.getWindId())) {
                // 记录风信号修改日志
                WindIdLog log = new WindIdLog();
                log.setUserId(userId);
                log.setPrevId(user.getWindId());
                log.setToId(body.getWindId());
                log.setUpdaterId(updater.getUserId());
                windIdLogRepository.save(log);

                user.setWindId(body.getWindId());
            }
        }
        // 名字
        user.setName(Optional.ofNullable(body.getName()).orElse(user.getName()));
        // 更新用户主表
        user.setAvatar(Optional.ofNullable(body.getAvatar()).orElse(user.getAvatar()));

        Map<String,String> update = new HashMap<>();
        update.put("id",user.getUserId());
        update.put("name", user.getName());
        update.put("avatar",user.getAvatar());
        update.put("tel",user.getTel());
        multiIOSource.output1().send(MessageBuilder.withPayload(update).build());


        userRepository.save(user);
        // 更新用户拓展表
        UserExtension ue = userExtensionRepository.findByUserId(userId);
        if (ue == null) {
            ue = new UserExtension();
            ue.setUserId(userId);
        }
        ue.setFlType(Optional.ofNullable(body.getFlType()).orElse(ue.getFlType()));
        userExtensionRepository.save(ue);
        return Result.getSuccess();
    }

    /**
     * 获取发货端用户资料
     */
    public Result getShipmentUserInfo() {
        User user = getCurrentUser();
        ShipmentUserInfoVO vo = new ShipmentUserInfoVO();
        vo.setUserId(user.getUserId());
        vo.setUserName(user.getUserName());
        vo.setName(user.getName());
        vo.setAvatar(user.getAvatar());
        vo.setTel(user.getTel());
        vo.setCountryCode(user.getCountryCode());
        // 当前所在唯一分组（非根团队），查表取第一个值，现在一个人只能在一个分组
        Team onlyTeam = teamService.getOnlyTeam(user.getUserId(), user.getCurrentTeamId());
        String project = null;
        if (onlyTeam != null) {
            vo.setTeamName(onlyTeam.getName());
            vo.setTeamId(onlyTeam.getTeamId());
            project = onlyTeam.getProject();
        }
        // 发货 & 余额信息
        DeliveryShipmentStatusBO statusBO = windForceApi.getUserShipmentStatus();
        vo.setShipmentCountToday(statusBO.getTodayOrder());
        vo.setShipmentCountTotal(statusBO.getTotalOrder());
        vo.setShipmentAmoutTotal(statusBO.getTotalPrice());
        vo.setAccountBalance(accountService.getAccountBalanceByRootTeamId(user.getCurrentTeamId()));
        return Result.getSuccess(vo);
    }

    /**
     * TODO [旧版本]获取用户的详细信息
     */
    public Result getUserDetailsInfo(String userId) {
        User me = getCurrentUser();
        Team rootTeam = getCurrentRootTeam();
        User user = userRepository.findByUserId(userId);
        String currentTeamId = me.getCurrentTeamId();
        if (user == null) {
            return Result.getFailI18N("error.user.not.found");
        }

        UserDetailsInfoVO vo = new UserDetailsInfoVO();
        vo.setUserId(user.getUserId());
        vo.setUserName(user.getUserName());
        vo.setName(user.getName());
        vo.setAvatar(user.getAvatar());
        vo.setTel(user.getTel());
        vo.setCountryCode(user.getCountryCode());
        vo.setMember(userTeamService.checkIsMember(me.getUserId(), user.getUserId(), me.getCurrentTeamId()));

        // 配送设置
        CourierSettingsVO courierSettings = busService.getCourierSettingsById(userId);
        if (courierSettings != null) {
            vo.setAge(courierSettings.getAge());
            vo.setIdCardNo(courierSettings.getIdCardNo());
            vo.setHomeAddress(courierSettings.getHomeAddress());
            vo.setHomeLat(courierSettings.getHomeLat());
            vo.setHomeLng(courierSettings.getHomeLng());
        }

        // 当前所在唯一分组（非根团队），查表取第一个值，现在一个人只能在一个分组
        Team onlyTeam = teamService.getOnlyTeam(user.getUserId(), currentTeamId);
        if (onlyTeam != null) {
            vo.setTeamName(onlyTeam.getName());
            vo.setTeamId(onlyTeam.getTeamId());
            UserTeamRelation onlyTeamRelation = userTeamRelationRepository.findByTeamIdAndUserId(onlyTeam.getTeamId(), user.getUserId());
            vo.setManager(onlyTeamRelation != null && onlyTeamRelation.isMananger());
            // 如果自己查看自己，member 字段为自己是否为管理员，即只有管理员才可以对自己有管理权限，普通用户对自己没有管理权限
            if (TextUtils.equals(me.getUserId(), user.getUserId())) {
                vo.setMember(vo.isManager());
            }
        }

        Team team = teamRepository.findByTeamId(currentTeamId);
        if (team != null) {
            // 所在公司下所有标签
            vo.setRoles(JsonUtil.parse(roleRepository.listTeamAllRoleByParentId(user.getUserId(), team.getParentIds(), team.getParentIds() + ",%"), RoleVO.class));
        }
        vo.setAttendance(null);
        return Result.getSuccess(vo);
    }

    /**
     * [NEW] 获取用户的详细信息（with 数据）
     */
    public Result getUserDetailsInfoWithData(String userId, String startDate, String endDate, String project) {
        User me = getCurrentUser();
        Team rootTeam = getCurrentRootTeam();
        User user = userRepository.findByUserId(userId);
        String currentTeamId = me.getCurrentTeamId();
        String token = ServletUtil.getCurrentRequest().getHeader(HttpHeaders.AUTHORIZATION);
        if (user == null) {
            return Result.getFailI18N("error.user.not.found");
        }

        UserDetailsInfoWithDataVO vo = new UserDetailsInfoWithDataVO();
        vo.setUserId(user.getUserId());
        vo.setUserName(user.getUserName());
        vo.setName(user.getName());
        vo.setTel(user.getTel());
        vo.setAvatar(user.getAvatar());
        vo.setCountryCode(user.getCountryCode());
        vo.setMember(userTeamService.checkIsMember(me.getUserId(), user.getUserId(), me.getCurrentTeamId()));
        Map<String, Object> accountMap = accountService.getAccountBalanceAndCount(userId);
        vo.setTotalIncome((BigDecimal) accountMap.get("amount"));
        vo.setIncomeRecordNum((Long) accountMap.get("count"));
        vo.setBillingMode(busService.getUserExpressBillingModeWithNotFromTeam(userId));
        // 配送设置
        CourierSettingsVO courierSettings = busService.getCourierSettingsById(userId);
        if (courierSettings != null) {
            vo.setIdCardNo(courierSettings.getIdCardNo());
            vo.setAge(courierSettings.getAge());
            vo.setHomeAddress(courierSettings.getHomeAddress());
            vo.setHomeLat(courierSettings.getHomeLat());
            vo.setHomeLng(courierSettings.getHomeLng());
            vo.setOrderLimit(courierSettings.getOrderLimit());
            vo.setLowerBound(courierSettings.getLowerBound());
            vo.setCarryNum(courierSettings.getCarryNum());
            vo.setReceiveOrder(courierSettings.getReceiveOrder());
        }


        // 当前所在唯一分组（非根团队），查表取第一个值，现在一个人只能在一个分组
        Team onlyTeam = teamService.getOnlyTeam(user.getUserId(), currentTeamId);
        if (onlyTeam != null) {
            vo.setTeamName(onlyTeam.getName());
            vo.setTeamId(onlyTeam.getTeamId());
            UserTeamRelation onlyTeamRelation = userTeamRelationRepository.findByTeamIdAndUserId(onlyTeam.getTeamId(), user.getUserId());
            vo.setManager(onlyTeamRelation != null && onlyTeamRelation.isMananger());
            // 如果自己查看自己，member 字段为自己是否为管理员，即只有管理员才可以对自己有管理权限，普通用户对自己没有管理权限
            if (TextUtils.equals(me.getUserId(), user.getUserId())) {
                vo.setMember(vo.isManager());
            }
        }

        Team team = teamRepository.findByTeamId(currentTeamId);
        if (team != null) {
            // 所在公司下所有标签
            vo.setRoles(JsonUtil.parse(roleRepository.listTeamAllRoleByParentId(user.getUserId(), team.getParentIds(), team.getParentIds() + ",%"), RoleVO.class));
        }

        vo.setAttendance(null);

        // 最新位置
        vo.setLatestLocation(getUserLatestLocation(userId));

        // 调用风力统计数据
        Map<String, Object> body = new HashMap<>();
        body.put("startDate", startDate);
        body.put("endDate", endDate);
        body.put("userId", userId);
        vo.getUserData().setExpress(windForceApi.getFlUserTeamData(body));

        return Result.getSuccess(vo);
    }

    /**
     * 获取用户资料页面工作配置页信息
     *
     * @param userId 用户 id，不传默认为当前用户
     */
    public Result getUserWorkSettingsInfo(String userId) {
        if (TextUtils.isEmpty(userId)) {
            userId = getCurrentUser().getUserId();
        }
        UserWorkSettingsInfoVO vo = new UserWorkSettingsInfoVO();
        vo.setLatestLocation(getUserLatestLocation(userId));
        vo.setAttendance(null);
        Map<String, Object> accountMap = accountService.getAccountBalanceAndCount(userId);
        vo.setTotalIncome((BigDecimal) accountMap.get("amount"));
        vo.setIncomeRecordNum((Long) accountMap.get("count"));
        Map<String, Object> associatedExpress = windForceApi.getAssociatedExpress(userId);
        vo.setExpressTotalNum(Optional.ofNullable(associatedExpress.get("total")).map(o -> Integer.valueOf(o.toString())).orElse(0));
        vo.setSettings(busService.getCourierSettingsById(userId));
        return Result.getSuccess(vo);
    }

    /**
     * 根据 token 获取用户信息
     */
    public Result getUserInfoByToken() {
        User user = getCurrentUser();
        CourierSettingsVO courierSettings = busService.getCourierSettingsById(user.getUserId());
        UserInfoVO vo = new UserInfoVO(user, courierSettings);
        return Result.getSuccess(vo);
    }

    /**
     * 根据手机号码获取用户信息
     */
    public Result getUserInfoByTel(String tel) {
        User user = userRepository.findByTel(tel);
        if (user == null) {
            return Result.getFailI18N("error.user.not.found");
        }
        CourierSettingsVO courierSettings = busService.getCourierSettingsById(user.getUserId());
        UserInfoVO vo = new UserInfoVO(user, courierSettings);
        return Result.getSuccess(vo);
    }

    /**
     * 生成 RSA 公钥
     *
     * @return 公钥
     */
    public Result getRSAPublicKey() {
        String publicKey = stringRedisTemplate.opsForValue().get(CommonConstants.Redis.RSA_PUBLIC);
        String privateKey;
        if (TextUtils.isEmpty(publicKey)) {
            logger.info("init RSA key");
            // 首次生成私钥公钥
            Map<String, Key> keyMap = RSAUtils.initKey();
            publicKey = RSAUtils.getPublicKey(keyMap);
            privateKey = RSAUtils.getPrivateKey(keyMap);
            stringRedisTemplate.opsForValue().set(CommonConstants.Redis.RSA_PUBLIC, publicKey);
            stringRedisTemplate.opsForValue().set(CommonConstants.Redis.RSA_PRIVATE, privateKey);
        }
        return Result.getSuccess(publicKey);
    }

    /**
     * 获取登录验证码
     * <p>
     * 客户端 60s 内只能获取一次
     */
    public Result getLoginCode(int countryCode, String tel) {
        String redisValue = stringRedisTemplate.opsForValue().get(CommonConstants.Redis.LOGIN_CODE + tel);
        if (!TextUtils.isEmpty(redisValue) && VerCodeUtil.isFrequently(redisValue)) {
            return Result.getFailI18N("error.frequently.get.ver.code");
        }
        int code = CommUtil.buildRandom(4);
        String content = String.format(LocaleType.getMessage("sms.login.code.template"), code);
        smsService.sendSms(tel, content);
        // 保存验证码到 Redis，有效期 10 分钟
        stringRedisTemplate.opsForValue().set(CommonConstants.Redis.LOGIN_CODE + tel, VerCodeUtil.generateRedisValue(String.valueOf(code)), VER_CODE_EXPIRED_TIME, TimeUnit.SECONDS);
        return Result.getSuccess();
    }

    /**
     * 验证码登录
     */
    public Result codeLogin(CodeLoginDTO body) {
        // 校验验证码 ( 万能验证码 1325 )
        String preCode = VerCodeUtil.extractValue(stringRedisTemplate.opsForValue().get(CommonConstants.Redis.LOGIN_CODE + body.getTel()));
        if (TELS.contains(body.getTel()) && !TextUtils.equals(preCode, body.getVerCode())) {
            return Result.getFailI18N("error.verification.code");
        }
        if (!TextUtils.equals(preCode, body.getVerCode()) && !TextUtils.equals(body.getVerCode(), GOD_KEY)) {
            return Result.getFailI18N("error.verification.code");
        }
        boolean autoRegister = (body.getAutoRegister() != null && body.getAutoRegister());
        User user = userRepository.findByTel(body.getTel());
        if (user == null) {
            if (autoRegister) {
            // 账号不存在自动注册
            user = register(new RegisterAccountBO(body.getCountryCode(), body.getTel()));

                Map<String,String> u = new HashMap<>();
                u.put("id",user.getUserId());
                u.put("name",user.getName());
                u.put("tel",user.getTel());
                u.put("avatar",user.getAvatar());
                multiIOSource.output1().send(MessageBuilder.withPayload(u).build());

            } else {
                return Result.getFailI18N("error.user.not.found");
            }
        } else {
            // 更新当前国家
            user.setCurrentCountry(body.getCountry());
            userRepository.save(user);
        }

        // 更新用户 App 语言
//        if (!TextUtils.isEmpty(body.getLanguage())) {
//            updateUserAppLanguage(user.getUserId(), body.getLanguage());
//        } else {
//            // 前端没传语言，则默认根据手机号来判定
////            updateUserAppLanguage(user.getUserId(), body.getCountryCode() == CountryCodeConstant.CHINA ? LanguageType.CHINESE : LanguageType.ENGLISH);
//        }
        String appName = TextUtils.isEmpty(body.getAppName()) ? "windbase" : body.getAppName();

        // 推送 您的账号在别的设备登录
//        pushMsgService.pushAlreadyLoginTeam(Arrays.asList(user.getUserId()));

        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(body.getDeviceID())) {
//            String clientId = WindPushApi.updateDeviceToken(user.getUserId(), body.getDeviceID(), appName);
//            logger.info(user.getUserId() + " clientId : " + clientId);
            map.put("clientId", "");
        }
        map.put("firstLogin", !userTokenRepository.existsByUserId(user.getUserId()));
        map.put("token", generateToken(user.getUserId(), user.getTel()));
        map.put("user", user);
        List<Team> roots = teamRepository.findUserAllRoots(user.getUserId()).stream().sorted(Comparator.comparing(t -> t.getTeamId().length())).collect(Collectors.toList());
        map.put("roots", roots);

        return Result.getSuccess(map);
    }

    /**
     * 账号密码登录
     */
    public Result userLogin(UserLoginDTO body) {
        // 使用 RSA 私钥解密密码
        User user = userRepository.findByUserNameAndPassword(body.getUserName(), decryptPassword(body.getPassword()));
        if (user == null) {
            return Result.getFailI18N("error.username.or.password.not.match");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("token", generateToken(user.getUserId(), user.getTel()));
        return Result.getSuccess(map);
    }

    /**
     * 登出
     * - 清除当前 token
     * - 清空 deviceToken
     * - 清空 currentTeamId
     */
    @Transactional(rollbackFor = Exception.class)
    public Result logout(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return Result.getSuccessI18N("error.user.not.found");
        }

        // 考勤登出
        if (!TextUtils.isEmpty(user.getCurrentTeamId())) {
            attenceApi.userLogout(user.getUserId(), user.getCurrentTeamId());
        }

        UserToken userToken = userTokenRepository.findByUserId(userId);
        if (userToken != null) {
            userToken.setCurrentToken("");
            userTokenRepository.save(userToken);
        }
        // 清空 currentTeamId
        user.setCurrentTeamId(null);
        userRepository.save(user);
        // 清空 deviceToken
//        boolean delResult = WindPushApi.delDeviceToken(userId, "windbase");
//        logger.info("删除 deviceToken: " + userId + "  " + delResult);
        return Result.getSuccess();
    }

    /**
     * TODO [Remove]账号密码注册
     */
    @Transactional(rollbackFor = Exception.class)
    public Result register(RegisterAccountDTO body) {
        if (userRepository.existsByUserNameOrTel(body.getUserName(), body.getTel())) {
            return Result.getFailI18N("error.username.or.tel.exist");
        }
        // 创建用户
        User user = new User();
        user.setUserName(body.getUserName());
        user.setPassword(decryptPassword(body.getPassword()));
        user.setTel(body.getTel());
        user.setName(body.getTel());
        user.setCountryCode(body.getCountryCode());
        userRepository.save(user);
        // 创建用户拓展记录
        UserExtension userExtension = new UserExtension();
        userExtension.setUserId(user.getUserId());
        userExtensionRepository.save(userExtension);
        // 为该用户创建团队
//        Team team = teamService.createRootTeam("", user.getUserName(), "", null, user);

//        userTeamRelationService.setTeamManager(team.getTeamId(), user.getUserId(), true);

        user.setName(body.getTel());
        return Result.getSuccess(user);
    }

    /**
     * 注册
     */
    public User register(RegisterAccountBO bo) {
        synchronized (bo.getTel().intern()) {
            User user = userRepository.findByTel(bo.getTel());
            if (user != null) {
                return user;
            }
            // 创建用户
            user = new User();
            user.setTel(bo.getTel());
            user.setName(TextUtils.isEmpty(bo.getName()) ? bo.getTel() : bo.getName());
            if (!TextUtils.isEmpty(bo.getAvatar())) {
                user.setAvatar(bo.getAvatar());
            }
            user.setCountryCode(bo.getCountryCode());
            userRepository.save(user);
            // 创建用户拓展记录
            UserExtension userExtension = new UserExtension();
            userExtension.setUserId(user.getUserId());
            userExtensionRepository.save(userExtension);
            // 为该用户创建团队
//            Team team = teamService.createRootTeam("", user.getTel(), "", null, user);
//            userTeamRelationService.setTeamManager(team.getTeamId(), user.getUserId(), true);
            //5. 创建资金账户
//            if (!walletApi.createAccount(user.getUserId())) {
//                logger.info("创建账户失败 ---> userId: " + user.getUserId());
//            }
            // 新注册用户通知wallet项目
            walletApi.newUser(user.getUserId());
            return user;
        }
    }

    /**
     * 获取修改手机号码的验证码
     */
    public Result getChangeTelCode(int countryCode, String newTel) {
        User user = getCurrentUser();
        String redisValue = stringRedisTemplate.opsForValue().get(CommonConstants.Redis.CHANGE_TEL_CODE + user.getUserId());
        if (!TextUtils.isEmpty(redisValue) && VerCodeUtil.isFrequently(redisValue)) {
            return Result.getFailI18N("error.frequently.get.ver.code");
        }
        int code = CommUtil.buildRandom(4);
        String content = String.format(LocaleType.getMessage("sms.change.tel.code.template"), code);
        smsService.sendSms(newTel, content);
        // 存在 Redis 中的值为: code,countryCode,newTel ( 不要用下划线分割，下划线分割了时间戳 )  有效期 10 分钟
        redisValue = code + "," + countryCode + "," + newTel;
        stringRedisTemplate.opsForValue().set(CommonConstants.Redis.CHANGE_TEL_CODE + user.getUserId(), VerCodeUtil.generateRedisValue(redisValue), VER_CODE_EXPIRED_TIME, TimeUnit.SECONDS);
        return Result.getSuccess();
    }

    /**
     * 修改手机号码
     */
    @Transactional(rollbackFor = Exception.class)
    public Result changeTel(String verCode) {
        User user = getCurrentUser();
        // 校验验证码 ( 万能验证码 1325 )
        String redisValue = VerCodeUtil.extractValue(stringRedisTemplate.opsForValue().get(CommonConstants.Redis.CHANGE_TEL_CODE + user.getUserId()));
        String code;
        String countryCode;
        String newTel;
        try {
            if (TextUtils.isEmpty(redisValue)) {
                throw new RuntimeException();
            }
            code = redisValue.split(",")[0];
            countryCode = redisValue.split(",")[1];
            newTel = redisValue.split(",")[2];
            if (!TextUtils.equals(code, verCode) && !TextUtils.equals(verCode, GOD_KEY)) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            return Result.getFailI18N("error.verification.code");
        }
        // 校验待绑定的手机号码
        if (userRepository.findByCountryCodeAndTel(Integer.parseInt(countryCode), newTel) != null) {
            return Result.getFailI18N("error.exist.tel");
        }
        user.setTel(newTel);
        user.setCountryCode(Integer.parseInt(countryCode));
        userRepository.save(user);
        return Result.getSuccess();
    }

    /**
     * 获取修改密码的验证码
     */
    public Result getChangePasswordCode(int countryCode, String tel) {
        if (userRepository.findByTel(tel) == null) {
            return Result.getFailI18N("error.invalid.tel");
        }
        String redisValue = stringRedisTemplate.opsForValue().get(CommonConstants.Redis.CHANGE_PASSWORD_CODE + tel);
        if (!TextUtils.isEmpty(redisValue) && VerCodeUtil.isFrequently(redisValue)) {
            return Result.getFailI18N("error.frequently.get.ver.code");
        }
        int code = CommUtil.buildRandom(4);
        String content = String.format(LocaleType.getMessage("sms.change.password.code.template"), code);
        smsService.sendSms(tel, content);
        // 保存验证码到 Redis，有效期 10 分钟
        stringRedisTemplate.opsForValue().set(CommonConstants.Redis.CHANGE_PASSWORD_CODE + tel, VerCodeUtil.generateRedisValue(String.valueOf(code)), VER_CODE_EXPIRED_TIME, TimeUnit.SECONDS);
        return Result.getSuccess();
    }

    /**
     * 修改密码
     */
    @Transactional(rollbackFor = Exception.class)
    public Result changePassword(ChangePasswordDTO body) {
        User user = userRepository.findByTel(body.getTel());
        if (user == null) {
            return Result.getFailI18N("error.invalid.tel");
        }
        // 校验验证码 ( 万能验证码 1325 )
        String preCode = VerCodeUtil.extractValue(stringRedisTemplate.opsForValue().get(CommonConstants.Redis.CHANGE_PASSWORD_CODE + body.getTel()));
        if (!TextUtils.equals(preCode, body.getCode()) && !TextUtils.equals(body.getCode(), GOD_KEY)) {
            return Result.getFailI18N("error.verification.code");
        }
        // 使用 RSA 私钥解密密码
        user.setPassword(decryptPassword(body.getNewPassword()));
        userRepository.save(user);
        return Result.getSuccess();
    }

    /**
     * 设置当前团队
     *
     * @param currentTeamId 当前团队 id
     * @return result
     */
    public Result updateCurrentTeamId(String currentTeamId, String userId) {
        User user = userRepository.findByUserId(userId);
        user.setCurrentTeamId(currentTeamId);
        userRepository.save(user);

        boolean isFirstLogin = !userLoginRecordRepository.existsByUserIdAndTeamId(userId, currentTeamId);
        if (isFirstLogin) {
            List<User> managers = getUserLeaders(user.getUserId(), user.getCurrentTeamId());
            Collection<String> ids = managers.stream().map(User::getUserId).collect(Collectors.toList());
            Map<String, String> msgContent = LocaleType.getMessageMap("chat.first.meet.to.leader");
//            MessageConfig config = MessageConfig.buildConfig(
//                    user.getUserId(),
//                    ids,
//                    getUserLanguageMap(),
//                    msgContent,
//                    currentTeamId
//            );
//            windChatApi.sendText(config, msgContent);

            // 记录已登陆过
            UserLoginRecord record = new UserLoginRecord();
            record.setUserId(userId);
            record.setTeamId(currentTeamId);
            userLoginRecordRepository.save(record);
        }

        // 考勤登录
        attenceApi.userLogin(userId, currentTeamId);

        return Result.getSuccess();
    }

    /**
     * 解密客户端传递过来的 RSA 明文加密后的密文
     *
     * @param rsaPassword RSA 明文加密后的密文
     * @return 解密后的密码
     */
    private String decryptPassword(String rsaPassword) {
        try {
            String privateKey = stringRedisTemplate.opsForValue().get(CommonConstants.Redis.RSA_PRIVATE);
            byte[] decodedData = RSAUtils.decryptByPrivateKey(rsaPassword, privateKey);
            return new String(decodedData);
        } catch (Exception e) {
            return rsaPassword;
//            throw FailResultException.get("rsa error");
        }
    }

    /**
     * 生成 token，顺便保存到数据库
     *
     * @param userId 用户 id
     * @param tel    手机号码
     * @return token 生成
     */
    private String generateToken(String userId, String tel) {
        String token;
        try {
            token = JwtUtil.createJWT(userId + "_" + tel);
            // 保存 token 到数据库
            userTokenRepository.save(new UserToken(userId, token));
            return token;
        } catch (Exception e) {
            logger.error("Generate token error : " + e.toString());
            throw FailResultException.getI18N("error.generate.token");
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * 给用户添加标签
     *
     * @param dto 用户 团队 权限 id
     * @return result
     */
    public Result addRoleForUser(AddRoleForUserDTO dto) {
        userTeamRoleRelationRepository.save(new UserTeamRoleRelation(dto.getUserId(), dto.getTeamId(), dto.getRoleId()));
        return Result.getSuccess();
    }

    /**
     * 删除用户标签
     *
     * @param dto 用户 团队 权限 id
     * @return result
     */
    public Result removeUserTeamRole(RemoveUserRoleDTO dto) {
        userTeamRoleRelationRepository.deleteAllByUserIdAndTeamIdAndRoleId(dto.getUserId(), dto.getTeamId(), dto.getRoleId());
        return Result.getSuccess();
    }

    /**
     * 姓名或手机号模糊查询
     *
     * @param dto query 关键字
     * @return userList
     */
    public Result searchUser(SearchUserDTO dto) {
        List<User> users = userRepository.findByTelOrName(dto.getQuery());
        return Result.getSuccess(users);
    }

    /**
     * 获取风先生团队下打着service标签的团队下的人
     *
     * @return result userList
     */
    public Result getServices() {
        Role serviceRole = roleRepository.findByName(CommonConstants.RoleKey.SERVICE_KEY);
        TeamRoleRelation teamRoleRelation = teamRoleRelationRepository.findByRoleId(serviceRole.getRoleId());
        Team serviceTeam = teamRepository.findByTeamId(teamRoleRelation.getTeamId());
        List<UserTeamRelation> userTeamRelations = userTeamRelationRepository.findByTeamIdAndOrderByManangerDescIdAsc(serviceTeam.getTeamId());
        List<User> users = userTeamRelations.stream().map(u -> userRepository.findByUserId(u.getUserId())).collect(Collectors.toList());
        return Result.getSuccess(users);
    }

    public void pushForUsers(UserPushDTO req) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("data", LocaleType.getMessageMap(req.getBody()));
//        new PushRequest.Builder<>(req.getUserIdList())
//                .setActionType(req.getActionType())
//                .setTitle(LocaleType.getMessageMap(req.getTitle()))
//                .setBody(LocaleType.getMessageMap(req.getBody()))
//                .setData(dataMap)
//                .build()
//                .push();
    }

    public Result getUserAvatarByUserIdList(List<String> userIdList) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (userIdList.isEmpty()) {
            return Result.getSuccess(result);
        }
        List<User> users = userRepository.findByUserIdIn(userIdList);
        for (User user : users) {

            Map<String, Object> map = new HashMap<>();
            map.put("userId", user.getUserId());
            map.put("avatar", user.getAvatar());
            map.put("userTel", user.getTel());
            map.put("userName", user.getName());
            map.put("name", user.getName());
            map.put("tel", user.getTel());
            List<UserTeamRelation> userTeamRelation = userTeamService.getUserTeamRelationWithUserId(user.getUserId());
            if (userTeamRelation != null && !userTeamRelation.isEmpty()) {
                map.put("currentTeamId", userTeamRelation.get(0).getTeamId());
            }

            result.add(map);
        }
        return Result.getSuccess(result);
    }

    /**
     * TODO REMOVE 获取个人数据页信息（考勤、派件、团队）
     */
    public Result getUserData() {
        User user = getCurrentUser();
        String currentTeamId = user.getCurrentTeamId();
        String date = TimeUtil.formatDate(new Date(), TimeUtil.yyyyMMdd1);

        OldUserDataVO vo = new OldUserDataVO();
        vo.setAttence(new OldUserDataVO.Attence());
        vo.setDelivery(new OldUserDataVO.Delivery());
        vo.setTeam(new OldUserDataVO.Team());

        // 1. 考勤

        Team onlyTeam = getOnlyTeam(user.getUserId(), user.getCurrentTeamId());
        if (onlyTeam != null) {
            // 2. 派件
            PickDeliverDistanceBO bo = windForceApi.getPickDeliverDistance(user.getUserId(), date, date);
            vo.getDelivery().setPick(bo.getPick());
            vo.getDelivery().setDelivered(bo.getDelivered());
            vo.getDelivery().setPickDistance(bo.getPickDistance());
            vo.getDelivery().setDeliveredDistance(bo.getDeliveredDistance());

            // 3. 团队出勤 & 外出数量
            List<String> teamUserIds = userRepository.getTeamAndChildTeamUserIds(String.valueOf(onlyTeam.getParentIds()));
            if (!teamUserIds.isEmpty()) {
                // 出勤人员数量
                // 外出人员数量
            }

            // 4. 团队离职 & 新成员数量
            List<String> teamIds = teamRepository.getTeamAndChildTeamIds(String.valueOf(onlyTeam.getParentIds()));
            // 离职人员数量
            int quitMember = 0;
            TeamMemberSummary quitMemberSummary = teamMemberSummaryDao.get(date, TeamMemberSummaryType.QUIT, currentTeamId);
            if (quitMemberSummary != null) {
                for (TeamMemberSummary.Summary summary : quitMemberSummary.getSummaries()) {
                    if (teamIds.contains(summary.getTeamId()) && summary.getUserIds() != null) {
                        quitMember += summary.getUserIds().size();
                    }
                }
                vo.getTeam().setQuitMember(quitMember);
            }
            // 新成员数量
            int newMember = 0;
            TeamMemberSummary newMemberSummary = teamMemberSummaryDao.get(date, TeamMemberSummaryType.NEW, currentTeamId);
            if (newMemberSummary != null) {
                for (TeamMemberSummary.Summary summary : newMemberSummary.getSummaries()) {
                    if (teamIds.contains(summary.getTeamId()) && summary.getUserIds() != null) {
                        newMember += summary.getUserIds().size();
                    }
                }
                vo.getTeam().setNewMember(newMember);
            }
        }

        return Result.getSuccess(vo);
    }

    public Result getUserCountryCodeMap(List<String> userIds) {
        UserCountryCodeMapVO vo = new UserCountryCodeMapVO();
        vo.setZh(new HashSet<>());
        vo.setEn(new HashSet<>());
        Map<String, String> map = getUserLanguageMap();
        assert map != null;
        for (String userId : userIds) {
            String language = map.get(userId);
            if (language == null) {
//                language = LanguageType.CHINESE;
            }
//            switch (language) {
//                case LanguageType.CHINESE:
//                    vo.getZh().add(userId);
//                    break;
//                case LanguageType.ENGLISH:
//                default:
//                    vo.getEn().add(userId);
//                    break;
//            }
        }
        return Result.getSuccess(vo);
    }

    public boolean listSizeCheck(List<Object> list) {
        if (list.size() < 1) {
            return false;
        }
        return true;
    }

    public boolean userParamCheck(List<String> userIdList, String teamId) {
        boolean flag = true;
        HashSet<UserTeamRelation> userList = userTeamRelationRepository.findByUserIdInAndTeamId(userIdList, teamId);
        if (userList.size() < userIdList.size()) {
            flag = false;
            return flag;
        }
        return flag;
    }

    /**
     * 自动登录
     */
    public Result autoLogin(AutoLoginDTO dto) {
        User user = null;
        if (!TextUtils.isEmpty(dto.getTel())) {
            user = userRepository.findByTel(dto.getTel());
        } else if (!TextUtils.isEmpty(dto.getUserId())) {
            user = userRepository.findByUserId(dto.getUserId());
        }
        if (user == null) {
            return Result.getFailI18N("error.user.not.found");
        }
        AutoLoginVO vo = new AutoLoginVO();
        vo.setUserId(user.getUserId());
        vo.setTel(user.getTel());
        UserToken userToken = userTokenRepository.findByUserId(user.getUserId());
        if (TextUtils.isEmpty(userToken.getCurrentToken())) {
            vo.setToken(generateToken(user.getUserId(), user.getTel()));
        } else {
            // 如果当前有 token 还要判断 token 是否能正常解析（例如过期无法解析）
            try {
                JwtUtil.parseJWT(userToken.getCurrentToken()).getSubject();
                vo.setToken(userToken.getCurrentToken());
            } catch (Exception e) {
                vo.setToken(generateToken(user.getUserId(), user.getTel()));
            }
        }
        return Result.getSuccess(vo);
    }

    /**
     * 更新用户 App 语言
     *
     * @param userId   用户 id
     * @param language 语言
     */
    public void updateUserAppLanguage(String userId, String language) {
        userAppLanguageRepository.save(new UserAppLanguage(userId, language));
        cacheService.updateRedisUserLanguageCache(userId, language);
    }


    /**
     * 获取B端客户列表及账户余额
     *
     * @return
     */
    public Result getBrowserUsersDetail() {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        //获取所有B端根团队列表
        List<Map<String, Object>> browserRootTeamList = teamRepository.getBrowserRootTeamList();
        //获取所有B端用户列表
        List<Map<String, Object>> browserUserList = userRepository.getBrowserUserList();
        for (int i = 0, length = browserRootTeamList.size(); i < length; i++) {
            String rootTeamId = browserRootTeamList.get(i).get("rootTeamId").toString();
            Map<String, Object> map = new HashMap<>();
            map.putAll(browserRootTeamList.get(i));
            map.put("userList", (browserUserList.stream().filter(t -> (rootTeamId.equals(t.get("rootTeamId").toString())))).collect(Collectors.toList()));
            mapList.add(map);
        }
        result.put("total", userRepository.getBrowserUserCount());
        result.put("list", mapList);
        return Result.getSuccess(result);

    }

    /**
     * 根据 userId 获取 token
     *
     * @param userId 用户 id
     * @return token
     */
    public String findTokenByUserId(String userId) {
        UserToken userToken = userTokenRepository.findByUserId(userId);
        return userToken == null ? null : userToken.getCurrentToken();
    }

    public Result usersUnLogin(List<String> userIds) {
        List<UserToken> tokens = userTokenRepository.findAllByUserIdIn(userIds);
        List<String> chats = tokens.stream().filter(t -> !TextUtils.isEmpty(t.getCurrentToken())).map(t -> t.getUserId()).collect(Collectors.toList());
        List<String> smses = userIds.stream().filter(t -> !chats.contains(t)).collect(Collectors.toList());
        List<String> tels = userRepository.findByUserIdIn(smses).stream().map(t -> t.getTel()).collect(Collectors.toList());
        JSONObject res = new JSONObject();
        res.put("chat", chats);
        res.put("sms", tels);
        return Result.getSuccess(res);
    }

    /**
     * 更新接单状态
     */
    public Result updateReceiveOrderStatus(Integer receive) {
        User me = getCurrentUser();
        return updateReceiveOrderStatusById(me.getUserId(), receive, true);
    }

    /**
     * 更新接单状态 by id
     */
    public Result updateReceiveOrderStatusById(String userId, Integer receive, boolean saveLog) {
        Calendar nowCalendar = Calendar.getInstance();
        UserExtension ue = userExtensionRepository.findByUserId(userId);
        if (ue == null) {
            ue = new UserExtension();
            ue.setUserId(userId);
        }
        ue.setReceiveOrderStatus(receive);
        ue.setReceiveOrderStatusUpdateTime(nowCalendar.getTime());
        ue.setWorkStatus(receive);
        ue.setWorkStatusUpdateTime(nowCalendar.getTime());

        if (receive == 1) {
            Date lastFirstTime = ue.getWorkStatusTodayFirstTime();
            if (lastFirstTime == null) {
                ue.setWorkStatusTodayFirstTime(nowCalendar.getTime());
            } else {
                // 需要判断是否更新 workStatusTodayFirstTime 字段

                // 今日上班时间开始点为考勤时间的前4个小时
                String startAttendanceTime = "09:00";
                Calendar todayStart = Calendar.getInstance();
                todayStart.setTime(nowCalendar.getTime());
                todayStart.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startAttendanceTime.split(":")[0]));
                todayStart.set(Calendar.MINUTE, Integer.parseInt(startAttendanceTime.split(":")[1]));
                todayStart.set(Calendar.SECOND, 0);
                todayStart.set(Calendar.MILLISECOND, 0);
                todayStart.add(Calendar.HOUR_OF_DAY, -4);
                if (todayStart.get(Calendar.DAY_OF_YEAR) != nowCalendar.get(Calendar.DAY_OF_YEAR)) {
                    // 如果减4小时到昨天了，将todayStart设为今天的0点
                    todayStart = TimeUtil.getPureCalendar();
                }
                if (nowCalendar.getTime().compareTo(todayStart.getTime()) > 0 && lastFirstTime.compareTo(todayStart.getTime()) < 0) {
                    ue.setWorkStatusTodayFirstTime(nowCalendar.getTime());
                }
            }
        }
        userExtensionRepository.save(ue);

        // 记录日志
        if (saveLog) {
            CourierSettingsLog log = new CourierSettingsLog(getCurrentUser().getUserId(), userId);
            log.setSettings(new CourierSettingsDTO(receive));
            courierSettingsLogDao.save(log);
        }

        return Result.getSuccess();
    }

    /**
     * 获取接单状态
     */
    public Integer getReceiveOrderStatus() {
        User me = getCurrentUser();
        return getReceiveOrderStatusById(me.getUserId());
    }

    /**
     * 获取接单状态 by id
     */
    public Integer getReceiveOrderStatusById(String userId) {
        Integer receiveStatus;
        UserExtension ue = userExtensionRepository.findByUserId(userId);
        if (ue == null) {
            receiveStatus = null;
        } else {
            receiveStatus = ue.getReceiveOrderStatus();
        }
        return receiveStatus;
    }

    /**
     * 判断用户是否为配送员
     */
    public boolean isCourier(String userId, String rootTeamId) {
        Team onlyTeam = getOnlyTeam(userId, rootTeamId);
        if (onlyTeam == null) {
            return false;
        }
        return onlyTeam.getType() == TeamType.STORE;
    }

    public Result createBatchReceiver(User creator, List<CreateReciverDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return Result.getFailI18N("error.parameter");
        }
        Runnable runnable = () -> {
            for (CreateReciverDTO dto : dtos) {
                createReceiver(creator, dto);
            }
        };
        APIThreadPool.getNewCachedThreadPool().execute(runnable);
        return Result.getSuccess();
    }

    public Result createReceiver(User creator, CreateReciverDTO createReciverDTO) {
        if (TextUtils.isEmpty(createReciverDTO.getTel()) || TextUtils.isEmpty(createReciverDTO.getName())) {
            return Result.getFailI18N("error.parameter");
        }
        synchronized (createReciverDTO.getTel().intern()) {
            User user = userRepository.findByTel(createReciverDTO.getTel());
            if (user == null) {
                user = register(new RegisterAccountBO(ValidUtil.getCountryCode(createReciverDTO.getTel()), createReciverDTO.getTel(), createReciverDTO.getName()));

                Map<String,String> u = new HashMap<>();
                u.put("id",user.getUserId());
                u.put("name",user.getName());
                u.put("tel",user.getTel());
                u.put("avatar",user.getAvatar());
                multiIOSource.output1().send(MessageBuilder.withPayload(u).build());
            }
//            userFriendService.makeContact(new AddFriendDTO(creator.getUserId(), user.getUserId()));
            if (createReciverDTO.getAddresses() == null) {
                return Result.getSuccess(user);
            }
            List<UserAddress> addresses = new ArrayList<>();
            for (AddressDTO dto : createReciverDTO.getAddresses()) {
                UserAddress address = userAddressRepository.findUserAddressByUserIdAndUserAddressAndUserCountryAndUserProvinceAndUserCityAndUserDistrictAndUserLandMark(user.getUserId(), dto.getUserAddress(),
                        dto.getUserCountry(), dto.getUserProvince(), dto.getUserCity(), dto.getUserDistrict(), dto.getUserLandMark());
                if (address != null) {
                    continue;
                }
                UserAddress userAddress = new UserAddress(user.getUserId(), dto.getUserAddrSort(), dto.getUserAddress(), dto.getUserAddressDetail(), dto.getUserLat(), dto.getUserLng(), dto.getUserCountry(), dto.getUserProvince(), dto.getUserCity(), dto.getUserDistrict(), dto.getUserLandMark(), 0);
                addresses.add(userAddress);
            }
            for (UserAddress address : addresses) {
                userAddressRepository.save(address);
            }
            return Result.getSuccess(user);
        }
    }

    public Result getUserAddress(String userId) {
        List<UserAddress> addresses = userAddressRepository.findAllByUserId(userId);
        return Result.getSuccess(addresses);
    }

    public Result createUserAddress(CreateUserAddressDTO dto) {
        UserAddress address = new UserAddress(dto.getUserId(), dto.getUserAddrSort(), dto.getUserAddress(), dto.getUserAddressDetail(), dto.getUserLat(), dto.getUserLng(), dto.getUserCountry(), dto.getUserProvince(), dto.getUserCity(), dto.getUserDistrict(), dto.getUserLandMark(), dto.getIsDefault());
        if (dto.getId() != null && !Long.valueOf(dto.getId()).equals(0)) {
            address.setId(dto.getId());
        }
        userAddressRepository.save(address);
        return Result.getSuccess(address);
    }

    public Result deleteUserAddress(long id) {
        try {
            userAddressRepository.deleteById(id);
        } catch (Exception ex) {
            return Result.getFail("id 不存在");
        }
        return Result.getSuccess();
    }

    public Result getUserAddressByAddressId(String addressId) {
        return Result.getSuccess(userAddressRepository.findById(Long.parseLong(addressId)).orElse(null));
    }

    public List<User> getUsersByCreateTime(Date startTime, Date endTime) {
        return userRepository.findByCreateTimeGreaterThanEqualAndCreateTimeLessThanEqual(startTime, endTime);
    }

    public List<User> users(List<String> uids) {
        List<User> users = userRepository.findByUserIdIn(uids);
        return users;
    }
}
