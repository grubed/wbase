package com.mrwind.windbase.service;

import com.mrwind.windbase.common.constant.ApiConstants;
import com.mrwind.windbase.common.constant.CommonConstants;
import com.mrwind.windbase.common.constant.CountryCodeConstant;
import com.mrwind.windbase.common.constant.WindAuthKeyEnum;
import com.mrwind.windbase.common.exception.FailResultException;
import com.mrwind.windbase.common.util.ServletUtil;
import com.mrwind.windbase.common.util.SmsUtil;
import com.mrwind.windbase.common.util.TextUtils;
import com.mrwind.windbase.common.util.ValidUtil;
import com.mrwind.windbase.entity.mongo.Sms;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 *
 * @author hanjie
 */

@Service
public class SmsService extends BaseService {


    /**
     * 发送短信
     *
     * @param tel     手机号码，批量发送使用逗号分隔
     * @param message 消息
     */
    public void sendSms(String tel, String message) {
        Map<String, String> messageMap = new HashMap<>();
        messageMap.put("chinese", message);
//        messageMap.put(LanguageType.ENGLISH, message);
        sendSms(tel, messageMap);
    }

    /**
     * 发送短信，支持多语言
     *
     * @param tel        手机号码，批量发送使用逗号分隔
     * @param messageMap 消息 Map，key 为 LanguageType.CHINESE 或者 LanguageType.ENGLISH，值为的对应的语言,
     *                   可以使用 LocaleType.getMessageMap() 方法快速生成
     */
    public void sendSms(String tel, Map<String, String> messageMap) {
        if (!ApiConstants.ENABLE_SMS) {
            return;
        }
        // 批量发送
        List<String> chinaTels = new ArrayList<>();
        List<String> nonChinaTels = new ArrayList<>();
        for (String s : tel.split(",")) {
            // 移除常见的特殊字符以及一些隐藏字符，只提取其中的数字
            StringBuilder sb = new StringBuilder();
            for (char c : s.toCharArray()) {
                if (c >= 48 && c <= 57) {
                    sb.append(c);
                }
            }
            s = sb.toString();

            if (s.length() >= 11 && ValidUtil.checkTel(CountryCodeConstant.CHINA, s.substring(s.length() - 11))) {
                // 国内手机号码
                chinaTels.add(s.substring(s.length() - 11));
            } else {
                // 非国内手机号码一律当做沙特号码处理，只取后面9位
                if (s.length() >= 9) {
                    s = "966" + s.substring(s.length() - 9);
                    nonChinaTels.add(s);
                }
            }
        }
        // 发送中国短信
        if (!chinaTels.isEmpty()) {
            logger.info("send chinaTels: " + chinaTels);
            sendByZhuanXinYun(StringUtils.join(chinaTels, ","), messageMap.get("chinese"));
        }
        // 发送沙特短信
        if (!nonChinaTels.isEmpty()) {
            logger.info("send nonChinaTels: " + nonChinaTels);
            sendByTelSign(nonChinaTels, messageMap.get("chinese"));
        }
    }

    /**
     * 发国内 中文
     */
    public void sendByZhuanXinYun(String tels, String message) {
        String content;
//        WindAuthKeyEnum from = (WindAuthKeyEnum) ServletUtil.getCurrentRequest().getAttribute(CommonConstants.AUTH_FROM);
        content = String.format("【连系】%s", message);
//        switch (from) {
//            case WIND_CHAT:
//                content = String.format("【连系】%s", message);
//                break;
//            case WIND_FRESH:
//                content = String.format("【凡食鲜】%s", message);
//                break;
//            case WIND_BASE:
//            case WIND_FORCE:
//            default:
//                content = String.format("【风先生】%s", message);
//                break;
//        }
        String status = SmsUtil.zhuanXinYunSmsSend(tels, content);
        saveSmsLog(tels, content, "ZXY", status);
    }

    /**
     * 发国外 英文
     */
    public void sendByTelSign(List<String> tels, String message) {
        for (String tel : tels) {
            String content;
            WindAuthKeyEnum from = (WindAuthKeyEnum) ServletUtil.getCurrentRequest().getAttribute(CommonConstants.AUTH_FROM);
            switch (from) {
                case WIND_CHAT:
                    content = String.format("【WindWire】%s", message);
                    break;
                case WIND_FRESH:
                    content = String.format("【Fresh】%s", message);
                    break;
                case WIND_BASE:
                case WIND_FORCE:
                default:
                    content = String.format("【Mr.Wind】%s", message);
                    break;
            }
            String status = SmsUtil.sendTeleSignSms(tel, content);
            saveSmsLog(tel, message, "TELE_SIGN", status);
        }
    }

    /**
     * 保存短信到数据库
     */
    private void saveSmsLog(String tels, String content, String channel, String status) {
        Sms sms = new Sms();
        sms.setTel(tels);
        sms.setMessage(content);
        sms.setStatus(status);
        sms.setFrom(((WindAuthKeyEnum) ServletUtil.getCurrentRequest().getAttribute(CommonConstants.AUTH_FROM)).name());
        sms.setChannel(channel);
        smsDao.save(sms);
        // 忽略短信发送失败异常
        if (!TextUtils.equals(status, "success")) {
            throw FailResultException.get("sms: " + status);
        }
    }

}
