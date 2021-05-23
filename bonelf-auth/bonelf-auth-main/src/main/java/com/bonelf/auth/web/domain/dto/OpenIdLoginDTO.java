package com.bonelf.auth.web.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * oauth2获取token传参
 * @author ccy
 * @date 2021/4/29 16:45
 */
@Data
public class OpenIdLoginDTO {
	@NotBlank
	private String code;
	@NotBlank
	private String encryptedData;
	@NotBlank
	private String iv;
}
