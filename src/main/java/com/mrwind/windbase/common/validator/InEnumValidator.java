package com.mrwind.windbase.common.validator;

import com.mrwind.windbase.common.annotation.InEnum;
import com.mrwind.windbase.common.util.TextUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;

/**
 * 枚举类型验证器
 *
 * @author hanjie
 */

public class InEnumValidator implements ConstraintValidator<InEnum, String> {

    private Class<?>[] cls;

    private boolean required;

    @Override
    public void initialize(InEnum constraintAnnotation) {
        cls = constraintAnnotation.target();
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        // required: 必须有值 && 值必须要对
        // !required: 可以没值, 但是有值的话值必须要对
        return !TextUtils.isEmpty(value) ? checkValue(value) : !required;
    }

    private boolean checkValue(String value) {
        if (cls.length > 0) {
            for (Class<?> cl : cls) {
                try {
                    if (cl.isEnum()) {
                        //枚举类验证
                        Object[] objs = cl.getEnumConstants();
                        Method method = cl.getMethod("getValue");
                        for (Object obj : objs) {
                            Object code = method.invoke(obj);
                            if (value.equals(code.toString())) {
                                return true;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            return true;
        }
        return false;
    }

}
