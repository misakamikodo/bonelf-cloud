package com.bonelf.product.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.bonelf.product.web.domain.entity.SkuKey;

import java.util.List;

public interface SkuKeyService extends IService<SkuKey> {

	/**
	 * 规格定义列表
	 * @param spuId
	 * @return
	 */
	List<SkuKey> selectSkuList(Long spuId);
}
