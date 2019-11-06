package com.test.elasticsearch.dto.wechat;

import lombok.Getter;
import lombok.Setter;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.dto.wechat
 * @ClassName: NoticeGIDTask
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/6 18:06
 * @Version: 1.0
 */
@Setter
@Getter
public class NoticeGIDTask {

    private Integer id;

    private String groupId;

    private String noticeId;

    private String openId;

    private String date;

    private String fileNumber;

    private String title;

    private String description;

    private String name;
}
