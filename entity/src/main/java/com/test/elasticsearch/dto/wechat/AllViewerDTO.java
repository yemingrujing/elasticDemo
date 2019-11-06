package com.test.elasticsearch.dto.wechat;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.dto.wechat
 * @ClassName: AllViewerDTO
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/6 17:41
 * @Version: 1.0
 */
@Setter
@Getter
public class AllViewerDTO {

    private Integer id;

    private String noticeId;

    private String userId;

    private String openId;

    private String appId;

    private String arcId;

    private String avatarUrl;

    private String city;

    private String language;

    private String nickName;

    private String province;

    private String telNumber;

    private String uName;

    private String time;

    private String joinerName;

    private String joinerTel;

    private String joinerRemark;
}
