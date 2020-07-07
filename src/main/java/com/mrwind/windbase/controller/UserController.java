package com.mrwind.windbase.controller;

import com.mrwind.windbase.common.annotation.SkipRootTeamAuth;
import com.mrwind.windbase.common.annotation.SkipTokenAndRootTeamAuth;
import com.mrwind.windbase.common.annotation.WindAuthorization;
import com.mrwind.windbase.common.constant.CommonConstants;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.common.util.ServletUtil;
import com.mrwind.windbase.common.util.TimeUtil;
import com.mrwind.windbase.common.util.ValidUtil;
import com.mrwind.windbase.dto.*;
import com.mrwind.windbase.entity.mysql.User;
import com.mrwind.windbase.service.SmsService;
import com.mrwind.windbase.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 登录 / 注册流程
 * 1. 前端 - 向后端获取 RSA 公钥
 * 2. 前端 - 使用 MD5 加密明文密码
 * 3. 前端 - 再将加密后的 MD5 值使用 RSA 公钥加密传给后端
 * 4. 后端 - 使用对应的 RSA 私钥解密出密码 MD5 值，直接存储到数据库 ( 注册 ) 或者比对数据库 ( 登录 )
 *
 * @author hanjie
 */
@Api(description = "用户相关接口")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;
    @Resource
    private SmsService smsService;
