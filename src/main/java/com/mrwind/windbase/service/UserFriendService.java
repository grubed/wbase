//package com.mrwind.windbase.service;
//
//import com.alibaba.fastjson.JSONObject;
//import com.mrwind.windbase.common.util.Result;
//import com.mrwind.windbase.common.util.TextUtils;
//import com.mrwind.windbase.dto.AddFriendDTO;
//import com.mrwind.windbase.entity.mysql.User;
//import com.mrwind.windbase.entity.mysql.UserFriend;
//import com.mrwind.windbase.entity.mysql.UserTeamRelation;
//import com.sun.org.apache.regexp.internal.RE;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
///**
// * Created by CL-J on 2018/12/5.
// */
//@Service
//public class UserFriendService extends BaseService {
//    /**
//     * 找到所有的联系人
//     * 找到当前team和team下的所有人
//     * 去重
//     * */
//    public Result contacts(String userId) {
//        List<Map<String,Object>> result = new ArrayList<>();
//        List<UserFriend> userFriends = userFriendRepository.findByUserIdOrOtherId(userId);
//
//        Map<String,UserFriend> ums = new HashMap<>();
//        for (UserFriend u : userFriends) {
//            ums.put(TextUtils.equals(u.getUserId(),userId) ? u.getOtherId() : u.getUserId(),u);
//        }
//        List<String> friendIds = userFriends.stream().map(t->TextUtils.equals(t.getUserId(),userId) ? t.getOtherId() : t.getUserId()).collect(Collectors.toList());
//        List<User> contacts = userRepository.findByUserIdIn(friendIds).stream().collect(Collectors.toList());
//
//        for (User u : contacts) {
//            Map<String,Object> m = new HashMap<>();
//            m.put("userId",u.getUserId());
//            m.put("name",u.getName());
//            m.put("avatar",u.getAvatar());
//            m.put("tel",u.getTel());
//            m.put("isFriend", ums.get(u.getUserId()).getIsFriend());
//            result.add(m);
//        }
//
//        JSONObject result2 = new JSONObject();
//        result2.put("friends",result);
//        return Result.getSuccess(result2);
//    }
//
//    public Result makeContact(AddFriendDTO dto) {
//        UserFriend userFriend = userFriendRepository.get2UserRelation(dto.getUserId(),dto.getOtherId());
//        if (userFriend == null) {
//            userFriend = new UserFriend(dto.getUserId(),dto.getOtherId(),0);
//            userFriendRepository.save(userFriend);
//        } else {
//            return Result.getFailI18N("error.friend.already.concat");
//        }
//        return Result.getSuccess();
//    }
//
//    public Result deleteContact(AddFriendDTO dto) {
//        UserFriend userFriend = userFriendRepository.get2UserRelation(dto.getUserId(),dto.getOtherId());
//        if (userFriend == null) {
//            return Result.getFailI18N("error.friend.not.contact");
//        }
//        userFriendRepository.delete(userFriend);
//        return Result.getSuccess();
//    }
//
//
//    //联系人成为好友 或者好友成为联系人
//    public Result contactOrFriend(AddFriendDTO dto,int toFriend) {
//        UserFriend userFriend = userFriendRepository.get2UserRelation(dto.getUserId(),dto.getOtherId());
//        if (userFriend == null) {
//            return Result.getFailI18N("error.friend.not.contact");
//        }
//        userFriend.setIsFriend(toFriend);
//        userFriendRepository.save(userFriend);
//        if (toFriend == 1) {
//            User user = userRepository.findByUserId(dto.getUserId());
//            User other = userRepository.findByUserId(dto.getOtherId());
//            chatService.ifAgreeFriendApply(user,other,toFriend);
//        }
//        return Result.getSuccess();
//    }
//
//    public Result sendFriendApply(AddFriendDTO dto) {
//        User user = userRepository.findByUserId(dto.getUserId());
//        User other = userRepository.findByUserId(dto.getOtherId());
//        chatService.sendFriendApply(user,other);
//        return Result.getSuccess();
//    }
//
//}
