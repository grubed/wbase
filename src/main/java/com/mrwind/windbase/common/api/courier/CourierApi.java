package com.mrwind.windbase.common.api.courier;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by CL-J on 2019/3/21.
 */
@Component
public class CourierApi {
    @Resource
    private CourierClient courierClient;

    public void newUser(String userId) {

    }
}
