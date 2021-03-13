package com.bonelf.product.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bonelf.product.web.domain.entity.SkuKey;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkuKeyMapper extends BaseMapper<SkuKey> {

	/**
	 * 所有规格列表
	 * @param spuId
	 * @return
	 */
	List<SkuKey> selectSkuList(Long spuId);
}