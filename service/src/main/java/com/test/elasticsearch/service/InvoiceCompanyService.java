package com.test.elasticsearch.service;

import com.test.elasticsearch.entity.InvoiceCompanyEntity;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service.impl
 * @ClassName: InvoiceCompanyService
 * @Author: guang
 * @Description: 发票公司信息
 * @Date: 2019/5/20 14:08
 * @Version: 1.0
 */
public interface InvoiceCompanyService {

    InvoiceCompanyEntity queryById(Integer id);
}
