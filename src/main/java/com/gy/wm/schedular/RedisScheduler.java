package com.gy.wm.schedular;

import com.gy.wm.util.JedisPoolUtils;
import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.Scheduler;

import java.io.IOException;

public class RedisScheduler implements Scheduler {
    private static final String QUEUE_PREFIX = "queue_";

    @Override
    public void push(Request request, Task task) {

        Jedis jedis = null;
        try {
            jedis = new JedisPoolUtils().getJedisPool().getResource();
        } catch (IOException e) {
            e.printStackTrace();
        }

        jedis.rpush(QUEUE_PREFIX + task.getUUID(), request.getUrl());
    }


    @Override
    public Request poll(Task task) {
        Jedis jedis = null;
        try {
            jedis = new JedisPoolUtils().getJedisPool().getResource();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = jedis.lpop(QUEUE_PREFIX+task.getUUID());
        if (url==null) {
            return null;
        } else {
            return new Request(url);
        }
    }

    public static void main(String[] args) {

        System.out.println();
    }
}