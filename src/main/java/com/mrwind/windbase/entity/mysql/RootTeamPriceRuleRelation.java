package com.mrwind.windbase.entity.mysql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zjw on 2018/9/5  上午10:29
 */
@Entity
@Table(name="root_team_price_rule")
public class RootTeamPriceRuleRelation implements Serializable {

    private static final long serialVersionUID = -6955202385681548937L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="rootTeamId")
    private String rootTeamId;

    @Column(name="priceRuleId")
    private long priceRuleId;

    @CreationTimestamp
    @JsonIgnore
    private Date createTime;
    @UpdateTimestamp
    @JsonIgnore
    private Date updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRootTeamId() {
        return rootTeamId;
    }

    public void setRootTeamId(String rootTeamId) {
        this.rootTeamId = rootTeamId;
    }

    public long getPriceRuleId() {
        return priceRuleId;
    }

    public void setPriceRuleId(long priceRuleId) {
        this.priceRuleId = priceRuleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
