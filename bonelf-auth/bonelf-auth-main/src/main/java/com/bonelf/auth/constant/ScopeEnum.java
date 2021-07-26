package com.bonelf.auth.constant;

/**
 * oauth2 scope
 * @author bonelf
 * @date 2021/5/6 9:18
 */
public enum ScopeEnum {
	/**
	 * oauth2 scope
	 */
	app("app"),
	web("web"),
	;

	private String code;

	ScopeEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
