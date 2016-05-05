package com.gy.wm.dbpipeline.impl;

import com.gy.wm.dbpipeline.DatabasePipeline;
import com.gy.wm.dbpipeline.dbclient.DBClient;
import com.gy.wm.dbpipeline.dbclient.MysqlClient;
import com.gy.wm.model.CrawlData;
import org.apache.http.annotation.ThreadSafe;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

/**
 * Created by TianyuanPan on 5/4/16.
 */

@ThreadSafe
public class MysqlPipeline implements DatabasePipeline {

    private int retCode;
    MysqlClient dbClient;

    public MysqlPipeline() {
        this.retCode = 0;
//        String projPath = System.getProperty("user.dir");
        this.dbClient = new MysqlClient();
    }

    @Override
    public int insertRecord(DBClient dbClient) {
        return 0;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {

//        for (Object o :) {
//
//        }

    }

    public void add(String tablename, CrawlData data) {
        this.dbClient.addItem(tablename, data);
    }

    public int doInser() {
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
