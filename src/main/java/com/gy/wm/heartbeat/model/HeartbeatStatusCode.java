package com.gy.wm.heartbeat.model;

/**
 * Created by TianyuanPan on 6/8/16.
 */

/**
 * 心跳状态码
 */
public final class HeartbeatStatusCode {

    public static final int FINISHED = 0x00;  // 爬取完成
    public static final int CRAWLING = 0x01;  // 正在爬取
    public static final int TIMEOUT =  0x03;  // 心跳超时
    public static final int EXECEPTION_STOP = 0x07; // 异常停止
    public static final int STARTING = 0x0f;   // 正在爬取
}
