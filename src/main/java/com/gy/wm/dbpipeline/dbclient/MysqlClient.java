package com.gy.wm.dbpipeline.dbclient;

import com.gy.wm.model.CrawlData;
import com.gy.wm.util.ConfigUtils;

import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TianyuanPan on 5/4/16.
 */
public class MysqlClient extends AbstractDBClient {

    protected String dbHostname;
    protected int dbPort;
    protected String dbName;
    protected String dbUser;
    protected String dbPassword;
    protected String connUrl;

    private String characterEnconding;

    private Statement myStatement;

    private ConfigUtils configUtils;


    private List<InsertSqlModel> insertSqlModels;

    public MysqlClient() {

        this.characterEnconding = "UTF-8";
        this.insertSqlModels = new ArrayList<>();
        this.configUtils = ConfigUtils.getConfigUtils("MYSQL_");
        this.dbHostname = configUtils.getHostname();
        this.dbPort = configUtils.getPort();
        this.dbName = configUtils.getDbName();
        this.dbUser = configUtils.getUser();
        this.dbPassword = configUtils.getPassword();
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
        this.configUtils = ConfigUtils.getConfigUtils("MYSQL_");
        this.dbHostname = configUtils.getHostname();
        this.dbPort = configUtils.getPort();
        this.dbName = configUtils.getDbName();
        this.dbUser = configUtils.getUser();
        this.dbPassword = configUtils.getPassword();
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
            logger.debug("Loading jdbc Driver ....");

            this.connection = DriverManager.getConnection(this.connUrl);
            this.myStatement = this.connection.createStatement();
            this.setConnOpen(true);

        } catch (Exception ex) {

            logger.error("Loading jdbc Driver error!!\nException Message:\n" + ex.getMessage());
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

        model.addKeyValue("title", "'" + data.getTitle() + "'");
        Long time = data.getPublishTime();
        if (time == null) {
            model.addKeyValue("publicTime", "'" + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date().getTime()) + "'");
        } else
            model.addKeyValue("publicTime", "'" + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date(data.getPublishTime())) + "'");

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


    public String getDbHostname() {
        return dbHostname;
    }

    public void setDbHostname(String dbHostname) {
        this.dbHostname = dbHostname;
    }

    public int getDbPort() {
        return dbPort;
    }

    public void setDbPort(int dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getConnUrl() {
        return connUrl;
    }

    public void setConnUrl(String connUrl) {
        this.connUrl = connUrl;
    }


}
