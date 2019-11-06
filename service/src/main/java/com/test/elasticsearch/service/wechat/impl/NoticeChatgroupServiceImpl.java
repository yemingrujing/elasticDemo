package com.test.elasticsearch.service.wechat.impl;

import com.test.elasticsearch.dto.wechat.NoticeGIDTask;
import com.test.elasticsearch.repository.mysql.wechat.NoticeChatgroupRepository;
import com.test.elasticsearch.service.wechat.NoticeChatgroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service.wechat.impl
 * @ClassName: NoticeChatgroupServiceImpl
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/6 17:51
 * @Version: 1.0
 */
@Service
public class NoticeChatgroupServiceImpl implements NoticeChatgroupService {

    @Autowired
    private NoticeChatgroupRepository noticeChatgroupRepository;

    @Override
    public void storeNoticeGId(String noticeId, String groupId) {

    }

    @Override
    public List<NoticeGIDTask> getGIDTask(String groupId) {
        return null;
    }
}
