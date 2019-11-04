package com.test.elasticsearch.repository.mysql.wechat;

import com.test.elasticsearch.entity.mysql.wechat.VoteTaskEntity;
import com.test.elasticsearch.repository.mysql.base.BaseJPA;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.repository.mysql
 * @ClassName: VoteTaskRepository
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/11/1 17:55
 * @Version: 1.0
 */
@Repository
public interface VoteTaskRepository extends BaseJPA<VoteTaskEntity> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE VoteTaskEntity SET optionData = :optionData WHERE voteId= :voteId")
   int storeVoteUpdata(@Param("voteId") String voteId, @Param("optionData") String optionData);
}
