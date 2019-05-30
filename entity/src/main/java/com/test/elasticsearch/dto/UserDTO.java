package com.test.elasticsearch.dto;

import lombok.Data;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.dto
 * @ClassName: UserDTO
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/5/30 18:25
 * @Version: 1.0
 */
@Data
public class UserDTO {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 用户密码
     */
    private String passWord;

    /**
     * 性别（男-0,1-女）
     */
    private Short sex;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户生日
     */
    private java.util.Date birthday;

    /**
     * 允许设备最大接入数量
     */
    private Integer maxLoginNumber;

    /**
     * 是否年缴费( 0 - 否，1 - 是) 第一次缴费后必须按年缴费
     */
    private Short isYearPayment;

    /**
     * 创建时间
     */
    private java.util.Date createTime;

    /**
     * 注册渠道(0：APP,1：PC,2：旁听)
     */
    private Short registerChannel;
}
