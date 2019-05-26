package com.test.elasticsearch.elasticsearch.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author guang
 * @title: TOrderRegionEntity
 * @projectName elasticDemo
 * @description: 全国省市区表
 * @date 2019/5/1923:57
 */
@Entity
@Table(name = "t_order_region", schema = "commerce", catalog = "")
public class TOrderRegionEntity {
    private int id;
    private Integer provinceId;
    private Integer parentId;
    private String name;
    private String mergerName;
    private String shortName;
    private String mergerShortName;
    private Short levelType;
    private String cityCode;
    private String zipCode;
    private String pinyin;
    private String jianpin;
    private String firstchar;
    private Double lng;
    private Double lat;
    private String remarks;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "province_id")
    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    @Basic
    @Column(name = "parent_id")
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "merger_name")
    public String getMergerName() {
        return mergerName;
    }

    public void setMergerName(String mergerName) {
        this.mergerName = mergerName;
    }

    @Basic
    @Column(name = "short_name")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Basic
    @Column(name = "merger_short_name")
    public String getMergerShortName() {
        return mergerShortName;
    }

    public void setMergerShortName(String mergerShortName) {
        this.mergerShortName = mergerShortName;
    }

    @Basic
    @Column(name = "level_type")
    public Short getLevelType() {
        return levelType;
    }

    public void setLevelType(Short levelType) {
        this.levelType = levelType;
    }

    @Basic
    @Column(name = "city_code")
    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    @Basic
    @Column(name = "zip_code")
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Basic
    @Column(name = "pinyin")
    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Basic
    @Column(name = "jianpin")
    public String getJianpin() {
        return jianpin;
    }

    public void setJianpin(String jianpin) {
        this.jianpin = jianpin;
    }

    @Basic
    @Column(name = "firstchar")
    public String getFirstchar() {
        return firstchar;
    }

    public void setFirstchar(String firstchar) {
        this.firstchar = firstchar;
    }

    @Basic
    @Column(name = "lng")
    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Basic
    @Column(name = "lat")
    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @Basic
    @Column(name = "remarks")
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TOrderRegionEntity that = (TOrderRegionEntity) o;
        return id == that.id &&
                Objects.equals(provinceId, that.provinceId) &&
                Objects.equals(parentId, that.parentId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(mergerName, that.mergerName) &&
                Objects.equals(shortName, that.shortName) &&
                Objects.equals(mergerShortName, that.mergerShortName) &&
                Objects.equals(levelType, that.levelType) &&
                Objects.equals(cityCode, that.cityCode) &&
                Objects.equals(zipCode, that.zipCode) &&
                Objects.equals(pinyin, that.pinyin) &&
                Objects.equals(jianpin, that.jianpin) &&
                Objects.equals(firstchar, that.firstchar) &&
                Objects.equals(lng, that.lng) &&
                Objects.equals(lat, that.lat) &&
                Objects.equals(remarks, that.remarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, provinceId, parentId, name, mergerName, shortName, mergerShortName, levelType, cityCode, zipCode, pinyin, jianpin, firstchar, lng, lat, remarks);
    }
}
