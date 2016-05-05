package com.gy.wm.dbpipeline.dbclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gy.wm.model.CrawlData;

import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.logging.SimpleFormatter;

/**
 * Created by TianyuanPan on 5/4/16.
 */
public class MysqlClient extends AbstractDBClient {

    private String characterEnconding;

    private Statement myStatement;

    private DBConfig dbConfig;


    private List<InsertSqlModel> insertSqlModels;

    public MysqlClient() {

        this.characterEnconding = "UTF-8";
        this.insertSqlModels = new ArrayList<>();
        this.dbConfig = DBConfig.getDBConfig("MYSQL_");
        this.dbHostname = dbConfig.getHostname();
        this.dbPort = dbConfig.getPort();
        this.dbName = dbConfig.getDBName();
        this.dbUser = dbConfig.getUser();
        this.dbPassword = dbConfig.getPassword();
        this.connUrl = "jdbc:mysql://" + dbHostname + ":" + dbPort +
                "/" + dbName + "?user=" + dbUser + "&password=" +
                dbPassword + "&useUnicode=true&characterEncoding=" + characterEnconding;
        this.connection = null;
        this.myStatement = null;
        this.connOpen = false;

    }

    public MysqlClient(String characterEnconding) {

        this.characterEnconding = characterEnconding;
        this.insertSqlModels = new ArrayList<>();
        this.dbConfig = DBConfig.getDBConfig("MYSQL_");
        this.dbHostname = dbConfig.getHostname();
        this.dbPort = dbConfig.getPort();
        this.dbName = dbConfig.getDBName();
        this.dbUser = dbConfig.getUser();
        this.dbPassword = dbConfig.getPassword();
        this.connUrl = "jdbc:mysql://" + dbHostname + ":" + dbPort +
                "/" + dbName + "?user=" + dbUser + "&password=" +
                dbPassword + "&useUnicode=true&characterEncoding=" + characterEnconding;
        this.connection = null;
        this.myStatement = null;
        this.connOpen = false;
    }

    @Override
    public Object getConnection() {

        try {

            Class.forName("com.mysql.jdbc.Driver");

            System.out.println("Loading jdbc Driver ....");

            this.connection = DriverManager.getConnection(this.connUrl);
            this.myStatement = this.connection.createStatement();
            this.setConnOpen(true);

        } catch (Exception ex) {

            ex.printStackTrace();
            this.setConnOpen(false);
            return null;
        }

        return this.connection;
    }

    @Override
    public void closeConnection() {

        try {

            this.connection.close();
            this.setConnOpen(false);

        } catch (Exception ex) {

            this.setConnOpen(false);
            ex.printStackTrace();

        }


    }

    @Override
    public int doSetInsert() {
        int lineSum = 0;
        if (!this.connOpen) {
            System.out.println("Warning: the connection is NOT open!!!");
            return lineSum;
        }
        for (InsertSqlModel model : this.insertSqlModels) {

            try {

                String sql = model.getInsertSql();
                lineSum += this.myStatement.executeUpdate(sql);

            } catch (Exception ex) {

                //ex.printStackTrace();
                System.out.println("SQL excute Exception ...");
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

        model.addKeyValue("title", "'" + data.getTitle() + "'");
        Long time = data.getPublicTime();
        if (time == null) {
            model.addKeyValue("publicTime", "'" + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date().getTime()) + "'");
        } else
            model.addKeyValue("publicTime", "'" + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date(data.getPublicTime())) + "'");

        model.addKeyValue("url", "'" + data.getUrl() + "'");
        model.addKeyValue("text", "'" + data.getText() + "'");
        model.addKeyValue("fetched", data.isFetched());
        model.addKeyValue("html", "'" + data.getHtml().replace("\\\'","\'").replace("\'", "\\\'") + "'");

        insertSqlModels.add(model);
        return model;
    }

    public Object addItem(CrawlData data) {

        return null;
    }


}
