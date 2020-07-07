package com.mrwind.windbase.common.util;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/8/2
 */

public class VerCodeUtil {

    /**
     * 验证码获取间隔（毫秒）,客户端限制 60 秒，服务器限制 50 秒
     */
    private static final int VER_CODE_REQUEST_INTERVAL = 50 * 1000;

    /**
     * 分隔符
     */
    private static final String SEPARATOR = "_";

    /**
     * 生成存储在 Redis 中的验证码值（携带时间戳）
     *
     * @param value 值
     * @return result
     */
    public static String generateRedisValue(String value) {
        return value + SEPARATOR + System.currentTimeMillis();
    }

    /**
     * 判断获取验证码是否过于频繁
     *
     * @param value redis 中的值（验证码_时间戳）
     * @return result
     */
    public static boolean isFrequently(String value) {
        if (TextUtils.isEmpty(value)) {
            return false;
        }
        // 判断两次获取的时间间隔
        long interval;
        if (value.contains(SEPARATOR)) {
            long lastSendTimeMills = Long.parseLong(value.split(SEPARATOR)[1]);
            interval = System.currentTimeMillis() - lastSendTimeMills;
        } else {
            interval = VER_CODE_REQUEST_INTERVAL;
        }
        return interval < VER_CODE_REQUEST_INTERVAL;
    }

    /**
     * 提取验证码
     *
     * @param value redis 中的值（验证码_时间戳）
     * @return result
     */
    public static String extractValue(String value) {
        if (TextUtils.isEmpty(value)) {
            return null;
        }
        if (value.contains(SEPARATOR)) {
            return value.split(SEPARATOR)[0];
        } else {
            return value;
        }
    }

}
