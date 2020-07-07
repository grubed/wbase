package com.mrwind.windbase.dto;

import javax.validation.constraints.NotBlank;

/**
 * Description
 *
 * @author hanjie
 * @date 2019-01-14
 */

public class TeamSwitchDTO {

    @NotBlank(message = "missing param: key")
    private String key;

    @NotBlank(message = "missing param: status")
    private String status;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
