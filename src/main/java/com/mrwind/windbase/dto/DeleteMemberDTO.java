package com.mrwind.windbase.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author wuyiming
 * Created by wuyiming on 2018/7/19.
 */
public class DeleteMemberDTO {
    /**团队id*/
    @NotBlank(message = "error.team.id.null")
    private String teamId;

    @NotEmpty(message = "error.param")
    private List<String> userIds;


    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }
}
