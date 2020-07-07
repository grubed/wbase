package com.mrwind.windbase.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wuyiming
 * Created by wuyiming on 2018/7/19.
 */
public class MoveMemberDTO {
    /**操作的用户id*/
    @NotEmpty(message = "error.userIds.size")
    private List<String> userIds;
    /**原团队id*/
    @NotBlank(message = "error.fromTeamId.null")
    private String fromTeamId;
    /**新团队id*/
    @NotBlank(message = "error.toTeamId.null")
    private String toTeamId;

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public String getFromTeamId() {
        return fromTeamId;
    }

    public void setFromTeamId(String fromTeamId) {
        this.fromTeamId = fromTeamId;
    }

    public String getToTeamId() {
        return toTeamId;
    }

    public void setToTeamId(String toTeamId) {
        this.toTeamId = toTeamId;
    }
}
