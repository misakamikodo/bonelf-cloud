package com.bonelf.auth.web.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * RefreshToken
 * @author bonelf
 * @date 2021/4/29 16:45
 */
@Data
public class RefreshTokenDTO {
	@NotBlank(message = "请输入refreshToken")
	private String refreshToken;
}
