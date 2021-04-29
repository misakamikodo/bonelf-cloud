package com.bonelf.auth.web.domain.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * oauth2获取token传参
 * @author ccy
 * @date 2021/4/29 16:45
 */
@Data
public class VerifyCodeLoginDTO {
	private String username;
	@JsonProperty("verify_code")
	private String verifyCode;
}
