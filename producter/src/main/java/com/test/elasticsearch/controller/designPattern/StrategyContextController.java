package com.test.elasticsearch.controller.designPattern;

import com.test.elasticsearch.service.designPattern.impl.StrategyContext;
import com.test.elasticsearch.utils.Result;
import com.test.elasticsearch.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.controller.designPattern
 * @ClassName: StrategyContextController
 * @Author: guang
 * @Description: 策略模式 + 模板方法模式(支付平台选择)
 * @Date: 2019/10/10 17:50
 * @Version: 1.0
 */
@RestController
public class StrategyContextController {

    @Autowired
    private StrategyContext strategyContext;

    @GetMapping("/strategy/choose")
    public Result choose(@RequestParam String poolId) {
        return ResultUtil.success(strategyContext.getResource(poolId));
    }
}
