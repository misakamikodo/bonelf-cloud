/*
 * Copyright (c) 2020. Bonelf.
 */

package com.bonelf.search.web.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * 类型lowercase就是types
 * 查看type
 * curl -H "Content-Type:application/json" -XGET 'http://localhost:9200/bonelf_spu/_mapping/?pretty'
 * @author bonelf
 */
@Data
@Document(indexName = "bonelf_product", indexStoreType = "fs", shards = 5, replicas = 1, refreshInterval = "-1")
public class BonelfSpu {
	@Id
	@JsonProperty("spu_id")
	@Field(type = FieldType.Long, name = "spu_id")
	private Long spuId;

	@Field(type = FieldType.Keyword)
	private String keywords;

	/**
	 * 商品编号
	 */
	@ApiModelProperty("商品编号")
	@JsonProperty("spu_no")
	@Field(type = FieldType.Auto, name = "spu_no")
	private String spuNo;

	/**
	 * 分类Id
	 */
	@ApiModelProperty("分类Id")
	@JsonProperty("category_id")
	@Field(type = FieldType.Long, name = "category_id")
	private Long categoryId;

	/**
	 * 商品标题（名称）
	 */
	@ApiModelProperty("商品标题（名称）")
	@Field(type = FieldType.Text, name = "title", analyzer = "ik_title")
	private String title;

	/**
	 * 副标题
	 */
	@ApiModelProperty("副标题")
	@Field(type = FieldType.Auto, name = "subtitle")
	private String subtitle;

	/**
	 * 商品主图
	 */
	@ApiModelProperty("商品主图")
	@JsonProperty("main_pic")
	@Field(type = FieldType.Auto, name = "main_pic")
	private String mainPic;

	/**
	 * 库存（实际在redis bonelf:sku:stock:{sku_id}）
	 */
	@Field(type = FieldType.Auto, name = "stock")
	private Integer stock;

	/**
	 * 多规格 原始价格
	 */
	@JsonProperty("origin_price")
	@Field(type = FieldType.Float, name = "origin_price")
	private Double originPrice;

	/**
	 * 多规格 销售价格
	 */
	@JsonProperty("sell_price")
	@Field(type = FieldType.Float, name = "sell_price")
	private Double sellPrice;

	/**
	 * 销量
	 */
	@ApiModelProperty("销量")
	@Field(type = FieldType.Auto, name = "sales")
	private Integer sales;

	/**
	 * 基础销售量
	 */
	@ApiModelProperty("库存")
	@JsonProperty("base_sell_count")
	@Field(type = FieldType.Auto, name = "base_sell_count")
	private Integer baseSellCount;

	@JsonProperty("is_enable_sku")
	@Field(type = FieldType.Auto, name = "is_enable_sku")
	private Integer enableSku;

	/**
	 * 品牌
	 */
	@ApiModelProperty("品牌")
	@Field(type = FieldType.Auto, name = "brand")
	private String brand;

	/**
	 * 是否为精品（0：否，1：是）
	 */
	@ApiModelProperty("是否为精品（0：否，1：是）")
	@Field(type = FieldType.Auto, name = "is_fine")
	@JsonProperty("is_fine")
	private Integer fine;

	/**
	 * 商品详情内容
	 */
	@ApiModelProperty("商品详情内容")
	@Field(type = FieldType.Auto, name = "content")
	private String content;

	/**
	 * 商品状态（0：未上架，1：已上架）
	 */
	@ApiModelProperty("商品状态（0：未上架，1：已上架）")
	@Field(type = FieldType.Auto, name = "status")
	private Integer status;

	/**
	 * 上架时间
	 */
	@ApiModelProperty("上架时间")
	@JsonProperty("add_time")
	@Field(type = FieldType.Date, name = "add_time")
	private Date addTime;

	/**
	 * 删除标识（0：未删除，1：已删除）
	 * mybatisplus没生效 可能版本或配置原因
	 */
	@ApiModelProperty("删除标识（0：未删除，1：已删除）")
	@Field(name = "is_deleted")
	@JsonProperty("is_deleted")
	private Boolean deleted;
	/**
	 * 货号
	 */
	@ApiModelProperty("货号")
	@JsonProperty("spu_code")
	@Field(type = FieldType.Auto, name = "spu_code")
	private String spuCode;
	/**
	 * 属性
	 */
	@ApiModelProperty("属性")
	@Field(type = FieldType.Auto, name = "property")
	private String property;
	@JsonProperty("spu_type")
	@Field(type = FieldType.Auto, name = "spu_type")
	private String spuType;
	/**
	 * 视频
	 */
	@ApiModelProperty("视频")
	@JsonProperty("main_video")
	@Field(type = FieldType.Auto, name = "main_video")
	private String mainVideo;

	@ApiModelProperty("排序值")
	@Field(type = FieldType.Auto, name = "sort")
	private Integer sort;

	@ApiModelProperty("点击量")
	@JsonProperty("click_count")
	@Field(type = FieldType.Auto, name = "click_count")
	private Integer clickCount;
}
