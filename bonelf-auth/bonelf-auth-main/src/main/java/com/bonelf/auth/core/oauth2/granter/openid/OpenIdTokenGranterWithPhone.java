/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.auth.core.oauth2.granter.openid;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.bonelf.auth.constant.GrantTypeEnum;
import com.bonelf.auth.core.oauth2.granter.base.BaseApiAuthenticationToken;
import com.bonelf.auth.core.oauth2.granter.base.BaseApiTokenGranter;
import com.bonelf.auth.core.oauth2.granter.domain.AuthUser;
import com.bonelf.auth.domain.User;
import com.bonelf.auth.service.UserService;
import com.bonelf.frame.core.exception.BonelfException;
import com.bonelf.frame.core.exception.enums.CommonBizExceptionEnum;
import com.bonelf.user.feign.constant.UniqueIdType;
import com.bonelf.user.feign.domain.request.RegisterUserRequest;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

/**
 * TODO ThreadLocal
 * @author bonelf
 * 短信验证码登录与用户名密码登录相似,密码为动态
 * 故继承ResourceOwnerPasswordTokenGranter
 */
@Slf4j
public class OpenIdTokenGranterWithPhone extends BaseApiTokenGranter {
	private final WxMaService wxMaService;
	private final UserService userService;
	protected static final String GRANT_TYPE = GrantTypeEnum.openid.getCode();
	// private String phone = null;
	private WxMaUserInfo wxMaUserInfo = null;

	public OpenIdTokenGranterWithPhone(AuthenticationManager authenticationManager,
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
		// 获取微信用户session
		WxMaJscode2SessionResult session;
		try {
			session = wxMaService.getUserService().getSessionInfo(code);
		} catch (WxErrorException e) {
			log.error("session获取失败", e);
			throw new BonelfException(CommonBizExceptionEnum.THIRD_FAIL, "session获取失败");
		}
		if (null == session) {
			throw new BonelfException(CommonBizExceptionEnum.THIRD_FAIL, "session获取失败");
		}
		WxMaUserInfo wxUserInfo = wxMaService.getUserService().getUserInfo(session.getSessionKey(), encryptedData, iv);
		if (null == wxUserInfo) {
			throw new BonelfException(CommonBizExceptionEnum.THIRD_FAIL, "无法找到用户信息");
		}
		this.wxMaUserInfo = wxUserInfo;

        WxMaPhoneNumberInfo wxMaPhoneNumberInfo = wxMaService.getUserService().getPhoneNoInfo(session.getSessionKey(),
                encryptedData, iv);
        if (null == wxMaPhoneNumberInfo) {
            throw new BonelfException(CommonBizExceptionEnum.THIRD_FAIL, "无法找到用户信息");
        }
        // 目前该接口针对非个人开发者,且完成了认证的小程序开放(不包合海外主体).,需诨值使用
		AuthUser principal = new AuthUser(
				wxMaPhoneNumberInfo.getPhoneNumber(),
				UniqueIdType.openId,
				wxMaUserInfo.getOpenId());
		principal.setIdType(UniqueIdType.phone);
		return principal;
	}

	@Override
	protected BaseApiAuthenticationToken getToken(Authentication userAuth) {
		return new OpenIdAuthenticationToken(userAuth);
	}

	@Override
	protected void handleUserNameNotFoundException(AuthUser principal, String openId, BonelfException exp) {
		// 用户不存在注册 保证openId和phone一定会同时匹配，否则出现注册openId、phone之一重复（造假数据同时修改phone和openId才能关闭一个微信用户）
		RegisterUserRequest registerUser = RegisterUserRequest.builder()
				.avatar(wxMaUserInfo.getAvatarUrl())
				.city(wxMaUserInfo.getCity())
				.country(wxMaUserInfo.getCountry())
				.gender(Integer.parseInt(wxMaUserInfo.getGender()))
				.language(wxMaUserInfo.getLanguage())
				.phone(principal.getUsername())
				.openId(openId)
				.nickname(wxMaUserInfo.getNickName())
				.province(wxMaUserInfo.getProvince())
				.unionId(wxMaUserInfo.getUnionId())
				.build();
		User userResult = userService.registerByOpenId(registerUser);
	}
}
