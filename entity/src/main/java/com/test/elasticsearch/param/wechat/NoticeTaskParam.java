package com.test.elasticsearch.param.wechat;

import lombok.Getter;
import lombok.Setter;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.param.wechat
 * @ClassName: NoticeTaskParam
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/6 15:27
 * @Version: 1.0
 */
@Setter
@Getter
public class NoticeTaskParam {

    private String openId;

    private String noticeId;

    private String date;

    private String fileNumber;

    private String title;

    private String description;

    private String name;
}
