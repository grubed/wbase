package com.mrwind.windbase.dao.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mrwind.windbase.entity.mongo.TeamPosition;
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
public class TeamPositionDao extends BasicDAO<TeamPosition, ObjectId> {

    @Autowired
    public TeamPositionDao(@Qualifier("windbase") Datastore ds) {
        super(ds);
    }

    public List<TeamPosition> findByTeamIdsIn(Collection<String> teamIds) {
        Query<TeamPosition> query = super.getDs().createQuery(TeamPosition.class);
        query.field("teamId").in(teamIds);

        return super.find(query).asList();
    }

    public TeamPosition findByTeamId(String teamId) {
        Query<TeamPosition> query = super.getDs().createQuery(TeamPosition.class);
        query.field("teamId").equal(teamId);
        return super.findOne(query);
    }

    public List<DBObject> nearSphere(String locationField, double lat, double lng, Collection<String> teamIds) {
        return nearSphere(locationField, lat, lng, -1, teamIds);
    }

    public List<DBObject> nearSphere(String locationField, double lat, double lng, int limit, Collection<String> teamIds) {
        DBObject query = new BasicDBObject();
        query.put(
                locationField,
                new BasicDBObject("$nearSphere", new BasicDBObject("$geometry", new BasicDBObject("type", "Point").append("coordinates", new double[]{lng, lat})))
        );
        query.put("type", new BasicDBObject().append("$ne", 0));
        query.put("_id", new BasicDBObject().append("$in", teamIds));
        if (limit < 0) {
            return getDs().getCollection(TeamPosition.class).find(query).toArray();
        } else {
            return getDs().getCollection(TeamPosition.class).find(query).limit(limit).toArray();
        }
    }

}
