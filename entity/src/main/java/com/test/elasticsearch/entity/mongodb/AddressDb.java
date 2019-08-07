package com.test.elasticsearch.entity.mongodb;

import com.test.elasticsearch.anno.AutoValue;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author guang
 * @title: AddressDb
 * @projectName elasticDemo
 * @description: 地址DTO
 * @date 2019/8/621:07
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Document(collection = "t_address")
public class AddressDb implements Serializable {

    // 主键
    @Id
    @AutoValue
    @Field("id")
    @Builder.Default
    private Long id = 0L;

    // 省市区ID
    @Field("province_id")
    private Integer provinceId;

    // 父ID
    @Field("parent_id")
    private Integer parentId;

    // 省市区名称
    @Field("name")
    private String name;

    // 省市区名称
    @Field("merge_name")
    private String mergeName;
}
