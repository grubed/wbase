package com.mrwind.windbase.common.api.windforce;

import com.alibaba.fastjson.JSONObject;
import com.mrwind.windbase.bo.CourierMissionStatusBO;
import com.mrwind.windbase.bo.DeliveryShipmentStatusBO;
import com.mrwind.windbase.bo.ExpressIncomeBO;
import com.mrwind.windbase.bo.PickDeliverDistanceBO;
import com.mrwind.windbase.common.constant.ApiConstants;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.dao.mongo.FeignErrorLogDao;
import com.mrwind.windbase.entity.mongo.FeignErrorLog;
import com.mrwind.windbase.vo.FlExpressUserDataVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-01-26
 */
@Component
public class WindForceApi {

    @Resource
    private FeignErrorLogDao feignErrorLogDao;

    @Resource
    private WindForceClient windForceClient;

    /**
     * 获取用户发货、账户余额详情
     */
    public DeliveryShipmentStatusBO getUserShipmentStatus() {
        DeliveryShipmentStatusBO bo = new DeliveryShipmentStatusBO();
        try {
            Result<DeliveryShipmentStatusBO> result = windForceClient.getUserShipmentStatus();
            bo = Optional.ofNullable(result).map(Result::getData).orElse(bo);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        return bo;
    }

    /**
     * 物流分组-订单数据统计
     */
    public int getOrderTeamStat(String teamId, String startDay, String endDay) {
        int num = 0;
        try {
            Result<Integer> result = windForceClient.getOrderTeamStat(teamId, startDay, endDay);
            num = Optional.ofNullable(result).map(Result::getData).orElse(num);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        return num;
    }

    /**
     * 获取用户发货、账户余额详情
     */
    public Map<String, Object> getCoreMoreData() {
        Map<String, Object> map = new HashMap<>();
        try {
            Result<Map<String, Object>> result = windForceClient.getCoreMoreData();
            map = Optional.ofNullable(result).map(Result::getData).orElse(map);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        return map;
    }

    public Map<String, Object> getAssociatedExpress(String userId) {
        Map map = new HashMap();
        try {
            Result<Map> result = windForceClient.getAssociatedExpress(ApiConstants.EXPRESS_KEY, userId);
            map = Optional.ofNullable(result).map(Result::getData).orElse(map);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        return map;
    }

    public Map<String, Object> pickDeliverDistance(String userId, String startDate, String endDate) {
        Map map = new HashMap();
        try {
            Result<Map> result = windForceClient.pickDeliverDistance(ApiConstants.EXPRESS_KEY, userId, startDate, endDate);
            map = Optional.ofNullable(result).map(Result::getData).orElse(map);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        return map;
    }

    /**
     * 获取取件单数、取件距离、妥投单数、妥投距离和重复投递单数统计
     *
     * @param userId    用户 id
     * @param startDate 预计派送开始时间（必填），格式：yyyy-MM-dd
     * @param endDate   预计派送截止时间（必填），格式：yyyy-MM-dd
     */
    public PickDeliverDistanceBO getPickDeliverDistance(String userId, String startDate, String endDate) {
        PickDeliverDistanceBO bo = new PickDeliverDistanceBO();
        try {
            Result<PickDeliverDistanceBO> result = windForceClient.getPickDeliverDistance(ApiConstants.EXPRESS_KEY, userId, startDate, endDate);
            bo = Optional.ofNullable(result).map(Result::getData).orElse(bo);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        return bo;
    }

    public FlExpressUserDataVO getFlUserTeamData(Map<String, Object> body) {
        FlExpressUserDataVO vo = new FlExpressUserDataVO();
        try {
            Result<FlExpressUserDataVO> result = windForceClient.getFlUserTeamData(ApiConstants.EXPRESS_KEY, body);
            vo = Optional.ofNullable(result).map(Result::getData).orElse(vo);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        return vo;
    }

    /**
     * [套壳接口] 根据指定teamId或者userId获取所有人员各状态统计数据统计
     */
    public Result getPickDeliverOrderNumStat(JSONObject body) {
        List list = new ArrayList();
        try {
            Result<List> result = windForceClient.getPickDeliverOrderNumStat(ApiConstants.EXPRESS_KEY, body);
            list = Optional.ofNullable(result).map(Result::getData).orElse(list);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        return Result.getSuccess(list);
    }

    /**
     * 获取用户发货、账户余额详情
     */
    public Map<String, Object> getAppMoreData() {
        Map<String, Object> map = new HashMap<>();
        try {
            Result<Map<String, Object>> result = windForceClient.getAppMoreData();
            map = Optional.ofNullable(result).map(Result::getData).orElse(map);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        return map;
    }

    /**
     * 获取配送员收入信息
     */
    public ExpressIncomeBO getUserIncomeInfo(String userId) {
        ExpressIncomeBO bo = new ExpressIncomeBO();
        try {
            Result<ExpressIncomeBO> result = windForceClient.getUserIncomeInfo(ApiConstants.EXPRESS_KEY, userId);
            bo = Optional.ofNullable(result).map(Result::getData).orElse(bo);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        return bo;
    }

    /**
     * 获取配送员当前是否有任务
     */
    public List<CourierMissionStatusBO> getCourierMissionStatus(Collection<String> userIds) {
        List<CourierMissionStatusBO> bos = new ArrayList<>();
        try {
            Result<List<CourierMissionStatusBO>> result = windForceClient.getCourierMissionStatus(ApiConstants.EXPRESS_KEY, userIds);
            bos = Optional.ofNullable(result).map(Result::getData).orElse(bos);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        return bos;
    }

}
