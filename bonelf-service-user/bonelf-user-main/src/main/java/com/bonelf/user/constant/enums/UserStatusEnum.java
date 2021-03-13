/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.user.constant.enums;

import com.bonelf.cicada.enums.CodeValueEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatusEnum implements CodeValueEnum<Integer> {
	/**
	 *
	 */
	FREEZE(1, "冻结"),

	ENABLE(0, "启用"),
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
