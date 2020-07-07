package com.mrwind.windbase.service;

import com.mrwind.windbase.dao.mysql.PermissionRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-02-25
 */

@Service
public class PermissionService extends BaseService {

    @Resource
    private PermissionRepository permissionRepository;
    

}
