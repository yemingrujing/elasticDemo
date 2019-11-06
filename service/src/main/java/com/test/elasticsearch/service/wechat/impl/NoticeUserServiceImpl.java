package com.test.elasticsearch.service.wechat.impl;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.elasticsearch.dto.wechat.NoticeViewDTO;
import com.test.elasticsearch.entity.mysql.wechat.QNoticeTaskEntity;
import com.test.elasticsearch.entity.mysql.wechat.QNoticeUserEntity;
import com.test.elasticsearch.repository.mysql.wechat.NoticeUserRepository;
import com.test.elasticsearch.service.wechat.NoticeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service.wechat.impl
 * @ClassName: NoticeUserServiceImpl
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/6 10:40
 * @Version: 1.0
 */
@Service
public class NoticeUserServiceImpl implements NoticeUserService {

    @Autowired
    private NoticeUserRepository noticeUserRepository;

    @Autowired
    private EntityManager entityManager;

    // JPA查询工厂
    private JPAQueryFactory queryFactory;

    @PostConstruct
    private void initFactory() {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    public List<NoticeViewDTO> getMyView(String openId) {
        if (StrUtil.isBlank(openId)) {
            return Lists.newArrayList();
        }
        QNoticeUserEntity noticeUserEntity = QNoticeUserEntity.noticeUserEntity;
        QNoticeTaskEntity noticeTaskEntity = QNoticeTaskEntity.noticeTaskEntity;
        JPAQuery<NoticeViewDTO> query = queryFactory.select(
                Projections.bean(
                        NoticeViewDTO.class,
                        noticeTaskEntity.id,
                        noticeUserEntity.noticeId,
                        noticeUserEntity.userId,
                        noticeTaskEntity.openId,
                        noticeTaskEntity.date,
                        noticeTaskEntity.fileNumber,
                        noticeTaskEntity.title,
                        noticeTaskEntity.description,
                        noticeTaskEntity.name
                )
        )
                .from(noticeUserEntity)
                .leftJoin(noticeTaskEntity)
                .on(noticeUserEntity.noticeId.eq(noticeTaskEntity.noticeId))
                .where(noticeUserEntity.userId.eq(openId));
        return query.fetchResults().getResults();
    }
}
