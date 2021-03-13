package com.bonelf.product.web.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bonelf.frame.web.domain.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@TableName(value = "bnf_sku_value")
public class SkuValue extends BaseEntity implements Serializable {
    /**
     * 商品规格id
     */
    @TableId(value = "sku_value_id", type = IdType.ASSIGN_ID)
    private Long skuValueId;

    /**
     * 规格键值id
     */
    @TableField(value = "sku_key_id")
    private Long skuKeyId;

    /**
     * 商品规格名称
     */
    @TableField(value = "sku_value_name")
    private String skuValueName;

    /**
     * 规格图片 保留
     */
    @TableField(value = "sku_image")
    private String skuImage;

    /**
     * 级别 升序 1开始
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 规格信息 保留
     */
    @TableField(value = "specification")
    private String specification;
}