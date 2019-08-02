package com.test.elasticsearch.controller;

import com.test.elasticsearch.service.sprider.processor.NovelPageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.controller
 * @ClassName: NovelController
 * @Author: guang
 * @Description: 爬取小说controller
 * @Date: 2019/8/2 15:40
 * @Version: 1.0
 */
@RestController
public class NovelController {

    private static String proxy_ip = "118.26.170.209";

    private static int proxy_port = 8080;

    @Autowired
    private NovelPageProcessor novelPageProcessor;

    @GetMapping("/crawel/novel/get")
    public void crawelNovel(String url) {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy(proxy_ip, proxy_port)));
        Spider.create(novelPageProcessor).addUrl(url).setDownloader(httpClientDownloader).thread(5).run();
    }
}