//    @Resource
//    private UserFriendService userFriendService;

    @SkipTokenAndRootTeamAuth
    @GetMapping(value = "/usership/{id}")
    public Result getUserShip(@PathVariable("id") String userId) {
        return Result.getSuccess(userService.findUserbyId(userId));
    }

    /**
     * 根据 id 获取用户信息
     */
    @ApiOperation("根据 id 获取用户信息")
    @SkipRootTeamAuth
    @GetMapping(value = "/{id}")
    public Result getUserInfoById(@PathVariable("id") String userId) {
        return userService.getUserInfoById(userId);
    }


    @SkipTokenAndRootTeamAuth
    @ApiOperation("根据 id 获取用户信息 wu token")
    @GetMapping(value = "/find/{id}/{root}")
    public Result getUserInfoByIdd(@PathVariable("id") String userId, @PathVariable("root") String root) {
        return userService.getUserInfoByIdd(userId, root);
    }

    @SkipTokenAndRootTeamAuth
    @GetMapping(value = "/relationInfo")
    public Result getUserInfoAndRelation(@RequestParam("from") String from, @RequestParam("target") String target) {
        return userService.getUserInfoAndRelation(from, target);
    }

    /**
     * 更新用户信息
     */
    @ApiOperation("更新用户信息")
    @SkipTokenAndRootTeamAuth
    @PutMapping(value = "/{id}")
    public Result updateUserInfo(@PathVariable("id") String userId, @RequestBody UpdateUserInfoDTO body) {
        return userService.updateUserInfo(userId, body);
    }

    /**
     * 获取发货端用户的详细信息
     */
    @GetMapping(value = "/detail")
    public Result getUserDetailsInfo() {
        return userService.getShipmentUserInfo();
    }

    /**
     * 获取用户的详细信息
     */
    @GetMapping(value = "/{id}/details")
    public Result getUserDetailsInfo(@PathVariable("id") String userId) {
        return userService.getUserDetailsInfo(userId);
    }

    /**
     * 获取用户的详细信息
     */
    @GetMapping(value = "/details_with_data")
    @SkipRootTeamAuth
    public Result getUserDetailsInfoWithData(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("userId") String userId,
            @RequestParam("project") String project) {
        if (!TimeUtil.isFormat(startDate, TimeUtil.yyyyMMdd1) || !TimeUtil.isFormat(endDate, TimeUtil.yyyyMMdd1)) {
            return Result.getFail("error date format");
        }
        if (TimeUtil.compareDate(startDate, endDate, TimeUtil.yyyyMMdd1) > 0) {
            return Result.getFail("error date range");
        }
        return userService.getUserDetailsInfoWithData(userId, startDate, endDate, project);
    }

    /**
     * 获取用户资料页面工作配置页信息
     */
    @GetMapping("/work_settings")
    public Result getUserWorkSettingsInfo(@RequestParam(value = "userId", required = false) String userId) {
        return userService.getUserWorkSettingsInfo(userId);
    }

    /**
     * 根据 token 获取用户信息
     */
    @ApiOperation("根据 token 获取用户信息")
    @GetMapping(value = "/byToken")
    public Result getUserInfoByToken() {
        return userService.getUserInfoByToken();
    }

    /**
     * 根据手机号码获取用户信息
     */
    @ApiOperation("根据手机号码获取用户信息")
    @GetMapping(value = "/byTel")
    public Result getUserInfoByTel(@RequestParam("tel") String tel) {
        return userService.getUserInfoByTel(tel);
    }

    /**
     * 获取 RSA 公钥
     */
    @ApiOperation("获取 RSA 公钥")
    @GetMapping(value = "/publicKey")
    @SkipTokenAndRootTeamAuth
    public Result getRSAPublicKey() {
        return userService.getRSAPublicKey();
    }

    /**
     * 账号密码注册
     */
    @ApiOperation("账号密码注册")
    @PostMapping(value = "/register")
    @SkipTokenAndRootTeamAuth
    public Result register(@RequestBody @Valid RegisterAccountDTO body, BindingResult bindingResult) {
        if (!ValidUtil.checkTel(body.getCountryCode(), body.getTel())) {
            return Result.getFailI18N("error.invalid.tel");
        }
        synchronized (body.getTel().intern()) {
            return userService.register(body);
        }
    }

    /**
     * 获取验证码
     */
    @ApiOperation("获取登录验证码")
    @GetMapping(value = "/getLoginCode")
    @SkipTokenAndRootTeamAuth
    public Result getLoginCode(@RequestParam int countryCode, @RequestParam String tel) {
        if (!ValidUtil.checkTel(countryCode, tel)) {
            return Result.getFailI18N("error.invalid.tel");
        }
        return userService.getLoginCode(countryCode, tel);
    }

    /**
     * 验证码登录
     */
    @ApiOperation("验证码登录")
    @PostMapping(value = "/codeLogin")
    @SkipTokenAndRootTeamAuth
    public Result codeLogin(@RequestBody @Valid CodeLoginDTO body, BindingResult bindingResult) {
        if (!ValidUtil.checkTel(body.getCountryCode(), body.getTel())) {
            return Result.getFailI18N("error.invalid.tel");
        }
        return userService.codeLogin(body);
    }

    /**
     * 账号密码登录
     */
    @ApiOperation("账号密码登录")
    @PostMapping(value = "/login")
    @SkipTokenAndRootTeamAuth
    public Result userLogin(@RequestBody @Valid UserLoginDTO body, BindingResult bindingResult) {
        return userService.userLogin(body);
    }

    /**
     * 登出
     */
    @SkipTokenAndRootTeamAuth
    @ApiOperation("登出")
    @DeleteMapping(value = "/logout/{id}")
    public Result logout(@PathVariable("id") String userId) {
        return userService.logout(userId);
    }

    /**
     * 获取修改手机号码的验证码
     * <p>
     * 这里的 countryCode 必填
     */
    @ApiOperation("获取修改手机号码的验证码")
    @GetMapping(value = "/getChangeTelCode")
    public Result getChangeTelCode(@RequestParam(value = "countryCode") int countryCode, @RequestParam("newTel") String newTel) {
        if (!ValidUtil.checkTel(countryCode, newTel)) {
            return Result.getFailI18N("error.invalid.tel");
        }
        return userService.getChangeTelCode(countryCode, newTel);
    }

    /**
     * 修改手机号码
     */
    @ApiOperation("修改手机号码")
    @PutMapping(value = "/changeTel")
    public Result changeTel(@RequestBody @Valid ChangeTelDTO body, BindingResult bindingResult) {
        return userService.changeTel(body.getCode());
    }

    /**
     * 获取修改密码的验证码
     */
    @ApiOperation("获取修改密码的验证码")
    @GetMapping(value = "/getChangePasswordCode")
    @SkipTokenAndRootTeamAuth
    public Result getChangePasswordCode(@RequestParam int countryCode, @RequestParam String tel) {
        if (!ValidUtil.checkTel(countryCode, tel)) {
            return Result.getFailI18N("error.invalid.tel");
        }
        return userService.getChangePasswordCode(countryCode, tel);
    }

    /**
     * 修改密码
     */
    @ApiOperation("修改密码")
    @PutMapping(value = "/changePassword")
    @SkipTokenAndRootTeamAuth
    public Result changePassword(@RequestBody @Valid ChangePasswordDTO body, BindingResult bindingResult) {
        return userService.changePassword(body);
    }

    /**
     * 设置当前团队
     */
    @SkipTokenAndRootTeamAuth
    @ApiOperation("设置当前团队")
    @PutMapping(value = "/currentTeamId")
    public Result updateCurrentTeamId(@RequestBody @Valid UpdateCurrentTeamIdDTO body, BindingResult bindingResult) {
        return userService.updateCurrentTeamId(body.getCurrentTeamId(), body.getUserId());
    }

    /**
     * 获取个人数据页信息（考勤、派件、团队）
     */
    @GetMapping(value = "/data")
    public Result getUserData() {
        return userService.getUserData();
    }

    /**
     * 给风力的自动登录
     */
    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @PostMapping(value = "/autoLogin")
    public Result autoLogin(@RequestBody @Valid AutoLoginDTO dto, BindingResult bindingResult) {
        return userService.autoLogin(dto);
    }

    /**
     * 更新接单状态
     */
    @PutMapping(value = "/receive_order_status")
    public Result updateReceiveOrderStatus(@RequestBody @Valid UpdateReceiveOrderStatusDTO dto, BindingResult bindingResult) {
        return userService.updateReceiveOrderStatus(dto.getReceive());
    }

    /**
     * 获取接单状态
     */
    @GetMapping(value = "/receive_order_status")
    public Result getReceiveOrderStatus() {
        return Result.getSuccess(userService.getReceiveOrderStatus());
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * 给用户添加标签
     */
    @ApiOperation(value = "给用户添加标签", httpMethod = "POST")
    @PostMapping("/role")
    public Result addRoleForUser(@RequestBody @Valid AddRoleForUserDTO dto) {
        return userService.addRoleForUser(dto);
    }

    /**
     * 删除用户标签
     */
    @ApiOperation(value = "删除用户标签", httpMethod = "DELETE")
    @DeleteMapping("/role")
    public Result removeUserRole(@RequestBody @Valid RemoveUserRoleDTO dto, BindingResult bindingResult) {
        return userService.removeUserTeamRole(dto);
    }

    /**
     * 模糊搜索人员
     * 姓名 手机号 仅限该公司内 和 客服人员
     */
    @ApiOperation(value = "模糊搜索人员", httpMethod = "POST")
    @SkipRootTeamAuth
    @PostMapping("/search")
    public Result searchUser(@RequestBody @Valid SearchUserDTO dto, BindingResult bindingResult) {
        return userService.searchUser(dto);
    }

    @ApiOperation(value = "获取客服人员列表", notes = "获取的是风先生团队下打着客服标签的团队")
    @GetMapping("/services")
    public Result getServices() {
        return userService.getServices();
    }

    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @ApiOperation(value = "给用户推送信息", notes = "给用户列表中的用户推送消息")
    @PostMapping("/push")
    public Result pushForUsers(@RequestBody @Valid UserPushDTO userPushDTO, BindingResult bindingResult) {
        userService.pushForUsers(userPushDTO);
        return Result.getSuccess();
    }

    @SkipTokenAndRootTeamAuth
    @WindAuthorization
    @ApiOperation(value = "获取一批用户的头像")
    @PostMapping("/avatar")
    public Result getUserAvatarByUserIdList(@RequestBody @Valid UserIdDTO userIdDTO, BindingResult bindingResult) {
        return userService.getUserAvatarByUserIdList(userIdDTO.getUserIdList());
    }

    @SkipTokenAndRootTeamAuth
    @PostMapping("/languages")
    public Result getUserCountryCodeMap(@RequestBody List<String> userIds) {
        return userService.getUserCountryCodeMap(userIds);
    }

    /**
     * 更新用户 App 语言
     */
    @PutMapping("/language")
    @SkipRootTeamAuth
    public Result updateUserAppLanguage(@RequestBody @Valid UpdateUserAppLanguageDTO dto, BindingResult bindingResult) {
        User user = (User) ServletUtil.getCurrentRequest().getAttribute(CommonConstants.USER_KEY);
        userService.updateUserAppLanguage(user.getUserId(), "chinese");
        return Result.getSuccess();
    }

    @RequestMapping(value = "/browser/balance", method = RequestMethod.GET)
    @ApiOperation(value = "获取B端用户列表及余额", httpMethod = "GET", produces = MediaType
            .APPLICATION_JSON_VALUE)
    public Result getBrowserUsers() {
        return userService.getBrowserUsersDetail();
    }

    @GetMapping(value = "/postman_token")
    @SkipTokenAndRootTeamAuth
    public Result findTokenByUserId(@RequestParam("userId") String userId) {
        return Result.getSuccess(userService.findTokenByUserId(userId));
    }

    /**
     * @param users
     * @return {sms未登录用户数组: ; chat已登录用户数组:}
     */
    @SkipTokenAndRootTeamAuth
    @PostMapping(value = "/unLogin")
    public Result usersUnLogin(@RequestBody List<String> users) {
        return userService.usersUnLogin(users);
    }

    /**
     * 新建发货人并添加好友
     */
    @PostMapping(value = "/receiver")
    @SkipRootTeamAuth
    public Result createReceiver(@RequestBody CreateReciverDTO dto) {
        User me = (User) ServletUtil.getCurrentRequest().getAttribute(CommonConstants.USER_KEY);
        return userService.createReceiver(me, dto);
    }

    /**
     * 批量新建发货人并添加好友
     */
    @PostMapping(value = "/batch_receiver")
    @SkipRootTeamAuth
    public Result createBatchReceiver(@RequestBody List<CreateReciverDTO> dto) {
        User me = (User) ServletUtil.getCurrentRequest().getAttribute(CommonConstants.USER_KEY);
        return userService.createBatchReceiver(me, dto);
    }

    @GetMapping(value = "/address/{id}")
    @SkipRootTeamAuth
    public Result getUserAddress(@PathVariable("id") String id) {
        return userService.getUserAddress(id);
    }

    @PostMapping(value = "/address")
    @SkipRootTeamAuth
    public Result createUserAddress(@RequestBody CreateUserAddressDTO dto) {
        return userService.createUserAddress(dto);
    }

    @DeleteMapping(value = "/address/{id}")
    @SkipRootTeamAuth
    public Result deleteUserAddress(@PathVariable("id") long id) {
        return userService.deleteUserAddress(id);
    }

    @GetMapping(value = "/address")
    @SkipRootTeamAuth
    public Result getUserAddressByAddressId(@RequestParam("addressId") String addressId) {
        return userService.getUserAddressByAddressId(addressId);
    }

    @PostMapping(value = "/users/all")
    @SkipTokenAndRootTeamAuth
    public Result getUserAddressByAddressId(@RequestBody UsersDTO dto) {
        return Result.getSuccess(userService.users(dto.getUserIds()));
    }
}
