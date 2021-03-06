package com.test.elasticsearch.repository.mysql.wechat;

import com.test.elasticsearch.entity.mysql.wechat.TaskEntity;
import com.test.elasticsearch.repository.mysql.base.BaseJPA;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.repository.mysql
 * @ClassName: TaskRepository
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/1 17:54
 * @Version: 1.0
 */
@Repository
public interface TaskRepository extends BaseJPA<TaskEntity> {
}
