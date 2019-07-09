package com.test.elasticsearch.entity.mongodb;

import com.test.elasticsearch.anno.AutoValue;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
@Document(collection = "t_order_region")
public class OrderRegionDb implements Serializable {

	/**
	 * 主键
	 */
  	@Id
	@AutoValue
	@Field("id")
	private Long id;

	/**
	 * 省市区ID
	 */
	@Field("province_id")
	private Long provinceId;

	/**
	 * 所属地区id
	 */
	@Field("parent_id")
	private Long parentId;

	/**
	 * 省市区名称
	 */
	@Field("name")
	private String name;

	/**
	 * 详细名称
	 */
	@Field("merger_name")
	private String mergerName;

	/**
	 * 简称
	 */
	@Field("short_name")
	private String shortName;

	@Field("merger_short_name")
	private String mergerShortName;

	/**
	 * 等级类型
	 */
	@Field("level_type")
	private Short levelType;

	/**
	 * 行政编码
	 */
	@Field("city_code")
	private String cityCode;

	/**
	 * 邮政编码
	 */
	@Field("zip_code")
	private String zipCode;

	/**
	 * 拼音
	 */
	@Field("pinyin")
	private String pinyin;

	/**
	 * 简拼
	 */
	@Field("jianpin")
	private String jianpin;

	@Field("firstchar")
	private String firstchar;

	/**
	 * 经度
	 */
	@Field("lng")
	private Double lng;

	/**
	 * 纬度
	 */
	@Field("lat")
	private Double lat;

	/**
	 * 备注
	 */
	@Field("remarks")
	private String remarks;

}
