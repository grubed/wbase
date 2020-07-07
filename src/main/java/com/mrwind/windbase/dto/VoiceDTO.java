package com.mrwind.windbase.dto;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public class VoiceDTO {
    @NotNull
    private String content;
    @NotNull
    private ArrayList<String> mobileList;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getMobileList() {
        return mobileList;
    }

    public void setMobileList(ArrayList<String> mobileList) {
        this.mobileList = mobileList;
    }
}
