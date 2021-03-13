package com.bonelf.product.web.domain.dto;

import lombok.Data;

import java.util.List;

/**
 * <p>
 * 
 * </p>
 * @author bonelf
 * @since 2020/11/14 9:53
 */
@Data
public class SkuKeyDTO {
	/**
	 * 键编号
	 */
	private Long skuKeyId;
	/**
	 * 名称
	 */
	private String skuName;
	/**
	 * 值
	 */
	private List<SkuValueDTO> skuValues;
}
