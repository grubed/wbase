package com.mrwind.windbase.common.exception;

public class BalanceNotEnoughException extends Exception {
    public String getErrMsg() {
        return "余额不足";
    }
}
