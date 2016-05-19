package com.gy.wm.queue;

import com.gy.wm.model.CrawlData;
import com.gy.wm.util.JedisPoolUtils;
import com.gy.wm.util.JsonUtil;
import redis.clients.jedis.Jedis;

/**
 * Created by Administrator on 2016/5/18.
 */
public class RedisCrawledQue {

    public void putCrawledQue(CrawlData crawlData, JedisPoolUtils jedisPoolUtils, String taskid) {

        Jedis jedis = jedisPoolUtils.getJedisPool().getResource();
        jedis.select(0);
        String crawlDataJson = JsonUtil.toJson(crawlData);
        jedis.hset("sparkcrawler::Crawled::" + taskid, crawlData.getUrl(),crawlDataJson);
    }
}
