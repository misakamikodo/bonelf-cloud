package com.bonelf.product.web.domain.dto;

import lombok.Data;

/**
 * <p>
 * 信息
 * </p>
 * @author bonelf
 * @since 2020/11/13 14:06
 */
@Data
public class SkuValueDTO {
	/**
	 * 属性
	 */
	private Long skuValueId;
	/**
	 * 属性名称
	 */
	private String skuValueName;
}
