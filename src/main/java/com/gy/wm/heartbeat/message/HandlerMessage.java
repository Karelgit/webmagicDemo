package com.gy.wm.heartbeat.message;

/**
 * Created by TianyuanPan on 6/4/16.
 */

/**
 * 处理器消息类
 */
public class HandlerMessage {

    private String message;
    private boolean status;

    private HandlerMessage(String message, boolean status){

        this.message = message;
        this.status = status;
    }

    /**
     * 获取消息
     * @return String json 消息
     * json 格式：
     *
     * {
     *     "message":"String",
     *     "status":"String"
     * }
     *
     */
    private String getMessage() {

        return "{\"message\":\"" + this.message + "\", \"status\":" + this.status + "}";
    }

    public  static String getMessage(String message, boolean status){

        return new HandlerMessage(message, status).getMessage();
    }

}
