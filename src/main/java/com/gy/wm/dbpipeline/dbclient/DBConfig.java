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


    private static String hostname;
    private static int port;
    private static String DBName;
    private static String user;
    private static String password;


    private static ResourceBundle rb;
    private static BufferedInputStream inputStream;

    static {

        String proFilePath = System.getProperty("user.dir") + "/resources/dbconfig.properties";
        try {

            inputStream = new BufferedInputStream(new FileInputStream(proFilePath));
            rb = new PropertyResourceBundle(inputStream);

            hostname = rb.getString("HOSTNAME");
            port = Integer.parseInt(rb.getString("PORT"));
            DBName = rb.getString("DBNAME");
            user = rb.getString("USER");
            password = rb.getString("PASSWORD");

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (IOException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static String getHostname() {
        return hostname;
    }

    public static int getPort() {
        return port;
    }

    public static String getDBName() {
        return DBName;
    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }

}
