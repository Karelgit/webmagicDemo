package com.gy.wm.dbpipeline.impl;

import com.gy.wm.dbpipeline.DatabasePipeline;
import com.gy.wm.model.CrawlData;
import com.gy.wm.model.CrawlDataMapper;
import org.apache.http.annotation.ThreadSafe;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by TianyuanPan on 5/4/16.
 */

@ThreadSafe
public class MysqlPipeline implements DatabasePipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        CrawlData crawlData = resultItems.get("crawlerData");
        insertTosql(crawlData);
    }

    public void insertTosql(CrawlData crawlData)   {
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();
        session.getConfiguration().addMapper(CrawlDataMapper.class);

        CrawlDataMapper mapper = session.getMapper(CrawlDataMapper.class);
        mapper.saveToMysql(crawlData);
        session.commit();
        session.close();
    }

}
