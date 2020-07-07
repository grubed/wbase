package com.mrwind.windbase.common.config;

import com.mrwind.windbase.common.interceptor.LocaleInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author wuyiming
 * Created by wuyiming on 2018/7/19.
 */
@Configuration
public class SpringMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 拦截器加载
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleInterceptor()).addPathPatterns("/**");
    }

    /**
     * 处理跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

}
