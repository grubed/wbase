package com.mrwind.windbase.entity.mysql;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by CL-J on 2018/12/5.
 */

@Entity
@Table(name = "user_friend")
public class UserFriend implements Serializable{

    private static final long serialVersionUID = -7078378116659979937L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //用户1
    private String userId;
    //用户2
    private String otherId;
    //0 聯繫人關係 1 好友關係
    private int     isFriend;
    @UpdateTimestamp
    @Column()
    private Date updateTime;
    @CreationTimestamp
    private Date createTime;

    public UserFriend() {
    }

    public UserFriend(String userId, String otherId, int isFriend) {
        this.userId = userId;
        this.otherId = otherId;
        this.isFriend = isFriend;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String other) {
        this.otherId = other;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }
}
