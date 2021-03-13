/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.auth.core.oauth2.service;

import com.bonelf.auth.domain.User;
import com.bonelf.frame.core.domain.Result;
import com.bonelf.support.feign.SupportFeignClient;
import com.bonelf.support.feign.domain.constant.VerifyCodeTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 手机验证码登录校验
 * 配置注入 {@link com.bonelf.auth.config.WebServerSecurityConfig}
 * @author bonelf
 */
@Slf4j
@Service("mobileUserDetailsService")
public class MobileUserDetailsService extends CustomUserDetailsService {
	@Autowired
	protected SupportFeignClient supportFeignClient;

	/**
	 * 调用/auth/token 调用这个方法校验
	 * @param uniqueId phone
	 * @author bonelf
	 * @date 2020-11-19 11:21
	 */
	@Override
	public UserDetails loadUserByUsername(String uniqueId) {
		User user = userService.getByUniqueId(uniqueId);
		if (user == null) {
			// 验证码正确 但是用户不存在注册
			user = userService.registerByPhone(uniqueId);
		}
		Result<String> codeResult = supportFeignClient.getVerify(user.getPhone(), VerifyCodeTypeEnum.LOGIN.getCode());
		if (codeResult.getSuccess()) {
			user.setVerifyCode(codeResult.getResult());
		}
		log.debug("load user by mobile:{}", user.toString());
		// 如果为mobile模式，从短信服务中获取验证码（动态密码） 其实可以去了加密
		//String credentials = userClient.getSmsCode(uniqueId, "LOGIN");
		//String credentials = passwordEncoder.encode(user.getVerifyCode());
		//String credentials = user.getVerifyCode();
		String credentials = new BCryptPasswordEncoder().encode(user.getVerifyCode());
		return new org.springframework.security.core.userdetails.User(
				user.getPhone(),
				credentials,
				user.getEnabled(),
				user.getAccountNonExpired(),
				user.getCredentialsNonExpired(),
				user.getAccountNonLocked(),
				super.obtainGrantedAuthorities(user));
	}
}
