package com.mrwind.windbase.common.util;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Description
 *
 * @author hanjie
 * @date 2018/6/4
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Result<T> {

    public static final String CODE_SUCCESS = "1";
    public static final String CODE_FAILED = "0";
    public static final String CODE_ERROR = "-1";

    private String code;
    private String msg;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static Result getSuccess() {
        Result result = new Result();
        result.code = CODE_SUCCESS;
        result.msg = LocaleType.getMessage("success");
        return result;
    }

    public static Result getSuccess(Object obj) {
        Result result = new Result();
        result.code = CODE_SUCCESS;
        result.msg = LocaleType.getMessage("success");
        result.data = obj;
        return result;
    }

    public static Result getSuccessI18N(String msgKey) {
        Result result = new Result();
        result.code = CODE_SUCCESS;
        result.msg = LocaleType.getMessage("success");
        result.data = LocaleType.getMessage(msgKey);
        return result;
    }

    public static Result getFail(String msg) {
        Result result = new Result();
        result.code = CODE_FAILED;
        result.msg = msg;
        return result;
    }

    public static Result getFailI18N(String msgKey) {
        Result result = new Result();
        result.code = CODE_FAILED;
        result.msg = LocaleType.getMessage(msgKey);
        return result;
    }

    public static Result getFailI18N(String msgKey, String customMsg) {
        Result result = new Result();
        result.code = CODE_FAILED;
        result.msg = LocaleType.getMessage(msgKey) + " " + customMsg;
        return result;
    }

}
