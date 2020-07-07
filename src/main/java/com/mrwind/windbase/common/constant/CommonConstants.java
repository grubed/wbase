package com.mrwind.windbase.common.constant;

/**
 * @author wuyiming
 * Created by wuyiming on 2018/7/19.
 */
public class CommonConstants {

    /**
     * 开发者手机号 用户接收崩溃信息
     */
    public final static String DEV_ZHANG = "18557538621";

    /**
     * request中提取user信息的key
     */
    public final static String USER_KEY = "user_key";

    /**
     * request中提取user的根团队
     */
    public final static String USER_ROOT_TEAM = "user_root_team";

    /**
     * request中提取其他项目的来源值
     */
    public final static String AUTH_FROM = "auth_from";

    /**
     * 风力标签类型Id
     */
    public final static String FENGLI_TYPE_ID = "ff8080816522ea4801652356b8840000";

    /**
     * 风力仓库标签Id
     */
    public final static String FENGLI_ID = "ff808081650a313c01650a3abd1c0004";

    /**
     * 客服标签类型Id
     */
    public final static String SERVICE_TYPE_ID = "ff8080816522ea48016523572bd70001";

    /**
     * 客服标签Id
     */
    public final static String SERVICE_ID = "ff8080816522ea4801652359db7f0003";

    /**
     * 风先生根团队id
     * */
    public final static String MRWIND_ID = "5afd5817cd0bd80ecf11e0dc";

    public static final String WIND_FORCE_GRAB_ORDER_PUSH = "grab_order_push";

    public static class Redis {

        /**
         * 项目在 redis 中的前缀
         */
        private static final String PREFIX = "wb_windBase_";

        /**
         * 在redis中保存的每个team的唯一的自增流水号
         */
        public final static String TEAM_KEY = PREFIX + "team_serial_number";

        /**
         * 登录验证码保存在 redis 的键前缀
         */
        public static final String LOGIN_CODE = PREFIX + "loginCode_";

        /**
         * 修改手机号码验证码保存在 redis 的键前缀
         */
        public static final String CHANGE_TEL_CODE = PREFIX + "changeTelCode_";

        /**
         * 修改密码验证码保存在 redis 的键前缀
         */
        public static final String CHANGE_PASSWORD_CODE = PREFIX + "changePasswordCode_";

        /**
         * RSA 加密用的公钥和私钥存储在 redis 中的 key
         */
        public static final String RSA_PUBLIC = PREFIX + "rsa_public_key_2";
        public static final String RSA_PRIVATE = PREFIX + "rsa_private_key_2";

        /**
         * 用户国家区号对应关系
         */
        public static final String USER_LANGUAGE = PREFIX + "user_language";

    }

    public static class RoleKey {

        public static final String SERVICE_KEY = "service";

        public static final String STORE_KEY = "store";

    }

    public final static int Alipay = 1;
    public final static int Wxpay = 2;

    public static class AccountLogType {

        public static int recharge = 1;
        public static int income = 2;
        public static int consume = 3;
        public static int derate = 4;

    }
}



