package com.mrwind.windbase.dao.mysql;

import com.mrwind.windbase.entity.mysql.PriceRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by zjw on 2018/8/22  下午3:18
 */
public interface PriceRuleRepository extends JpaRepository<PriceRule,Long> {

    List<PriceRule> findByProjectAndDefaultMode(String project,boolean defaultMode);

    List<PriceRule> findByIdIn(Collection<Long> priceRuleIds);

    @Query(value = "select * from price_rule t where t.id in (select r.priceRuleId from root_team_price_rule r where r.rootTeamId = :rootTeamId )",nativeQuery = true)
    List<PriceRule> findByRootTeamId(@Param("rootTeamId") String rootTeamId);

    List<PriceRule> findByName(String name);

    PriceRule findById(long id);
    }
