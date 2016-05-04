package com.gy.wm.dbpipeline.dbclient;

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


    private List<InsertSqlModel> insertSqlModels;

    public MysqlClient() {

//        this.dbConfigFilePath = dbconfigFileName;
        this.characterEnconding = "UTF-8";
        this.insertSqlModels = new ArrayList<>();
//        super.loadDBConfig();
        this.dbHostname = DBConfig.getHostname();
        this.dbPort = DBConfig.getPort();
        this.dbName = DBConfig.getDBName();
        this.dbUser = DBConfig.getUser();
        this.dbPassword = DBConfig.getPassword();
        this.connUrl = "jdbc:mysql://" + dbHostname + ":" + dbPort +
                "/" + dbName + "?user=" + dbUser + "&password=" +
                dbPassword + "&useUnicode=true&characterEncoding=" + characterEnconding;
        this.connection = null;
        this.myStatement = null;
        this.connOpen = false;

    }

    public MysqlClient(String characterEnconding) {

//        this.dbConfigFilePath = dbconfigFileName;
        this.characterEnconding = characterEnconding;
        this.insertSqlModels = new ArrayList<>();
//        super.loadDBConfig();
        this.dbHostname = DBConfig.getHostname();
        this.dbPort = DBConfig.getPort();
        this.dbName = DBConfig.getDBName();
        this.dbUser = DBConfig.getUser();
        this.dbPassword = DBConfig.getPassword();
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

            System.out.println("Loading jdbc Driver....");

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
//        int i = 0;
        if (!this.connOpen) {
            System.out.println("Warning: the connection is NOT open!!!");
            return lineSum;
        }
        for (InsertSqlModel model : this.insertSqlModels) {

            try {

                String sql = model.getInsertSql();
//                this.insertSqlModels.remove(i);
//                ++i;
                lineSum += this.myStatement.executeUpdate(sql);

            } catch (Exception ex) {

                ex.printStackTrace();
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
//            time = new Date().getTime();
            model.addKeyValue("publicTime", "'" + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date().getTime()) + "'");
        } else
            model.addKeyValue("publicTime", "'" + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date(data.getPublicTime()).getTime()) + "'");
        model.addKeyValue("url", "'" + data.getUrl() + "'");
        model.addKeyValue("html", "'" + data.getHtml() + "'");
        model.addKeyValue("text", "'" + data.getText() + "'");
        model.addKeyValue("fetched", data.isFetched());
        insertSqlModels.add(model);
        return model;
    }

    public Object addItem(CrawlData data) {

        return null;
    }


}
