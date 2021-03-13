/*
 * Copyright (c) 2020. Bonelf.
 */

package com.bonelf.pay.web.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PayDTO {
	@ApiModelProperty("订单号")
	private String orderNo;
	/**
	 *
	 */
	@ApiModelProperty("支付方式")
	private Integer payType;

	@ApiModelProperty("订单类型")
	private Integer orderType;
}
