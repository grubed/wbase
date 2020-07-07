package com.mrwind.windbase.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 注册账户
 *
 * @author hanjie
 */

public class RegisterAccountBO {

    private Integer countryCode;

    private String tel;

    private String name;

    private String avatar;

    public RegisterAccountBO() {
    }

    public RegisterAccountBO(Integer countryCode, String tel) {
        this.countryCode = countryCode;
        this.tel = tel;
    }

    public RegisterAccountBO(Integer countryCode, String tel, String name) {
        this.countryCode = countryCode;
        this.tel = tel;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
