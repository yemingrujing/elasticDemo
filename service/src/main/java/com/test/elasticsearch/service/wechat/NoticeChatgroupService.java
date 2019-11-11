package com.test.elasticsearch.service.wechat;

import com.test.elasticsearch.dto.wechat.NoticeGIDTaskDTO;

import java.util.List;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service.wechat
 * @ClassName: NoticeChatgroupService
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/1 18:17
 * @Version: 1.0
 */
public interface NoticeChatgroupService {

    void storeNoticeGId(String noticeId, String groupId);

    List<NoticeGIDTaskDTO> getGIDTask(String groupId);
}
