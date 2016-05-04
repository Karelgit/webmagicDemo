package com.gy.wm.dbpipeline.dbclient;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TianyuanPan on 5/4/16.
 */
public abstract class AbstractDBClient implements DBClient {

    protected String dbHostname;
    protected int dbPort;
    protected String dbName;
    protected String dbUser;
    protected String dbPassword;
    protected String connUrl;


//    @Override
//    public Connection getConnection() {
//        return connection;
//    }

    protected boolean connOpen;

    protected String dbConfigFilePath;

    protected Connection connection;

//    public Object getConnection() {
//        return this.connection;
//    }
//
//    public void closeConnection() {
//
//    }

//    public void loadDBConfig() {
//
//        DBConfig.loadConfigFile(this.dbConfigFilePath);
//    }

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

    public String getDbConfigFilePath() {
        return dbConfigFilePath;
    }

    public void setDbConfigFilePath(String dbConfigFilePath) {
        this.dbConfigFilePath = dbConfigFilePath;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


//    public boolean isConnOpen() {
//        return connOpen;
//    }

    public void setConnOpen(boolean connOpen) {
        this.connOpen = connOpen;
    }

    class InsertSqlModel {



        private String tableName;
        private Map<String, Object> keyValuePair;
        private List<String> keys;

        public InsertSqlModel() {
            this.keyValuePair = new HashMap<>();
            this.keys = new ArrayList<>();
            this.tableName = "";
        }

        public InsertSqlModel(String tableName) {
            this.keyValuePair = new HashMap<>();
            this.keys = new ArrayList<>();
            this.tableName = tableName;
        }

        public Object addKeyValue(String key, Object value) {

            keys.add(key);
            return this.keyValuePair.put(key, value);
        }

        public Object deleKeyValue(String key) {
            int i = 0;
            for (String k : keys) {
                if (k.equals(key)) {
                    keys.remove(i);
                    break;
                }
                ++i;
            }
            return this.keyValuePair.remove(key);
        }

        public Object getKeyValue(String key) {

            return this.keyValuePair.get(key);
        }

        public String getInsertSql(){
            String prefix = "INSERT INTO " + this.tableName + " ";
            String attr = new String();
            String value = new String();

            int i = 0;
            for (String k : keys){
                ++i;
                attr += k;
                value += keyValuePair.get(k);

                if (i != keys.size()) {
                    attr += ",";
                    value += ",";
                }
            }

            return prefix + "(" + attr + ")" + " VALUES (" + value + ")";
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }
    }
}
