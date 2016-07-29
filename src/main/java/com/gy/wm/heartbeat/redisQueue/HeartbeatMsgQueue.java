package com.gy.wm.heartbeat.redisQueue;


import com.alibaba.fastjson.JSON;
import com.gy.wm.heartbeat.ObjectSerializeUtils;
import com.gy.wm.heartbeat.message.Message;
import com.gy.wm.heartbeat.model.HeartbeatMsgModel;
import com.gy.wm.util.JedisPoolUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;

/**
 * Created by TianyuanPan on 6/4/16.
 */

/**
 * 心跳消息队列类
 */
public class HeartbeatMsgQueue implements MessageQueue {


    private static final String QUEUE_KEY = QueueKeys.QUEUE_KEY_HEARTBEAT_MESSAGE; // redis 的键名
    private static final int dbIndex = 7;   // 索引号（数据库号）

    private Jedis jedis;
    private JedisPool jedisPool;
    private HeartbeatMsgModel heartbeat; // 心跳消息模型

    public HeartbeatMsgQueue() {

        heartbeat = new HeartbeatMsgModel();

        try {
            new JedisPoolUtils();
            jedisPool = JedisPoolUtils.getJedisPool();
        } catch (IOException e) {

            e.printStackTrace();
        }



    }


    /**
     * 设置心跳消息
     * @param message  心跳消息对象
     * @return 此消息队列对象
     */
    @Override
    public MessageQueue setMessage(Message message) {
        this.heartbeat = (HeartbeatMsgModel) message;
        return this;
    }

    /**
     * 把消息压入redis队列
     * @return 返回压入的消息对象
     */
    @Override
    public Message pushMessage() {

        try {
            String jsonMsg = JSON.toJSONString(this.heartbeat);
            byte[] bytes = ObjectSerializeUtils.serializeToBytes(jsonMsg);
            jedis = jedisPool.getResource();
            jedis.select(dbIndex);
            jedis.lpush(QUEUE_KEY.getBytes(), bytes);

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            jedisPool.returnResource(jedis);
        }

        return heartbeat;
    }


    /**
     * 取出redis队列中的消息
     * @return 取出的消息，异常时返回 null。
     */
    @Override
    public Message popMessage() {

        try {
            jedis = jedisPool.getResource();
            jedis.select(dbIndex);
            byte[] bytes = jedis.rpop(QueueKeys.QUEUE_KEY_HEARTBEAT_MESSAGE.getBytes());

            if (bytes == null)
                return null;

            String jsonMsg = (String) ObjectSerializeUtils.getEntityFromBytes(bytes);
            heartbeat = JSON.parseObject(jsonMsg, HeartbeatMsgModel.class);

            return heartbeat;

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            jedisPool.returnResource(jedis);
        }

        return null;
    }
}
