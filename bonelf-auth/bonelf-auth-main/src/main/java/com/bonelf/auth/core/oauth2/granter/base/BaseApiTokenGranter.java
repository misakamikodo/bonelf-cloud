/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.auth.core.oauth2.granter.base;

import com.bonelf.auth.core.oauth2.granter.mobile.MobileAuthenticationToken;
import com.bonelf.auth.core.oauth2.service.MobileUserDetailsService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author joe_chen
 * 短信验证码登录与用户名密码登录相似,密码为动态
 * 故继承ResourceOwnerPasswordTokenGranter
 *
 * POST：http://XXX:8001/bonelf/oauth/token
 * 以下参数对应数据库 和 GRANT_TYPE
 * grant_type:mobile
 * 以下参数对应数据库
 * scope:read
 * client_id:test_client
 * client_secret:test_secret
 * 以下参数对应用户
 * username:13758233000
 * password:123456
 * 验证{@link MobileUserDetailsService}
 */
public abstract class BaseApiTokenGranter extends ResourceOwnerPasswordTokenGranter {

	private AuthenticationManager authenticationManager;

	public BaseApiTokenGranter(AuthenticationManager authenticationManager,
							   AuthorizationServerTokenServices tokenServices,
							   ClientDetailsService clientDetailsService,
							   OAuth2RequestFactory requestFactory,
							   String grantType) {
		super(authenticationManager, tokenServices, clientDetailsService, requestFactory, grantType);
		this.authenticationManager = authenticationManager;
	}

	protected abstract String getUsernameParam(Map<String, String> parameters);

	protected abstract String getPasswordParam(Map<String, String> parameters);

	protected void afterGet(Map<String, String> parameters) {

	}

	protected void handleBadCredentialsException(String username, String password, BadCredentialsException exp) {
		// If the username/password are wrong the spec says we should send 400/invalid grant, you can override and register hear
		throw new InvalidGrantException(exp.getMessage());
	}

	@Override
	protected final OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
		Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
		// 手机号还可以是邮箱等
		String username = getUsernameParam(parameters);
		// 验证码
		String password = getPasswordParam(parameters);
		afterGet(parameters);

		Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);
		MobileAuthenticationToken mobileAuthenticationToken = new MobileAuthenticationToken(userAuth);
		((AbstractAuthenticationToken)userAuth).setDetails(parameters);
		try {
			userAuth = this.authenticationManager.authenticate(mobileAuthenticationToken);
		} catch (AccountStatusException ase) {
			// covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
			throw new InvalidGrantException(ase.getMessage());
		} catch (BadCredentialsException e) {
			handleBadCredentialsException(username, password, e);
		}
		if (userAuth == null || !userAuth.isAuthenticated()) {
			throw new InvalidGrantException("Could not authenticate user: " + username);
		}

		OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
		return new OAuth2Authentication(storedOAuth2Request, mobileAuthenticationToken);
	}
}
