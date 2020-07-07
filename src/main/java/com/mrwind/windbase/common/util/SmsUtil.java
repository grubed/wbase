package com.mrwind.windbase.common.util;

import com.alibaba.fastjson.JSONObject;
import com.mrwind.windbase.common.constant.ApiConstants;
import com.telesign.MessagingClient;
import com.telesign.RestClient;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CL-J
 * @date 2018/8/14
 */
public class SmsUtil {

    public static String zhuanXinYunSmsSend(String tels, String content) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("appKey", ApiConstants.ZhuanXinYun.ZXY_APP_KEY));
        params.add(new BasicNameValuePair("appSecret", ApiConstants.ZhuanXinYun.ZXY_APP_SECRET));
        params.add(new BasicNameValuePair("phones", tels));
        params.add(new BasicNameValuePair("content", content));
        String resultMsg = post(ApiConstants.ZhuanXinYun.ZXY_SMS, params);
        JSONObject jsonObject = JSONObject.parseObject(resultMsg);
        return TextUtils.equals(jsonObject.getString("errorCode"), "000000") ? "success" : jsonObject.getString("errorMsg");
    }

    public static String post(String url, List<NameValuePair> pairList) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            method.setEntity(new UrlEncodedFormEntity(pairList, "UTF-8"));
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity, "UTF8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }

    public static String sendTeleSignSms(String tel, String content) {
        String messageType = "ARN";
        String result;
        try {
            MessagingClient messagingClient = new MessagingClient(
                    ApiConstants.TeleSign.CUSTOMER_ID,
                    ApiConstants.TeleSign.API_KEY,
                    ApiConstants.TeleSign.REST_END_POINT
            );
            RestClient.TelesignResponse message = messagingClient.message(tel, content, messageType, null);
            System.out.println("TeleSign: " + message.body);
            if (message.ok) {
                result = "success";
            } else {
                result = message.body;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = e.toString();
        }
        return result;
    }

}