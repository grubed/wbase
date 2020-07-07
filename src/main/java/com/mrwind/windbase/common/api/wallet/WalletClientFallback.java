package com.mrwind.windbase.common.api.wallet;

import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.dto.AccountNewDTO;
import com.mrwind.windbase.vo.AccountVO;
import com.mrwind.windbase.vo.TeamWalletDataVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/11/7
 */
@Component
public class WalletClientFallback implements WalletClient {

    @Override
    public Result<Object> createAccount(String key, String userId) {
        return null;
    }

    @Override
    public Result<List<AccountVO>> getUserAccounts(String key, String userId) {
        return null;
    }

    @Override
    public Result<TeamWalletDataVO> getTeamWalletData(String key, String teamId, String startTime, String endTime) {
        return null;
    }

    @Override
    public Result deliveryTeamTotals(String teamid, String start, String end) {
        return null;
    }

    @Override
    public Result<Map<String, Object>> hasAccountPayPwd(String key, String id) {
        return null;
    }

    @Override
    public Result<Object> newAccount(String key, AccountNewDTO dto) {
        return null;
    }

}