/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.common.base.security.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.bonelf.frame.base.service.IdUserDetailsService;
import com.bonelf.frame.core.auth.domain.Role;
import com.bonelf.frame.core.auth.domain.User;
import com.bonelf.frame.core.auth.service.AuthRoleService;
import com.bonelf.frame.core.auth.service.AuthUserService;
import com.bonelf.frame.core.constant.UsernameType;
import com.bonelf.frame.web.security.domain.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
@Primary
@Slf4j
@Service("idUserDetailsService")
public class IdUserDetailsServiceImpl implements IdUserDetailsService {
	@Autowired
	protected AuthUserService userService;
	@Autowired
	protected AuthRoleService roleService;

	/**
	 * 调用/auth/token 调用这个方法校验
	 * @param uniqueId account、phone、mail
	 * @return
	 * @see BCryptPasswordEncoder 密码加密
	 */
	@Override
	public UserDetails loadUserByUsername(String uniqueId) {
		User user = userService.getByUniqueId(uniqueId, UsernameType.id);
		// 2020/11/19 错误和NPE处理
		log.info("load user by id :{}", user.toString());
		return new AuthUser(
				user.getUserId(),
				UsernameType.username,
				user.getUsername(),
				// passwordEncoder.encode(user.getPassword()),
				user.getPassword(),
				user.getEnabled(),
				user.getAccountNonExpired(),
				user.getCredentialsNonExpired(),
				user.getAccountNonLocked(),
				CollectionUtil.isEmpty(user.getRoles()) ?
						this.obtainGrantedAuthorities(user) :
						user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toSet()));
	}


	/**
	 * 获得登录者所有角色的权限集合.
	 * @param user
	 * @return
	 */
	protected Set<GrantedAuthority> obtainGrantedAuthorities(User user) {
		Set<Role> roleResponses = roleService.queryUserRolesByUserId(user.getUserId());
		log.info("=====获取到的用户信息====\nuser:{},roles:{}", user.getUsername(), roleResponses);
		return roleResponses.stream().map(role -> new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toSet());
	}
}
