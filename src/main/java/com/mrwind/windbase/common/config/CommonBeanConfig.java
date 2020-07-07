package com.mrwind.windbase.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author wuyiming
 * Created by wuyiming on 2018/7/20.
 */
@Configuration
public class CommonBeanConfig {

    /**
     * 国际化信息源
     *
     * @return
     */
    @Bean("messageSource")
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("message");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
//
//    @Bean("windChatApi")
//    public WindChatApi windChatApi() {
//        return new WindChatApi();
//    }
//
//    @Bean("messageHelperConfig")
//    public MessageHelperConfig messageHelperConfig() {
//        return new MessageHelperConfig();
//    }

}
