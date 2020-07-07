package com.mrwind.windbase.entity.mongo;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Description
 *
 * @author hanjie
 * @date 2018-12-06
 */
@Entity(noClassnameStored = true)
public class FeignErrorLog {

    @Id
    private String id;
    private String functionName;
    private Date createTime;
    private String msg;

    public FeignErrorLog(String functionName, String msg) {
        this.functionName = functionName;
        this.msg = msg;
        this.createTime = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
