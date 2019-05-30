package com.test.elasticsearch.repository;

import com.test.elasticsearch.entity.InvoiceCompanyEntity;
import com.test.elasticsearch.repository.base.BaseJPA;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.repository
 * @ClassName: InvoiceCompanyRepository
 * @Author: guang
 * @Description:
 * @Date: 2019/5/20 10:27
 * @Version: 1.0
 */
@Repository
public interface InvoiceCompanyRepository extends BaseJPA<InvoiceCompanyEntity> {
}
