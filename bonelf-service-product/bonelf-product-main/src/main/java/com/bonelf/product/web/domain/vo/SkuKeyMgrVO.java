package com.bonelf.product.web.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class SkuKeyMgrVO {
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
	private List<SkuValueMgrVO> skuValues;
}
