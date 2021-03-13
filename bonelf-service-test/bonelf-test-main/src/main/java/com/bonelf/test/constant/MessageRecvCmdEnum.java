package com.bonelf.test.constant;


import com.bonelf.cicada.enums.CodeValueEnum;
import lombok.Getter;

@Getter
public enum MessageRecvCmdEnum implements CodeValueEnum {
	/**
	 * 用户连接提示
	 */
	INIT_CALLBACK(0, "连接成功提示"),

	/**
	 * 心跳测试
	 */
	PING_PONG(1, "心跳测试"),

	/**
	 * 普通测试
	 */
	TEST(2, "普通测试"),
	;
	private final Integer code;
	private final String value;

	MessageRecvCmdEnum(Integer code, String desc) {
		this.code = code;
		this.value = desc;
	}
}
