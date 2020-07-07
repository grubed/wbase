//package com.mrwind.windbase.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.mrwind.windbase.common.constant.CommonConstants;
//import com.mrwind.windbase.common.util.Result;
//import com.mrwind.windbase.common.util.ServletUtil;
//import com.mrwind.windbase.dto.ActionBodyDTO;
//import com.mrwind.windbase.dto.AddFriendDTO;
//import com.mrwind.windbase.entity.mysql.User;
//import com.mrwind.windbase.service.BlackListService;
//import com.mrwind.windbase.service.UserFriendService;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//
///**
// * Created by CL-J on 2018/12/5.
// */
//@RestController
//@RequestMapping("/userFriend")
//public class UserFriendController {
//
//    @Resource
//    private UserFriendService userFriendService;
//
//    @Resource
//    private BlackListService blackListService;
//
//    @GetMapping(value = "/contact")
//    //获取所有联系人和好友
//    public Result contacts() {
//        User user = (User) ServletUtil.getCurrentRequest().getAttribute(CommonConstants.USER_KEY);
//        return userFriendService.contacts(user.getUserId());
//    }
//
//    @PostMapping(value = "/contact")
//    //添加联系人
//    public Result addContact(@RequestBody AddFriendDTO dto) {
//        return userFriendService.makeContact(dto);
//    }
//
//    @DeleteMapping(value = "/contact")
//    //解除联系人
//    public Result deleteContact(@RequestBody AddFriendDTO dto) {
//        return userFriendService.deleteContact(dto);
//    }
//
//    //发送好友申请
//    @PostMapping(value = "/friend")
//    public Result makeFriendApply(@RequestBody AddFriendDTO dto) {
//        return userFriendService.sendFriendApply(dto);
//    }
//    //同意好友申请
//    @PostMapping(value = "/friend/agree")
//    public Result agreeFriend(@RequestBody ActionBodyDTO body) {
//        AddFriendDTO dto = JSONObject.parseObject(body.getBody(),AddFriendDTO.class);
//        return userFriendService.contactOrFriend(dto,1);
//    }
//    //拒绝好友申请
//    @PostMapping(value = "/friend/disagree")
//    public Result disagreeFriend(@RequestBody ActionBodyDTO body) {
//        AddFriendDTO dto = JSONObject.parseObject(body.getBody(),AddFriendDTO.class);
//        return userFriendService.contactOrFriend(dto,0);
//    }
//
//
//
//    @DeleteMapping(value = "/friend")
//    public Result deleteFriend(@RequestBody AddFriendDTO dto) {
//        return userFriendService.contactOrFriend(dto,0);
//    }
//
//
//
//
//    @PostMapping(value = "/blackList")
//    public Result moveToBlackList(@RequestBody AddFriendDTO dto) {
//        return Result.getSuccess(blackListService.moveToBlcakList(dto.getUserId(),dto.getOtherId()));
//    }
//
//    @DeleteMapping(value = "/blackList")
//    public Result deleteFromBlackList(@RequestBody AddFriendDTO dto) {
//        blackListService.removeFromBlackList(dto.getUserId(),dto.getOtherId());
//        addContact(dto);
//        return Result.getSuccess();
//    }
//
//    @GetMapping(value = "/blackList")
//    public Result getBlackList(@RequestParam String userId) {
//        return Result.getSuccess(blackListService.allBlackList(userId));
//    }
//}
