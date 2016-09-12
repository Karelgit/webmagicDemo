package com.gy.wm.model;

import org.apache.ibatis.annotations.Insert;

/**
 * <类详细说明：栏目更新数据的model>
 *
 * @Author： Huanghai
 * @Version: 2016-09-12
 **/
public interface CrawlDataMapper {
    @Insert("INSERT INTO crawlerdata(tid,url,statusCode,pass,type,rootUrl,fromUrl,text,html,title,startTime,crawlTime,publishTime,depthfromSeed,count,tag,fetched) VALUES " +
            "(#{tid},#{url},#{statusCode},#{pass},#{type},#{rootUrl},#{fromUrl},#{text},#{html},#{title},#{startTime},#{crawlTime},#{publishTime},#{depthfromSeed},#{count},#{tag},#{fetched})")
    public void saveToMysql(CrawlData crawlData);
}
