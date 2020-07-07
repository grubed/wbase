package com.mrwind.windbase.service;

import com.mrwind.windbase.common.constant.CommonConstants;
import com.mrwind.windbase.common.util.CollectionUtil;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.common.util.ServletUtil;
import com.mrwind.windbase.common.util.TextUtils;
import com.mrwind.windbase.dto.CourierSettingsDTO;
import com.mrwind.windbase.entity.mongo.CourierSettings;
import com.mrwind.windbase.entity.mongo.CourierSettingsLog;
import com.mrwind.windbase.entity.mongo.GpsInfo;
import com.mrwind.windbase.entity.mysql.*;
import com.mrwind.windbase.vo.CourierSettingsVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/11/20
 */

@Service
public class BusService extends BaseService {

    /**
     * 更新配送员相关配置
     */
    public void updateCourierSettings(CourierSettingsDTO dto) {
        User currentUser = getCurrentUser();
        updateCourierSettingsById(currentUser.getUserId(), dto);
    }

    /**
     * 更新配送员相关配置 by id
     */
    public void updateCourierSettingsById(String userId, CourierSettingsDTO dto) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return;
        }
        Date currentDate = new Date();
        CourierSettings settings = courierSettingsDao.findByUserId(userId);
        if (settings == null) {
            settings = new CourierSettings();
            settings.setUserId(userId);
            settings.setCreateTime(currentDate);
        }
        settings.setUpdateTime(currentDate);
        // 姓名
        if (!TextUtils.isEmpty(dto.getUserName())) {
            user.setName(dto.getUserName());
        }
        // 头像
        if (dto.getUserAvatar() != null) {
            user.setAvatar(dto.getUserAvatar());
            userRepository.save(user);
        }

        // 回车住址
        settings.setHomeAddress(Optional.ofNullable(dto.getHomeAddress()).orElse(settings.getHomeAddress()));
        GpsInfo homeGps = settings.getHomeGps();
        if (homeGps == null) {
            homeGps = new GpsInfo();
            settings.setHomeGps(homeGps);
        }
        homeGps.setLat(Optional.ofNullable(dto.getHomeLat()).orElse(homeGps.getLat()));
        homeGps.setLng(Optional.ofNullable(dto.getHomeLng()).orElse(homeGps.getLng()));

        // 接单设置
        CourierSettings.Receive rs = settings.getReceive();
        if (rs == null) {
            rs = new CourierSettings.Receive();
            settings.setReceive(rs);
        }
        if (dto.getReceiveOrder() != null) {
            // 转到以前设置接单状态的接口方法
            userService.updateReceiveOrderStatusById(userId, dto.getReceiveOrder(), false);
        }
        rs.setAutoReceiveOrder(Optional.ofNullable(dto.getAutoReceiveOrder()).orElse(rs.getAutoReceiveOrder()));
        rs.setOrderLimit(Optional.ofNullable(dto.getOrderLimit()).orElse(rs.getOrderLimit()));
        rs.setLowerBound(Optional.ofNullable(dto.getLowerBound()).orElse(rs.getLowerBound()));
        rs.setCarryNum(Optional.ofNullable(dto.getCarryNum()).orElse(rs.getCarryNum()));
        rs.setAutoStartTime(Optional.ofNullable(dto.getAutoStartTime()).orElse(rs.getAutoStartTime()));
        rs.setAutoEndTime(Optional.ofNullable(dto.getAutoEndTime()).orElse(rs.getAutoEndTime()));

        // 配送设置
        CourierSettings.Express es = settings.getExpress();
        if (es == null) {
            es = new CourierSettings.Express();
            settings.setExpress(es);
        }
        es.setBillingModeId(Optional.ofNullable(dto.getBillingModeId()).orElse(es.getBillingModeId()));
        es.setMinDistance(Optional.ofNullable(dto.getMinDistance()).orElse(es.getMinDistance()));
        es.setMaxDistance(Optional.ofNullable(dto.getMaxDistance()).orElse(es.getMaxDistance()));
        es.setCarLoad(Optional.ofNullable(dto.getCarLoad()).orElse(es.getCarLoad()));
        es.setCarType(Optional.ofNullable(dto.getCarType()).orElse(es.getCarType()));
        es.setCarCubage(Optional.ofNullable(dto.getCarCubage()).orElse(es.getCarCubage()));
        es.setExpectRestTime(Optional.ofNullable(dto.getExpectRestTime()).orElse(es.getExpectRestTime()));

        // 配送地区
        settings.setDeliverAreaAddress(Optional.ofNullable(dto.getDeliverAreaAddress()).orElse(settings.getDeliverAreaAddress()));
        GpsInfo deliverAreaGps = settings.getDeliverAreaGps();
        if (deliverAreaGps == null) {
            deliverAreaGps = new GpsInfo();
            settings.setDeliverAreaGps(deliverAreaGps);
        }
        deliverAreaGps.setLat(Optional.ofNullable(dto.getDeliverAreaLat()).orElse(deliverAreaGps.getLat()));
        deliverAreaGps.setLng(Optional.ofNullable(dto.getDeliverAreaLng()).orElse(deliverAreaGps.getLng()));

        // 其他配置
        settings.setAge(Optional.ofNullable(dto.getAge()).orElse(settings.getAge()));
        settings.setIdCardNo(Optional.ofNullable(dto.getIdCardNo()).orElse(settings.getIdCardNo()));
        settings.setFrontIdCardImg(Optional.ofNullable(dto.getFrontIdCardImg()).orElse(settings.getFrontIdCardImg()));
        settings.setBackIdCardImg(Optional.ofNullable(dto.getBackIdCardImg()).orElse(settings.getBackIdCardImg()));
        settings.setCarNo(Optional.ofNullable(dto.getCarNo()).orElse(settings.getCarNo()));
        settings.setCarImg(Optional.ofNullable(dto.getCarImg()).orElse(settings.getCarImg()));
        settings.setDriverLicenseImg(Optional.ofNullable(dto.getDriverLicenseImg()).orElse(settings.getDriverLicenseImg()));

        // 记录日志
        CourierSettingsLog log = new CourierSettingsLog(getCurrentUser().getUserId(), userId);
        log.setSettings(dto);
        courierSettingsLogDao.save(log);

        courierSettingsDao.save(settings);
    }

    /**
     * 获取当前用户配送相关设置
     */
    public CourierSettingsVO getCourierSettings() {
        User user = getCurrentUser();
        return getCourierSettingsById(user.getUserId());
    }

    /**
     * 根据用户 id 获取其相关配送设置
     */
    public CourierSettingsVO getCourierSettingsById(String userId) {
        CourierSettings settings = courierSettingsDao.findByUserId(userId);
        CourierSettingsVO vo = new CourierSettingsVO(userId);
        vo.setReceiveOrder(userService.getReceiveOrderStatusById(userId));
        if (settings != null) {

            if (settings.getReceive() != null) {
                vo.setAutoReceiveOrder(settings.getReceive().getAutoReceiveOrder());
                vo.setOrderLimit(settings.getReceive().getOrderLimit());
                vo.setLowerBound(settings.getReceive().getLowerBound());
                vo.setCarryNum(settings.getReceive().getCarryNum());
                vo.setAutoStartTime(settings.getReceive().getAutoStartTime());
                vo.setAutoEndTime(settings.getReceive().getAutoEndTime());
            }
            if (settings.getExpress() != null) {
                vo.setBillingModeId(settings.getExpress().getBillingModeId());
                vo.setMinDistance(settings.getExpress().getMinDistance());
                vo.setMaxDistance(settings.getExpress().getMaxDistance());
                vo.setCarLoad(settings.getExpress().getCarLoad());
                vo.setCarType(settings.getExpress().getCarType());
                vo.setCarCubage(settings.getExpress().getCarCubage());
                vo.setExpectRestTime(settings.getExpress().getExpectRestTime());
            }
            vo.setHomeAddress(settings.getHomeAddress());
            if (settings.getHomeGps() != null) {
                vo.setHomeLat(settings.getHomeGps().getLat());
                vo.setHomeLng(settings.getHomeGps().getLng());
            }
            vo.setAge(settings.getAge());
            vo.setIdCardNo(settings.getIdCardNo());
            vo.setFrontIdCardImg(settings.getFrontIdCardImg());
            vo.setBackIdCardImg(settings.getBackIdCardImg());
            vo.setCarNo(settings.getCarNo());
            vo.setCarImg(settings.getCarImg());
            vo.setDriverLicenseImg(settings.getDriverLicenseImg());
            vo.setDeliverAreaAddress(settings.getDeliverAreaAddress());
            if (settings.getDeliverAreaGps() != null) {
                vo.setDeliverAreaLat(settings.getDeliverAreaGps().getLat());
                vo.setDeliverAreaLng(settings.getDeliverAreaGps().getLng());
            }
        }
        User user = userRepository.findByUserId(userId);
        if (user != null) {
            vo.setUserName(user.getName());
            vo.setUserAvatar(user.getAvatar());
            vo.setTel(user.getTel());
        }
        Team currentTeam = getOnlyTeam(userId, Optional.ofNullable(getCurrentUser()).map(User::getCurrentTeamId).orElse(CommonConstants.MRWIND_ID));
        if (currentTeam != null) {
            vo.setCurrentTeamId(currentTeam.getTeamId());
            vo.setCurrentTeamName(currentTeam.getName());
            TeamExtention te = teamExtentionRepository.findByTeamId(currentTeam.getTeamId());
            if (te != null) {
                vo.setExpressStartTime(te.getExpressStartTime());
                vo.setExpressEndTime(te.getExpressEndTime());
            }
        }
        return vo;
    }

    /**
     * 每次打开风信需要获取用户的一些状态值
     */
    public Result getUserAppStatus() {
        User me = getCurrentUser();
        Map<String, Object> statusMap = new HashMap<>();

        // 是否为配送员
        statusMap.put("isCourier", userService.isCourier(me.getUserId(), me.getCurrentTeamId()));

        CourierSettingsVO courierSettings = busService.getCourierSettingsById(me.getUserId());

        // 接单状态
        statusMap.put("receiveOrder", courierSettings.getReceiveOrder());

        return Result.getSuccess(statusMap);
    }

    /**
     * 获取所有配送员可用的计费规则
     */
    public Result getAllExpressBillingMode() {
        return Result.getSuccess(expressBillingModeRepository.findAll());
    }

    /**
     * 获取配送员的计费规则（自己没有，不向上从团队找）
     */
    public ExpressBillingMode getUserExpressBillingModeWithNotFromTeam(String userId) {
        CourierSettingsVO courierSettings = getCourierSettingsById(userId);
        String userBillingId = Optional.ofNullable(courierSettings).map(CourierSettingsVO::getBillingModeId).orElse(null);
        if (StringUtils.isBlank(userBillingId)) {
            return null;
        } else {
            return expressBillingModeRepository.findById(userBillingId).orElse(null);
        }
    }

    /**
     * [向上团队寻找] 获取配送员的计费规则（自己没有，向上从团队找）(默认风先生团队)
     */
    public ExpressBillingMode getUserExpressBillingModeWithFromTeam(String userId) {
        ExpressBillingMode mode = getUserExpressBillingModeWithNotFromTeam(userId);
        if (mode != null) {
            return mode;
        }
        // 自己没有，从团队向上寻找
        // 用户自己没有考勤信息，向上寻找其团队设置的考勤信息
        Team onlyTeam = getOnlyTeam(userId, CommonConstants.MRWIND_ID);
        if (onlyTeam == null) {
            return null;
        }

        String[] teamNos = onlyTeam.getParentIds().split(",");

        // 所在团队到根团队经过的所有团队
        List<Team> lineTeams = teamRepository.findByTeamIdNoIn(Arrays.stream(teamNos).map(Long::valueOf).collect(Collectors.toList()));
        // teamIdNo 和 team 映射
        Map<String, Team> teamIdNoMap = new HashMap<>();
        for (Team team : lineTeams) {
            teamIdNoMap.put(String.valueOf(team.getTeamIdNo()), team);
        }
        List<String> lineTeamIds = lineTeams.stream().map(Team::getTeamId).collect(Collectors.toList());
        // 库中所有计费规则 id 和规则的映射
        Map<String, ExpressBillingMode> allModeIdMap = CollectionUtil.takeFieldsToMap(expressBillingModeRepository.findAll(), "id");

        Map<String, TeamExtention> lineTeamIdExtentionMap = CollectionUtil.takeFieldsToMap(teamExtentionRepository.findByTeamIdIn(lineTeamIds), "teamId");

        for (int i = teamNos.length - 1; i >= 0; i--) {
            String teamNo = teamNos[i];
            String tid = Optional.ofNullable(teamIdNoMap.get(teamNo)).map(Team::getTeamId).orElse(null);
            if (tid == null) {
                continue;
            }
            String expressBillingModeId = Optional.ofNullable(lineTeamIdExtentionMap.get(tid)).map(TeamExtention::getExpressBillingModeId).orElse(null);
            if (expressBillingModeId == null) {
                continue;
            }
            ExpressBillingMode expressBillingMode = Optional.ofNullable(allModeIdMap.get(expressBillingModeId)).orElse(null);
            if (expressBillingMode != null) {
                return expressBillingMode;
            }
        }
        return null;
    }

    /**
     * App 更多页数据
     */
    public Result getAppMoreData(String project) {
        User me = getCurrentUser();
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> dispatchMoreDataMap = windForceApi.getCoreMoreData();
        Map<String, Object> expressMoreDataMap = windForceApi.getAppMoreData();
        // 可调度运单
        result.put("canDispatchNum", Optional.ofNullable(expressMoreDataMap.get("canDispatchNum")).orElse(0));
        // 优先服务客户
        result.put("priorityClientNum", Optional.ofNullable(dispatchMoreDataMap.get("merchantRelationNumber")).orElse(0));
        // 今日下单量
        result.put("todayOrderNum", Optional.ofNullable(expressMoreDataMap.get("todayOrderNum")).orElse(0));
        // 订单库
        result.put("allOrderNum", Optional.ofNullable(expressMoreDataMap.get("allOrderNum")).orElse(0));
        // 系统日志
        result.put("systemLogNum", Optional.ofNullable(dispatchMoreDataMap.get("logNumber")).orElse(0));
        // 团队人数&团队名
        Team team = getOnlyTeam(me.getUserId(), me.getCurrentTeamId());
        int teamMemberCount = 0;
        String teamName = null;
        if (team != null) {
            teamMemberCount = teamService.getTeamUserNum(team.getTeamId());
            teamName = team.getName();
        }
        result.put("teamMemberNum", teamMemberCount);
        result.put("teamName", teamName);
        // 资金
        Map<String, Object> accountMap = accountService.getAccountBalanceAndCount(me.getUserId());
        result.put("totalIncome", accountMap.get("amount"));
        result.put("incomeRecordNum", accountMap.get("count"));
        // 接单状态
        CourierSettingsVO courierSettings = busService.getCourierSettingsById(me.getUserId());
        result.put("receiveOrder", courierSettings.getReceiveOrder());
        // 是否为快递员
        result.put("isCourier", userService.isCourier(me.getUserId(), me.getCurrentTeamId()));
        return Result.getSuccess(result);
    }

    /**
     * 获取账户余额
     *
     * @return
     */
    public Result getUserAccountBalance() {
        Map<String, Object> result = new HashMap<>();
        User me = getCurrentUser();
        String token = ServletUtil.getCurrentRequest().getHeader(HttpHeaders.AUTHORIZATION);
        Map<String, Object> accountMap = accountService.getAccountBalanceAndCount(me.getUserId());
        result.put("totalIncome", accountMap.get("amount"));
        return Result.getSuccess(result);
    }

    /**
     * 获取配送员的接单状态映射
     */
    public Map<String, Integer> getCourierReceiveOrderStatus(Collection<String> courierIds) {
        List<UserExtension> ues = userExtensionRepository.findByUserIdIn(courierIds);
        Map<String, UserExtension> map = CollectionUtil.takeFieldsToMap(ues, "userId");
        Map<String, Integer> result = new HashMap<>();
        for (String courierId : courierIds) {
            result.put(courierId, Optional.ofNullable(map.get(courierId)).map(UserExtension::getReceiveOrderStatus).orElse(null));
        }
        return result;
    }

}
