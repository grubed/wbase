package com.mrwind.windbase.common.api.courier;

import com.alibaba.fastjson.JSONObject;
import com.mrwind.windbase.common.api.attence.AttendanceClientFallback;
import com.mrwind.windbase.common.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by CL-J on 2019/3/21.
 */

@FeignClient(name = "courier", fallback = CourierClientFallback.class)
public interface CourierClient {

    @PostMapping("/courier/config/createCourier")
    Result newCourier(String courierId);

}
