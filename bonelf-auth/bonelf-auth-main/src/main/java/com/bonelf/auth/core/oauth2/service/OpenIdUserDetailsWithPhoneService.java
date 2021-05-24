/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.auth.core.oauth2.service;

import com.bonelf.auth.domain.User;
import com.bonelf.frame.cloud.security.constant.UniqueIdType;
import com.bonelf.frame.cloud.security.domain.AuthUser;
import com.bonelf.frame.core.exception.BonelfException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 微信登录校验
 * @author bonelf
 */
@Slf4j
// @Service("openidUserDetailsWithPhoneService")
public class OpenIdUserDetailsWithPhoneService extends CustomUserDetailsService {

	/**
	 * 调用/auth/token 调用这个方法校验
	 * @param uniqueId openId
	 * @author bonelf
	 * @date 2020-11-19 11:21
	 */
	@Override
	public UserDetails loadUserByUsername(String uniqueId) {
		// 这里还是phone openId作为密码
		User user;
		try {
			user = userService.getByUniqueId(uniqueId, UniqueIdType.phone);
		} catch (BonelfException be) {
			if ("404".equals(be.getCode())) {
				throw new UsernameNotFoundException(be.getErrorMessage());
			} else {
				throw be;
			}
		}
		// 2020/11/19 错误和NPE处理
		log.info("load user by phone(vx):{}", user.toString());
		// 如果为openId模式，从短信服务中获取验证码（动态密码）
		return new AuthUser(
				user.getUserId(),
				UniqueIdType.phone,
				user.getPhone(),
				user.getOpenId(),
				user.getEnabled(),
				user.getAccountNonExpired(),
				user.getCredentialsNonExpired(),
				user.getAccountNonLocked(),
				super.obtainGrantedAuthorities(user));
	}
}