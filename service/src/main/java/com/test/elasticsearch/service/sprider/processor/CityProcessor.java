package com.test.elasticsearch.service.sprider.processor;

import com.test.elasticsearch.service.sprider.BaseProcessor;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

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

    @Override
    public void process(Page page) {

    }

    @Override
    public Site getSite() {
        return null;
    }
}
