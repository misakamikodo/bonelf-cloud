package com.bonelf.auth.web.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * oauth2获取token传参
 * @author bonelf
 * @date 2021/4/29 16:45
 */
@Data
public class PswLoginDTO {
	@NotBlank(message = "请输入账号")
	private String username;
	@NotBlank(message = "请输入密码")
	private String password;
}
