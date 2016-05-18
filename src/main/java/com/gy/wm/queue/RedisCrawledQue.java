package com.gy.wm.queue;

import com.gy.wm.model.CrawlData;
import com.gy.wm.util.JedisPoolUtils;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by Administrator on 2016/5/18.
 */
public class RedisCrawledQue {

    public void putRDD(List<CrawlData> crawlDataList, JedisPoolUtils jedisPoolUtils, String taskid) {

        Jedis jedis = jedisPoolUtils.getJedisPool().getResource();
        jedis.select(0);
        for (CrawlData crawlData : crawlDataList) {
            jedis.rpush("sparkcrawler::ToCrawl::" + taskid, crawlData.getUrl());
        }
    }
}
