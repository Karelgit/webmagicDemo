package com.gy.wm.service;

import com.gy.wm.entry.ConfigLoader;
import com.gy.wm.model.CrawlData;
import com.gy.wm.parser.analysis.TextAnalysis;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 网页抓取器。
 * @author yinlei
 * 2014-3-5 下午4:27:51
 */
public class ColumnPageProcessor implements PageProcessor {
    private CrawlData crawlData;

    public CrawlData getCrawlData() {
        return crawlData;
    }

    public void setCrawlData(CrawlData crawlData) {
        this.crawlData = crawlData;
    }
    private  Queue<String> linkQueue = new LinkedList<>();
    private TextAnalysis textAnalysis = new TextAnalysis(new ConfigLoader().loadTemplateConfig());

    private Site site = Site.me().setDomain("http://www.gog.cn/").setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {



        // part2:extract and save
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name") == null) {
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));

        // part3: fetch from the url founded later
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
    }

    @Override
    public Site getSite() {
        return site;

    }

    public static void main(String[] args) {
        List<String> seedsList = new ConfigLoader().loadSeedConfig();
        CrawlData crawlData = new ColumnPageProcessor().getCrawlData();

        for(String seed:seedsList) {
            Spider.create(new ColumnPageProcessor())
                    //从"https://github.com/code4craft"开始抓
                    .addUrl(seed)
                            //开启5个线程抓取
                    .thread(5)
                            //启动爬虫
                    .run();
        }

    }
}