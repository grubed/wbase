package com.mrwind.windbase.service;

import com.mrwind.windbase.dao.mongo.WindforceProjectDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by michelshout on 2018/8/6.
 */
@Service
public class WindforceProjectService {
    @Resource
    private WindforceProjectDao windforceProjectApiDao;


}
