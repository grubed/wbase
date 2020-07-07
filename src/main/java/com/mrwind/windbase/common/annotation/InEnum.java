package com.mrwind.windbase.common.annotation;

import com.mrwind.windbase.common.validator.InEnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 自定义枚举注解类型校验
 * ps: 注解需要有 getValue() 方法
 *
 * @author hanjie
 */

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {InEnumValidator.class})
public @interface InEnum {

    String message() default "";

    boolean required() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?>[] target() default {};

}
