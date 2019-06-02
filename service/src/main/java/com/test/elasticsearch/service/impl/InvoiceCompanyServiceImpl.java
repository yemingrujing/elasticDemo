package com.test.elasticsearch.service.impl;

import com.test.elasticsearch.entity.InvoiceCompanyEntity;
import com.test.elasticsearch.repository.mysql.InvoiceCompanyRepository;
import com.test.elasticsearch.service.InvoiceCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.service.impl
 * @ClassName: InvoiceCompanyServiceImpl
 * @Author: guang
 * @Description: ${description}
 * @Date: 2019/5/20 14:09
 * @Version: 1.0
 */
@Service
public class InvoiceCompanyServiceImpl implements InvoiceCompanyService {

    @Autowired
    private InvoiceCompanyRepository invoiceCompanyRepository;

    @Override
    public InvoiceCompanyEntity queryById(Integer id) {
        Optional<InvoiceCompanyEntity> invoiceCompanyEntity = invoiceCompanyRepository.findById(id);
        return invoiceCompanyEntity.get();
    }
}
