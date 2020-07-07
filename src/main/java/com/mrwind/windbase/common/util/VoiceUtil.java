package com.mrwind.windbase.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mrwind.windbase.common.constant.ApiConstants;
import com.mrwind.windbase.entity.mongo.VoiceLog;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;

public class VoiceUtil {

    public static JSONObject DaSanTongSend(List<String> tels, String content) {
        Map<String,Object> params = new HashMap<String, Object>();
        ArrayList<VoiceDataMessage> voiceDataMessageList = new
                ArrayList<VoiceDataMessage>();
        tels.forEach(tel -> {
            VoiceDataMessage voiceDataMessage = new VoiceDataMessage();
            voiceDataMessage.setCallee(tel);
            voiceDataMessage.setPlaymode(0);
            voiceDataMessage.setCalltype(0);
            voiceDataMessage.setText(content);
            voiceDataMessage.setMsgid(CommUtil.getNonce_str());

            ((List) voiceDataMessageList).add(voiceDataMessage);
        });

        params.put("account",ApiConstants.DaSanTong.DST_ACCOUNT);
        params.put("password",ApiConstants.DaSanTong.DST_PASSWORD);
        params.put("data",voiceDataMessageList);
        String resultMsg = "";
        try {
            resultMsg = sendPostDataByJson(ApiConstants
                    .DaSanTong.DST_URL,
                    JSON.toJSONString(params), "utf-8");
             System.out.print(resultMsg);
        } catch (ClientProtocolException e) {

        } catch (IOException e) {

        }
        return JSONObject.parseObject(resultMsg);
//        JSONObject jsonObject = JSONObject.parseObject(resultMsg);
//        return TextUtils.equals(jsonObject.getString("result"), "DH:0000") ?
//                "success" : jsonObject.getString("desc");
    }

    public static String sendPostDataByJson(String url, String json, String encoding) throws ClientProtocolException, IOException {
        String result = "";

        // 创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        // 设置参数到请求对象中
        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        stringEntity.setContentEncoding("utf-8");
        httpPost.setEntity(stringEntity);

        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = httpClient.execute(httpPost);

        // 获取结果实体
        // 判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        }
        // 释放链接
        response.close();

        return result;
    }

}
