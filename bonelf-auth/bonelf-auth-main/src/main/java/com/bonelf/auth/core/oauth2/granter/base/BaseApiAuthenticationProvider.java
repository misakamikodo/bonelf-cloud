/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.auth.core.oauth2.granter.base;

import com.bonelf.frame.web.security.BaseApiAuthenticationToken;
import com.bonelf.frame.web.security.domain.AuthUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

/**
 * 手机验证码登录provider
 */
public abstract class BaseApiAuthenticationProvider extends DaoAuthenticationProvider {

	public BaseApiAuthenticationProvider(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
		// UserNotFound异常需要显示则设置
		this.setHideUserNotFoundExceptions(false);
		// Principal使用AuthUser对象需要设置
		this.setForcePrincipalAsString(false);
	}

	@Override
	public final boolean supports(Class<?> authentication) {
		return authenticationToken().isAssignableFrom(authentication);
	}

	protected abstract Class<? extends BaseApiAuthenticationToken> authenticationToken();

	// @Override
	// protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
	// 	Authentication result = super.createSuccessAuthentication(principal, authentication, user);
	// 	return result;
	// }

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		super.additionalAuthenticationChecks(userDetails, authentication);
		// 添加ID到authentication中
		if (authentication instanceof BaseApiAuthenticationToken && userDetails instanceof AuthUser) {
			BaseApiAuthenticationToken apiToken = (BaseApiAuthenticationToken)authentication;
			AuthUser authUser = (AuthUser)userDetails;
			apiToken.setUserId(authUser.getUserId());
			if (apiToken.getDetails() instanceof Map) {
			// if (apiToken.getDetails() == null) {
				Map<String, String> details = (Map<String, String>)apiToken.getDetails();
				// Map<String, String> details = new LinkedHashMap<>();
				// 认证后BaseApiAuthenticationToken改为UserPasswordToken保存(降级),通过detail复制过去了,所以这里要添加userId
				details.put("user_id", String.valueOf(authUser.getUserId()));
				apiToken.setDetails(details);
			}
		}
	}
}