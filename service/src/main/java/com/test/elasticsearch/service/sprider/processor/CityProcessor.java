package com.test.elasticsearch.service.sprider.processor;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.CharsetUtil;
import com.test.elasticsearch.entity.mongodb.AddressDb;
import com.test.elasticsearch.service.sprider.BaseProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

import java.util.List;

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

    @Autowired
    private MongoTemplate mongoTemplate;

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
            .setSleepTime(5000)
            .setRetryTimes(3)
            .setCharset(CharsetUtil.GBK)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");

    @Override
    public void process(Page page) {
        List<String> urlList;
        String baseUrl;
        if (page.getUrl().regex(TJSJ_CITY_WEB_URL).match()) {
            // 获取省份code列表
            List<String> provinceCodes = page.getHtml().regex("<td><a href=\\\"(.{1,30}).html\\\">.*?<br></a></td>").all();
            // 获取省份信息列表
            List<String> provinceNames = page.getHtml().regex("<td><a href=\\\".*?.html\\\">(.{1,30})<br></a></td>").all();
            AddressDb addressDb;
            String parentName = "中国";
            for (int i = 0, n = provinceCodes.size(); i < n; i++) {
                addressDb = AddressDb.builder()
                        .provinceId(Integer.valueOf(provinceCodes.get(i) + "0000"))
                        .parentId(100000)
                        .name(provinceNames.get(i))
                        .mergeName(parentName + "," + provinceNames.get(i))
                        .levelType((short) 1).build();
                mongoTemplate.insert(addressDb);
            }
            urlList = page.getHtml().xpath("//*[@class=\"provincetr\"]/td/a/@href").all();
            if (CollectionUtil.isNotEmpty(urlList)) {
                urlList.stream().forEach( str -> page.addTargetRequest(TJSJ_CITY_BASE_URL + str));
            }
        } else if (page.getUrl().regex(PROVINCE_URL).match()) {
            // 获取省份Code和省份信息
            int provinceFromId = Integer.valueOf(page.getUrl().regex("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/(.{2,}).html").toString() + "0000");
            Query query = new Query(new Criteria().and("provinceId").is(provinceFromId));
            AddressDb provinceInfo = mongoTemplate.findOne(query, AddressDb.class);
            if (provinceInfo != null) {
                AddressDb addressDb;
                String parentName = provinceInfo.getMergeName();
                // 获取城市信息
                List<String> cityInfos = page.getHtml().regex("<td><a href=\\\".*?.html\\\">(.{1,30})</a></td>").all();
                for (int i = 0, n = cityInfos.size(); i < n; i++) {
                    addressDb = AddressDb.builder()
                            .provinceId(Integer.valueOf(cityInfos.get(i).substring(0, 6)))
                            .parentId(provinceInfo.getProvinceId())
                            .name(cityInfos.get(++i))
                            .mergeName(parentName + "," + cityInfos.get(i))
                            .levelType((short) 2).build();
                    mongoTemplate.insert(addressDb);
                }
            }
            urlList = page.getHtml().xpath("//*[@class=\"citytr\"]/td/a/@href").all();
            if (CollectionUtil.isNotEmpty(urlList)) {
                urlList.stream().distinct().forEach( str -> page.addTargetRequest(TJSJ_CITY_BASE_URL + str));
            }
        } else if (page.getUrl().regex(CITY_URL).match()) {
            baseUrl = page.getUrl().regex("^http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/\\d{1,2}/").toString();
            urlList = page.getHtml().xpath("//*[@class=\"countytr\"]/td/a/@href").all();
//            if (CollectionUtil.isNotEmpty(urlList)) {
//                urlList.stream().distinct().forEach( str -> page.addTargetRequest(baseUrl + str));
//            }
            // 获取城市Code和信息
            int cityFromIdId = Integer.valueOf(page.getUrl().regex("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/\\d{1,}/(.{2,}).html").toString() + "00");
            Query query = new Query(new Criteria().and("provinceId").is(cityFromIdId));
            AddressDb cityInfo = mongoTemplate.findOne(query, AddressDb.class);
            // 获取区县信息
            List<String> countyInfos = page.getHtml().regex("<td><a href=\\\".*?.html\\\">(.{1,30})</a></td>").all();
            if (cityInfo != null) {
                AddressDb addressDb;
                String parentName = cityInfo.getMergeName();
                for (int i = 0, n = countyInfos.size(); i < n; i++) {
                    addressDb = AddressDb.builder()
                            .provinceId(Integer.valueOf(countyInfos.get(i).substring(0, 6)))
                            .parentId(cityInfo.getProvinceId())
                            .name(countyInfos.get(++i))
                            .mergeName(parentName + "," + countyInfos.get(i))
                            .levelType((short) 3).build();
                    mongoTemplate.insert(addressDb);
                }
            }
//            page.addTargetRequest(baseUrl + urlList.get(1));
        } else if (page.getUrl().regex(AREA_URL).match()) {
            // 获取区县Code
            int areaFormId = Integer.valueOf(page.getUrl().regex("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/\\d{1,}/\\d{1,}/(.{2,}).html").toString());
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
