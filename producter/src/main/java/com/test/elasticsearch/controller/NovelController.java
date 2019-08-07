package com.test.elasticsearch.controller;

import com.test.elasticsearch.service.sprider.processor.CityProcessor;
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

    private Proxy[] proxies = new Proxy[]{
            new Proxy("118.26.170.209", 8080),
            new Proxy("111.231.93.66", 8888),
            new Proxy("111.231.94.44", 8888)
    };

    @Autowired
    private NovelPageProcessor novelPageProcessor;

    @Autowired
    private CityProcessor cityProcessor;

    @GetMapping("/crawel/novel/get")
    public void crawelNovel(String url) {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(proxies));
        Spider.create(novelPageProcessor).addUrl(url).setDownloader(httpClientDownloader).thread(5).run();
    }

    @GetMapping("/crawel/city/get")
    public void crawelCity(String url) {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(proxies));
        Spider.create(cityProcessor).addUrl(url).setDownloader(httpClientDownloader).thread(5).run();
    }
}
