package com.bonelf.user.web.service;

import com.bonelf.user.feign.domain.response.RoleResponse;

import java.util.Set;

/**
 * 角色
 * @author bonelf
 * @date 2021/5/31 16:36
 */
public interface RoleService {
	Set<RoleResponse> getRoleByUserId(Long userId);
}
