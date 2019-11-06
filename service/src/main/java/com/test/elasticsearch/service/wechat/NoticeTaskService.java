package com.test.elasticsearch.service.wechat;

import com.test.elasticsearch.dto.wechat.NoticeTaskDTO;

import java.util.List;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service.wechat
 * @ClassName: NoticeTaskService
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/1 18:17
 * @Version: 1.0
 */
public interface NoticeTaskService {

    List<NoticeTaskDTO> getMyCreateNotice(String openId);
}
