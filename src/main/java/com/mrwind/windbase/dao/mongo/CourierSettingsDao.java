package com.mrwind.windbase.dao.mongo;

import com.mrwind.windbase.entity.mongo.CourierSettings;
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
 * Description
 *
 * @author hanjie
 */

@Repository
public class CourierSettingsDao extends BasicDAO<CourierSettings, ObjectId> {

    @Autowired
    protected CourierSettingsDao(@Qualifier("windbase") Datastore ds) {
        super(ds);
    }

    public CourierSettings findByUserId(String userId) {
        Query<CourierSettings> query = super.getDs().createQuery(CourierSettings.class);
        query.field("userId").equal(userId);
        return super.findOne(query);
    }

    public List<CourierSettings> findByUserIdIn(Collection<String> userIds) {
        Query<CourierSettings> query = super.getDs().createQuery(CourierSettings.class);
        query.field("userId").in(userIds);
        return super.find(query).asList();
    }

    public List<CourierSettings> findByHasExpressBillingMode() {
        Query<CourierSettings> query = super.getDs().createQuery(CourierSettings.class);
        query.field("express.billingModeId").exists();
        return super.find(query).asList();
    }

}
