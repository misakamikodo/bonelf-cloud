package com.bonelf.product.web.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.bonelf.frame.web.domain.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@TableName(value = "bnf_sku")
public class Sku extends BaseEntity implements Serializable {
    /**
     * 商品规格id
     */
    @TableId(value = "sku_id", type = IdType.ASSIGN_ID)
    private Long skuId;

    /**
     * 多规格 逗号分割
     */
    @TableField(value = "sku_value_ids")
    private String skuValueIds;

    @JsonIgnore
    @TableField(exist = false)
    private List<Long> skuValueIdList;

    /**
     * 多规格 商品规格信息 颜色:红;容量:21
     */
    @TableField(value = "specs")
    private String specs;

    /**
     * 商品id
     */
    @TableField(value = "spu_id")
    private Long spuId;

    /**
     * 多规格 规格图片
     */
    @TableField(value = "sku_image")
    private String skuImage;

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
     * 是否默认（0：否，1：是）
     */
    @TableField(value = "is_default")
    private Integer isDefault;

    /**
     * 是否删除（0：否，1：是）
     */
    @TableField(value = "is_deleted")
    @JsonProperty("isDeleted")
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;
}