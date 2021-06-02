package com.bonelf.auth.constant;

/**
 * oauth2 grantType
 * @author ccy
 * @date 2021/5/6 9:18
 */
public enum GrantTypeEnum {
	/**
	 * oauth2 grantType
	 */
	mobile("mobile"),
	mail("mail"),
	openid("openid"),
	password("password"),
	refreshToken("refresh_token"),
	;

	private String code;

	GrantTypeEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
