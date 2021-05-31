package com.bonelf.user.web.service;

import com.bonelf.user.feign.domain.response.Role;

import java.util.Set;

/**
 * 角色
 * @author ccy
 * @date 2021/5/31 16:36
 */
public interface RoleService {
	Set<Role> getRoleByUserId(Long userId);
}
