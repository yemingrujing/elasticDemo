package test.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

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

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        if (!page.getUrl().regex("https://www.cnblogs.com/[a-z0-9-]+/p/[0-9]{8}.html").match()) {
            page.putField(page.getHtml().xpath("").toString(), page.getHtml().xpath("").toString());
        }
    }

    @Override
    public Site getSite() {
        return null;
    }

    public static void main(String[] args) {
        Spider.create(new MyCnblogsSpider2()).addUrl("").addPipeline(new ConsolePipeline()).run();
    }
}
