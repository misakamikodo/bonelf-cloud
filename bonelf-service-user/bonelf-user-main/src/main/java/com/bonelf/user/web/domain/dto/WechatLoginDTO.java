package com.bonelf.user.web.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("微信登录")
public class WechatLoginDTO {
	/**
	 * code 获取手机号码
	 */
	@ApiModelProperty("code")
	@NotNull(message = "请检查微信登录必要参数是否传参")
	private String code;
	/**
	 * encryptedData 获取手机号码
	 */
	@ApiModelProperty("encryptedData")
	@NotNull(message = "请检查微信登录必要参数是否传参")
	private String encryptedData;
	/**
	 * iv 获取手机号码
	 */
	@ApiModelProperty("iv")
	@NotNull(message = "请检查微信登录必要参数是否传参")
	private String iv;
}
