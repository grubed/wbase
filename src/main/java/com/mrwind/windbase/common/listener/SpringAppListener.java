package com.mrwind.windbase.common.listener;

import com.mrwind.windbase.dao.mysql.ExpressBillingModeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Description
 *
 * @author hanjie
 */

@Component
public class SpringAppListener implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ExpressBillingModeRepository expressBillingModeRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

    }

}
