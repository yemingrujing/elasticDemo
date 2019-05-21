package com.test.elasticsearch.controller;

import com.test.elasticsearch.service.InvoiceCompanyService;
import com.test.elasticsearch.utils.Result;
import com.test.elasticsearch.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.controller
 * @ClassName: InvoiceCompanyController
 * @Author: guang
 * @Description: 发票公司信息
 * @Date: 2019/5/21 9:34
 * @Version: 1.0
 */
@RestController
public class InvoiceCompanyController {

    @Autowired
    private InvoiceCompanyService invoiceCompanyService;

    @GetMapping("/invoice/company/query/{id}")
    public Result queryById(@PathVariable("id") Integer id) {
        return ResultUtil.success(invoiceCompanyService.queryById(id));
    }
}
