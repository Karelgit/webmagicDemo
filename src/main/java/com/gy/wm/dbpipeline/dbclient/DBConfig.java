package com.gy.wm.dbpipeline.dbclient;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Created by TianyuanPan on 5/4/16.
 */
public class DBConfig {

    private  String hostname;
    private  int port;
    private  String DBName;
    private  String user;
    private  String password;


    DBConfig(){

    }

    public static DBConfig getDBConfig(String prefix){

        String proFilePath = System.getProperty("user.dir") + "/resources/dbconfig.properties";

        ResourceBundle rb;
        BufferedInputStream inputStream;

        DBConfig dbConfig = new DBConfig();

        try {

            inputStream = new BufferedInputStream(new FileInputStream(proFilePath));
            rb = new PropertyResourceBundle(inputStream);

            dbConfig.hostname = rb.getString(prefix + "HOSTNAME");
            dbConfig.port = Integer.parseInt(rb.getString(prefix + "PORT"));
            dbConfig.DBName = rb.getString(prefix + "DBNAME");
            dbConfig.user = rb.getString(prefix + "USER");
            dbConfig.password = rb.getString(prefix + "PASSWORD");

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (IOException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dbConfig;
    }

    public static ResourceBundle getResourceBundle(){

        String proFilePath = System.getProperty("user.dir") + "/resources/dbconfig.properties";
        ResourceBundle rb;
        BufferedInputStream inputStream;

        try {

            inputStream = new BufferedInputStream(new FileInputStream(proFilePath));
            rb = new PropertyResourceBundle(inputStream);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        } catch (IOException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return rb;
    }


    public  String getHostname() {
        return this.hostname;
    }

    public  int getPort() {
        return this.port;
    }

    public  String getDBName() {
        return this.DBName;
    }

    public  String getUser() {
        return this.user;
    }

    public  String getPassword() {
        return this.password;
    }

}
