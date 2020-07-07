package com.mrwind.windbase.entity.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户 App 语言
 *
 * @author hanjie
 * @date 2018/9/10
 */

@Entity
@Table(name = "user_app_language")
public class UserAppLanguage {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "language")
    private String language;

    public UserAppLanguage() {
    }

    public UserAppLanguage(String userId, String language) {
        this.userId = userId;
        this.language = language;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
