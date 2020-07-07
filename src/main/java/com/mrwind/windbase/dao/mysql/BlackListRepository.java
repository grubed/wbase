package com.mrwind.windbase.dao.mysql;

import com.mrwind.windbase.entity.mysql.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * Created by CL-J on 2019/1/7.
 */
public interface BlackListRepository extends JpaRepository<BlackList,Long>{

     BlackList findByUserIdAndTarget(String userId,String target);

     List<BlackList> findAllByUserId(String userId);

     int deleteAllByUserIdAndTarget(String userId,String target);
}
