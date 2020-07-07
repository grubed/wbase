package com.mrwind.windbase.service;

import com.mrwind.windbase.common.constant.CommonConstants;
import com.mrwind.windbase.common.util.AlgReUtil;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.entity.mongo.attendance.UserLatestLocation;
import com.mrwind.windbase.entity.mysql.Role;
import com.mrwind.windbase.entity.mysql.User;
import com.mrwind.windbase.vo.NearCourierVO;
import com.mrwind.windbase.vo.PageListVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/10/17
 */

@Service
public class WindBusinessService extends BaseService {


    /**
     * 获取附近的配送员
     *
     * @param lat     纬度
     * @param lng     经度
     * @param project project BU
     * @param page    页号，从 1 开始
     * @param limit   每页大小，等于 0 代表只获取总数，小于 0 代表获取所有，大于 0 按分页处理
     * @return 附近的配送员集合
     */
    public Result getNearCouriers(double lat, double lng, String project, int page, int limit) {

        PageListVO<NearCourierVO> pageVO = new PageListVO<>();

        // 半径 km
        double radius = 70;
        // 有效的位置时间，60分钟内
        int validLocationTime = 60;

        Role storeRole = roleRepository.findByName(CommonConstants.RoleKey.STORE_KEY);
        if (storeRole == null) {
            return Result.getFail("no store role!");
        }

        // 指定 project 下的所有配送员
        List<User> couriers = userRepository.getCourierByProject(storeRole.getRoleId(), project);
        Map<String, User> couriersInfoMap = new HashMap<>();
        for (User courier : couriers) {
            couriersInfoMap.put(courier.getUserId(), courier);
        }
        Set<String> courierUserIds = couriersInfoMap.keySet();

        List<UserLatestLocation> nearCouriersLocationInfo;

        if (limit == 0) {
            // 仅返回总数
            return Result.getSuccess(pageVO);
        } else if (limit < 0) {
            // 所有
        } else {
            // 分页
            if (page <= 0) {
                page = 1;
            }
        }

        // 用户 id 与用户名字的 map 映射


        return Result.getSuccess(pageVO);
    }

}
