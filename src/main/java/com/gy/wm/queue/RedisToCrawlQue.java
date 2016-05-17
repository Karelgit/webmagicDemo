package com.gy.wm.queue;

import com.gy.wm.util.JedisPoolUtils;
import redis.clients.jedis.Jedis;

/**
 * Created by Administrator on 2016/5/17.
 */
public class RedisToCrawlQue {

    public boolean hasMoreUrls(String taskid,JedisPoolUtils jedisPoolUtils) {

        Jedis jedis = jedisPoolUtils.getJedisPool().getResource();
        try {
            return jedis.llen("webmagicCrawler::ToCrawl::"+taskid) != 0;
        } finally {
            jedisPoolUtils.getJedisPool().returnResource(jedis);
        }
    }

    public void putNextUrls(String url) {

    }
}
