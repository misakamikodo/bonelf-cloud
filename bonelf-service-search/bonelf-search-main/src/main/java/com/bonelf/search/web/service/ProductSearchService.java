/*
 * Copyright (c) 2020. Bonelf.
 */

package com.bonelf.search.web.service;

import com.bonelf.search.web.domain.dto.ProductSearchDTO;
import com.bonelf.search.web.domain.vo.ProductSearchVO;
import org.springframework.data.domain.Page;

public interface ProductSearchService {
	/**
	 * 搜索
	 * @param productSearchDto
	 * @return
	 */
	Page<ProductSearchVO> searchProduct(ProductSearchDTO productSearchDto);
}
