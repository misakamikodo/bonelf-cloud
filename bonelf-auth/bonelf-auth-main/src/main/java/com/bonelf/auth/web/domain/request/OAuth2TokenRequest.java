package com.bonelf.auth.web.domain.request;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * oauth2获取token传参
 * @author bonelf
 * @date 2021/4/29 16:45
 */
@Data
public class OAuth2TokenRequest {
	@JsonProperty("grant_type")
	private String grantType;
	private String scope;
	@JsonProperty("client_id")
	private String clientId;
	@JsonProperty("client_secret")
	private String clientSecret;

	/*========================== 自定义额外参数 ==============================*/

	private String username;
	@JsonProperty("verify_code")
	private String verifyCode;

	private String code;
	private String encryptedData;
	private String iv;

	private String password;
}
