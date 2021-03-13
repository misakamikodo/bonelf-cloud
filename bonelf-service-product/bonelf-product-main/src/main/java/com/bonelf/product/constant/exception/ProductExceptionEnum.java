package com.bonelf.product.constant.exception;

import com.bonelf.frame.core.exception.AbstractBaseExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 商品服务异常信息定义
 * </p>
 * @author bonelf
 * @since 2020/10/12 10:48
 */
@Getter
public enum ProductExceptionEnum implements AbstractBaseExceptionEnum {
	STOCK_ERROR("C0001", "库存不足");

	ProductExceptionEnum(String code, String message, String devMessage) {
		this.code = code;
		this.message = message;
		this.devMessage = devMessage;
	}

	ProductExceptionEnum(String code, String message) {
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
