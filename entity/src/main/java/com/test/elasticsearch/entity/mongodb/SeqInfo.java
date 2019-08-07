package com.test.elasticsearch.entity.mongodb;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.entity.mongodb
 * @ClassName: SeqInfo
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/6/10 17:21
 * @Version: 1.0
 */
@Setter
@Getter
@ToString
@Entity
@Document(collection = "sequence")
public class SeqInfo {

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 集合名称
     */
    @Field
    private String collName;

    /**
     * 序列值
     */
    @Field
    private Long seqId;
}
