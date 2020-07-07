package com.mrwind.windbase.dao.mysql;

import com.mrwind.windbase.entity.mysql.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;

/**
 * Created by CL-J on 2018/8/3.
 */
public interface RoleTypeRepository extends JpaRepository<RoleType, String>{

    RoleType findByName(@NotNull() String name);
}
