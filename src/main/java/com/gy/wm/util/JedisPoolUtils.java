package com.gy.wm.util;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;


/**
 * Created by 黄海 on 15-12-30.
 */

public class JedisPoolUtils implements Serializable {
    private static JedisPool pool;

    public JedisPoolUtils() throws FileNotFoundException,IOException{
        makepool();
    }

    public static void makepool() throws FileNotFoundException,IOException {
        if (pool == null) {
            JedisPoolConfig conf = new JedisPoolConfig();
            conf.setMaxTotal(1000);
            conf.setMaxWaitMillis(60000L);
//            RemoteLinker remoteLinker = new RemoteLinker();
//            String redisIP = remoteLinker.getPropety().getProperty("redis.ip");
//            String redisPORT = remoteLinker.getPropety().getProperty("redis.port");
//            pool = new JedisPool(conf, redisIP, Integer.parseInt(redisPORT),1000,"TA1WFIFXBJHUFPM3");
            pool = new JedisPool(conf, "118.118.118.11", 6379,1000);
        }
    }

    public  JedisPool getJedisPool() {
        return pool;
    }
}

