package com.test.elasticsearch.service.wechat.impl;

import cn.hutool.core.bean.BeanUtil;
import com.test.elasticsearch.entity.mysql.wechat.VoteTaskEntity;
import com.test.elasticsearch.param.wechat.VoteTaskParam;
import com.test.elasticsearch.repository.mysql.wechat.VoteTaskRepository;
import com.test.elasticsearch.service.wechat.VoteTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service.wechat.impl
 * @ClassName: VoteTaskServiceImpl
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/4 13:55
 * @Version: 1.0
 */
@Service
public class VoteTaskServiceImpl implements VoteTaskService {

    @Autowired
    private VoteTaskRepository voteTaskRepository;

    @Override
    public void createVoteTask(VoteTaskParam param) {
        if (Objects.nonNull(param)) {
            VoteTaskEntity voteTaskEntity = new VoteTaskEntity();
            BeanUtil.copyProperties(param, voteTaskEntity);
            voteTaskRepository.save(voteTaskEntity);
        }
    }
}
