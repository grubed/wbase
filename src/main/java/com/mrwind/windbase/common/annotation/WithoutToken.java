package com.mrwind.windbase.common.annotation;

import java.lang.annotation.*;

/**
 * CL-J
 * Created on 2019/4/18.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WithoutToken {

}
