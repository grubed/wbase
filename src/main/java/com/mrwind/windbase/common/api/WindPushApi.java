package com.mrwind.windbase.common.api;

import com.alibaba.fastjson.JSONObject;
import com.mrwind.windbase.common.constant.ApiConstants;
import com.mrwind.windbase.common.util.HttpMethod;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.common.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/8/29
 */

public class WindPushApi {


    /**
     * 发送推送
     */
    public static String SEND_PUSH = "windChat/pushMsg";

    /**
     * 更新 device token
     *
     * @param userId   用户 id
     * @param deviceId 设备 id
     * @return clientId for Android
     */
//    public static String updateDeviceToken(String userId, String deviceId, String appName) {
//        String url = ApiConstants.WIND_BASE_URL + UPDATE_DEVICE_TOKEN;
//        Map<String, Object> body = new HashMap<>();
//        body.put("userId", userId);
//        body.put("deviceToken", deviceId);
//        body.put("appName", appName);
//        JSONObject jsonObject = HttpMethod.post(url, JSONObject.toJSONString(body));
//        try {
//            if (TextUtils.equals(jsonObject.getString("code"), String.valueOf(Result.CODE_SUCCESS))) {
//                return jsonObject.getJSONObject("data").getString("deviceId");
//            }
//            return null;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    /**
//     * 删除 device token
//     *
//     * @param userId 用户 id
//     */
//    public static boolean delDeviceToken(String userId, String appName) {
//        String url = ApiConstants.WIND_BASE_URL + DEL_DEVICE_TOKEN;
//        Map<String, Object> body = new HashMap<>();
//        body.put("userId", userId);
//        body.put("appName", appName);
//        JSONObject jsonObject = HttpMethod.post(url, JSONObject.toJSONString(body));
//        try {
//            if (TextUtils.equals(jsonObject.getString("code"), String.valueOf(Result.CODE_SUCCESS))) {
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            return false;
//        }
//    }
//


//    public static JSONObject createDefaultGroup(String userId, String name, String avatar, ) {
//
//        String url = ApiConstants.WIND_BASE_URL + "USER/userShip/group?id=%s&name=%s&tel=%s&avatar=%s";
//
//        JSONObject jsonObject = HttpMethod.get(url);
//        return jsonObject;
//    }
}
