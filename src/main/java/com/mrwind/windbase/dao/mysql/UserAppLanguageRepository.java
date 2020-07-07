package com.mrwind.windbase.dao.mysql;

import com.mrwind.windbase.entity.mysql.UserAppLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/9/10
 */

public interface UserAppLanguageRepository extends JpaRepository<UserAppLanguage, String> {

    UserAppLanguage findByUserId(String userId);

}
