package com.test.elasticsearch.service.sprider.processor;

import cn.hutool.core.util.CharsetUtil;
import com.test.elasticsearch.entity.mongodb.NovelDb;
import com.test.elasticsearch.repository.mongodb.NovelMBRepository;
import com.test.elasticsearch.service.sprider.BaseProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

import java.util.List;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service.sprider.processor
 * @ClassName: NovelPageProcessor
 * @Author: guang
 * @Description: 爬取88网小说
 * @Date: 2019/8/2 10:10
 * @Version: 1.0
 */
@Component
public class NovelPageProcessor implements BaseProcessor {

    @Autowired
    private NovelMBRepository novelMBRepository;

    // url匹配正则表达式
    public static final String URL_REGEX = "^((ht|f)tps?):\\/\\/[\\w\\-]+(\\.[\\w\\-]+)+([\\w\\-.,@?^=%&:\\/~+#]*[\\w\\-@?^=%&\\/~+#])?$";

    // 88小说下载主域名
    public static final String NOVEL_BASE_URL = "https://www.88dush.com";

    // 匹配小说URL
    public static final String NOVEL_ALBUM_URL = "((ht|f)tps?):\\/\\/[\\w\\-]+\\.88dush\\.com/xiaoshuo/\\d{1,}/\\d{1,}/";

    public static final String NOVEL_CHAPTER_URL = "((ht|f)tps?):\\/\\/[\\w\\-]+\\.88dush\\.com/xiaoshuo/\\d{1,}/\\d{1,}/\\d{1,}\\.html";

    private Site site = Site.me()
            .setDomain(NOVEL_BASE_URL)
            .setSleepTime(1000)
            .setRetryTimes(3)
            .setCharset(CharsetUtil.GBK)
            .setTimeOut(30000)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");

    @Override
    public void process(Page page) {
        if (page.getUrl().toString().matches(NOVEL_ALBUM_URL)) {
            String url = page.getUrl().toString();
            List<String> urlList = page.getHtml().xpath("//*[@class=\"mulu\"]/ul/li/a/@href").all();
            for (String str : urlList) {
                page.addTargetRequest(url + str);
            }
        } else if (page.getUrl().toString().matches(NOVEL_CHAPTER_URL)) {
            System.out.println(page.getUrl());
            NovelDb novelDb = NovelDb.builder()
                    .url(page.getUrl().toString())
                    .title(page.getHtml().xpath("//*[@class=\"novel\"]/h1/text()").toString())
                    .content(page.getHtml().xpath("//*[@class=\"novel\"]/div[@class=\"yd_text2\"]/text()").toString()).build();
            novelMBRepository.save(novelDb);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
