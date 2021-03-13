/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.auth.core.exception;

import com.bonelf.frame.core.exception.AbstractBaseExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 认证服务异常信息定义
 * </p>
 * @author bonelf
 * @since 2020/10/12 10:48
 */
@Getter
public enum AuthExceptionEnum implements AbstractBaseExceptionEnum {
	/**
	 * 来自OAuth AuthException 的错误码 枚举名作为解析
	 */
	SYSTEM_ERROR("40080", "其他错误"),
	INVALID_REQUEST("40001", "无效请求"),
	INVALID_CLIENT("40002", "无效client_id"),
	INVALID_GRANT("40003", "无效授权"),
	INVALID_SCOPE("40004", "无效scope"),
	INVALID_TOKEN("40005", "用户凭据非法"),
	INSUFFICIENT_SCOPE("40010", "授权不足"),
	REDIRECT_URI_MISMATCH("40020", "redirect url不匹配"),
	ACCESS_DENIED("40030", "拒绝访问"),
	METHOD_NOT_ALLOWED("40040", "不支持该方法"),
	SERVER_ERROR("40050", "权限服务错误"),
	UNAUTHORIZED_CLIENT("40060", "未授权客户端"),
	UNAUTHORIZED("40061", "未授权"),
	UNSUPPORTED_RESPONSE_TYPE("40070", "不支持的响应类型"),
	UNSUPPORTED_GRANT_TYPE("40071", "不支持的授权类型"),
	/**
	 * 自定义部分
	 */
	EXAMPLE("-1", "-"),
	;


	AuthExceptionEnum(String code, String message, String devMessage) {
		this.code = code;
		this.message = message;
		this.devMessage = devMessage;
	}

	AuthExceptionEnum(String code, String message) {
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
