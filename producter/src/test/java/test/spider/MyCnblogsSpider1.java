package test.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

/**
 * @ProjectName: elasticsearch
 * @Package: test.spider
 * @ClassName: MyCnblogsSpider1
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/7/31 16:14
 * @Version: 1.0
 */
public class MyCnblogsSpider1 implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {
        if (!page.getUrl().regex("https://www.cnblogs.com/[a-z0-9-]+/p/[0-9]{7}.html").match()) {
            page.addTargetRequests(page.getHtml().xpath("//*[@id=\"mainContent\"]/div/div/div[@class=\"postTitle\"]/a/@href").all());
        } else {
            page.putField(page.getHtml().xpath("//*[@id=\"cb_post_title_url\"]/text()").toString(),
                    page.getHtml().xpath("//*[@id=\"cb_post_title_url\"]/@href").toString());
        }

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("118.26.170.209", 8080)));
        Spider.create(new MyCnblogsSpider1()).addUrl("https://www.cnblogs.com/justcooooode/").setDownloader(httpClientDownloader).thread(5)
                .addPipeline(new ConsolePipeline()).run();
    }
}
