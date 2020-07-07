package com.mrwind.windbase.common.annotation;

import java.lang.annotation.*;

/**
 * 校验：
 * header 中必须携带 key 字段
 *
 * @author hanjie
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WindAuthorization {
}
