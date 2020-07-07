package com.mrwind.windbase.entity.mongo;

import com.alibaba.fastjson.JSONObject;
import org.mongodb.morphia.annotations.Id;

import java.util.List;

public class VoiceLog {
    @Id
    private String id;

    private List<String> tels;
    private String content;
    private JSONObject status;

    public JSONObject getStatus() {
        return status;
    }

    public void setStatus(JSONObject status) {
        this.status = status;
    }

    public List<String> getTels() {
        return tels;
    }

    public void setTels(List<String> tels) {
        this.tels = tels;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
