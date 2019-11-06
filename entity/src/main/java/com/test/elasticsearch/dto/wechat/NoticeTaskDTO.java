package com.test.elasticsearch.dto.wechat;

import lombok.Getter;
import lombok.Setter;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.dto.wechat
 * @ClassName: NoticeTaskDTO
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/6 10:35
 * @Version: 1.0
 */
@Setter
@Getter
public class NoticeTaskDTO {

    private Integer id;

    private String openId;

    private String noticeId;

    private String date;

    private String fileNumber;

    private String title;

    private String description;

    private String name;
}
