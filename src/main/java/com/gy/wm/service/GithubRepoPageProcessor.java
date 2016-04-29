package com.gy.wm.service;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 网页抓取器。
 * @author yinlei
 * 2014-3-5 下午4:27:51
 */
public class GithubRepoPageProcessor implements PageProcessor {
    private  Queue<String> linkQueue = new LinkedList<>();

    private Site site = Site.me().setDomain("news.163.com")
            .addStartUrl("http://news.163.com/");

    @Override
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name") == null) {
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));

        // 部分三：从页面发现后续的url地址来抓取
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
    }

    @Override
    public Site getSite() {
        return site;

    }

    public static void main(String[] args) {
        Spider.create(new GithubRepoPageProcessor())
                //从"https://github.com/code4craft"开始抓
                .addUrl("https://github.com/code4craft")
                        //开启5个线程抓取
                .thread(5)
                        //启动爬虫
                .run();
    }
}