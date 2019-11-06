package com.test.elasticsearch.service.wechat;

import com.test.elasticsearch.dto.wechat.NoticeViewDTO;

import java.util.List;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service.wechat
 * @ClassName: NoticeUserService
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/1 18:18
 * @Version: 1.0
 */
public interface NoticeUserService {

    List<NoticeViewDTO> getMyView(String openId);
}
