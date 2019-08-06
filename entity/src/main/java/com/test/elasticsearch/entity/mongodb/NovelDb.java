package com.test.elasticsearch.entity.mongodb;

import com.test.elasticsearch.anno.AutoValue;
import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.entity.mongodb
 * @ClassName: NovelDB
 * @Author: guang
 * @Description: 小说DTO(db.t_novel.find({},{title: 1, content: 1, _id: 0, by: 1}).sort({'url': 1}))
 * @Date: 2019/8/2 14:49
 * @Version: 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Document(collection = "t_novel")
@CompoundIndexes(
        @CompoundIndex(name = "url_index", def = "{'url': 1}")
)
public class NovelDb implements Serializable {

    /**
     * 主键
     */
    @Id
    @AutoValue
    @Field("id")
    private Long id;

    /**
     * 章节url
     */
    @Field("url")
    private String url;

    /**
     * 章节标题
     */
    @Field("title")
    private String title;

    /**
     * 章节内容
     */
    @Field("content")
    private String content;
}
