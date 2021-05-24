/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.auth.core.oauth2.granter.openid;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.bonelf.auth.constant.GrantTypeEnum;
import com.bonelf.auth.core.oauth2.granter.base.BaseApiTokenGranter;
import com.bonelf.auth.domain.User;
import com.bonelf.auth.service.UserService;
import com.bonelf.frame.cloud.security.constant.UniqueIdType;
import com.bonelf.frame.cloud.security.domain.AuthUser;
import com.bonelf.frame.cloud.security.token.BaseApiAuthenticationToken;
import com.bonelf.frame.core.exception.BonelfException;
import com.bonelf.user.feign.domain.request.RegisterUserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

/**
 * ThreadLocal
 * @author bonelf
 * 短信验证码登录与用户名密码登录相似,密码为动态
 * 故继承ResourceOwnerPasswordTokenGranter
 */
@Slf4j
public class OpenIdTokenGranter extends BaseApiTokenGranter {
	private final WxMaService wxMaService;
	private final UserService userService;
	protected static final String GRANT_TYPE = GrantTypeEnum.openid.getCode();
	public static final String PASSWORD = "pass";
	// private String phone = null;
	private final ThreadLocal<WxMaUserInfo> wxMaUserInfo = new ThreadLocal<>();

	public OpenIdTokenGranter(AuthenticationManager authenticationManager,
							  AuthorizationServerTokenServices tokenServices,
							  ClientDetailsService clientDetailsService,
							  OAuth2RequestFactory requestFactory,
							  WxMaService wxMaService,
							  UserService userService) {
		super(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
		this.wxMaService = wxMaService;
		this.userService = userService;
	}

	@Override
	protected AuthUser getAuthUserFromParam(Map<String, String> parameters) {
		String code = parameters.get("code");
		String encryptedData = parameters.get("encryptedData");
		String iv = parameters.get("iv");
		// // 获取微信用户session
		// WxMaJscode2SessionResult session;
		// try {
		// 	session = wxMaService.getUserService().getSessionInfo(code);
		// } catch (WxErrorException e) {
		// 	log.error("session获取失败", e);
		// 	throw new BonelfException(CommonBizExceptionEnum.THIRD_FAIL, "session获取失败");
		// }
		// if (null == session) {
		// 	throw new BonelfException(CommonBizExceptionEnum.THIRD_FAIL, "session获取失败");
		// }
		// WxMaUserInfo wxUserInfo = wxMaService.getUserService().getUserInfo(session.getSessionKey(), encryptedData, iv);
		// if (null == wxUserInfo) {
		// 	throw new BonelfException(CommonBizExceptionEnum.THIRD_FAIL, "无法找到用户信息");
		// }
		// this.wxMaUserInfo.set(wxUserInfo);;
		// return wxUserInfo.getOpenId();
		AuthUser principal = new AuthUser(
				// wxUserInfo.getOpenId(),
				"oSxri4irwmzqj4VnxfwOtiMjZxaw",
				UniqueIdType.openId,
				PASSWORD);
		// principal.setUsername(wxUserInfo.getOpenId());
		principal.setIdType(UniqueIdType.openId);
		return principal;
	}

	@Override
	protected BaseApiAuthenticationToken getToken(Authentication userAuth) {
		OpenIdAuthenticationToken token = new OpenIdAuthenticationToken(userAuth);
		return token;
	}

	@Override
	protected void handleUserNameNotFoundException(AuthUser principal,
												   String psw,
												   BonelfException exp) {
		RegisterUserRequest.RegisterUserRequestBuilder build = RegisterUserRequest.builder();
		WxMaUserInfo wmui = wxMaUserInfo.get();
		if (wmui != null) {
			build.avatar(wmui.getAvatarUrl())
					.city(wmui.getCity())
					.country(wmui.getCountry())
					.gender(Integer.parseInt(wmui.getGender()))
					.language(wmui.getLanguage())
					.nickname(wmui.getNickName())
					.province(wmui.getProvince())
					.unionId(wmui.getUnionId());
		}
		RegisterUserRequest registerUser = build
				.openId(principal.getUsername())
				.build();
		User userResult = userService.registerByOpenId(registerUser);
	}

	@Override
	protected void authExpFinalyDo() {
		wxMaUserInfo.remove();
	}
}
