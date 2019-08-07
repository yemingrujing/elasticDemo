package com.test.elasticsearch.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.config.anno
 * @ClassName: AutoIncKey
 * @Author: guang
 * @Description: 标识注解：标识主键ID需要自动增长
 * @Date: 2019/6/10 17:03
 * @Version: 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoValue {

}
