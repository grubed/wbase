package com.mrwind.windbase.dao.mysql;

import com.mrwind.windbase.entity.mysql.CapitalDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Created by zjw on 2018/8/23  上午9:54
 */
public interface CapitalDetailRepository extends JpaRepository<CapitalDetail,Long> {

    @Query(value = "select d.title as title,d.createTime as time,d.subType as type,d.cost as bill " +
            "from CapitalDetail d where d.createTime >:startTime and d.createTime <:endTime and " +
            "d.rootTeamId =:rootTeamId order by d.createTime desc")
    Page<Map<String,Object>> findByRootTeamIdAndDateAndPage(@Param("startTime")Timestamp startTime, @Param("endTime") Timestamp endTime, @Param("rootTeamId") String rootTeamId, Pageable pageable);

}
