package com.mrwind.windbase.service;

import com.mrwind.windbase.common.constant.CommonConstants;
import com.mrwind.windbase.common.constant.WindForceProjectEnum;
import com.mrwind.windbase.common.constant.WindForceProjectTypeEnum;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.common.util.ServletUtil;
import com.mrwind.windbase.entity.mongo.WindforceProject;
import com.mrwind.windbase.entity.mysql.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by michelshout on 2018/8/6.
 */
@Service
public class StatService extends BaseService {

    /**
     *获取取件单数、取件距离、妥投单数、妥投距离和重复投递单数统计
     * @param token                 令牌
     * @param project               项目/城市
     * @param startDate             统计开始时间
     * @param endDate               统计结束时间
     * @return
     */
    public Result pickDeliverDistance(String token,String project,String startDate,String endDate){
        if(StringUtils.isBlank(project) ||
                !WindForceProjectEnum.hasEnumDesc(project)){
            return Result.getFailI18N("error.parameter");
        }

        User user = (User) ServletUtil.getCurrentRequest().getAttribute(CommonConstants.USER_KEY);
        if(user == null || StringUtils.isBlank(user.getUserId())){
            return Result.getFailI18N("error.token.auth");
        }
        String userId = user.getUserId();
        Map map = windForceApi.pickDeliverDistance(userId,startDate,endDate);
        if(map != null) {
//            System.out.println(JSON.toJSONString(map));
            return Result.getSuccess(map);
        }else{
            return Result.getFailI18N("error.operation.failed");
        }
    }
}
