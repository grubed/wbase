package com.mrwind.windbase.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Description
 *
 * @author hanjie
 */
@Configuration
public class ApiConstants {

    /**
     * 本项目域名
     */
    public static String WIND_BASE_URL;

    /**
     * DELIVERY 项目域名，key 为 team 表中的 project 区分地区，值为域名
     */
    public static Map<String, String> DELIVERY_BASE_URL_MAP;

    /**
     * DISPATCH 项目域名，key 为 team 表中的 project 区分地区，值为域名
     */
    public static Map<String, String> DISPATCH_BASE_URL_MAP;

    /**
     * DELIVERY 项目请求的 key header 值
     */
    public static String DELIVER_KEY;

    /**
     * EXPRESS 项目域名，key 为 team 表中的 project 区分地区，值为域名
     */
    public static Map<String, String> EXPRESS_BASE_URL_MAP;

    /**
     * EXPRESS 项目请求的 key header 值
     */
    public static String EXPRESS_KEY;

    /**
     * 是否开启短信
     */
    public static boolean ENABLE_SMS = true;

//    @Resource
//    private MessageHelperConfig messageHelperConfig;

    @Value("${mrwind.enable_sms}")
    public void setEnableSms(boolean enableSms) {
        ENABLE_SMS = enableSms;
    }

    /**
     * 注入域名
     */
    @Value("${mrwind.baseUrl.wind}")
    public void setBaseUrl(String baseUrl) {
        WIND_BASE_URL = baseUrl;
        // 初始化风信聊天&推送包 url
//        messageHelperConfig.setWindBaseUrl(baseUrl);
    }

    @Value("${mrwind.baseUrl.delivery}")
    public void setDeliveryBaseUrlMap(String urlMap) {
        DELIVERY_BASE_URL_MAP = new HashMap<>();
        for (String s : urlMap.split(",")) {
            DELIVERY_BASE_URL_MAP.put(s.split("&")[0], s.split("&")[1]);
        }
    }

    @Value("${mrwind.key.delivery}")
    public void setDeliverKey(String deliverKey) {
        DELIVER_KEY = deliverKey;
    }

    @Value("${mrwind.baseUrl.express}")
    public void setExpressBaseUrlMap(String urlMap) {
        EXPRESS_BASE_URL_MAP = new HashMap<>();
        for (String s : urlMap.split(",")) {
            EXPRESS_BASE_URL_MAP.put(s.split("&")[0], s.split("&")[1]);
        }
    }

    @Value("${mrwind.key.express}")
    public void setExpressKey(String expressKey) {
        EXPRESS_KEY = expressKey;
    }

    @Value("${mrwind.baseUrl.dispatch}")
    public void setDispatchBaseUrlMap(String urlMap) {
        DISPATCH_BASE_URL_MAP = new HashMap<>();
        for (String s : urlMap.split(",")) {
            DISPATCH_BASE_URL_MAP.put(s.split("&")[0], s.split("&")[1]);
        }
    }

    public static class LuoSiMao {

        /**
         * 发送验证码
         * Tips 批量发送URL http://sms-api.luosimao.com/v1/send_batch.json 参数mobile改为mobile_list
         */
        public static final String SEND_SMS = "http://sms-api.luosimao.com/v1/send.json";

    }

    public static class ZhuanXinYun {

        public static final String ZXY_SMS = "https://api.zhuanxinyun.com/api/v2/sendSms.json";

        public static final String ZXY_APP_KEY = "a3UZJZntGlQTjXw5JQsTYBvMua9OuKpb";

        public static final String ZXY_APP_SECRET = "8254b69eb2f79bc4b2655a971a547b506744";

    }

    public static class DaSanTong {

        public static final String DST_URL = "http://voice.3tong" +
                ".net/json/voiceSms/SubmitVoc";

        public static final String DST_ACCOUNT =
                "dh92351";

        public static final String DST_PASSWORD =
                "9babb7b9c5d694c46864880781cb5136";

    }

    public static class TeleSign {

        public static final String API_KEY = "q5qUcdG59aMd6CPamdXU1JmznIyToGp1J3JJEra1Rd9htMXfeivinGAUMLTC9B5c51el5QW9IRwd6upT1gW5FQ==";
        public static final String CUSTOMER_ID = "2A354614-EA23-4062-B5AE-FCAA87C0A3D7";
        public static final String REST_END_POINT = "https://rest-api.telesign.com";

    }


}
