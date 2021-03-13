package com.bonelf.product.web.domain.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.bonelf.frame.web.domain.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author caiyuan
 * @since 2020-07-31
 */
@ApiModel
@Data
//@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bnf_spu")
public class Spu  extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ApiModelProperty("主键ID")
    @TableId(value = "spu_id", type = IdType.ASSIGN_ID)
    private Long spuId;

    /**
     * 商品编号
     */
    @ApiModelProperty("商品编号")
    @TableField("spu_no")
    private String spuNo;

    /**
     * 分类Id
     */
    @ApiModelProperty("分类Id")
    @TableField("category_id")
    private Long categoryId;

    /**
     * 商品标题（名称）
     */
    @ApiModelProperty("商品标题（名称）")
    @TableField("title")
    private String title;

    /**
     * 副标题
     */
    @ApiModelProperty("副标题")
    @TableField("subtitle")
    private String subtitle;

    /**
     * 商品主图
     */
    @ApiModelProperty("商品主图")
    @TableField("main_pic")
    private String mainPic;

    /**
     * 库存（实际在redis bonelf:sku:stock:{sku_id}）
     */
    @TableField(value = "stock")
    private Integer stock;

    /**
     * 多规格 原始价格
     */
    @TableField(value = "origin_price")
    private BigDecimal originPrice;

    /**
     * 多规格 销售价格
     */
    @TableField(value = "sell_price")
    private BigDecimal sellPrice;

    /**
     * 销量
     */
    @ApiModelProperty("销量")
    @TableField("sale")
    private Integer sale;

    /**
     * 基础销售量
     */
    @ApiModelProperty("库存")
    @TableField("base_sell_count")
    private Integer baseSellCount;

    /**
     * 关键字
     */
    @ApiModelProperty("关键字")
    @TableField("keywords")
    private String keywords;

    @JSONField(name = "is_enable_sku")
    @TableField("is_enable_sku")
    private Integer enableSku;

    /**
     * 品牌
     */
    @ApiModelProperty("品牌")
    @TableField("brand")
    private String brand;

    /**
     * 是否为精品（0：否，1：是）
     */
    @ApiModelProperty("是否为精品（0：否，1：是）")
    @TableField("is_fine")
    @JsonProperty("isFine")
    private Integer fine;

    /**
     * 商品详情内容
     */
    @ApiModelProperty("商品详情内容")
    @TableField("content")
    private String content;

    /**
     * 商品状态（0：未上架，1：已上架）
     */
    @ApiModelProperty("商品状态（0：未上架，1：已上架）")
    @TableField("status")
    private Integer status;

    /**
     * 上架时间
     */
    @ApiModelProperty("上架时间")
    @TableField("add_time")
    private LocalDateTime addTime;

    /**
     * 删除标识（0：未删除，1：已删除）
     * mybatisplus没生效 可能版本或配置原因
     */
    @ApiModelProperty("删除标识（0：未删除，1：已删除）")
    @TableField(value = "is_deleted")
    @JsonProperty("isDeleted")
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;
    /**
     * 货号
     */
    @ApiModelProperty("货号")
    @TableField("spu_code")
    private String spuCode;
    /**
     * 属性
     */
    @ApiModelProperty("属性")
    @TableField("property")
    private String property;
    /**
     * 视频
     */
    @ApiModelProperty("视频")
    @TableField("main_video")
    private String mainVideo;

    @ApiModelProperty("排序值")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("点击量")
    @TableField("click_count")
    private Integer clickCount;
}
