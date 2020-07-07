package com.mrwind.windbase.common.exception;

public class RepeatException extends Exception {
    private String msg;
    public RepeatException(String msg) {
        this.msg = msg;
    }
    public String getErrMsg() {
        return this.msg;
    }
}
