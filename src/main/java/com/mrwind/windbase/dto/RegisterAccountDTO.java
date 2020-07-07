package com.mrwind.windbase.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 注册账户
 *
 * @author hanjie
 */

public class RegisterAccountDTO {

    @NotBlank(message = "missing param: userName")
    @Pattern(regexp = "^[a-z0-9_-]{4,16}$", message = "error.username.invalid")
    private String userName;

    @NotBlank(message = "missing param: password")
    private String password;

    @NotNull(message = "missing param: countryCode")
    private Integer countryCode;

    @NotBlank(message = "missing param: tel")
    private String tel;

    public RegisterAccountDTO() {
    }

    public RegisterAccountDTO(Integer countryCode, String tel) {
        this.countryCode = countryCode;
        this.tel = tel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return "RegisterAccountDTO{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", countryCode=" + countryCode +
                ", tel='" + tel + '\'' +
                '}';
    }
}
