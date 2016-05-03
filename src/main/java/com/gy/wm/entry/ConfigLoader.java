package com.gy.wm.entry;

import com.gy.wm.parser.analysis.BaseTemplate;
import com.gy.wm.util.LoadSeeds;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public class ConfigLoader {
    private List<BaseTemplate> listTemplate;
    private final static LoadSeeds loadSeeds = new LoadSeeds();

    public List<BaseTemplate> getListTemplate() {
        return listTemplate;
    }

    public void setListTemplate(List<BaseTemplate> listTemplate) {
        this.listTemplate = listTemplate;
    }

    public List<String> loadSeedConfig()  {
        return LoadSeeds.load();
    }

   public List<BaseTemplate> loadTemplateConfig() {
       String projPath = System.getProperty("user.dir");
       listTemplate = new ArrayList<>();
       String str;
       File files = new File(projPath+"\\templates");
       File[] templateFiles = files.listFiles();
       List<File> fileList = new ArrayList<>();
       for(File templateFile : templateFiles)    {
           List<String> tokens = new ArrayList<>();
           String domain = new String();
           try {
               InputStream in = new FileInputStream(templateFile);
               BufferedReader reader = new BufferedReader(new InputStreamReader(in));
               domain = reader.readLine();
               while ((str = reader.readLine()) != null) tokens.add(str);
               reader.close();
               listTemplate.add(new BaseTemplate(domain,tokens));
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       return listTemplate;
   }
}
