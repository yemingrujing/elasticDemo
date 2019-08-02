package test.spider;

import cn.hutool.core.collection.CollectionUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.util.List;

/**
 * @ProjectName: elasticsearch
 * @Package: test.spider
 * @ClassName: MyCnblogsSpider2
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/7/31 17:48
 * @Version: 1.0
 */
public class MyCnblogsSpider2 implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000)
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
    private static final String URL_LIST = "https://www.cnblogs.com/sitehome/p/\\d{1,3}";
    private static final String ROOT_URL = "https://www.cnblogs.com/";
    private static int pageNum = 1;
    // 要爬去的页数
    private static int totalPageNum = 3;

    @Override
    public void process(Page page) {
        if (page.getUrl().regex("^https://www.cnblogs.com/$").match()) {
            page.addTargetRequests(page.getHtml().xpath("//*[@id=\"post_list\"]/div/div[@class=\"post_item_body\"]/h3/a/@href").all());
            List<String> pagers = page.getHtml().xpath("//*[@id=\"paging_block\"]/div/a/text()").all();
            if (CollectionUtil.isNotEmpty(pagers)) {
                totalPageNum = Integer.valueOf(pagers.get(pagers.size() - 2));
            }
            page.addTargetRequest(ROOT_URL + "sitehome/p/" + ++pageNum);
        } else if (page.getUrl().regex(URL_LIST).match() && pageNum <= totalPageNum) { // 爬取2-totalPageNum页，
            List<String> urls = page.getHtml().xpath("//*[@class=\"post_item\"]/div[@class=\"post_item_body\"]/h3/a/@href").all();
            page.addTargetRequests(urls);
            page.addTargetRequest(ROOT_URL + "sitehome/p/" + ++pageNum);
        } else {
            page.putField(page.getHtml().xpath("//*[@id=\"cb_post_title_url\"]/text()").toString(), page.getHtml().xpath("//*[@id=\"cb_post_title_url\"]/@href").toString());
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("118.26.170.209", 8080)));
        Spider.create(new MyCnblogsSpider2())
                .setDownloader(httpClientDownloader)
                .addUrl(ROOT_URL)
                .addPipeline(new ConsolePipeline())
                .thread(5)
                .runAsync();
    }
}
