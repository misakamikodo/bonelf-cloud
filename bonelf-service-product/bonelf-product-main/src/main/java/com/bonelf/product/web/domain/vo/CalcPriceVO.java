package com.bonelf.product.web.domain.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class CalcPriceVO {
	@ApiParam("用户可用优惠券信息")
	private UserCouponVO userCoupon;
}