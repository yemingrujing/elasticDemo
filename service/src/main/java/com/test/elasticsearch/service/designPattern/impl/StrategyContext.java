package com.test.elasticsearch.service.designPattern.impl;

import com.test.elasticsearch.service.designPattern.Strategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service.designPattern.impl
 * @ClassName: StrategyServiceImpl
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/10/10 17:46
 * @Version: 1.0
 */
@Slf4j
@Service
public class StrategyContext {

    @Autowired
    private final Map<String, Strategy> strategyMap = new ConcurrentHashMap<>();

    public StrategyContext(Map<String, Strategy> strategyMap) {
        this.strategyMap.clear();
        strategyMap.forEach(this.strategyMap::put);
    }

    public String getResource(String poolId) {
        return strategyMap.get(poolId).getVpcList(poolId);
    }
}
