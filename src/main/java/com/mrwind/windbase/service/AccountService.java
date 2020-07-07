package com.mrwind.windbase.service;

import com.mrwind.windbase.common.annotation.SkipTokenAndRootTeamAuth;
import com.mrwind.windbase.common.constant.CommonConstants;
import com.mrwind.windbase.common.util.JwtUtil;
import com.mrwind.windbase.common.util.LocaleType;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.common.util.ServletUtil;
import com.mrwind.windbase.dao.mongo.UpdateAccountLogDao;
import com.mrwind.windbase.dao.mysql.AccountRepository;
import com.mrwind.windbase.dto.AccountSearchDTO;
import com.mrwind.windbase.dto.AccountUpdataDto;
import com.mrwind.windbase.entity.mongo.UpdateAccountLog;
import com.mrwind.windbase.entity.mysql.Account;
import com.mrwind.windbase.entity.mysql.User;
import com.mrwind.windbase.mq.MultiIOSource;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description
 *
 * @author hanjie
 */

@Service
@Transactional
@EnableBinding(MultiIOSource.class)
public class AccountService extends BaseService{

    @Resource
    private AccountRepository accountRepository;

    @Resource
    private UpdateAccountLogDao updateAccountLogDao;

//    @StreamListener("input1")
//    public void mqNotify(PayMessage message) {
//        AccountUpdataDto accountUpdataDto = new AccountUpdataDto();
//        accountUpdataDto.setAmount(message.getAmountTotal());
//        accountUpdataDto.setType(CommonConstants.AccountLogType.recharge);
//        accountUpdataDto.setMethod(true);
//        accountUpdataDto.setOrderId(message.getTradeOut());
//        accountUpdataDto.setSource(String.valueOf(message.getPayType()));
//        accountUpdataDto.setUserId(message.getUserId());
//
//        UpdateAmount(message.getUserId(), accountUpdataDto);
//    }
//
//    @StreamListener("input2")
//    public void mqNotifyExpress(DriverMessage message) {
//        ProcessExpress(message);
//    }
//
//    public void ProcessExpress(DriverMessage message) {
//        UpdateAccountLog updateAccountLog = new UpdateAccountLog();
//        updateAccountLog.setAmount(message.getAmount());
//        updateAccountLog.setJsonType(message.getJsonType());
//        updateAccountLog.setType(message.getType());
//        updateAccountLog.setMethod(message.getMethod());
//        updateAccountLog.setOrderId(message.getOrderId());
//        updateAccountLog.setSource(String.valueOf(message.getSource()));
//        updateAccountLog.setData(message.getData());
//        updateAccountLog.setTargetUserId(message.getUserId());
//        updateAccountLog.setSourceUserId("express");
//        UpdateAmountLogs(message.getUserId(), updateAccountLog);
//    }

//    @StreamListener("input3")
//    public void mqNotifyExpressTaiwan(DriverMessage message) {
//        ProcessExpress(message);
//    }

//    public String getUserId() {
//        HttpServletRequest request = ServletUtil.getCurrentRequest();
//
//        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
//        try {
//            return JwtUtil.parseJWT(token).getSubject().split("_")[0];
//        } catch (Exception e) {
//            return null;
//        }
//    }

    public Result UpdateAmount(String id,
                               AccountUpdataDto accountUpdataDto) {
        UpdateAccountLog updateAccountLog = accountService.CreateAccountLog(accountUpdataDto.getUserId(), id,
                accountUpdataDto.getOrderId(),
                accountUpdataDto.getType(),
                accountUpdataDto.getSource(),
                accountUpdataDto.getMethod(),
                accountUpdataDto.getDescribe(),
                accountUpdataDto.getAmount());

        return UpdateAmountLogs(accountUpdataDto.getUserId(), updateAccountLog);
    }
    public Result UpdateAmountLogs(String id,
                                  UpdateAccountLog updateAccountLog) {

        int n = 0;
        if(updateAccountLog.getMethod() == false) {
            if(updateAccountLog.getType().equals(CommonConstants.AccountLogType.derate)){
                accountService.consumeAmount(id, updateAccountLog.getAmount(), updateAccountLog);
            } else {
                n = accountService.consumeAmount(id, updateAccountLog.getAmount(), updateAccountLog);
                if(n <= 0 ) {
                    return Result.getFail("扣费失败，余额不足");
                }
            }

        } else {
            accountService.UpdateAmount(id, updateAccountLog.getAmount().toString(), updateAccountLog);
        }

        return Result.getSuccess();
    }
    public Result createAccount(Account account) {
        accountRepository.save(account);
        return Result.getSuccess();
    }
//    public void save(UpdateAccountLog updateAccountLog){
//        updateAccountLogDao.save(updateAccountLog);
//    }
    public void testUpdateAccountLog() {
        UpdateAccountLog updateAccountLog = new UpdateAccountLog();
        updateAccountLog.setDescribe("1");
        updateAccountLog.setOrderId("1");
        updateAccountLog.setType(1);
        updateAccountLog.setSource("1");
        updateAccountLog.setMethod(true);

        updateAccountLogDao.save(updateAccountLog);
    }
    public Map<String, Object> getAccountBalanceAndCount(String userId) {
        Map<String, Object> result = new HashMap<String, Object>();
        Long count = updateAccountLogDao.countByUserId(userId);
        BigDecimal amount = getAccountBalanceByRootTeamId(userId);
        result.put("count", count);
        result.put("amount", amount);
        return result;
    }
    public void UpdateAmount(String userId, String amount, UpdateAccountLog updateAccountLog) {
        BigDecimal bd = new BigDecimal(amount);
        try {
            accountRepository.UpdateAccountAmount(userId, bd);
            updateAccountLogDao.save(updateAccountLog);
        } catch (Exception e) {

        }
    }
    public void UpdateWxAmount(String userId, String amount) {
        BigDecimal bdamount = new BigDecimal(amount);
        BigDecimal b = new BigDecimal(100);
        accountRepository.UpdateAccountAmount(userId, bdamount.divide(b, 2,
                RoundingMode.HALF_UP));
    }
    public int consumeAmount(String userId, BigDecimal amount, UpdateAccountLog updateAccountLog) {
        int n = accountRepository.consumeAccountAmount(userId, amount);
        if(n <= 0) {
            throw new RuntimeException("余额不足");
        } else {
            updateAccountLogDao.save(updateAccountLog);
            return n;
        }
    }

