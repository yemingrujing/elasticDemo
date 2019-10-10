package com.test.elasticsearch.service.designPattern.impl;

import com.test.elasticsearch.service.designPattern.Strategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service.designPattern.impl
 * @ClassName: StrategyAImpl
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/10/10 17:42
 * @Version: 1.0
 */
@Slf4j
@Component("A")
public class StrategyAImpl implements Strategy {
    @Override
    public String getVpcList(String id) {
        log.info("A,getVpcList =========== {}", id);
        return id;
    }
}
