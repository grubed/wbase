package com.mrwind.windbase.dao.mysql;

import com.mrwind.windbase.entity.mysql.ExpressBillingMode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * Created by CL-J on 2018/11/20.
 */
public interface ExpressBillingModeRepository extends JpaRepository<ExpressBillingMode,String> {

     ExpressBillingMode findByDefaultMode(boolean defaultMode);

     List<ExpressBillingMode> findByIdIn(Collection<String> id);

}
