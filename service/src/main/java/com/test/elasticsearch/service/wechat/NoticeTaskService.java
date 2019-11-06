package com.test.elasticsearch.service.wechat;

import com.test.elasticsearch.dto.wechat.NoticeTaskDTO;
import com.test.elasticsearch.param.wechat.NoticeTaskParam;

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

    /**
     * 查询创建的通知任务
     * @param openId
     * @return
     */
    List<NoticeTaskDTO> getMyCreateNotice(String openId);

    /**
     * 创建通知任务
     * @param param
     */
    void createNoticeTask(NoticeTaskParam param);

    /**
     * 群通知任务的详情数据
     * @param noticeId
     * @return
     */
    List<NoticeTaskDTO> getNoticeTask(String noticeId);
}
