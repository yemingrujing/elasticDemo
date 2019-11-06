package com.test.elasticsearch.service.wechat.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.test.elasticsearch.dto.wechat.MyVoteCreateDTO;
import com.test.elasticsearch.entity.mysql.wechat.VoteTaskEntity;
import com.test.elasticsearch.entity.mysql.wechat.VoteUserEntity;
import com.test.elasticsearch.param.wechat.VoteTaskParam;
import com.test.elasticsearch.repository.mysql.wechat.VoteTaskRepository;
import com.test.elasticsearch.repository.mysql.wechat.VoteUserRepository;
import com.test.elasticsearch.service.wechat.VoteTaskService;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    private VoteUserRepository voteUserRepository;

    @Override
    public void createVoteTask(VoteTaskParam param) {
        if (Objects.nonNull(param)) {
            VoteTaskEntity voteTaskEntity = new VoteTaskEntity();
            BeanUtil.copyProperties(param, voteTaskEntity);
            voteTaskRepository.save(voteTaskEntity);
        }
    }

    @Override
    public List<MyVoteCreateDTO> getVoteTask(String voteId) {
        List<VoteTaskEntity> voteTaskEntities = voteTaskRepository.findAll(Example.of(VoteTaskEntity.builder().voteId(voteId).build()));
        if (CollectionUtil.isNotEmpty(voteTaskEntities)) {
            return JSON.parseArray(JSON.toJSONString(voteTaskEntities), MyVoteCreateDTO.class);
        }
        return Lists.newArrayList();
    }

    @Override
    public void storeVoteOne(String voteId, String optionData, String openId) {
        int i = voteTaskRepository.storeVoteUpdata(voteId, optionData);
        if (i > 0) {
            VoteUserEntity voteUserEntity = VoteUserEntity.builder().voteId(voteId).userId(openId).build();
            voteUserRepository.save(voteUserEntity);
        }
    }
}
