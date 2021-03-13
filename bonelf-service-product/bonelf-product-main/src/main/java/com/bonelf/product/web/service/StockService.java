/*
 * Copyright (c) 2020. Bonelf.
 */

package com.bonelf.product.web.service;

/**
 * <p>
 * 库存服务
 * </p>
 * @author Chenyuan
 * @since 2020/12/1 15:24
 */
public interface StockService {
	/**
	 * 添加库存
	 * @param spuId
	 * @param num
	 */
	void addSpuStock(Long spuId, Integer num);


	/**
	 * 扣库存操作,秒杀的处理方案
	 * @param skuId
	 * @param num
	 * @return
	 */
	boolean subtractSpuStock(Long skuId, Integer num);

	/**
	 * 添加库存
	 * @param spuId
	 * @param num
	 */
	void addSkuStock(Long spuId, Integer num);


	/**
	 * 扣库存操作,秒杀的处理方案
	 * @param skuId
	 * @param num
	 * @return
	 */
	boolean subtractSkuStock(Long skuId, Integer num);
}
