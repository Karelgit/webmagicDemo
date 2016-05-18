package com.gy.wm.service;

import com.gy.wm.model.CrawlData;
import com.gy.wm.parser.analysis.TextAnalysis;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 网页抓取器。
 * @author yinlei
 * 2014-3-5 下午4:27:51
 */
public class ColumnPageProcessor implements PageProcessor {
    private TextAnalysis textAnalysis;

    public ColumnPageProcessor(TextAnalysis textAnalysis)   {
        this.textAnalysis = textAnalysis;
    }

    private Site site = Site.me().setDomain("http://www.gog.cn").setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {

        /*String url = page.getRequest().getUrl();
        String html = page.getHtml().toString();
        CrawlData beginCralwerData = initCrawlData(url,html);
        crawlDataList.add(beginCralwerData);
        List<CrawlData> perPageCrawlDateList = this.getTextAnalysis().analysisHtml(beginCralwerData);
        for(CrawlData crawlData : perPageCrawlDateList)    {
            if(crawlData.isFetched() == false)  {
                page.addTargetRequest(crawlData.getUrl());
            }else {
                crawlDataList.add(crawlData);
                page.putField("crawlerData", crawlData);
            }
        }

        page.putField("crawlerDataList", crawlDataList);*/

    }


    @Override
    public Site getSite() {
        return site;
    }

    public static  CrawlData initCrawlData(String url,String html)    {
        CrawlData crawlData = new CrawlData();
        crawlData.setUrl(url);
        crawlData.setHtml(html);
        crawlData.setFetched(false);
        return crawlData;
    }

    public TextAnalysis getTextAnalysis() {
        return textAnalysis;
    }

    public void setTextAnalysis(TextAnalysis textAnalysis) {
        this.textAnalysis = textAnalysis;
    }



}