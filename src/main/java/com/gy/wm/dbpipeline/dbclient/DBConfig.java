package com.gy.wm.dbpipeline.dbclient;

import com.alibaba.fastjson.JSON;
import com.gy.wm.parser.analysis.BaseTemplate;
import com.gy.wm.util.JsonUtil;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by TianyuanPan on 5/4/16.
 */
public class DBConfig {

    public static String hostname = null;
    public static int port = 54321;
    public static String DBName = null;
    public static String user = null;
    public static String password = null;

    class ConfigModel {

        private String hostname;
        private int port;
        private String DBName;
        private String user;
        private String password;

//        public ConfigModel(String hostname, int port, String DBName, String user, String password) {
//
//            this.hostname = hostname;
//            this.port = port;
//            this.DBName = DBName;
//            this.user = user;
//            this.password = password;
//        }

        public ConfigModel(){

        }

        public String getHostname() {
            return hostname;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getDBName() {
            return DBName;
        }

        public void setDBName(String DBName) {
            this.DBName = DBName;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public DBConfig() {
        DBConfig.DBName = null;
        DBConfig.hostname = null;
        DBConfig.password = null;
        DBConfig.port = 54321;
        DBConfig.user = null;
    }

    public static void loadConfigFile(String dbconfigFileName) {

//        String projPath = System.getProperty("user.dir");
        File file = new File(dbconfigFileName);

        String jsonConfigString = new String();
        String tmpStr;
        try {
            InputStream in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while ((tmpStr = reader.readLine()) != null) {

                jsonConfigString += tmpStr;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            ConfigModel model = JSON.parseObject(jsonConfigString, ConfigModel.class);

            DBConfig.user = model.getUser();
            DBConfig.port = model.getPort();
            DBConfig.password = model.getPassword();
            DBConfig.hostname = model.getHostname();
            DBConfig.DBName = model.getDBName();


        }catch (Exception ex){
            ex.printStackTrace();
        }


    }

}
