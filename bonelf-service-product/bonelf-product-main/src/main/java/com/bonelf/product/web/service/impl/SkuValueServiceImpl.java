package com.bonelf.product.web.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bonelf.product.web.domain.entity.SkuValue;
import com.bonelf.product.web.mapper.SkuValueMapper;
import com.bonelf.product.web.service.SkuValueService;
import org.springframework.stereotype.Service;

@Service
public class SkuValueServiceImpl extends ServiceImpl<SkuValueMapper, SkuValue> implements SkuValueService {

	@Override
	public String getSpecsBySkuValueIds(long[] skuValueIds) {
		return this.baseMapper.selectSpecsBySkuValueIds(skuValueIds);
	}
}
