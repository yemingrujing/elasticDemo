package com.test.elasticsearch.service.sprider.processor;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.CharsetUtil;
import com.test.elasticsearch.service.sprider.BaseProcessor;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service.sprider.processor
 * @ClassName: CityProcessor
 * @Author: guang
 * @Description: 省市区数据获取(
 * https://blog.csdn.net/bihansheng2010/article/details/89177237,
 * https://blog.csdn.net/xiangyuecn/article/details/88812526
 * )
 * @Date: 2019/8/5 11:29
 * @Version: 1.0
 */
@Component
public class CityProcessor implements BaseProcessor {

    private static final String TJSJ_CITY_BASE_URL  = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/";
    private static final String TJSJ_CITY_WEB_URL = "^http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/[A-Za-z]+.html$";

    // 获取市信息
    private static final String PROVINCE_URL = "^http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/\\d{1,2}.html$";
    // 获取区县信息
    private static final String CITY_URL = "^http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/\\d{1,2}/\\d{1,4}.html$";
    // 获取城镇信息
    private static final String AREA_URL = "^http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/\\d{1,2}/\\d{1,2}/\\d{5,8}.html$";

    private Site site = Site.me()
            .setDomain("http://www.stats.gov.cn")
            .setSleepTime(1000)
            .setRetryTimes(3)
            .setTimeOut(30000)
            .setCharset(CharsetUtil.GBK)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");

    private static int provinceFromId = 0;
    private static int cityFromIdId = 0;
    private static int areaFormId = 0;
    private static int townFormId = 0;

    @Override
    public void process(Page page) {
        List<String> urlList;
        String baseUrl;
        if (page.getUrl().regex(TJSJ_CITY_WEB_URL).match()) {
            urlList = page.getHtml().xpath("//*[@class=\"provincetr\"]/td/a/@href").all();
//            if (CollectionUtil.isNotEmpty(urlList)) {
//                urlList.stream().forEach( str -> page.addTargetRequest(TJSJ_CITY_BASE_URL + str));
//            }
            // 获取省份code
            List<String> provinceCodes = page.getHtml().regex("<td><a href=\\\"(.{1,30}).html\\\">.*?<br></a></td>").all();
            // 获取省份信息
            List<String> provinceNames = page.getHtml().regex("<td><a href=\\\".*?.html\\\">(.{1,30})<br></a></td>").all();
            for (int i = 0, n = provinceCodes.size(); i < n; i++) {
                System.out.println(provinceCodes.get(i) + " " + provinceNames.get(i));
            }
            page.addTargetRequest(TJSJ_CITY_BASE_URL + urlList.get(3));
        } else if (page.getUrl().regex(PROVINCE_URL).match()) {
            urlList = page.getHtml().xpath("//*[@class=\"citytr\"]/td/a/@href").all();
//            if (CollectionUtil.isNotEmpty(urlList)) {
//                urlList.stream().distinct().forEach( str -> page.addTargetRequest(TJSJ_CITY_BASE_URL + str));
//            }
            // 获取省份Code
            String provinceCode = page.getUrl().regex("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/(.{2,}).html").toString();
            // 获取城市信息
            List<String> cityInfos = page.getHtml().regex("<td><a href=\\\".*?.html\\\">(.{1,30})</a></td>").all();
            for (int i = 0, n = cityInfos.size(); i < n; i++) {
                System.out.println(cityInfos.get(i) + " " + cityInfos.get(++i));
            }
            page.addTargetRequest(TJSJ_CITY_BASE_URL + urlList.get(2));
        } else if (page.getUrl().regex(CITY_URL).match()) {
            baseUrl = page.getUrl().regex("^http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/\\d{1,2}/").toString();
            urlList = page.getHtml().xpath("//*[@class=\"countytr\"]/td/a/@href").all();
//            if (CollectionUtil.isNotEmpty(urlList)) {
//                urlList.stream().distinct().forEach( str -> page.addTargetRequest(baseUrl + str));
//            }
            // 获取城市Code
            String cityCode = page.getUrl().regex("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/\\d{1,}/(.{2,}).html").toString();
            // 获取区县信息
            List<String> countyInfos = page.getHtml().regex("<td><a href=\\\".*?.html\\\">(.{1,30})</a></td>").all();
            for (int i = 0, n = countyInfos.size(); i < n; i++) {
                System.out.println(countyInfos.get(i) + " " + countyInfos.get(++i));
            }
            page.addTargetRequest(baseUrl + urlList.get(1));
        } else if (page.getUrl().regex(AREA_URL).match()) {
            // 获取区县Code
            String countyCode = page.getUrl().regex("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/\\d{1,}/\\d{1,}/(.{2,}).html").toString();
            // 获取乡镇信息
            List<String> townInfos = page.getHtml().regex("<td><a href=\\\".*?.html\\\">(.{1,30})</a></td>").all();
            for (int i = 0, n = townInfos.size(); i < n; i++) {
                System.out.println(townInfos.get(i) + " " + townInfos.get(++i));
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
