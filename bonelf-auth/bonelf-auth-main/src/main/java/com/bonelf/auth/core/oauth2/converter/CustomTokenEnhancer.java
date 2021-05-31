package com.bonelf.auth.core.oauth2.converter;

import com.bonelf.auth.core.oauth2.granter.mobile.MobileAuthenticationToken;
import com.bonelf.auth.core.oauth2.granter.openid.OpenIdAuthenticationToken;
import com.bonelf.frame.web.security.domain.AuthUser;
import com.google.common.collect.Maps;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Map;

/**
 * 自定义token携带内容
 * @see com.bonelf.auth.core.aop.AuthTokenAspect 使用了额外的字段
 */
public class CustomTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> additionalInfo = Maps.newHashMap();
		if (authentication.getPrincipal() instanceof AuthUser) {
			AuthUser user = (AuthUser)authentication.getPrincipal();
			additionalInfo.put("user_id", user.getUserId());
		}
		// 自定义token内容
		additionalInfo.put("username", authentication.getName());
		// 验证码登录
		if (authentication.getUserAuthentication() instanceof MobileAuthenticationToken) {
			MobileAuthenticationToken token = (MobileAuthenticationToken)authentication.getUserAuthentication();
			additionalInfo.put("payload", token.getPayload());
		} else if (authentication.getUserAuthentication() instanceof OpenIdAuthenticationToken) {
			OpenIdAuthenticationToken token = (OpenIdAuthenticationToken)authentication.getUserAuthentication();
			additionalInfo.put("payload", token.getPayload());
		}
		((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(additionalInfo);
		return accessToken;
	}
}