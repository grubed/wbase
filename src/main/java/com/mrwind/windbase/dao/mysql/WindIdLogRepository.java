package com.mrwind.windbase.dao.mysql;

import com.mrwind.windbase.entity.mysql.WindIdLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/7/26
 */

public interface WindIdLogRepository extends JpaRepository<WindIdLog, Long> {

    long countByUserId(String userId);

}
