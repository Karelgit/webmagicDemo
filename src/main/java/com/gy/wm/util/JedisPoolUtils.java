package com.gy.wm.util;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

/**
 * Created by 黄海 on 15-12-30.
 */
public class JedisPoolUtils implements Serializable {
    private static JedisPool pool;

    public JedisPoolUtils() throws FileNotFoundException,IOException{
        makepool();
    }

    public Properties getPropety()   {
        String result = "";
        ClassLoader classLoader = getClass().getClassLoader();
        Properties properties=new Properties();
        try {
            properties.load(classLoader.getResourceAsStream("outerHost.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public  void makepool() throws FileNotFoundException,IOException {
        if (pool == null) {
            JedisPoolConfig conf = new JedisPoolConfig();
            conf.setMaxTotal(1000);
            conf.setMaxWaitMillis(60000L);
            String redisIP = this.getPropety().getProperty("redis.ip");
            String redisPORT = this.getPropety().getProperty("redis.port");
            pool = new JedisPool(conf, redisIP, Integer.parseInt(redisPORT),1000,"TA1WFIFXBJHUFPM3");
        }
    }

    public JedisPool getJedisPool() {
        return pool;
    }

    public static void main(String[] args) throws Exception{
        System.out.println(new JedisPoolUtils().getPropety().get("redis.ip"));
    }
}
