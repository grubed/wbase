package com.mrwind.windbase.common.aspect;

import com.alibaba.fastjson.JSON;
import com.mrwind.windbase.common.constant.CommonConstants;
import com.mrwind.windbase.common.exception.FailResultException;
import com.mrwind.windbase.common.threadpool.APIThreadPool;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.common.util.ServletUtil;
import com.mrwind.windbase.dao.mongo.UserActionLogDao;
import com.mrwind.windbase.entity.mongo.UserActionLog;
import com.mrwind.windbase.service.SmsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;

/**
 * 请求日志与异常处理切面
 *
 * @author wuyiming
 * Created by wuyiming on 2018/4/27.
 */
@Component
@Aspect
@Order(0)
public class LogAndExceptionHandlerAspect {

    private Log logger = LogFactory.getLog(this.getClass());

    @Resource
    private UserActionLogDao userActionLogDao;

    @Pointcut("execution(public * com.mrwind.windbase.controller..*.*(..))")
    public void requestPointCut() {

    }

    @Around(value = "requestPointCut()")
    public Object doLogAndExceptionHandle(ProceedingJoinPoint joinPoint) {
        Object result;
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        StringBuilder paramBuilder = new StringBuilder();
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            if (joinPoint.getArgs()[i] != null) {
                String name = joinPoint.getArgs()[i].getClass().getName();
                if (name.contains("org.apache.catalina.connector")
                        || "org.springframework.web.multipart.commons.CommonsMultipartFile".equals(name)
                        || name.contains("org.springframework.validation")) {
                    continue;
                }
                paramBuilder.append(JSON.toJSONString(joinPoint.getArgs()[i]));
            }
        }
        long startTimeMillis = System.currentTimeMillis();
        UserActionLog userActionLog = new UserActionLog();
        userActionLog.setFunctionName(methodName);
        userActionLog.setControllerName(className);
        userActionLog.setParamentValue(paramBuilder.toString());
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
            if (throwable instanceof FailResultException) {
                result = Result.getFail(throwable.getMessage());
            } else {
                result = Result.getFail(throwable.toString());
            }
        }
        userActionLog.setSpendTime(System.currentTimeMillis() - startTimeMillis);
        HttpServletRequest currentRequest = ServletUtil.getCurrentRequest();
        Enumeration<String> headerNames = currentRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String s = headerNames.nextElement();
            userActionLog.getHeaders().add(s + ": " + currentRequest.getHeader(s));
        }
        userActionLog.setResponseValue(JSON.toJSONString(result));
        userActionLog.setCreateTime(new Date());
        Runnable runnable = () -> {
            if (logger.isDebugEnabled()) {
                logger.debug(userActionLog);
            }
            userActionLogDao.save(userActionLog);
        };
        APIThreadPool.getNewCachedThreadPool().execute(runnable);
        return result;
    }

}
