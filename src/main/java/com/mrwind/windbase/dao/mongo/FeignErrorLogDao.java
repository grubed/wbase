package com.mrwind.windbase.dao.mongo;

import com.mrwind.windbase.entity.mongo.FeignErrorLog;
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
public class FeignErrorLogDao extends BasicDAO<FeignErrorLog, ObjectId> {

    @Autowired
    protected FeignErrorLogDao(@Qualifier("windbase") Datastore ds) {
        super(ds);
    }

}
