package com.mrwind.windbase.common.exception;

public class FoundException extends Exception {
    private String msg;
    public FoundException(String msg) {
        this.msg = msg;
    }
    public String getErrMsg() {
        return this.msg;
    }
}
