package com.gy.wm.parser.analysis;

import com.gy.wm.model.CrawlData;
import com.gy.wm.parser.urljudge.HtmlSort;
import us.codecraft.webmagic.Page;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 15-12-22.
 */
public class TextAnalysis implements Serializable {
    private List<BaseTemplate> baseTemplates;

    private AnalysisArticle analysisArticle;

    public TextAnalysis(List<BaseTemplate> baseTemplates) {
        this.baseTemplates = baseTemplates;
        this.analysisArticle = new AnalysisArticle();
    }

    public List<CrawlData> analysisHtml(Page page,List<CrawlData> crawlDataList)   {
        List<BaseAnalysisURL> baseAnalysisURLList = new ArrayList<>();
        //初始化
        AnalysisNavigation analysisNavigation = new AnalysisNavigation();
        CrawlData crawlData = new CrawlData();
        String url = page.getRequest().getUrl();
        String html = page.getHtml().toString();
        crawlData.setUrl(url);
        crawlData.setHtml(html);
        String title = "";
        Long date = 0L;

        BaseAnalysisURL oldUrl = new BaseAnalysisURL(url, title, date, html);

        //网页分类
        int sort = HtmlSort.getHtmlSort(url, html);

        //导航解析
        if(sort ==1)    {
            try {
                baseAnalysisURLList = analysisNavigation.getUrlList(url,html);
                for(BaseAnalysisURL baseAnalysisURL : baseAnalysisURLList)  {
                    CrawlData newCrawlData = new CrawlData();
                    newCrawlData.setUrl(baseAnalysisURL.getUrl());
                    newCrawlData.setTitle(baseAnalysisURL.getTitle());
                    newCrawlData.setPublicTime(baseAnalysisURL.getDate());
                    newCrawlData.setHtml(baseAnalysisURL.getHtml());
                    newCrawlData.setText(baseAnalysisURL.getText());
                    newCrawlData.setFetched(false);

                    crawlDataList.add(newCrawlData);
                }
                crawlData.setFetched(true);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            //文章页解析
            try {
                oldUrl = analysisArticle.analysisArticle(oldUrl, baseTemplates);
            } catch (IOException e) {
                e.printStackTrace();
            }
            crawlData.setTitle(oldUrl.getTitle());
            crawlData.setPublicTime(oldUrl.getDate());
            crawlData.setText(oldUrl.getText());
            crawlData.setHtml(oldUrl.getHtml());
            crawlData.setText(oldUrl.getText());
            crawlData.setFetched(true);

            crawlDataList.add(crawlData);
        }
        return crawlDataList;

    }
}
