package com.mrwind.windbase.common.constant;

import com.mrwind.windbase.common.util.TextUtils;

/**
 * 其他服务访问本项目的 key
 *
 * @author hanjie
 * @date 2018/8/16
 */

public enum WindAuthKeyEnum {

    /**
     * WindBase - default 值无所谓，内部使用，主要是不能使用空字符串
     */
    WIND_BASE("default"),

    /**
     * 风力
     */
    WIND_FORCE("31040481c9dd4d936986cd214f181a61"),

    /**
     * 凡食鲜
     */
    WIND_FRESH("beb7b67b168348ddaf4cb9d9764d16c7"),

    /**
     * 考勤
     */
    WIND_ATTENDANCE("ceb0b69e168338ddcf4cb929764d16c9"),

    /**
     * 聊天
     */
    WIND_CHAT("aeb0b34c168443ddcf4cb929764d16c8"),

    /**
     * 钱包
     */
    WIND_WALLET("41040471c9dd4d096986cd214f181b87");

    private String key;

    WindAuthKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static WindAuthKeyEnum get(String key) {
        for (WindAuthKeyEnum windAuthKeyEnum : WindAuthKeyEnum.values()) {
            if (TextUtils.equals(windAuthKeyEnum.getKey(), key)) {
                return windAuthKeyEnum;
            }
        }
        return null;
    }

}
