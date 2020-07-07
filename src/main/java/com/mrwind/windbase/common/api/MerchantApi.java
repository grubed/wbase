package com.mrwind.windbase.common.api;

import com.alibaba.fastjson.JSONObject;
import com.mrwind.windbase.common.constant.ApiConstants;
import com.mrwind.windbase.common.util.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by CL-J on 2019/4/2.
 */
public class MerchantApi {

    /**
     * 创建店铺
     * @param teamId
     *          团队Id
     * */
    public static void createStore(String teamId) {
        System.out.println(ApiConstants.WIND_BASE_URL);
        JSONObject result = HttpMethod.post(ApiConstants.WIND_BASE_URL+"MERCHANT/merchant/store/byTeam/"+teamId, null);
        System.out.println(result.toString());
    }
}
