package com.mrwind.windbase.dao.mysql;

import com.mrwind.windbase.entity.mysql.RootTeamPriceRuleRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by zjw on 2018/9/5  上午10:39
 */
public interface RootTeamPriceRuleRepository extends JpaRepository<RootTeamPriceRuleRelation,Long> {

    /**
     * 根据rooTeamId获取对应的计费规则IdList
     * @param rootTeamId
     * @return
     */
    @Query(value = "select t.priceRuleId from RootTeamPriceRuleRelation t where t.rootTeamId = :rootTeamId ")
    List<Long> getPriceRuleIdListByRootTeamId(@Param("rootTeamId") String rootTeamId);

    List<RootTeamPriceRuleRelation> findByRootTeamId(String rootTeamId);
}
