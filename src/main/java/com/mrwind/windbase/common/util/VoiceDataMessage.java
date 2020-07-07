package com.mrwind.windbase.common.util;

public class VoiceDataMessage {
    private String msgid;
    private String callee;
    private String text;
    private  int playmode;

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getCallee() {
        return callee;
    }

    public void setCallee(String callee) {
        this.callee = callee;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPlaymode() {
        return playmode;
    }

    public void setPlaymode(int playmode) {
        this.playmode = playmode;
    }

    public int getCalltype() {
        return calltype;
    }

    public void setCalltype(int calltype) {
        this.calltype = calltype;
    }

    private  int calltype;

}
