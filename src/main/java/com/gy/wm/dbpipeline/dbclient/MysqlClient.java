package com.gy.wm.dbpipeline.dbclient;


import com.gy.wm.model.CrawlData;
import com.gy.wm.model.FengBirdModel;
import com.gy.wm.util.ConfigUtils;
import com.gy.wm.util.CrawlerDataUtils;
import com.gy.wm.util.MySqlPoolUtils;

import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by TianyuanPan on 5/4/16.
 */
public class MysqlClient extends AbstractDBClient {

    private Statement myStatement;
    private ConfigUtils configUtils;
    private MySqlPoolUtils pool;


    private List<InsertSqlModel> insertSqlModels;

    public MysqlClient() {

        this.insertSqlModels = new ArrayList<>();
        this.configUtils = ConfigUtils.getConfigUtils("MYSQL_");
        this.pool = MySqlPoolUtils.getMySqlPoolUtils(configUtils);
        this.connection = null;
        this.myStatement = null;
        this.connOpen = false;

    }


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

    @Override
    public int doSetInsert() {
        int lineSum = 0;
        if (!this.connOpen) {
            System.out.println("Warning: the connection is NOT open!!!");
            logger.warn("Warning: the connection is NOT open!!!");
            return lineSum;
        }
        for (InsertSqlModel model : this.insertSqlModels) {

            try {

                String sql = model.getInsertSql();
                lineSum += this.myStatement.executeUpdate(sql);

            } catch (Exception ex) {
                System.out.println("SQL excute Exception ...");
                logger.warn("SQL excute Exception ...");
                //ex.printStackTrace();

            }
        }
        this.insertSqlModels.clear();
        return lineSum;
    }

    @Override
    public boolean isConnOpen() {
        return this.connOpen;
    }


    public Object addItem(String tableName, CrawlData data) {

        InsertSqlModel model = new InsertSqlModel(tableName);

        FengBirdModel fengBirdModel = new FengBirdModel();

        List<Map<String, Object>> fieldList = CrawlerDataUtils.getCrawlerDataUtils(data).getAttributeInfoList();

        for (int i = 0; i < fieldList.size(); ++i) {

            String field = (String) fieldList.get(i).get("name");

            switch (field) {
                case "tid":
                    fengBirdModel.setTopicTaskID((String) fieldList.get(i).get("value"));
                    break;
                case "url":
                    fengBirdModel.setUrl((String) fieldList.get(i).get("value"));
                    break;
                case "crawlTime":
                    fengBirdModel.setCrawlTime((long) fieldList.get(i).get("value"));
                    break;
                case "publishTime":
                    fengBirdModel.setLabelTime((long) fieldList.get(i).get("value"));
                    break;
                case "title":
                    fengBirdModel.setTitle((String) fieldList.get(i).get("value"));
                    break;
                case "rootUrl":
                    fengBirdModel.setRootUrl((String) fieldList.get(i).get("value"));
                    break;
                case "fromUrl":
                    fengBirdModel.setFromUrl((String) fieldList.get(i).get("value"));
                    break;
                default:
                    break;
            }

        }

        fieldList = CrawlerDataUtils.getCrawlerDataUtils(fengBirdModel).getAttributeInfoList();

        for (int i = 0; i < fieldList.size(); ++i) {

            String type = (String) fieldList.get(i).get("type");

            switch (type) {
                case "long":
                    String dateString = "'" +
                            new SimpleDateFormat("YYYY-MM-dd HH:mm:ss")
                                    .format(new Date((long) fieldList.get(i).get("value"))) + "'";

                    model.addKeyValue((String) fieldList.get(i).get("name"), dateString);
                    break;
                case "int":
                    model.addKeyValue((String) fieldList.get(i).get("name"), fieldList.get(i).get("value"));
                    break;
                default:
                    model.addKeyValue((String) fieldList.get(i).get("name"), "'" + fieldList.get(i).get("value") + "'");
                    break;
            }


        }

        insertSqlModels.add(model);
        return model;
    }

    public Object addItem(CrawlData data) {

        return null;
    }

}
