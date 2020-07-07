package com.mrwind.windbase.common.util;

import com.mrwind.windbase.common.context.SpringContext;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.NamedThreadLocal;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author wuyiming
 * Created by wuyiming on 2017/10/24.
 */
public class LocaleType {

    private static MessageSource messageSource;

    private static ThreadLocal<Locale> threadLocal = new NamedThreadLocal<>("locale_threadLocal");

    public static void setLocale(Locale locale) {
        threadLocal.set(locale);
    }

    public static String getMessage(String key) {
        return getMessage(key, threadLocal.get());
    }

    public static String getMessage(String key, Locale locale) {
        if (messageSource == null) {
            messageSource = (MessageSource) SpringContext.getBean("messageSource");
        }
        try {
            return messageSource.getMessage(key, null, locale);
        } catch (NoSuchMessageException e) {
            return key;
        }
    }

    public static Map<String, String> getMessageMap(String key, Object... args) {
        Map<String, String> messageMap = new HashMap<>();
        messageMap.put("chinese", String.format(getMessage(key, Locale.CHINA), args));
//        messageMap.put(LanguageType.ENGLISH, String.format(getMessage(key, Locale.ENGLISH), args));
        return messageMap;
    }

    public static Locale getLocale() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
