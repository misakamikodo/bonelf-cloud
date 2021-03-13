package com.bonelf.product.web.domain.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;

@Data
public class ConfirmOrderDTO {
	@ApiParam("选定优惠券")
	private Boolean couponId;
	/*===========================立即结算方式：一般不需要计算价格而是直接订单确认===========================*/
	@ApiParam("商品编号 与skuId二选一 此方式商品需不支持规格")
	private Long spuId;
	@ApiParam("商品编号 与spuId二选一 此方式商品需支持规格")
	private Long skuId;
	@ApiParam("购买数量")
	private Integer buyNum;
	/*===========================购物车结算方式===========================*/
	@ApiParam("商品编号 与skuId或spuId选一")
	private List<Long> cartId;
}