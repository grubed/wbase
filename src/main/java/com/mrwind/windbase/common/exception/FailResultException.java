package com.mrwind.windbase.common.exception;

import com.mrwind.windbase.common.util.LocaleType;

/**
 * Result 返回异常
 *
 * @author hanjie
 */

public class FailResultException extends RuntimeException {

    private FailResultException(String message) {
        super(message);
    }

    public static FailResultException get(String errorMsg) {
        return new FailResultException(errorMsg);
    }

    public static FailResultException getI18N(String msgKey) {
        return new FailResultException(LocaleType.getMessage(msgKey));
    }

}
