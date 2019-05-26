package com.test.elasticsearch.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guang
 * @title: TestController
 * @projectName elasticDemo
 * @description: TODO
 * @date 2019/5/2223:19
 */
@RestController
@Slf4j
public class TestController {

    @GetMapping("/test")
    public void test(){
        log.info("进入test方法");
        log.debug("进入test方法");
        log.warn("进入test方法");
        log.error("进入test方法");
    }
}
