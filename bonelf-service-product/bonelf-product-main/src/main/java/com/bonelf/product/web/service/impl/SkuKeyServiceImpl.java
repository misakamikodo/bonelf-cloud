package com.bonelf.product.web.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bonelf.product.web.domain.entity.SkuKey;
import com.bonelf.product.web.mapper.SkuKeyMapper;
import com.bonelf.product.web.service.SkuKeyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuKeyServiceImpl extends ServiceImpl<SkuKeyMapper, SkuKey> implements SkuKeyService {

	@Override
	public List<SkuKey> selectSkuList(Long spuId) {
		return this.baseMapper.selectSkuList(spuId);
	}
}
