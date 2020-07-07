package com.mrwind.windbase.common.api.windforce;

import com.alibaba.fastjson.JSONObject;
import com.mrwind.windbase.bo.CourierMissionStatusBO;
import com.mrwind.windbase.bo.DeliveryShipmentStatusBO;
import com.mrwind.windbase.bo.ExpressIncomeBO;
import com.mrwind.windbase.bo.PickDeliverDistanceBO;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.vo.FlExpressUserDataVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-01-26
 */
@FeignClient(name = "deliverynew", fallback = WindForceClientFallback.class)
public interface WindForceClient {

    @GetMapping(value = "/deliverynew/bus/getuserstat")
    Result<DeliveryShipmentStatusBO> getUserShipmentStatus();

    @GetMapping("/deliverynew/bus/deliveryorderteamstat")
    Result<Integer> getOrderTeamStat(@RequestParam("teamId") String teamId,
                                     @RequestParam("startDay") String startDay,
                                     @RequestParam("endDay") String endDay);

    @GetMapping(value = "/deliverynew/core/getMoreData")
    Result<Map<String, Object>> getCoreMoreData();

    @GetMapping(value = "/deliverynew/clientserver/getassociatedexpress")
    Result<Map> getAssociatedExpress(@RequestHeader("key") String key, @RequestParam("userId") String userId);

    @GetMapping(value = "/deliverynew/windchatapp/pickdeliverdistance")
    Result<Map> pickDeliverDistance(@RequestHeader("key") String key,
                                    @RequestParam("userId") String userId,
                                    @RequestParam("startDate") String startDate,
                                    @RequestParam("endDate") String endDate);

    @GetMapping(value = "/deliverynew/windchatapp/pickdeliverdistance")
    Result<PickDeliverDistanceBO> getPickDeliverDistance(@RequestHeader("key") String key,
                                                         @RequestParam("userId") String userId,
                                                         @RequestParam("startDate") String startDate,
                                                         @RequestParam("endDate") String endDate);

    @PostMapping(value = "/deliverynew/deliverapp/usercenterstat")
    Result<FlExpressUserDataVO> getFlUserTeamData(@RequestHeader("key") String key, @RequestBody Map<String, Object> body);

    @PostMapping(value = "/deliverynew/windchatapp/pickdeliverordernumstat")
    Result<List> getPickDeliverOrderNumStat(@RequestHeader("key") String key, @RequestBody JSONObject body);

    @GetMapping(value = "/deliverynew/deliverapp/app_more_data")
    Result<Map<String, Object>> getAppMoreData();

    @GetMapping(value = "/deliverynew/windchatapp/income_data")
    Result<ExpressIncomeBO> getUserIncomeInfo(@RequestHeader("key") String key, @RequestParam("userId") String userId);

    @PostMapping(value = "/deliverynew/clientserver/getcourierishasmission")
    Result<List<CourierMissionStatusBO>> getCourierMissionStatus(@RequestHeader("key") String key, @RequestBody Collection<String> userIds);


}
