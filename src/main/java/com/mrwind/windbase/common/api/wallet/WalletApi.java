package com.mrwind.windbase.common.api.wallet;

import com.mrwind.windbase.common.constant.WindAuthKeyEnum;
import com.mrwind.windbase.common.util.Result;
import com.mrwind.windbase.common.util.TextUtils;
import com.mrwind.windbase.dao.mongo.FeignErrorLogDao;
import com.mrwind.windbase.dto.AccountNewDTO;
import com.mrwind.windbase.entity.mongo.FeignErrorLog;
import com.mrwind.windbase.vo.AccountVO;
import com.mrwind.windbase.vo.TeamWalletDataVO;
import com.mrwind.windbase.vo.WalletCollectVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/8/29
 */
@Component
public class WalletApi {

    @Resource
    private FeignErrorLogDao feignErrorLogDao;

    @Resource
    private WalletClient walletClient;

    /**
     * 创建账户
     *
     * @param userId
     * @return
     */
    public boolean createAccount(String userId) {
        boolean success = false;
        try {
            Result<Object> result = walletClient.createAccount(WindAuthKeyEnum.WIND_WALLET.getKey(), userId);
            String code = Optional.ofNullable(result).map(Result::getCode).orElse(null);
            success = TextUtils.equals(code, Result.CODE_SUCCESS);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        return success;
    }



    /**
     * 获取账户数组
     */
    public List<AccountVO> getUserAccounts(String userId) {
        List<AccountVO> vos = new ArrayList<>();
        try {
            Result<List<AccountVO>> result = walletClient.getUserAccounts(WindAuthKeyEnum.WIND_WALLET.getKey(), userId);
            vos = Optional.ofNullable(result).map(Result::getData).orElse(vos);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        return vos;
    }

    /**
     * 获取团队钱包数据
     */
    public TeamWalletDataVO getTeamWalletData(String teamId, String startTime, String endTime) {
        TeamWalletDataVO vo = new TeamWalletDataVO();
        try {
            Result<TeamWalletDataVO> result = walletClient.getTeamWalletData(WindAuthKeyEnum.WIND_WALLET.getKey(), teamId, startTime, endTime);
            vo = Optional.ofNullable(result).map(Result::getData).orElse(vo);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        return vo;
    }

    /**
     * 获取团队钱包数据
     */
    public WalletCollectVO deliveryTeamTotals(String teamId, String startTime, String endTime) {
        WalletCollectVO vo = new WalletCollectVO();
        try {
            Result<WalletCollectVO> result = walletClient.deliveryTeamTotals(teamId, startTime, endTime);
            vo = Optional.ofNullable(result).map(Result::getData).orElse(vo);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        return vo;
    }

    /**
     * 用户是否有支付密码
     */
    public Boolean hasPayPwd(String userId) {
        Boolean hasPayPwd = null;
        try {
            Result<Map<String, Object>> result = walletClient.hasAccountPayPwd(WindAuthKeyEnum.WIND_WALLET.getKey(), userId);
            hasPayPwd = Optional.ofNullable(result).map(Result::getData).map(map -> (boolean) map.get("paypwd")).orElse(null);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
        return hasPayPwd;
    }

    public void newUser(String userId) {
        try {
            AccountNewDTO accountNewDTO = new AccountNewDTO();
            accountNewDTO.setCurrency(null);
            accountNewDTO.setUserId(userId);
            walletClient.newAccount(WindAuthKeyEnum.WIND_WALLET.getKey(),accountNewDTO);
        } catch (Exception e) {
            feignErrorLogDao.save(new FeignErrorLog(Thread.currentThread().getStackTrace()[1].getMethodName(), e.toString()));
        }
    }

}
