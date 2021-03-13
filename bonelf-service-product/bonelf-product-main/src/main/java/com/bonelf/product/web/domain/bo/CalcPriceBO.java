/*
 * Copyright (c) 2020. Bonelf.
 */

package com.bonelf.product.web.domain.bo;

import lombok.Data;

import java.util.List;

@Data
public class CalcPriceBO {
	/**
	 * 是否在没有选择优惠券时自动选择最佳优惠券计算价格 默认 是
	 */
	private Boolean autoSelectCoupon = true;
	/**
	 * 选定优惠券
	 */
	private Boolean couponId;
	/*===========================立即结算方式===========================*/
	/**
	 * 商品编号 与skuId二选一 此方式商品需不支持规格
	 */
	private Long spuId;
	/**
	 * 商品编号 与spuId二选一 此方式商品需支持规格
	 */
	private Long skuId;
	/**
	 * 购买数量
	 */
	private Integer buyNum;
	/*===========================购物车结算方式===========================*/
	/**
	 * 商品编号 与skuId或spuId选一
	 */
	private List<Long> cartId;
	/*===========================价格计算中间产物定义接口接收===========================*/
	private PriceInfo priceInfo = new PriceInfo() {
	};
}