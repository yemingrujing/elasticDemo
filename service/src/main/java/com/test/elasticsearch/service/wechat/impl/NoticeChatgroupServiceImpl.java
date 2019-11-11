package com.test.elasticsearch.service.wechat.impl;

import cn.hutool.core.util.StrUtil;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.elasticsearch.dto.wechat.NoticeGIDTaskDTO;
import com.test.elasticsearch.entity.mysql.wechat.NoticeChatgroupEntity;
import com.test.elasticsearch.entity.mysql.wechat.QNoticeChatgroupEntity;
import com.test.elasticsearch.entity.mysql.wechat.QNoticeTaskEntity;
import com.test.elasticsearch.repository.mysql.wechat.NoticeChatgroupRepository;
import com.test.elasticsearch.service.wechat.NoticeChatgroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service.wechat.impl
 * @ClassName: NoticeChatgroupServiceImpl
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/6 17:51
 * @Version: 1.0
 */
@Service
public class NoticeChatgroupServiceImpl implements NoticeChatgroupService {

    @Autowired
    private NoticeChatgroupRepository noticeChatgroupRepository;

    @Autowired
    private EntityManager entityManager;

    // JPA查询工厂
    private JPAQueryFactory queryFactory;

    @PostConstruct
    private void initFactory() {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public void storeNoticeGId(String noticeId, String groupId) {
        long count = noticeChatgroupRepository.count(Example.of(NoticeChatgroupEntity.builder().noticeId(noticeId).groupId(groupId).build()));
        if (count == 0) {
            noticeChatgroupRepository.save(NoticeChatgroupEntity.builder().noticeId(noticeId).groupId(groupId).build());
        }
    }

    @Override
    public List<NoticeGIDTaskDTO> getGIDTask(String groupId) {
        if (StrUtil.isNotBlank(groupId)) {
            return null;
        }
        QNoticeChatgroupEntity noticeChatgroupEntity = QNoticeChatgroupEntity.noticeChatgroupEntity;
        QNoticeTaskEntity noticeTaskEntity = QNoticeTaskEntity.noticeTaskEntity;
        JPAQuery<NoticeGIDTaskDTO> query = queryFactory.select(
                Projections.bean(
                        NoticeGIDTaskDTO.class,
                        noticeChatgroupEntity.id,
                        noticeChatgroupEntity.groupId,
                        noticeChatgroupEntity.noticeId,
                        noticeTaskEntity.openId,
                        noticeTaskEntity.date,
                        noticeTaskEntity.fileNumber,
                        noticeTaskEntity.title,
                        noticeTaskEntity.description,
                        noticeTaskEntity.name
                )
        )
                .from(noticeChatgroupEntity)
                .leftJoin(noticeTaskEntity)
                .on(noticeChatgroupEntity.noticeId.eq(noticeTaskEntity.noticeId))
                .where(noticeChatgroupEntity.groupId.eq(groupId));
        return query.fetchResults().getResults();
    }
}
