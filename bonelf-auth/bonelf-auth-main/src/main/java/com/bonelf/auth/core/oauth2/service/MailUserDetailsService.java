/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.auth.core.oauth2.service;

import cn.hutool.core.collection.CollectionUtil;
import com.bonelf.common.base.security.domain.User;
import com.bonelf.frame.core.constant.UniqueIdType;
import com.bonelf.frame.core.domain.Result;
import com.bonelf.frame.core.exception.BonelfException;
import com.bonelf.frame.web.security.domain.AuthUser;
import com.bonelf.support.feign.SupportFeignClient;
import com.bonelf.support.feign.domain.constant.VerifyCodeTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * 手机验证码登录校验
 * @author bonelf
 */
@Slf4j
@Service("mailUserDetailsService")
public class MailUserDetailsService extends CustomUserDetailsService {
	@Autowired
	protected SupportFeignClient supportFeignClient;
	@Autowired
	protected PasswordEncoder passwordEncoder;

	/**
	 * 调用/auth/token 调用这个方法校验
	 * @param uniqueId mail
	 * @author bonelf
	 * @date 2020-11-19 11:21
	 */
	@Override
	public UserDetails loadUserByUsername(String uniqueId) {
		User user;
		try {
			user = userService.getByUniqueId(uniqueId, UniqueIdType.mail);
		} catch (BonelfException be) {
			if ("404".equals(be.getCode())) {
				throw new UsernameNotFoundException((be.getErrorMessage()), be);
			} else {
				throw be;
			}
		}
		Result<String> codeResult = supportFeignClient.getVerifyMail(user.getMail(), VerifyCodeTypeEnum.LOGIN.getCode());
		if (codeResult.getSuccess()) {
			user.setVerifyCode(codeResult.getResult());
		} else {
			throw BonelfException.builder().code(codeResult.getCode()).msg(codeResult.getMessage()).build();
		}
		log.debug("load user by mail:{}", user.toString());
		// 如果为mail模式，从短信服务中获取验证码（动态密码） 其实可以去了加密
		//String credentials = userClient.getSmsCode(uniqueId, "LOGIN");
		String credentials = passwordEncoder.encode(user.getVerifyCode());
		//String credentials = user.getVerifyCode();
		// String credentials = new BCryptPasswordEncoder().encode(user.getVerifyCode());
		return  new AuthUser(
				user.getUserId(),
				UniqueIdType.mail,
				user.getMail(),
				credentials,
				user.getEnabled(),
				user.getAccountNonExpired(),
				user.getCredentialsNonExpired(),
				user.getAccountNonLocked(),
				CollectionUtil.isEmpty(user.getRoles()) ?
						this.obtainGrantedAuthorities(user) :
						user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toSet()));
	}
}
