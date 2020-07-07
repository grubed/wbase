package com.mrwind.windbase.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UserPushDTO {
    @NotNull
    private String actionType;
    @NotNull
    private String title;
    @NotNull
    private String body;
    @NotNull
    private List<String> userIdList;

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }
}
