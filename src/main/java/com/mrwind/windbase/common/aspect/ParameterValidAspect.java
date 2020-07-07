package com.mrwind.windbase.common.aspect;

import com.mrwind.windbase.common.util.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

/**
 * 参数校验切面
 *
 * @author hanjie
 */

@Component
@Aspect
@Order(2)
public class ParameterValidAspect {

    @Pointcut("execution(public * com.mrwind.windbase.controller..*.*(..))")
    public void validPointCut() {

    }

    @Around("validPointCut()")
    public Object doValid(ProceedingJoinPoint joinPoint) throws Throwable {
        // requestBody 参数校验
        for (Object o : joinPoint.getArgs()) {
            if (o instanceof BindingResult) {
                BindingResult result = (BindingResult) o;
                if (result.hasErrors()) {
                    return Result.getFailI18N(result.getFieldError().getDefaultMessage());
                }
            }
        }
        return joinPoint.proceed();
    }

}
