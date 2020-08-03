package com.fh.common;

public class ServerResponse {

    private int code;

    private String msg;

    private Object data;


    public static ServerResponse success(Object data) {

        return  new ServerResponse(200,"操作成功",data);
    }

    public static ServerResponse success() {

        return new ServerResponse(200,"操作成功");
    }

    public static ServerResponse error(){
        return new ServerResponse(meiju.ERROR.getCode(),meiju.ERROR.getRuname());
    }

    public static ServerResponse error(Object data){
        return new ServerResponse(meiju.ERROR.getCode(),meiju.ERROR.getRuname(),data);
    }

    public static ServerResponse loginerror(){
        return new ServerResponse(meiju.LOGINERROR.getCode(),meiju.LOGINERROR.getRuname());
    }


    public String getMsg() {
        return msg;
    }

    public ServerResponse(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public ServerResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
