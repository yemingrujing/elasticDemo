package com.test.elasticsearch.entity.mysql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "t_order_region")
public class OrderRegionEntity implements Serializable {

	/**
	 * 主键
	 */
  	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 省市区ID
	 */
	@Column(name = "province_id")
	private Integer provinceId;

	/**
	 * 所属地区id
	 */
	@Column(name = "parent_id")
	private Integer parentId;

	/**
	 * 省市区名称
	 */
	private String name;

	/**
	 * 详细名称
	 */
	@Column(name = "merger_name")
	private String mergerName;

	/**
	 * 简称
	 */
	@Column(name = "short_name")
	private String shortName;

	@Column(name = "merger_short_name")
	private String mergerShortName;

	/**
	 * 等级类型
	 */
	@Column(name = "level_type")
	private Short levelType;

	/**
	 * 行政编码
	 */
	@Column(name = "city_code")
	private String cityCode;

	/**
	 * 邮政编码
	 */
	@Column(name = "zip_code")
	private String zipCode;

	/**
	 * 拼音
	 */
	private String pinyin;

	/**
	 * 简拼
	 */
	private String jianpin;

	private String firstchar;

	/**
	 * 经度
	 */
	private Double lng;

	/**
	 * 纬度
	 */
	private Double lat;

	/**
	 * 备注
	 */
	private String remarks;

}
