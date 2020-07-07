package com.mrwind.windbase.common.api.windforce;

import com.alibaba.fastjson.JSONObject;
import com.mrwind.windbase.bo.CourierMissionStatusBO;
import com.mrwind.windbase.bo.DeliveryShipmentStatusBO;
import com.mrwind.windbase.bo.ExpressIncomeBO;
import com.mrwind.windbase.bo.PickDeliverDistanceBO;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.vo.FlExpressUserDataVO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-01-26
 */

public class WindForceClientFallback implements WindForceClient {

    @Override
    public Result<DeliveryShipmentStatusBO> getUserShipmentStatus() {
        return null;
    }

    @Override
    public Result<Integer> getOrderTeamStat(String teamId, String startDay, String endDay) {
        return null;
    }

    @Override
    public Result<Map<String, Object>> getCoreMoreData() {
        return null;
    }

    @Override
    public Result<Map> getAssociatedExpress(String key, String userId) {
        return null;
    }

    @Override
    public Result<Map> pickDeliverDistance(String key, String userId, String startDate, String endDate) {
        return null;
    }

    @Override
    public Result<PickDeliverDistanceBO> getPickDeliverDistance(String key, String userId, String startDate, String endDate) {
        return null;
    }

    @Override
    public Result<FlExpressUserDataVO> getFlUserTeamData(String key, Map<String, Object> body) {
        return null;
    }

    @Override
    public Result<List> getPickDeliverOrderNumStat(String key, JSONObject body) {
        return null;
    }

    @Override
    public Result<Map<String, Object>> getAppMoreData() {
        return null;
    }

    @Override
    public Result<ExpressIncomeBO> getUserIncomeInfo(String key, String userId) {
        return null;
    }

    @Override
    public Result<List<CourierMissionStatusBO>> getCourierMissionStatus(String key, Collection<String> userIds) {
        return null;
    }

}
