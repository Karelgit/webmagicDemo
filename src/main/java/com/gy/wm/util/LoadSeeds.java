package com.gy.wm.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public class LoadSeeds {

    public static List<String> load()    {
        String projPath = System.getProperty("user.dir");
        List<String> seedsList = new ArrayList<>();
        try {
            // read file content from file
            StringBuffer sb= new StringBuffer("");

            FileReader reader = new FileReader(projPath+"//data//seeds.txt");
            BufferedReader br = new BufferedReader(reader);

            String str = null;

            while((str = br.readLine()) != null) {
                seedsList.add(str);
            }

            br.close();
            reader.close();
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return seedsList;
    }

    public static void main(String[] args) {
        //load();
    }
}
