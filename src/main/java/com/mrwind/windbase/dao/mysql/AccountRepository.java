package com.mrwind.windbase.dao.mysql;

import com.mrwind.windbase.entity.mysql.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;


/**
 * Created by CL-J on 2018/7/24.
 */
public interface AccountRepository extends JpaRepository<Account,String>{


    @Modifying
    @Query("update Account a set a.amount=a.amount+?2 where a.userId=?1")
    void UpdateAccountAmount(String userId, BigDecimal Amount);

    @Modifying
    @Query("update Account a set a.amount=a.amount-?2 where a.userId=?1 and a.amount > ?2")
    int consumeAccountAmount(String userId, BigDecimal Amount);

//    @Query("select a.amount from  account a where a.userId=?1")
//    BigDecimal findByUserId(String userId);
}
