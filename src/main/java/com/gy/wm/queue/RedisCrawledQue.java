package com.gy.wm.queue;

import com.gy.wm.model.CrawlData;
import com.gy.wm.util.JedisPoolUtils;
import com.gy.wm.util.JsonUtil;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by Administrator on 2016/5/18.
 */
public class RedisCrawledQue {

    public void putCrawledQue(List<CrawlData> crawlData, JedisPoolUtils jedisPoolUtils, String taskid) {

        Jedis jedis = jedisPoolUtils.getJedisPool().getResource();
        for (CrawlData data : crawlData) {
            String crawlDataJson = JsonUtil.toJson(data);
            jedis.hset("webmagicCrawler::Crawled::" + taskid, data.getUrl(),crawlDataJson);
        }
    }
}
