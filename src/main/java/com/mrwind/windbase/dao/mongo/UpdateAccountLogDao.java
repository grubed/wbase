package com.mrwind.windbase.dao.mongo;


import com.mrwind.windbase.entity.mongo.TeamPosition;
import com.mrwind.windbase.entity.mongo.UpdateAccountLog;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


/**
 * @author wuyiming
 * Created by wuyiming on 2018/7/24.
 */
@Repository
public class UpdateAccountLogDao extends BasicDAO<UpdateAccountLog, ObjectId> {

    @Autowired
    public UpdateAccountLogDao(@Qualifier("windbase") Datastore ds) {
        super(ds);
    }

    public List<UpdateAccountLog> findByUserId(String userId, String project, Integer offset, Integer limit) {
        Query<UpdateAccountLog> query = super.getDs().createQuery(UpdateAccountLog.class);

        // query.field("source").equal(project);
        query.field("targetUserId").equal(userId);
        query.offset(offset).limit(limit);
        List<UpdateAccountLog> updateAccountLogs = super.find(query).asList();
        return updateAccountLogs;
    }

    public Long countByUserId(String userId) {
        Query<UpdateAccountLog> query = super.getDs().createQuery(UpdateAccountLog.class);
        query.field("targetUserId").equal(userId);
        return super.find(query).countAll();
    }
}
