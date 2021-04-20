/*
 * Copyright (c) 2021. Bonelf.
 */

package com.bonelf.product.consumer;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bonelf.frame.base.util.redis.RedisUtil;
import com.bonelf.product.constant.CacheConstant;
import com.bonelf.product.web.domain.entity.Sku;
import com.bonelf.product.web.domain.entity.Spu;
import com.bonelf.product.web.service.SkuService;
import com.bonelf.product.web.service.SpuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductMqConsumer {
	@Autowired
	private SpuService spuService;
	@Autowired
	private SkuService skuService;
	@Autowired
	private RedisUtil redisUtil;

	/*
	 * 可以定义Sink筛选Tag
	 * 也可以使用condition筛选
	 * 我使用Tag筛选Topic，condition筛选head
	 * @param message
	 */
	/**
	 * 缓存点击量入库
	 */
	// @StreamListener(value = Sink.INPUT, condition = "headers['rocketmq_TAGS']=='SpuStatisticSumTag'")
	public void spuStatisticSumTag(String message) {
		spuService.sumStatistic();
	}

	/**
	 * 商品售出
	 */
	// @StreamListener(value = Sink.INPUT, condition = "headers['rocketmq_TAGS']=='ProductPaidTag'")
	public void productPaidTag(String message) {
		// FIXME: 2020/12/1 数量
		spuService.spuSoldOut(Long.parseLong(message));
	}

	/**
	 * 库存变化
	 */
	// @StreamListener(value = Sink.INPUT, condition = "headers['rocketmq_TAGS']=='StockSkuTag'")
	public void stockSkuTag(String message) {
		skuService.update(Wrappers.<Sku>lambdaUpdate().set(Sku::getStock, (Integer)redisUtil.hget(CacheConstant.SKU_STOCK_HASH, message)).eq(Sku::getSkuId, Long.parseLong(message)));
		// 更新 Spu 表总库存 XXX 这里耗时可能有点大
		spuService.updateStockBySkuId(Long.parseLong(message));
	}

	// @StreamListener(value = Sink.INPUT, condition = "headers['rocketmq_TAGS']=='StockSpuTag'")
	public void stockSpuTag(String message) {
		spuService.update(Wrappers.<Spu>lambdaUpdate().set(Spu::getStock, (Integer)redisUtil.hget(CacheConstant.SKU_STOCK_HASH, message)).eq(Spu::getSpuId, Long.parseLong(message)));
	}

}
