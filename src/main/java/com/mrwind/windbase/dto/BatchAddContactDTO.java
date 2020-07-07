package com.mrwind.windbase.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by CL-J on 2019/3/24.
 */
public class BatchAddContactDTO {
    @NotNull
    private String userId;

    private List<String> friendIds;
    @NotNull
    private String groupId;

    private NewUserDTO newUser;

    public BatchAddContactDTO(@NotNull String userId, List<String> friendIds, @NotNull String groupId, NewUserDTO newUser) {
        this.userId = userId;
        this.friendIds = friendIds;
        this.groupId = groupId;
        this.newUser = newUser;
    }

    public BatchAddContactDTO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getFriendIds() {
        return friendIds;
    }

    public void setFriendIds(List<String> friendIds) {
        this.friendIds = friendIds;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public NewUserDTO getNewUser() {
        return newUser;
    }

    public void setNewUser(NewUserDTO newUser) {
        this.newUser = newUser;
    }

    @Override
    public String toString() {
        return "BatchAddContactDTO{" +
                "userId='" + userId + '\'' +
                ", friendIds=" + friendIds +
                ", groupId='" + groupId + '\'' +
                ", newUser=" + newUser +
                '}';
    }
}
