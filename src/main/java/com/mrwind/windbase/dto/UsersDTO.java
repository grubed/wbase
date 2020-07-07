package com.mrwind.windbase.dto;

import org.apache.kafka.common.protocol.types.Field;

import java.util.List;

/**
 * Created by CL-J on 2019/3/22.
 */
public class UsersDTO {
    private List<String> userIds;

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public UsersDTO() {
    }

    public UsersDTO(List<String> userIds) {
        this.userIds = userIds;
    }
}
