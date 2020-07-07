package com.mrwind.windbase.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/7/26
 */

public class GetUserByJobDTO {

    @NotNull(message = "missing param: teamId")
    private String teamId;

    @NotNull(message = "missing param: isand")
    private Boolean isand;

    @NotEmpty(message = "missing param: jobIDs")
    private List<String> jobIDs;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Boolean getIsand() {
        return isand;
    }

    public void setIsand(Boolean isand) {
        this.isand = isand;
    }

    public List<String> getJobIDs() {
        return jobIDs;
    }

    public void setJobIDs(List<String> jobIDs) {
        this.jobIDs = jobIDs;
    }

    @Override
    public String toString() {
        return "GetUserByJobDTO{" +
                "teamId='" + teamId + '\'' +
                ", isand=" + isand +
                ", jobIDs=" + jobIDs +
                '}';
    }

}
