package com.bonelf.user.constant.exception;

import com.bonelf.frame.core.exception.AbstractBaseExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 用户服务异常信息定义
 * </p>
 * @author bonelf
 * @since 2020/10/12 10:48
 */
@Getter
public enum UserExceptionEnum implements AbstractBaseExceptionEnum {

	FREEZE_USER("A0202", "账号已被锁定"),

	ALREADY_REGISTER("A0001", "此账号已注册");

	UserExceptionEnum(String code, String message, String devMessage) {
		this.code = code;
		this.message = message;
		this.devMessage = devMessage;
	}

	UserExceptionEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * 服务状态码 版本号+模块号+序号 类似A0101
	 */
	private final String code;
	/**
	 * 弹窗异常信息
	 */
	private final String message;
	/**
	 * 异常信息
	 */
	private String devMessage;
}
