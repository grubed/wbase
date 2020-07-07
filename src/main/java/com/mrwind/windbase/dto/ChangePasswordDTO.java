package com.mrwind.windbase.dto;

import javax.validation.constraints.NotBlank;

/**
 * Description
 *
 * @author hanjie
 */

public class ChangePasswordDTO {

    @NotBlank(message = "missing param: newPassword")
    private String newPassword;

    @NotBlank(message = "missing param: tel")
    private String tel;

    @NotBlank(message = "missing param: code")
    private String code;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ChangePasswordDTO{" +
                "newPassword='" + newPassword + '\'' +
                ", tel='" + tel + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

}
