package com.mrwind.windbase.common.api.courier;

import com.alibaba.fastjson.JSONObject;
import com.mrwind.windbase.common.util.Result;
import org.springframework.stereotype.Component;

/**
 * Created by CL-J on 2019/3/21.
 */

@Component
public class CourierClientFallback implements CourierClient {

    @Override
    public Result newCourier(String courierId) {
        return null;
    }
}
