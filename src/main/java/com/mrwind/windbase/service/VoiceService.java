package com.mrwind.windbase.service;

import com.alibaba.fastjson.JSONObject;
import com.mrwind.windbase.common.util.TextUtils;
import com.mrwind.windbase.common.util.VoiceUtil;
import com.mrwind.windbase.dao.mongo.VoiceLogDao;
import com.mrwind.windbase.entity.mongo.VoiceLog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoiceService extends BaseService {
    public void sendVoice(List<String> tels, String content) {
        JSONObject jsonObject = VoiceUtil.DaSanTongSend(tels, content);
        mongoLog(tels, content, jsonObject);


    }
    public void mongoLog(List<String> tels, String content, JSONObject status) {
        VoiceLog voiceLog = new VoiceLog();
        voiceLog.setTels(tels);
        voiceLog.setContent(content);
        voiceLog.setStatus(status);
        voiceLogDao.save(voiceLog);
    }
}
