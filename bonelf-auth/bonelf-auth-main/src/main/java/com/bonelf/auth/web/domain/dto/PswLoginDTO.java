package com.bonelf.auth.web.domain.dto;

import lombok.Data;

/**
 * oauth2获取token传参
 * @author ccy
 * @date 2021/4/29 16:45
 */
@Data
public class PswLoginDTO {
	private String username;
	private String password;
}
