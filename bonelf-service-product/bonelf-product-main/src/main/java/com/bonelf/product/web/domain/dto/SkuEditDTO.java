package com.bonelf.product.web.domain.dto;

import com.bonelf.product.web.domain.entity.Sku;
import lombok.Data;

import java.util.List;

/**
 * Sku信息
 * @author bonelf
 * @date 2020-11-14 09:20:49
 */
@Data
public class SkuEditDTO {
	private Long spuId;
    /**
     * 规格笛卡尔积列表
     */
    private List<Sku> skus;
}
