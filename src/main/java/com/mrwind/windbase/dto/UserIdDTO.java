package com.mrwind.windbase.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by zjw on 2018/8/23  下午3:28
 */
public class UserIdDTO {
    @NotNull
    private List<String> userIdList;

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }
}
