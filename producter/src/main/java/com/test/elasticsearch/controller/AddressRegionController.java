package com.test.elasticsearch.controller;

import com.alibaba.fastjson.JSONObject;
import com.test.elasticsearch.service.AddressRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 控制类
 */
@RestController
public class AddressRegionController {

    @Autowired
    private AddressRegionService addressRegionService;

    @GetMapping("/es/query/{id}")
    public JSONObject queryById(@PathVariable("id") String id) {
        String response = addressRegionService.queryAddressRegionById(id);
        if (response != null) {
            return JSONObject.parseObject(response);
        }
        return null;
    }

    @GetMapping("/es/scroll/search")
    public List queryAll() {
        List<JSONObject>  response = addressRegionService.queryScrollSearch();
        if (response != null) {
            return response;
        }
        return null;
    }

    @PostMapping("/es/template/search")
    public List queryScrollSearch(@RequestParam(required = false) Long id,
                                        @RequestParam(required = false) Long provinceId,
                                        @RequestParam(required = false) Long parentId,
                                        @RequestParam(required = false) String name) {
        List<JSONObject> response = addressRegionService.queryTemplateSearch(id, provinceId, parentId, name);
        if (response != null) {
            return response;
        }
        return null;
    }
}
