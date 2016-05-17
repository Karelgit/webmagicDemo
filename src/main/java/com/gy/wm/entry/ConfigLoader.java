package com.gy.wm.entry;

import com.gy.wm.parser.analysis.BaseTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public class ConfigLoader {
    private List<BaseTemplate> listTemplate;

    public List<BaseTemplate> loadTemplateConfig() {
       String projPath = System.getProperty("user.dir");
       listTemplate = new ArrayList<>();
       String str;
       File files = new File(projPath + "/templates");
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


    public List<String> loadSeedConfig()    {
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

//    public List<CrawlData> load(String tid,)

}
