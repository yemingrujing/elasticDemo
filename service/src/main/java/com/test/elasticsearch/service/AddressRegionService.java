package com.test.elasticsearch.service;

import java.util.List;

/**
 * AddressRegionService
 */
public interface AddressRegionService {

    /**
     * 根据主键查询地区信息
     * @param id
     * @return
     */
    String queryAddressRegionById(String id);

    /**
     * 查询所有地区信息
     * @return
     */
    List queryScrollSearch();

    /**
     * Long id, Long provinceId, Long parentId,String name
     * @param id
     * @param provinceId
     * @param parentId
     * @param name
     * @return
     */
    List queryTemplateSearch(Long id, Long provinceId, Long parentId, String name);
}
