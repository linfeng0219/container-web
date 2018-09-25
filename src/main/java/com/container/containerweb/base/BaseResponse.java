package com.container.containerweb.base;

public class BaseResponse {

    private int code;

    private String msg;

    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static BaseResponse success(){
        BaseResponse response = new BaseResponse();
        response.setCode(1000);
        response.setMsg("success");
        return response;
    }

    public static BaseResponse success(Object data){
        BaseResponse response = new BaseResponse();
        response.setCode(1000);
        response.setMsg("success");
        response.setData(data);
        return response;
    }

    public static BaseResponse error(int code, String msg){
        BaseResponse response = new BaseResponse();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
