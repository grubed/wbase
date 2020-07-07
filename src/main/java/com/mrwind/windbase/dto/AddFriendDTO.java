package com.mrwind.windbase.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by CL-J on 2018/12/5.
 */
public class AddFriendDTO {

    @NotNull(message = "userId is null")
    private String userId;

    @NotNull(message = "otherId is null")
    private String otherId;

    public AddFriendDTO() {
    }

    public AddFriendDTO(@NotNull(message = "userId is null") String userId, @NotNull(message = "otherId is null") String otherId) {
        this.userId = userId;
        this.otherId = otherId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }

}
