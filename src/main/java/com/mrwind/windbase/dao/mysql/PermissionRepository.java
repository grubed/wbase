package com.mrwind.windbase.dao.mysql;

import com.mrwind.windbase.entity.mysql.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

}

