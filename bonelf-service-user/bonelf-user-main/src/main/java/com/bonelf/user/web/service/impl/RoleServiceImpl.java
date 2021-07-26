package com.bonelf.user.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.bonelf.user.feign.domain.response.Role;
import com.bonelf.user.web.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 角色
 * @author bonelf
 * @date 2021/5/31 16:36
 */
@Service
public class RoleServiceImpl implements RoleService {
	@Override
	public Set<Role> getRoleByUserId(Long userId) {
		Role exampleRole = new Role();
		exampleRole.setCode("test:authority");
		exampleRole.setName("testAuthority");
		exampleRole.setDescription("this is an example authority");
		Role exampleRole2 = new Role();
		exampleRole2.setCode("ROLE_role");
		exampleRole2.setName("testRole");
		exampleRole2.setDescription("this is an example role");
		return CollectionUtil.newHashSet(exampleRole, exampleRole2);
	}
}
