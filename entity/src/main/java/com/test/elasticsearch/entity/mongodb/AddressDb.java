package com.test.elasticsearch.entity.mongodb;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
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
    private Integer id;

    // 省市区ID
    private Integer provinceId;

    // 父ID
    private Integer parentId;

    // 省市区名称
    private String name;
}
