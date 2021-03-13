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
import com.bonelf.auth.core.oauth2.granter.base.BaseApiTokenGranter;
import com.bonelf.auth.domain.User;
import com.bonelf.auth.service.UserService;
import com.bonelf.frame.core.exception.BonelfException;
import com.bonelf.frame.core.exception.enums.CommonBizExceptionEnum;
import com.bonelf.user.feign.domain.request.RegisterUserRequest;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

/**
 * @author joe_chen
 * 短信验证码登录与用户名密码登录相似,密码为动态
 * 故继承ResourceOwnerPasswordTokenGranter
 */
@Slf4j
public class OpenIdTokenGranter extends BaseApiTokenGranter {
	private final WxMaService wxMaService;
	private final UserService userService;
	protected static final String GRANT_TYPE = "openid";
	private String phone = null;
	private WxMaUserInfo wxMaUserInfo = null;

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
	protected String getUsernameParam(Map<String, String> parameters) {
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
		this.phone = wxMaPhoneNumberInfo.getPhoneNumber();
		return phone;
	}

	@Override
	protected String getPasswordParam(Map<String, String> parameters) {
		return wxMaUserInfo.getOpenId();
	}

	@Override
	protected void handleBadCredentialsException(String phone, String openId, BadCredentialsException exp) {
		// 用户不存在注册 保证openId和phone一定会同时匹配，否则出现注册openId、phone之一重复（造假数据同时修改phone和openId才能关闭一个微信用户）
		RegisterUserRequest registerUser = RegisterUserRequest.builder()
				.avatar(wxMaUserInfo.getAvatarUrl())
				.city(wxMaUserInfo.getCity())
				.country(wxMaUserInfo.getCountry())
				.gender(Integer.parseInt(wxMaUserInfo.getGender()))
				.language(wxMaUserInfo.getLanguage())
				.phone(phone)
				.openId(openId)
				.nickname(wxMaUserInfo.getNickName())
				.province(wxMaUserInfo.getProvince())
				.unionId(wxMaUserInfo.getUnionId())
				.build();
		User userResult = userService.registerByOpenId(registerUser);
	}
}
