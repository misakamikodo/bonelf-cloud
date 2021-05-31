/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.auth.core.oauth2.granter.base;

import com.bonelf.auth.core.oauth2.service.MobileUserDetailsService;
import com.bonelf.frame.core.exception.BonelfException;
import com.bonelf.frame.web.security.BaseApiAuthenticationToken;
import com.bonelf.frame.web.security.domain.AuthUser;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 短信验证码登录与用户名密码登录相似,密码为动态
 * 故继承ResourceOwnerPasswordTokenGranter
 * <p>
 * POST：http://XXX:8001/bonelf/oauth/token
 * 以下参数对应数据库 和 GRANT_TYPE
 * grant_type:mobile
 * 以下参数对应数据库
 * scope:read
 * client_id:test_client
 * client_secret:test_secret
 * 以下参数对应用户
 * username:137582330XX
 * password:980826
 * 验证{@link MobileUserDetailsService}
 * @author bonelf
 */
public abstract class BaseApiTokenGranter extends ResourceOwnerPasswordTokenGranter {

	private AuthenticationManager authenticationManager;
	private boolean reAuthIfNotFound = false;

	public boolean isReAuthIfNotFound() {
		return reAuthIfNotFound;
	}

	public void setReAuthIfNotFound(boolean reAuthIfNotFound) {
		this.reAuthIfNotFound = reAuthIfNotFound;
	}

	public BaseApiTokenGranter(AuthenticationManager authenticationManager,
							   AuthorizationServerTokenServices tokenServices,
							   ClientDetailsService clientDetailsService,
							   OAuth2RequestFactory requestFactory,
							   String grantType) {
		super(authenticationManager, tokenServices, clientDetailsService, requestFactory, grantType);
		this.authenticationManager = authenticationManager;
	}

	protected abstract AuthUser getAuthUserFromParam(Map<String, String> parameters);

	protected abstract BaseApiAuthenticationToken getToken(Authentication userAuth);

	protected void afterGet(Map<String, String> parameters) {

	}

	protected void authExpFinalyDo() {

	}

	protected void handleUserNameNotFoundException(AuthUser username, String password, BonelfException exp) {
		// If the username/password are wrong the spec says we should send 400/invalid grant, you can override and register hear
		throw new InvalidGrantException(exp.getMessage());
	}

	@Override
	protected final OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
		Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
		// 手机号还可以是邮箱等 {id:,username:,type:}
		AuthUser principal = getAuthUserFromParam(parameters);
		// 验证码
		String password = principal.getPassword();
		afterGet(parameters);

		// principal可以替换为username字符串 效果一样，本意想在解token获得principal的内容，但是不行
		AbstractAuthenticationToken usernamePasswordToken = new UsernamePasswordAuthenticationToken(
				principal, password
		);
		// 只使用username作为principal setForcePrincipalAsString(true)
		// AbstractAuthenticationToken usernamePasswordToken = new UsernamePasswordAuthenticationToken(principal.getUsername(), password);
		BaseApiAuthenticationToken authenticationToken = getToken(usernamePasswordToken);
		// 新建一个存放附带信息 如果需要这里放map 不然BaseApiAuthenticationProvider处理报错
		// Map<String, String> details = new LinkedHashMap<>();
		// authenticationToken.setDetails(details);
		authenticationToken.setDetails(parameters);

		Authentication userAuth;
		try {
			// 再auth中loadUserByUsername
			userAuth = this.authenticationManager.authenticate(authenticationToken);
		} catch (AccountStatusException ase) {
			// covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
			throw new InvalidGrantException(ase.getMessage());
		} catch (InternalAuthenticationServiceException e) {
			// 处理异常 注意 DaoAuthenticationProvider 对所有异常的捕获
			if (e.getCause() instanceof BonelfException && "404".equals(((BonelfException)e.getCause()).getCode())) {
				// 更推荐使用 UsernameNotFoundException
				handleUserNameNotFoundException(principal, password, (BonelfException)e.getCause());
				if (reAuthIfNotFound) {
					try {
						userAuth = this.authenticationManager.authenticate(authenticationToken);
					} catch (AccountStatusException ase) {
						throw new InvalidGrantException(ase.getMessage());
					}
				} else {
					throw e;
				}
			} else {
				throw e;
			}
		} finally {
			authExpFinalyDo();
		}

		if (userAuth == null || !userAuth.isAuthenticated()) {
			throw new InvalidGrantException("Could not authenticate user: " + principal);
		}

		OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
		return new OAuth2Authentication(storedOAuth2Request, authenticationToken);
	}
}
