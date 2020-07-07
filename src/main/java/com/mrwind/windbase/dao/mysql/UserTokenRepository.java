package com.mrwind.windbase.dao.mysql;

import com.mrwind.windbase.entity.mysql.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/7/26
 */

public interface UserTokenRepository extends JpaRepository<UserToken, String> {

    /**
     * 根据 useId 获取
     */
    UserToken findByUserId(String userId);

    int deleteByUserId(String userId);

    int deleteAllByUserIdIn(Collection<String> userId);

    boolean existsByUserId(String userId);

    List<UserToken> findAllByUserIdIn(Collection<String> userIds);

}
