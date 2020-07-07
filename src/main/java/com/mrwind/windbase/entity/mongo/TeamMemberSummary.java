package com.mrwind.windbase.entity.mongo;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.*;

/**
 * 团队人数操作记录（包括新成员、已离职人员）
 *
 * @author hanjie
 * @date 2018/8/23
 */
@Entity(noClassnameStored = true)
public class TeamMemberSummary {

    @Id
    private String id;

    private String date;

    private String rootTeamId;

    private String type;

    private List<Summary> summaries = new ArrayList<>();

    public static class Summary {

        private String teamId;

        private Set<String> userIds = new HashSet<>();

        public Summary() {
        }

        public Summary(String teamId) {
            this.teamId = teamId;
        }

        public String getTeamId() {
            return teamId;
        }

        public void setTeamId(String teamId) {
            this.teamId = teamId;
        }

        public Set<String> getUserIds() {
            return userIds;
        }

        public void setUserIds(Set<String> userIds) {
            this.userIds = userIds;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Summary summary = (Summary) o;
            return Objects.equals(teamId, summary.teamId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(teamId);
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRootTeamId() {
        return rootTeamId;
    }

    public void setRootTeamId(String rootTeamId) {
        this.rootTeamId = rootTeamId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Summary> getSummaries() {
        return summaries;
    }

    public void setSummaries(List<Summary> summaries) {
        this.summaries = summaries;
    }
}
