/*
 * Copyright (c) 2020. Bonelf.
 */

package com.bonelf.search.web.domain.vo;

import lombok.Data;

@Data
public class ProductSearchVO {
	private Long spuId;
	private String title;
	private String keywords;
	private Double originPrice;
}
