package com.gy.wm.heartbeat.redisQueue;

/**
 * Created by TianyuanPan on 6/4/16.
 */

/**
 * 队列键类
 */
public final class QueueKeys {

    public static String QUEUE_KEY_TASK = "crawlerTask::taskQueue"; // 任务队列键名称
    public static String QUEUE_KEY_HEARTBEAT_MESSAGE = "crawlerTask::heartbeatMsgQueue"; // 心跳队列键名称
}
