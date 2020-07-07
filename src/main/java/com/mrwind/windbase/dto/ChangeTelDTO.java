package com.mrwind.windbase.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Description
 *
 * @author hanjie
 */

public class ChangeTelDTO {

    @NotBlank(message = "missing param: code")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ChangeTelDTO{" +
                "code='" + code + '\'' +
                '}';
    }

}
