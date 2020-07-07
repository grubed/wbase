package com.mrwind.windbase.dao.mongo;

import com.mrwind.windbase.entity.mongo.CourierSettings;
import com.mrwind.windbase.entity.mongo.CourierSettingsLog;
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
 * @date 2018-11-28
 */
@Repository
public class CourierSettingsLogDao extends BasicDAO<CourierSettingsLog, ObjectId> {

    @Autowired
    protected CourierSettingsLogDao(@Qualifier("windbase") Datastore ds) {
        super(ds);
    }

}
