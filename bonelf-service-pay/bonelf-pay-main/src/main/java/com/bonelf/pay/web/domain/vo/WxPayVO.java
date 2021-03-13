/*
 * Copyright (c) 2020. Bonelf.
 */

package com.bonelf.pay.web.domain.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * 微信支付回显
 * </p>
 * @author bonelf
 * @since 2020/9/9 14:42
 */
@Builder
@Data
public class WxPayVO {
	/**
	 * nonceStr
	 */
	private String nonceStr;
	/**
	 * signType
	 */
	private String signType;
	/**
	 * packageValue
	 */
	@JsonProperty(value = "package")
	private String packAge;
	/**
	 * timeStamp
	 */
	private String timeStamp;
	/**
	 * paySign
	 */
	private String paySign;
}