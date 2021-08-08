/*
 * Copyright (c) 2020. Bonelf.
 */

package com.bonelf.user.feign.domain.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserResponse {
	private Long userId;

	/**
	 * 手机
	 */
	private String phone;

	/**
	 * 手机
	 */
	private String username;
	/**
	 * 邮箱
	 */
	private String mail;

	/**
	 * unionId
	 */
	private String unionId;

	/**
	 * openId
	 */
	private String openId;

	/**
	 * 密码 暂且取openId加密值做密码
	 */
	private String password;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 头像链接
	 */
	private String avatar;

	/**
	 * 性别 0 未设置 1男 2女
	 */
	private Byte gender;

	/**
	 * 国家
	 */
	private String country;

	/**
	 * 省份
	 */
	private String province;

	/**
	 * 城市
	 */
	private String city;

	/**
	 * 语言
	 */
	private String language;

	/**
	 * 上次登录时间
	 */
	private LocalDateTime lastLoginTime;
	/**
	 * status
	 */
	private Integer status;

	/**
	 * 语言
	 */
	private String verifyCode;

	/**
	 * 角色
	 */
	private Set<RoleResponse> roles;
}
