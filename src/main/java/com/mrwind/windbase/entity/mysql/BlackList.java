package com.mrwind.windbase.entity.mysql;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by CL-J on 2019/1/7.
 */

@Entity
@Table(name = "black_list")
public class BlackList implements Serializable{

    private static final long serialVersionUID = 1246781404310521152L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userId;

    private String target;

    @CreationTimestamp
    private Date createTime;

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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BlackList(String userId, String target) {
        this.userId = userId;
        this.target = target;
    }
}
