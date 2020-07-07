package com.mrwind.windbase.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class OperationTeamDTO {

    @NotNull
    private List<String> teamIdList;

    private String name;

    private String project;

    public List<String> getTeamIdList() {
        return teamIdList;
    }

    public void setTeamIdList(List<String> teamIdList) {
        this.teamIdList = teamIdList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

}
