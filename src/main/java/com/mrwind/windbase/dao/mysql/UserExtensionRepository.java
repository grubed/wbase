package com.mrwind.windbase.dao.mysql;

import com.mrwind.windbase.entity.mysql.UserExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/7/25
 */

public interface UserExtensionRepository extends JpaRepository<UserExtension, Long> {

    /**
     * 根据 userId 获取
     *
     * @param userId userId
     * @return result
     */
    UserExtension findByUserId(String userId);
    
    List<UserExtension> findByUserIdIn(Collection<String> userId);

    List<UserExtension> findAllByReceiveOrderStatus(Integer receiveOrderStatus);


}
