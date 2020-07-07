package com.mrwind.windbase.dto;

import javax.validation.constraints.NotBlank;

/**
 * Description
 *
 * @author hanjie
 */

public class SmsDTO {

    @NotBlank(message = "error.parameter")
    private String tel;

    @NotBlank(message = "error.parameter")
    private String message;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SmsDTO{" +
                ", tel='" + tel + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}
