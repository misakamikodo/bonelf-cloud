/*
 * Copyright (c) 2020. Bonelf.
 */

package com.bonelf.pay.constant.enums;

import com.bonelf.cicada.enums.CodeValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 订单类型
 * </p>
 * @author guaishou
 * @since 2020-10-19 09:14:18
 */
@Getter
@AllArgsConstructor
public enum OrderTypeEnum implements CodeValueEnum<Integer> {
	/**
	 * 订单类型
	 */
	PRODUCT(0, "采购"),

	VIP(1, "Vip")
	;

	private final Integer code;

	private final String value;
}
