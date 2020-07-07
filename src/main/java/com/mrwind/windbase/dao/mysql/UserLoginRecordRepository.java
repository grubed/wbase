package com.mrwind.windbase.dao.mysql;

import com.mrwind.windbase.entity.mysql.UserLoginRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/8/30
 */

public interface UserLoginRecordRepository extends JpaRepository<UserLoginRecord, Long> {

    boolean existsByUserIdAndTeamId(String userId, String teamId);

}
