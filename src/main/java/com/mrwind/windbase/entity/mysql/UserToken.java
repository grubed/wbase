package com.mrwind.windbase.entity.mysql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/7/26
 */
@Entity
@Table(name = "user_token")
public class UserToken {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "current_token")
    private String currentToken;

    @UpdateTimestamp
    @JsonIgnore
    private Date updateTime;

    public UserToken() {
    }

    public UserToken(String userId, String currentToken) {
        this.userId = userId;
        this.currentToken = currentToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(String currentToken) {
        this.currentToken = currentToken;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "UserToken{" +
                "userId='" + userId + '\'' +
                ", currentToken='" + currentToken + '\'' +
                '}';
    }

}
