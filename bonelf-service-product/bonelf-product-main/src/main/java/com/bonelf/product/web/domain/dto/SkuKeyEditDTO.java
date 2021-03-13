package com.bonelf.product.web.domain.dto;

import lombok.Data;

import java.util.List;

/**
 * Sku信息
 * @author bonelf
 * @date 2020-11-14 09:20:49
 */
@Data
public class SkuKeyEditDTO {
	private Long spuId;
    /**
	 * 规格定义列表
     */
    private List<SkuKeyDTO> skuKeys;
}
