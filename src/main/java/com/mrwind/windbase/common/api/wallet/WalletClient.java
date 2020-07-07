package com.mrwind.windbase.common.api.wallet;

import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.dto.AccountNewDTO;
import com.mrwind.windbase.vo.AccountVO;
import com.mrwind.windbase.vo.TeamWalletDataVO;
import com.mrwind.windbase.vo.WalletCollectVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@FeignClient(name = "wallet", fallback = WalletClientFallback.class, url = "http://app.wisready.com/WALLET/")
@FeignClient(name = "wallet", fallback = WalletClientFallback.class)
public interface WalletClient {

    @PostMapping(value = "/api/wallet/user/{id}/account")
    Result<Object> createAccount(@RequestHeader("key") String key, @PathVariable("id") String userId);

    @GetMapping(value = "/api/wallet/user/{id}/accounts")
    Result<List<AccountVO>> getUserAccounts(@RequestHeader("key") String key, @PathVariable("id") String userId);

    @GetMapping(value = "/api/wallet/feng/totals")
    Result<TeamWalletDataVO> getTeamWalletData(@RequestHeader("key") String key,
                                               @RequestParam("teamId") String teamId,
                                               @RequestParam("start") String startTime,
                                               @RequestParam("end") String endTime);

    @RequestMapping(value = "/api/wallet/deliveryteam/{id}/profit", method = RequestMethod.GET)
    Result<WalletCollectVO> deliveryTeamTotals(@PathVariable("id") String teamid,
                                               @RequestParam("start") String start,
                                               @RequestParam("end") String end);

    @GetMapping(value = "/api/wallet/user/{id}/haspaypwd")
    Result<Map<String, Object>> hasAccountPayPwd(@RequestHeader("key") String key, @PathVariable("id") String id);

    @PostMapping(value = "/api/wallet/account/user")
    Result<Object> newAccount(@RequestHeader("key") String key, @RequestBody AccountNewDTO dto);

}