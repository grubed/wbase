package com.mrwind.windbase.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Description
 *
 * @author hanjie
 */

public class SmsMultiLanguageDTO {

    @NotBlank(message = "error.parameter")
    private String tel;

    @NotNull(message = "error.parameter")
    private Map<String, String> messageMap;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Map<String, String> getMessageMap() {
        return messageMap;
    }

    public void setMessageMap(Map<String, String> messageMap) {
        this.messageMap = messageMap;
    }
    
}
