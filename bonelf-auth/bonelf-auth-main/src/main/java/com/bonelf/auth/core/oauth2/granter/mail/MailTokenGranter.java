/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.auth.core.oauth2.granter.mail;

import com.bonelf.auth.constant.GrantTypeEnum;
import com.bonelf.auth.core.oauth2.granter.base.BaseApiTokenGranter;
import com.bonelf.auth.domain.User;
import com.bonelf.auth.service.UserService;
import com.bonelf.frame.cloud.security.constant.UniqueIdType;
import com.bonelf.frame.cloud.security.domain.AuthUser;
import com.bonelf.frame.cloud.security.token.BaseApiAuthenticationToken;
import com.bonelf.frame.core.exception.BonelfException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

/**
 * @author joe_chen
 * 短信验证码登录与用户名密码登录相似,密码为动态
 * 故继承ResourceOwnerPasswordTokenGranter
 *
 * POST：http://XXX:8001/bonelf/oauth/token
 * 以下参数对应数据库 和 GRANT_TYPE
 * grant_type:mail
 * 以下参数对应数据库
 * scope:read
 * client_id:test_client
 * client_secret:test_secret
 * 以下参数对应用户
 * username:13758233000
 * password:123456
 * 验证{@link com.bonelf.auth.core.oauth2.service.MailUserDetailsService}
 *
 * 注册见{@link com.bonelf.auth.core.oauth2.service.MailUserDetailsService}
 */
public class MailTokenGranter extends BaseApiTokenGranter {
	private final UserService userService;

	protected static final String GRANT_TYPE = GrantTypeEnum.mail.getCode();


	public MailTokenGranter(AuthenticationManager authenticationManager,
							  AuthorizationServerTokenServices tokenServices,
							  ClientDetailsService clientDetailsService,
							  OAuth2RequestFactory requestFactory,
							UserService userService) {
		super(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
		this.userService = userService;
	}

	@Override
	protected AuthUser getAuthUserFromParam(Map<String, String> parameters) {
		AuthUser principal = new AuthUser(parameters.get("username"), UniqueIdType.mail, parameters.get("verify_code"));
		return principal;
	}

	@Override
	protected BaseApiAuthenticationToken getToken(Authentication userAuth) {
		return new MailAuthenticationToken(userAuth);
	}

	@Override
	protected void handleUserNameNotFoundException(AuthUser account,
												   String psw,
												   BonelfException exp) {
		User userResult = userService.registerByMail(account.getUsername());
	}

	@Override
	protected void afterGet(Map<String, String> parameters) {
	}
}
