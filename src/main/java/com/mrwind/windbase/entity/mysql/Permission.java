package com.mrwind.windbase.entity.mysql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * 权限配置
 *
 * @author hanjie
 * @date 2019-02-25
 */

@Entity
@Table(name = "permission_config")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @JsonIgnore
    private Date createTime;

    /**
     * 权限类型
     */
    private String type;

    /**
     * 授权人 id
     */
    private String grantorId;

    /**
     * 团队分组 id
     */
    private String teamId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGrantorId() {
        return grantorId;
    }

    public void setGrantorId(String grantorId) {
        this.grantorId = grantorId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

}
