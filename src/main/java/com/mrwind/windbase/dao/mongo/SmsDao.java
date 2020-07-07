package com.mrwind.windbase.dao.mongo;

import com.mrwind.windbase.entity.mongo.Sms;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * Description
 *
 * @author hanjie
 */

@Repository
public class SmsDao extends BasicDAO<Sms, ObjectId> {

    @Autowired
    protected SmsDao(@Qualifier("windbase") Datastore ds) {
        super(ds);
    }

}
