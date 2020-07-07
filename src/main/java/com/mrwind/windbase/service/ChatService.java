//package com.mrwind.windbase.service;
//
//import com.alibaba.fastjson.JSONObject;
//import com.mrwind.messagehelper.chat.MessageConfig;
//import com.mrwind.messagehelper.chat.payload.Action;
//import com.mrwind.messagehelper.chat.payload.Location;
//import com.mrwind.messagehelper.chat.payload.Payload;
//import com.mrwind.messagehelper.chat.payload.Trigger;
//import com.mrwind.messagehelper.constant.CardActionType;
//import com.mrwind.messagehelper.constant.ConversationType;
//import com.mrwind.windbase.common.constant.ApiConstants;
//import com.mrwind.windbase.common.util.LocaleType;
//import com.mrwind.windbase.entity.mysql.User;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
///**
// * Created by CL-J on 2019/1/7.
// */
//@Service
//public class ChatService extends BaseService {
//
//
//    /**
//     * 发送添加好友申请
//     */
//    public void sendFriendApply(User from,User to) {
//
//        String agree = ApiConstants.WIND_BASE_URL + "api/windbase/userFriend/friend/agree";
//        String disagree = ApiConstants.WIND_BASE_URL + "api/windbase/userFriend/friend/disagree";
//
//        MessageConfig config = MessageConfig.buildConfig(
//                Arrays.asList(to.getUserId()),
//                getUserLanguageMap(),
//                from.getName(),
//                ConversationType.SYSTEM_TO_MEMBER
//        );
//        Payload payload = new Payload();
//        // 触发人
//        payload.setTrigger(new Trigger(from.getUserId(), from.getName(), from.getAvatar(), from.getTel()));
//        // 内容标题
//        payload.setTitle(LocaleType.getMessageMap("好友申请"));
//        // 内容正文
//        String content = from.getName() + "申请添加您为好友";
//        payload.setContent(LocaleType.getMessageMap(content));
//        // 地图
//
//        // 按钮
//        List<Action> actions = new ArrayList<>();
//        // 主要按钮: 风信
//        Map<String, Object> firstBtnBodyMap = new HashMap<>();
//        //点击按钮之后 from和to互换 from为点击人 也就是被邀请方
//        firstBtnBodyMap.put("userId", to.getUserId());
//        firstBtnBodyMap.put("otherId", from.getUserId());
//        Action firstBtnAction = new Action(CardActionType.FIRST_BUTTON, LocaleType.getMessageMap("同意"), agree, JSONObject.toJSONString(firstBtnBodyMap));
//        actions.add(firstBtnAction);
//        // 次要按钮: 修改考勤配置按钮
//        Map<String, Object> secondBtnBodyMap = new HashMap<>();
//        secondBtnBodyMap.put("userId", to.getUserId());
//        secondBtnBodyMap.put("otherId", from.getUserId());
//        Action secondBtnAction = new Action(CardActionType.SECOND_BUTTON, LocaleType.getMessageMap("拒绝"), disagree, JSONObject.toJSONString(secondBtnBodyMap));
//        actions.add(secondBtnAction);
//        payload.setAction(actions);
//        windChatApi.send(config, 11202, payload);
//    }
//
//
//    public void ifAgreeFriendApply(User from,User to,int agree) {
//        String cont = agree == 1 ?  "同意" : "拒绝";
//        MessageConfig config = MessageConfig.buildConfig(
//                Arrays.asList(to.getUserId()),
//                getUserLanguageMap(),
//                from.getName() + "," + cont,
//                ConversationType.SYSTEM_TO_MEMBER
//        );
//        Payload payload = new Payload();
//        // 触发人
//        payload.setTrigger(new Trigger(from.getUserId(), from.getName(), from.getAvatar(), from.getTel()));
//        // 内容标题
//        payload.setTitle(LocaleType.getMessageMap("好友申请"));
//        // 内容正文
//        String content = from.getName() + "已" + cont + "您的好友请求!";
//        payload.setContent(LocaleType.getMessageMap(content));
//        windChatApi.send(config, 11203, payload);
//    }
//
//}
