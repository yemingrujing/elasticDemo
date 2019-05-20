package com.test.elasticsearch.service;

import com.test.elasticsearch.entity.TInvoiceCompanyEntity;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service.impl
 * @ClassName: InvoiceCompanyService
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/5/20 14:08
 * @Version: 1.0
 */
public interface InvoiceCompanyService {

    TInvoiceCompanyEntity queryById(Integer id);
}
