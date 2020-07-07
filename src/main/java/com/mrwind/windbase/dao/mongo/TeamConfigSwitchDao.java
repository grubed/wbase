package com.mrwind.windbase.dao.mongo;

import com.mrwind.windbase.entity.mongo.TeamConfigSwitch;
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
public class TeamConfigSwitchDao extends BasicDAO<TeamConfigSwitch, ObjectId> implements BaseMorphiaDao<TeamConfigSwitch, ObjectId> {

    @Autowired
    protected TeamConfigSwitchDao(@Qualifier("windbase") Datastore ds) {
        super(ds);
    }

    public TeamConfigSwitch findBy(String teamId, String key) {
        Query<TeamConfigSwitch> query = super.getDs().createQuery(TeamConfigSwitch.class);
        query.field("teamId").equal(teamId);
        query.field("key").equal(key);
        return super.findOne(query);
    }

    public List<TeamConfigSwitch> findBy(Collection<String> teamIds, String key) {
        Query<TeamConfigSwitch> query = super.getDs().createQuery(TeamConfigSwitch.class);
        query.field("teamId").in(teamIds);
        query.field("key").equal(key);
        return super.find(query).asList();
    }

}
