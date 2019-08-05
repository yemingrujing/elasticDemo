package com.test.elasticsearch.service.sprider.processor.pipiline;

import com.test.elasticsearch.repository.mongodb.NovelMBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service.sprider.processor
 * @ClassName: NovelPipeline
 * @Author: guang
 * @Description: 爬取小说service
 * @Date: 2019/8/2 14:45
 * @Version: 1.0
 */
@Service
public class NovelPipeline implements Pipeline {

    @Autowired
    private NovelMBRepository novelMBRepository;

    @Override
    public void process(ResultItems resultItems, Task task) {
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {

        }
    }
}
