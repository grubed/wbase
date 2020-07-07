package com.mrwind.windbase.dto;

import com.mrwind.windbase.common.annotation.InEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 验证码登录 body
 *
 * @author hanjie
 */

public class CodeLoginDTO {

    @NotNull(message = "missing param: countryCode")
    private Integer countryCode;

    @NotBlank(message = "missing param: tel")
    private String tel;

    @NotBlank(message = "missing param: verCode")
    private String verCode;

    private String deviceID;

    private String country;

    private String appName;

    private Boolean autoRegister;

//    @InEnum(required = false, target = LanguageEnum.class, message = "error param language: UnSupport")
//    private String language;

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

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

//    public String getLanguage() {
//        return language;
//    }
//
//    public void setLanguage(String language) {
//        this.language = language;
//    }

    public Boolean getAutoRegister() {
        return autoRegister;
    }

    public void setAutoRegister(Boolean autoRegister) {
        this.autoRegister = autoRegister;
    }

    @Override
    public String toString() {
        return "CodeLoginDTO{" +
                "countryCode=" + countryCode +
                ", tel='" + tel + '\'' +
                ", verCode='" + verCode + '\'' +
                ", deviceID='" + deviceID + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

}
