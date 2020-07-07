package com.mrwind.windbase.dao.mysql;

import com.mrwind.windbase.entity.mysql.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by CL-J on 2018/12/5.
 */
public interface UserFriendRepository extends JpaRepository<UserFriend,Long> {

    @Query(value = "select * from user_friend t where (t.userId = :id or t.otherId = :id)",nativeQuery = true)
    List<UserFriend> findByUserIdOrOtherId(@Param("id") String id);

    @Query(value = "select * from user_friend where (userId = :id and otherId = :otherId) or (userId = :otherId and otherId = :id)",nativeQuery = true)
    UserFriend get2UserRelation(@Param("id") String id, @Param("otherId") String otherId);


}
