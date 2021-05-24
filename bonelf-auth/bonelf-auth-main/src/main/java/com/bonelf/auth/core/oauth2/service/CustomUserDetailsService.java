/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.auth.core.oauth2.service;

import com.bonelf.auth.domain.User;
import com.bonelf.auth.service.RoleService;
import com.bonelf.auth.service.UserService;
import com.bonelf.frame.cloud.security.constant.UniqueIdType;
import com.bonelf.frame.cloud.security.domain.AuthUser;
import com.bonelf.user.feign.domain.response.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 账号密码登录校验
 * </p>
 * @author bonelf
 * @since 2020/11/19 13:10
 */
@Slf4j
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	protected UserService userService;
	@Autowired
	protected RoleService roleService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("980826"));
	}

	/**
	 * 调用/auth/token 调用这个方法校验
	 * @param uniqueId account、phone、mail
	 * @return
	 * @see org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder 密码加密
	 */
	@Override
	public UserDetails loadUserByUsername(String uniqueId) {
		User user = userService.getByUniqueId(uniqueId, new UniqueIdType[]{UniqueIdType.username,
				UniqueIdType.phone, UniqueIdType.mail});
		// 2020/11/19 错误和NPE处理
		log.info("load user by username :{}", user.toString());
		return new AuthUser(
				user.getUserId(),
				UniqueIdType.username,
				user.getUsername(),
				passwordEncoder.encode(user.getPassword()),
				user.getEnabled(),
				user.getAccountNonExpired(),
				user.getCredentialsNonExpired(),
				user.getAccountNonLocked(),
				this.obtainGrantedAuthorities(user));
	}

	/**
	 * 获得登录者所有角色的权限集合.
	 * @param user
	 * @return
	 */
	protected Set<GrantedAuthority> obtainGrantedAuthorities(User user) {
		Set<Role> roles = roleService.queryUserRolesByUserId(user.getUserId());
		log.info("=====获取到的用户信息====\nuser:{},roles:{}", user.getUsername(), roles);
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toSet());
	}
}
