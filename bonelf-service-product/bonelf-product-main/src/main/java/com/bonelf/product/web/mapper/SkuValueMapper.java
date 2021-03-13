package com.bonelf.product.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bonelf.product.web.domain.entity.SkuValue;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkuValueMapper extends BaseMapper<SkuValue> {
	/**
	 * 查询不属于skuValueIds上级skuKey的其他skuValue
	 * @param skuValueIds
	 * @return
	 */
	List<SkuValue> selectSkuValueDiffFromSkuKey(Long[] skuValueIds);
	/**
	 * 根据SkuValueIds差规格字符串信息
	 * @param skuValueIds
	 * @return
	 */
	String selectSpecsBySkuValueIds(long[] skuValueIds);
}