package com.gy.wm.heartbeat.redisQueue;


import com.gy.wm.heartbeat.message.Message;

/**
 * Created by TianyuanPan on 6/4/16.
 */

/**
 * 消息队列接口
 */
public interface MessageQueue {

    /***
     * 设置要压入队列的消息
     * @param message 消息对象
     * @return 消息队列对象本身
     */
    public MessageQueue setMessage(Message message);

    /**
     * 把消息压入队列
     * @return 压入的消息对象
     */
    public Message pushMessage();

    /**
     * 从消息队列中取出消息
     * @return 取出的消息
     */
    public Message popMessage();
}
