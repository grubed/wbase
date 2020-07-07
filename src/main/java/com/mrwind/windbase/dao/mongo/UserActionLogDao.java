package com.mrwind.windbase.dao.mongo;

import com.mrwind.windbase.entity.mongo.UserActionLog;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author wuyiming
 * Created by wuyiming on 2018/4/28.
 */
@Component
public class UserActionLogDao extends BasicDAO<UserActionLog, ObjectId> {

    @Autowired
    public UserActionLogDao(@Qualifier("windbase") Datastore ds) {
        super(ds);
    }

}
