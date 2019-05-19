package com.test.elasticsearch.utils;

import java.util.List;

/**
 * @author guang
 * @title: PageBean
 * @projectName elasticDemo
 * @description: 动态分页Bea
 * @date 2019/5/1919:13
 */
public class PageBean<T> {

    private List<T> pageData;
    private Integer currentPage = Integer.valueOf(1);
    private Integer pageSize = Integer.valueOf(10);
    private Integer totalCount;

    public int getPageCount(){
        if (this.totalCount.intValue() % this.pageSize.intValue() == 0) {
            return this.totalCount.intValue() / this.pageSize.intValue();
        }
        return this.totalCount.intValue() / this.pageSize.intValue() + 1;
    }

    public PageBean(List<T> pageData, Integer totalCount) {
        this.pageData = pageData;
        this.totalCount = totalCount;
    }

    public PageBean() {}

    public boolean isFirst()
    {
        return (this.currentPage.intValue() == 1) || (this.totalCount.intValue() == 0);
    }

    public boolean isLast() {
        return (this.totalCount.intValue() == 0) || (this.currentPage.intValue() >= getPageCount());
    }

    public boolean isHasNext()
    {
        return this.currentPage.intValue() < getPageCount();
    }

    public boolean isHasPrev() {
        return this.currentPage.intValue() > 1;
    }
    public Integer getNextPage()
    {
        if (this.currentPage.intValue() >= getPageCount()) {
            return Integer.valueOf(getPageCount());
        }
        return Integer.valueOf(this.currentPage.intValue() + 1);
    }

    public Integer getPrevPage() {
        if (this.currentPage.intValue() <= 1) {
            return Integer.valueOf(1);
        }
        return Integer.valueOf(this.currentPage.intValue() - 1);
    }

    public List<T> getPageData() {
        return this.pageData;
    }

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }

    public Integer getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
