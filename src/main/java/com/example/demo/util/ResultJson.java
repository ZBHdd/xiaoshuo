package com.example.demo.util;

public class ResultJson {
    private Boolean success;

    private String msg;

    private Object obj;

    private String code;

    public Boolean getSuccess() {
        return success;
    }

    public ResultJson setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResultJson setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getObj() {
        return obj;
    }

    public ResultJson setObj(Object obj) {
        this.obj = obj;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ResultJson setCode(String code) {
        this.code = code;
        return this;
    }
}
