package com.gy.wm.dbpipeline.impl;

import com.gy.wm.dbpipeline.dbclient.MysqlClient;
import com.gy.wm.model.CrawlData;
import com.gy.wm.model.rdb.RdbModel;
import org.apache.http.annotation.ThreadSafe;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by TianyuanPan on 5/4/16.
 */

/**
 * Mysql Pipeline
 */
@ThreadSafe
public class MysqlPipeline extends BaseDBPipeline {

    private MysqlClient dbClient;
    private String tableName;
    private RdbModel rdbModel;
    private Lock myLock;


    public MysqlPipeline(String tableName, RdbModel rdbModel) {

        this.dbClient = new MysqlClient();
        this.tableName = tableName;
        this.rdbModel = rdbModel;
        this.myLock = new ReentrantLock();
    }

    @Override
    public int insertRecord(Object obj) {
        return 0;
    }

    /**
     * 数据的持久化处理方法
     * @param resultItems 结果条目对象
     * @param task 任务对象
     */
    @Override
    public void process(ResultItems resultItems, Task task) {


        try {
            myLock.lock();

            System.out.println("MysqlPipeline resultItems size: " + resultItems.getAll().size() +
                    "\n\tTask uuid: " + task.getUUID());

        /*logger.debug("MysqlPipeline resultItems size: " + resultItems.getAll().size() +
                "\n\tTask uuid: " + task.getUUID());*/

            CrawlData crawlData = resultItems.get("crawlerData");

            if (crawlData == null) {
                System.out.println("MysqlPipeline crwalerData is NULL");
                logger.warn("MysqlPipeline crwalerData is NULL !!!");
                return;
            }
            add(tableName, crawlData);
            int sum = doInsert();
            System.out.println("MysqlPipeline doInsert Successful number: " + sum);
            //logger.debug("MysqlPipeline doInsert Successful number: " + sum);
        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            myLock.unlock();

        }

    }

    /**
     * 添加数据
     * @param tablename  数据库表名
     * @param data       爬取的数据
     */
    public void add(String tablename, CrawlData data) {
        this.dbClient.addItem(tablename, rdbModel, data);
    }

    /**
     * 插入数据操作
     * @return
     */
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
