/*
 * Copyright (c) 2020. Bonelf.
 */

package com.bonelf.pay.constant.enums;

import com.bonelf.cicada.enums.CodeValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayTypeEnum implements CodeValueEnum<Integer> {
	/**
	 *
	 */
	ALI(0, "支付宝"),

	WECHAT(1, "微信"),

	MINI(2, "小程序"),
	;
	/**
	 * code 唯一code
	 */
	private final Integer code;
	/**
	 * value 值
	 */
	private final String value;
}
