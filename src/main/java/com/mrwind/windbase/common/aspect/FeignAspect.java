package com.mrwind.windbase.common.aspect;


import com.mrwind.windbase.common.util.ServletUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class FeignAspect implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            HttpServletRequest request = ServletUtil.getCurrentRequest();
            requestTemplate.header("Authorization", request.getHeader(HttpHeaders.AUTHORIZATION));
            requestTemplate.header("wind-position", request.getHeader("wind-position"));
            requestTemplate.header("wind-address", request.getHeader("wind-address"));
        } catch (Exception e) {

        }

    }

}