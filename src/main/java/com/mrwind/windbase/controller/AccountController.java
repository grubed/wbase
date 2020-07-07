package com.mrwind.windbase.controller;

import com.mrwind.windbase.common.annotation.SkipRootTeamAuth;
import com.mrwind.windbase.common.annotation.SkipTokenAndRootTeamAuth;
import com.mrwind.windbase.common.constant.CommonConstants;
import com.mrwind.windbase.common.util.JwtUtil;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.common.util.ServletUtil;
import com.mrwind.windbase.dto.AccountSearchDTO;
import com.mrwind.windbase.dto.AccountUpdataDto;
import com.mrwind.windbase.entity.mongo.UpdateAccountLog;
import com.mrwind.windbase.entity.mysql.User;
import com.mrwind.windbase.mq.MultiIOSource;
import com.mrwind.windbase.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.math.BigDecimal;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@Api(description = "资金账户接口")
@RestController
@RequestMapping(value = "/account")
public class AccountController extends BaseController {
    @Resource
    private AccountService accountService;

//    @RequestMapping(value = "/pay", method = RequestMethod.POST)
//    @ApiOperation(value = "支付运单", httpMethod = "POST", produces = MediaType
//            .APPLICATION_JSON_VALUE)
//    @ApiResponse(code = 200, message = "OK", response = Result.class)
//    @SkipTokenAndRootTeamAuth
//    public Result pay(@RequestBody DriverMessage driverMessage) {
//        accountService.ProcessExpress(driverMessage);
//        int n = accountService.consumeAmount();
//        if(n < 0) {
//            return Result.getFail("扣费失败，余额不足");
//        } else {
//
//            return Result.getSuccess();
//        }
//        return Result.getSuccess();
//    }

    @SkipRootTeamAuth
    @RequestMapping(value = "/{id}/consume", method = RequestMethod.POST)
    public Result consumeAmount(@PathVariable("id") String id,
                                @RequestBody AccountUpdataDto accountUpdataDto) {
        accountUpdataDto.setUserId(id);
        return accountService.UpdateAmount(id, accountUpdataDto);
    }
    @SkipRootTeamAuth
    @RequestMapping(value = "/{id}/income", method = RequestMethod.POST)
    public Result incomeAmount(@PathVariable("id") String id,
                                @RequestBody AccountUpdataDto accountUpdataDto) {
        accountUpdataDto.setUserId(id);
        return accountService.UpdateAmount(id, accountUpdataDto);
    }

    /**
     * 获取账户余额
     */
    @SkipRootTeamAuth
    @RequestMapping(value = "/{id}/balance", method = RequestMethod.GET)
    @ApiOperation(value = "根据当前登录用户获取当前userId资金账户余额", httpMethod = "GET", produces = MediaType
            .APPLICATION_JSON_VALUE)
    @ApiResponse(code = 200, message = "OK", response = Result.class)
    public Result getAccountBalance(@PathVariable("id") String id) {
        // User me = (User) ServletUtil.getCurrentRequest().getAttribute(CommonConstants.USER_KEY);
        return accountService.getAccountBalance(id);
    }

    @SkipRootTeamAuth
    @RequestMapping(value = "/{id}/amountlog", method = RequestMethod.GET)
    @ApiOperation(value = "", httpMethod = "GET", produces = MediaType
            .APPLICATION_JSON_VALUE)
    @ApiResponse(code = 200, message = "OK", response = Result.class)
    public Result getAccountLog(@PathVariable("id") String id,
                                @RequestParam String project,
                                @RequestParam Integer offset,
                                @RequestParam Integer limit) {
        // User me = (User) ServletUtil.getCurrentRequest().getAttribute(CommonConstants.USER_KEY);
        return Result.getSuccess(accountService.findByUserId(id, project, offset, limit));
    }

    /**
     * 指定根团队 获取余额
     *
     * */
//    @GetMapping("/balance")
//    @SkipRootTeamAuth
//    public Result accountBalance(@RequestParam("root") String root) {
//        return accountService.getAccountBalance(root);
//    }

    /**
     * 获取账户详情
     */
//    @RequestMapping(value = "/search", method = RequestMethod.POST)
//    @ApiOperation(value = "分页查询某个时间段下当前用户所在当前根团队的资金账户流水及余额", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @ApiResponse(code = 200, message = "OK", response = Result.class)
//    public Result getAccountDetail(@RequestBody AccountSearchDTO req) {
//        User me = (User) ServletUtil.getCurrentRequest().getAttribute(CommonConstants.USER_KEY);
//        return accountService.getAccountDetails(req,me.getCurrentTeamId());
//    }

    /**
     * B端用户账户余额不足发送短信提醒
     * @return
     */
    @SkipRootTeamAuth
    @RequestMapping(value = "/balancelack", method = RequestMethod.GET)
    @ApiResponse(code = 200, message = "OK", response = Result.class)
    public Result sendBalanaceLackSms(){
         User me = (User) ServletUtil.getCurrentRequest().getAttribute(CommonConstants.USER_KEY);
         return accountService.sendBalanaceLackSms(me);
    }


}
