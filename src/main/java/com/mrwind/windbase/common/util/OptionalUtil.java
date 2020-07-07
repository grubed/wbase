package com.mrwind.windbase.common.util;

import com.mrwind.windbase.common.exception.FailResultException;

import java.util.Optional;

/**
 * Created by CL-J on 2018/8/7.
 */
public class OptionalUtil {

    public static <T> T checkOptionalAndThrew(T t, String i18Msg) {
        Optional.ofNullable(t).orElseThrow(() -> FailResultException.getI18N(i18Msg));
        return t;
    }

}
