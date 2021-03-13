/*
 * Copyright (c) 2020. Bonelf.
 */

package com.bonelf.product.web.domain.bo;


import com.bonelf.product.web.domain.vo.UserCouponVO;

/**
 * 计算价格产生的信息接收接口
 *
 * @author bonelf
 * @since 2020-12-1 14:16:54
 */
public interface PriceInfo {
	/**
	 * 优惠券信息
	 * @param coupon
	 */
	default void getCoupon(UserCouponVO coupon) {
	}

}
