package com.test.elasticsearch.config.listener;

import com.test.elasticsearch.anno.AutoValue;
import com.test.elasticsearch.entity.mongodb.SeqInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.config.listener
 * @ClassName: SaveEventListener
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/6/10 17:06
 * @Version: 1.0
 */
@Slf4j
@Component
public class SaveEventListener extends AbstractMongoEventListener<Object> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        final Object source = event.getSource();
        if (source != null) {
            log.info(source.toString());
            ReflectionUtils.doWithFields(source.getClass(), field -> {
                ReflectionUtils.makeAccessible(field);
                // 如果字段添加了我们自定义的AutoValue注解
                if (field.isAnnotationPresent(AutoValue.class)
                        && field.get(source) instanceof  Number
                        && Long.valueOf(field.get(source).toString()) == 0) {
                    field.set(source, getNextId(source.getClass().getSimpleName()));
                    log.info("集合的ID为：{}", source);
                }
            });
        }
    }

    /**
     * 获取下一个自增ID
     * @param collName 集合（这里用类名，就唯一性来说最好还是存放长类名）名称
     * @return 序列值
     */
    private Long getNextId(String collName) {
        Query query = new Query(Criteria.where("collName").is(collName));
        Update update = new Update();
        update.inc("seqId", 1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(Boolean.TRUE);
        options.returnNew(Boolean.TRUE);
        SeqInfo seqInfo = mongoTemplate.findAndModify(query, update, options, SeqInfo.class);
        log.info(collName + "集合的ID为：{}", seqInfo.getSeqId());
        return seqInfo.getSeqId();
    }
}
