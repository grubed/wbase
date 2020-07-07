package com.mrwind.windbase.service;

import com.mrwind.windbase.common.constant.CommonConstants;
import com.mrwind.windbase.entity.mysql.UserAppLanguage;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存相关
 *
 * @author hanjie
 * @date 2018/9/12
 */
@Service
public class CacheService extends BaseService {

    /**
     * 从数据库获取用户和对应区号信息缓存到 Redis 中
     */
    private Map<String, String> loadUserLanguageMapToRedisCache() {
        // 缓存用户与语言对应关系
        Map<String, String> userLanguageMap = new HashMap<>();
        for (UserAppLanguage userAppLanguage : userAppLanguageRepository.findAll()) {
            userLanguageMap.put(userAppLanguage.getUserId(), userAppLanguage.getLanguage());
        }
        userLanguageRedisTemplate.opsForValue().set(CommonConstants.Redis.USER_LANGUAGE, userLanguageMap);
        logger.info("load user language map to Redis");
        return userLanguageMap;
    }

    /**
     * 更新 Redis 用户语言缓存
     */
    public void updateRedisUserLanguageCache(String userId, String language) {
        Map<String, String> userLanguageMap = userLanguageRedisTemplate.opsForValue().get(CommonConstants.Redis.USER_LANGUAGE);
        if (userLanguageMap == null) {
            loadUserLanguageMapToRedisCache();
        } else {
            userLanguageMap.put(userId, language);
            userLanguageRedisTemplate.opsForValue().set(CommonConstants.Redis.USER_LANGUAGE, userLanguageMap);
        }
    }

    /**
     * 从 redis 中获取用户和其语言 map
     */
    public Map<String, String> getUserLanguageMap() {
        Map<String, String> map = userLanguageRedisTemplate.opsForValue().get(CommonConstants.Redis.USER_LANGUAGE);
        if (map == null) {
            map = loadUserLanguageMapToRedisCache();
        }
        return map;
    }

}
