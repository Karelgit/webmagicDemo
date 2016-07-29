package com.gy.wm.dbpipeline.dbclient;


import com.gy.wm.model.CrawlData;
import com.gy.wm.model.InsertSqlModel;
import com.gy.wm.model.rdb.RdbModel;
import com.gy.wm.util.ConfigUtils;
import com.gy.wm.util.MySqlPoolUtils;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TianyuanPan on 5/4/16.
 */

/**
 * Mysql数据库客户端类
 */
public class MysqlClient extends AbstractDBClient {

    private Statement myStatement;
    private ConfigUtils configUtils;
    private MySqlPoolUtils pool;


    /**
     * 此成员生成SQL语句和相关数据。
     */
    private List<InsertSqlModel> insertSqlModelList;

    public MysqlClient() {

        this.insertSqlModelList = new ArrayList<>();
        this.configUtils = ConfigUtils.getConfigUtils("MYSQL_");
        this.pool = MySqlPoolUtils.getMySqlPoolUtils(configUtils);
        this.connection = null;
        this.myStatement = null;
        this.connOpen = false;

    }


    /**
     * 获取数据库连接，在操作数据库前，先调用此方法打开数据库连接
     * @return
     */
    @Override
    public Object getConnection() {

        try {
            logger.debug("get Mysql connection ...");
            this.connection = this.pool.getConnection();
            this.myStatement = this.connection.createStatement();
            if (this.connection != null)
                this.setConnOpen(true);

        } catch (Exception ex) {

            logger.error("get mysql connection error!! Exception Message: " + ex.getMessage());
            ex.printStackTrace();
            this.setConnOpen(false);
            return null;
        }

        return this.connection;
    }

    /**
     * 关闭数据库连接
     */
    @Override
    public void closeConnection() {

        try {

            this.pool.releaseConnection(this.connection);

            this.setConnOpen(false);

        } catch (Exception ex) {

            this.setConnOpen(false);
            logger.error("connection.close() exception!! Message:" + ex.getMessage());
            ex.printStackTrace();

        }


    }

    /**
     * 数据集插入
     * @return int 受影响的函数
     */
    @Override
    public int doSetInsert() {
        int lineSum = 0;
        if (!this.connOpen) {
            System.out.println("Warning: the connection is NOT open!!!");
            logger.warn("Warning: the connection is NOT open!!!");
            return lineSum;
        }
        int size = this.insertSqlModelList.size();
        for (int i = 0; i < size; i++) {

            try {
                String sql = this.insertSqlModelList.get(i).getInsertSql();
                System.out.println("SQL ===>> " + sql);
                lineSum += this.myStatement.executeUpdate(sql);
            } catch (Exception ex) {
                System.out.println("SQL excute Exception ...");
                logger.warn("SQL excute Exception ...");

                ex.printStackTrace();

            }
        }
        this.insertSqlModelList = new ArrayList<>();
        return lineSum;
    }

    /**
     * 获取数据库连接状态
     */
    @Override
    public boolean isConnOpen() {
        return this.connOpen;
    }


    /**
     * 添加要插入的数据
     * @param tableName  数据库表名
     * @param rdbModel  关系模型对象
     * @param data      要插入的爬虫数据对象
     * @return SQL 插入模型对象（InsertSQLModel）
     */
    public Object addItem(String tableName, RdbModel rdbModel, CrawlData data) {

        rdbModel.setThisModelFields(data);
        InsertSqlModel model = rdbModel.insertSqlModelBuilder(tableName);
        insertSqlModelList.add(model);
        return model;
    }

}
