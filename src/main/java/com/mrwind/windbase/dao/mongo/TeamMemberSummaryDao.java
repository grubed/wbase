package com.mrwind.windbase.dao.mongo;

import com.mrwind.windbase.common.constant.TeamMemberSummaryType;
import com.mrwind.windbase.common.util.TimeUtil;
import com.mrwind.windbase.entity.mongo.TeamMemberSummary;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/8/24
 */
@Repository
public class TeamMemberSummaryDao extends BasicDAO<TeamMemberSummary, ObjectId> implements BaseMorphiaDao {

    @Autowired
    protected TeamMemberSummaryDao(@Qualifier("windbase") Datastore ds) {
        super(ds);
    }

    public TeamMemberSummary get(String date, TeamMemberSummaryType type, String rootTeamId) {
        Query<TeamMemberSummary> query = super.getDs().createQuery(TeamMemberSummary.class);
        query.field("rootTeamId").equal(rootTeamId);
        query.field("type").equal(type.name());
        query.field("date").equal(date);
        return super.findOne(query);
    }

    /**
     * 将新添加的成员或者离职成员添加到记录表中
     * 新添加的成员: 先删除之前集合中的该成员 id（因为还涉及移动成员），然后添加
     * 离职成员: 直接添加记录
     *
     * @param type       类型
     * @param rootTeamId 根团队 id
     * @param userId     用户 id
     * @param teamId     新团队 id
     */
    private void add(TeamMemberSummaryType type, String rootTeamId, String userId, String teamId) {
        String date = TimeUtil.formatDate(new Date(), TimeUtil.yyyyMMdd1);
        TeamMemberSummary tms = get(date, type, rootTeamId);
        if (tms == null) {
            tms = new TeamMemberSummary();
            tms.setType(type.name());
            tms.setDate(date);
            tms.setRootTeamId(rootTeamId);
        }
        if (type == TeamMemberSummaryType.NEW) {
            for (TeamMemberSummary.Summary summary : tms.getSummaries()) {
                if (summary.getUserIds().remove(userId)) {
                    break;
                }
            }
        }
        TeamMemberSummary.Summary summary = new TeamMemberSummary.Summary(teamId);
        int index = tms.getSummaries().indexOf(summary);
        if (index != -1) {
            tms.getSummaries().get(index).getUserIds().add(userId);
        } else {
            summary.getUserIds().add(userId);
            tms.getSummaries().add(summary);
        }
        insertOrUpdateById(tms);
    }

    /**
     * 添加已离职人员记录
     */
    public void addQuitMember(String userId, String teamId, String rootTeamId) {
        add(TeamMemberSummaryType.QUIT, rootTeamId, userId, teamId);
    }

    /**
     * 添加新成员记录
     */
    public void addNewMember(String userId, String teamId, String rootTeamId) {
        add(TeamMemberSummaryType.NEW, rootTeamId, userId, teamId);
    }

    /**
     * 查询特定日期范围某个团队特定类型人员统计数据
     *
     * @param rootTeamId 根团队 id
     * @param startDate  起始时间
     * @param endDate    结束时间
     * @param type       类型
     * @return List<TeamMemberSummary>
     */
    public List<TeamMemberSummary> findRangeTeamTypeSummary(String rootTeamId, String startDate, String endDate, TeamMemberSummaryType type) {
        Query<TeamMemberSummary> query = super.getDs().createQuery(TeamMemberSummary.class);
        query.field("rootTeamId").equal(rootTeamId);
        query.field("type").equal(type.name());
        query.field("date").lessThanOrEq(endDate);
        query.field("date").greaterThanOrEq(startDate);
        return super.find(query).asList();
    }

}
