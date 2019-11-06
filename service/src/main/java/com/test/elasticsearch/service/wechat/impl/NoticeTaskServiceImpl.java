package com.test.elasticsearch.service.wechat.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.test.elasticsearch.dto.wechat.NoticeTaskDTO;
import com.test.elasticsearch.entity.mysql.wechat.NoticeTaskEntity;
import com.test.elasticsearch.param.wechat.NoticeTaskParam;
import com.test.elasticsearch.repository.mysql.wechat.NoticeTaskRepository;
import com.test.elasticsearch.service.wechat.NoticeTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service.wechat.impl
 * @ClassName: NoticeTaskServiceImpl
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/6 10:31
 * @Version: 1.0
 */
@Service
public class NoticeTaskServiceImpl implements NoticeTaskService {

    @Autowired
    private NoticeTaskRepository noticeTaskRepository;

    @Override
    public List<NoticeTaskDTO> getMyCreateNotice(String openId) {
        List<NoticeTaskEntity> noticeTaskEntities = noticeTaskRepository.findAll(Example.of(NoticeTaskEntity.builder().openId(openId).build()));
        return JSON.parseArray(JSON.toJSONString(noticeTaskEntities), NoticeTaskDTO.class);
    }

    @Override
    public void createNoticeTask(NoticeTaskParam param) {
        NoticeTaskEntity noticeTaskEntity = new NoticeTaskEntity();
        BeanUtil.copyProperties(param, noticeTaskEntity);
        noticeTaskRepository.save(noticeTaskEntity);
    }

    @Override
    public List<NoticeTaskDTO> getNoticeTask(String noticeId) {
        List<NoticeTaskEntity> noticeTaskEntities = noticeTaskRepository.findAll(Example.of(NoticeTaskEntity.builder().noticeId(noticeId).build()));
        return JSON.parseArray(JSON.toJSONString(noticeTaskEntities), NoticeTaskDTO.class);
    }
}
