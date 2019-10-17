package com.test.elasticsearch.controller.designPattern;

import com.test.elasticsearch.event.ContentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.controller.designPattern
 * @ClassName: ContentController
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/10/17 19:05
 * @Version: 1.0
 */
@RestController
public class ContentController {

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/event/{content}")
    public void sendEvent(@PathVariable(value = "content") String content) {
        applicationContext.publishEvent(new ContentEvent(this, content));
    }
}
