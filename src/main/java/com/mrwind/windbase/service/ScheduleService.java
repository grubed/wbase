package com.mrwind.windbase.service;

import com.mrwind.windbase.common.constant.CommonConstants;
import com.mrwind.windbase.dto.DeleteAndResignDTO;
import com.mrwind.windbase.entity.mysql.UserExtension;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by CL-J on 2018/11/26.
 */
@Component
@Service
public class ScheduleService extends BaseService {

//    /**
//     * 每天凌晨1点 检查所有配送员24小时内没有上传过位置的，关掉接单功能
//     */
//    @Scheduled(cron = "0 0 1 * * ?")
//    public void checkExpressActive() {
//        List<String> uids = userExtensionRepository.findAllByReceiveOrderStatus(2).stream().map(UserExtension::getUserId).collect(Collectors.toList());
//        List<String> ids = userLatestLocationDao.findHoursNoLocation(uids, -24).stream().map(t -> t.getUserId()).collect(Collectors.toList());
//        List<UserExtension> userExs = userExtensionRepository.findByUserIdIn(ids).stream().map(t -> {
//            t.setReceiveOrderStatus(2);
//            return t;
//        }).collect(Collectors.toList());
//        userExtensionRepository.saveAll(userExs);
//    }
//
//    //每周一晚上12点。连续一周没有上线，登出。
//    @Scheduled(cron = "0 0 0 ? * MON")
//    public void checkOffLine() {
//        List<String> ids = userLatestLocationDao.findHoursNoLocation(null, -24 * 7).stream().map(t -> t.getUserId()).collect(Collectors.toList());
//        userTokenRepository.deleteAllByUserIdIn(ids);
//    }
//
//    //每月16号晚上12点 连续2周没有登录，移出组织。
//    @Scheduled(cron = "0 0 0 16 * ?")
//    public void checkNotLogin() {
//        List<String> ids = userLatestLocationDao.findHoursNoLocation(null, -24 * 15).stream().map(t -> t.getUserId()).collect(Collectors.toList());
//        ids.forEach(t -> userTeamService.deleteAndResignMembers(new DeleteAndResignDTO(CommonConstants.MRWIND_ID, t, "reason", "")));
//    }

}
