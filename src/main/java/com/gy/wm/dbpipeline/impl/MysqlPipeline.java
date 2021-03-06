package com.gy.wm.dbpipeline.impl;

import com.gy.wm.dbpipeline.DatabasePipeline;
import com.gy.wm.dbpipeline.dbclient.MysqlClient;
import com.gy.wm.model.CrawlData;
import org.apache.http.annotation.ThreadSafe;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

import java.util.List;

/**
 * Created by TianyuanPan on 5/4/16.
 */

@ThreadSafe
public class MysqlPipeline implements DatabasePipeline {

    MysqlClient dbClient;
    String tableName;

    public MysqlPipeline() {

        this.dbClient = new MysqlClient();
        this.tableName = "tb_crawler";
    }

    public MysqlPipeline(String tableName) {

        this.dbClient = new MysqlClient();
        this.tableName = tableName;
    }

    @Override
    public int insertRecord(Object obj) {
        return 0;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {

        System.out.println("MysqlPipeline resultItems size: " + resultItems.getAll().size() +
                "\n\tTask uuid: " + task.getUUID());

        List<CrawlData> crawlDataList = resultItems.get("crawlerDataList");
        System.out.println("MysqlPipeline crwalerDataList Size: " + crawlDataList.size());
        for (CrawlData data : crawlDataList) {
            add(tableName, data);
        }
        System.out.println("MysqlPipeline doInsert return Code: " + doInsert());

    }


    public void add(String tablename, CrawlData data) {
        this.dbClient.addItem(tablename, data);
    }

    public int doInsert() {
        this.dbClient.getConnection();
        int sum = this.dbClient.doSetInsert();
        this.dbClient.closeConnection();
        return sum;
    }


/*    public static void main(String[] args) {

        MysqlPipeline mysqlPipeline = new MysqlPipeline();
        mysqlPipeline.dbClient.getConnection();
        System.out.println("connection Status: " + mysqlPipeline.dbClient.isConnOpen());
        if (mysqlPipeline.dbClient.isConnOpen())
            mysqlPipeline.dbClient.closeConnection();

    }*/
}
