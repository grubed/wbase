package com.mrwind.windbase.dto;

import javax.validation.constraints.NotBlank;

/**
 * Description
 *
 * @author hanjie
 */

public class UserLoginDTO {

    @NotBlank(message = "missing param: userName")
    private String userName;

    @NotBlank(message = "missing param: password")
    private String password;

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

    @Override
    public String toString() {
        return "UserLoginDTO{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
