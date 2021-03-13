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
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@TableName(value = "bnf_sku_key")
public class SkuKey extends BaseEntity implements Serializable {
    /**
     * 商品规格id
     */
    @TableId(value = "sku_key_id", type = IdType.ASSIGN_ID)
    private Long skuKeyId;

    /**
     * 商品id
     */
    @TableField(value = "spu_id")
    private Long spuId;

    /**
     * 商品规格名称
     */
    @TableField(value = "sku_name")
    private String skuName;

    /**
     * 级别 升序 1开始
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 规格信息
     */
    @TableField(value = "specification")
    private String specification;

    @TableField(exist = false)
    private List<SkuValue> skuValue;
}