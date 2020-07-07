package com.mrwind.windbase.dao.mongo;

import com.mrwind.windbase.entity.mongo.WindforceProject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * Created by michelshout on 2018/8/6.
 */
@Repository
public class WindforceProjectDao extends BasicDAO<WindforceProject, ObjectId>  {
    @Autowired
    public WindforceProjectDao(@Qualifier("windbase") Datastore ds) {
        super(ds);
    }

    /**
     * 获取指定的接口信息
     * @param projectType   项目类型，数据项见枚举WindForceProjectTypeEnum
     * @param project       项目/城市，数据项见枚举WindForceProjectEnum
     * @return
     */
    public WindforceProject findByProject(String projectType, String project) {
        Query<WindforceProject> query = super.getDs().createQuery(WindforceProject.class);
        query.field("projectType").equal(projectType);
        query.field("project").equal(project);
        return super.findOne(query);
    }

    /**
     * 获取指定的接口信息
     * @param wfProjectApiId   关键字id
     * @return
     */
    public WindforceProject findByWfProjectApiId(String wfProjectApiId) {
        Query<WindforceProject> query = super.getDs().createQuery(WindforceProject.class);
        query.field("wfProjectApiId").equal(wfProjectApiId);
        return super.findOne(query);
    }
}