    public UpdateAccountLog CreateAccountLog(String sourceUserId,
                          String targetUserId,
                          String orderId,
                          Integer type,
                          String source,
                          Boolean method,
                          String describe,
                          BigDecimal amount) {
        UpdateAccountLog updataAccountLog = new UpdateAccountLog();
        updataAccountLog.setMethod(method);
        updataAccountLog.setAmount(amount);
        updataAccountLog.setSource(source);
        updataAccountLog.setType(type);
        updataAccountLog.setOrderId(orderId);
        updataAccountLog.setDescribe(describe);
        updataAccountLog.setSourceUserId(sourceUserId);
        updataAccountLog.setTargetUserId(targetUserId);
        return updataAccountLog;
        // accountService.save(updataAccountLog);

    }
    /**
     * 获取账户余额(userId)
     * @param userId
     * @return
     */
    public Result getAccountBalance(String userId){
        Account account=accountRepository.findById(userId).orElse(null);
        if(account==null){
            return Result.getFailI18N("error.account.not.found");
        }
        Map<String,Object> result=new HashMap<>();
        result.put("accountBalance",account.getAmount());
        return Result.getSuccess(result);
    }
    public List<UpdateAccountLog> findByUserId(String userId, String project, Integer offset, Integer limit){
        return updateAccountLogDao.findByUserId(userId, project, offset, limit);
    }

    public BigDecimal getAccountBalanceByRootTeamId(String userId){
        BigDecimal accountBalance = BigDecimal.valueOf(0.00);
        Account account=accountRepository.findById(userId).orElse(null);
        if(account==null){
            return accountBalance;
        }
        return account.getAmount();
    }

    /**
     * 分页查询某个时间段下某个根团队的资金账户流水及余额
     * @param req
     * @param userId
     * @return
     */
    public Result getAccountDetails(AccountSearchDTO req,String userId) {
        Account account=accountRepository.findById(userId).orElse(null);
        if(account==null){
            return Result.getFailI18N("error.account.not.found");
        }
        Map<String,Object> result=new HashMap<>();
        PageRequest pageRequest = new PageRequest(req.getPage()-1,req.getSize());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            Timestamp startTime= new Timestamp(df.parse(req.getStartTime()).getTime());
//            Timestamp endTime= new Timestamp(df.parse(req.getEndTime()).getTime());
//            Page<Map<String,Object>> capitalDetailList=capitalDetailRepository.findByRootTeamIdAndDateAndPage(startTime,endTime,userId,pageRequest);
//            List<Map<String,Object>> billHistoryList=new ArrayList<>();
//            Map<String,Object> billHistory;
//
//              for(int i=0,length=capitalDetailList.getContent().size();i<length;i++ ) {
//                  billHistory = new HashMap<>();
//                  Map<String,Object> map=capitalDetailList.getContent().get(i);
//                  billHistory.put("title", map.get("title"));
//                  Date date=(Date)map.get("time");
//                  billHistory.put("time",df.format(date) );
//                  if (Integer.parseInt(map.get("type").toString()) == 1) {
//                      billHistory.put("bill", "+" + map.get("bill"));
//                  }
//                  if (Integer.parseInt(map.get("type").toString()) == 2) {
//                      billHistory.put("bill", "-" + map.get("bill"));
//                  }
//                  billHistoryList.add(billHistory);
//              }
//            result.put("accountBalance",account.getAmount());
//            result.put("billHistory",billHistoryList);
//            result.put("total",capitalDetailList.getTotalElements());
//            return Result.getSuccess(result);
//        }catch (ParseException e){
//            return Result.getFail(e.getMessage());
//        }
        return Result.getSuccess();
    }

    /**
     * B端用户发送账户余额不足的短信
     * @param user
     * @return
     */
    public Result  sendBalanaceLackSms(User user){

        String rootTeamId=user.getCurrentTeamId();
        String userId=user.getUserId();
        //判断当前登录人是否为B端用户
        if("5afd5817cd0bd80ecf11e0dc".equals(rootTeamId)){
            //非B端用户，不做任何操作
            return  Result.getSuccess();
        }
        Account account=accountRepository.findById(userId).orElse(null);
        if(account==null){
            return Result.getFailI18N("error.account.not.found");
        }

        String tel=userRepository.getUserTelByUserTeamIn(rootTeamId);

        //发送短信的多语言
        //现在没有提供充值的网址，后续提供了直接更新"remind.root.team.user.insufficient.account.balance"对应的中英文语言里的充值地址
        Map<String,String> smsMessageMap=LocaleType.getMessageMap("remind.root.team.user.insufficient.account.balance",account.getAmount());
        smsService.sendSms(tel,smsMessageMap);
        return  Result.getSuccess();

    }

}
