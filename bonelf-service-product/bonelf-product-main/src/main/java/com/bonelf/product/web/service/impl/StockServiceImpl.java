/*
 * Copyright (c) 2020. Bonelf.
 */

package com.bonelf.product.web.service.impl;

import com.bonelf.frame.base.util.redis.RedisUtil;
import com.bonelf.frame.core.exception.BonelfException;
import com.bonelf.product.constant.CacheConstant;
import com.bonelf.product.constant.MQSendTag;
import com.bonelf.product.constant.exception.ProductExceptionEnum;
import com.bonelf.product.web.domain.request.StockChangeRequest;
import com.bonelf.product.web.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockServiceImpl implements StockService {
	// @Autowired
	// private ProductProvider productProvider;
	@Autowired
	private RedisUtil redisUtil;

	private enum StockType {
		sku, spu
	}

	/**
	 * 添加库存
	 * @param spuId
	 * @param num
	 */
	@Override
	public void addSpuStock(Long spuId, Integer num) {
		addStock(StockType.spu, spuId, num);
	}

	/**
	 * 扣库存操作,秒杀的处理方案
	 * @param skuId
	 * @param num
	 * @return
	 */
	@Override
	public boolean subtractSpuStock(Long skuId, Integer num) {
		return subtractStock(StockType.spu, skuId, num);
	}

	/**
	 * 添加库存
	 * @param spuId
	 * @param num
	 */
	@Override
	public void addSkuStock(Long spuId, Integer num) {
		addStock(StockType.sku, spuId, num);
	}

	/**
	 * 扣库存操作,秒杀的处理方案
	 * @param skuId
	 * @param num
	 * @return
	 */
	@Override
	public boolean subtractSkuStock(Long skuId, Integer num) {
		return subtractStock(StockType.sku, skuId, num);
	}

	/**
	 * 添加库存
	 * @param stockType
	 * @param id
	 * @param num
	 */
	private void addStock(StockType stockType, Long id, Integer num) {
		String redisKey = getRedisKeyByStockType(stockType);
		redisUtil.hincr(redisKey, String.valueOf(id), num.longValue());
		// productProvider.send(getTagByStockType(stockType), new StockChangeRequest(id, num));
	}

	/**
	 * 扣库存操作，秒杀的处理方案
	 * @param stockType
	 * @param id
	 * @param num
	 * @return
	 */
	private boolean subtractStock(StockType stockType, Long id, Integer num) {
		String redisKey = getRedisKeyByStockType(stockType);
		Object value = redisUtil.hget(redisKey, String.valueOf(id));
		if (value == null) {
			//前提 提前将商品库存放入缓存 ,如果缓存不存在，视为没有该商品
			throw new BonelfException(ProductExceptionEnum.STOCK_ERROR);
		}
		//先检查 库存是否充足
		Integer stock = (Integer)value;
		if (stock < num) {
			log.info("库存不足");
			throw new BonelfException(ProductExceptionEnum.STOCK_ERROR);
		}
		//不可在这里直接操作数据库减库存，否则导致数据不安全
		//因为此时可能有其他线程已经将redis的key修改了
		//redis 减少库存，然后才能操作数据库
		double newStock = redisUtil.hdecr(redisKey, String.valueOf(id), num.longValue());
		//库存充足
		if (newStock >= 0) {
			log.info("成功抢购");
			// 真正扣库存操作 可用MQ 进行 redis 和 mysql 的数据同步，减少响应时间
			// productProvider.send(getTagByStockType(stockType), new StockChangeRequest(id, num));
		} else {
			//库存不足，需要增加刚刚减去的库存
			redisUtil.hincr(redisKey, String.valueOf(id), num.longValue());
			log.info("库存不足，并发");
			throw new BonelfException(ProductExceptionEnum.STOCK_ERROR);
		}
		return true;
	}

	private String getRedisKeyByStockType(StockType stockType) {
		switch (stockType) {
			case sku:
				return CacheConstant.SKU_STOCK_HASH;
			case spu:
				return CacheConstant.SPU_STOCK_HASH;
			default:
				throw new RuntimeException("invalid stockType");
		}
	}

	private String getTagByStockType(StockType stockType) {
		switch (stockType) {
			case sku:
				return MQSendTag.STOCK_SKU_TAG;
			case spu:
				return MQSendTag.STOCK_SPU_TAG;
			default:
				throw new RuntimeException("invalid stockType");
		}
	}

}
