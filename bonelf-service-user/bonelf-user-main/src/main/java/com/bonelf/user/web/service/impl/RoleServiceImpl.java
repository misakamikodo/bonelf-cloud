package com.bonelf.user.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.bonelf.user.feign.domain.response.RoleResponse;
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
	public Set<RoleResponse> getRoleByUserId(Long userId) {
		RoleResponse exampleRoleResponse = new RoleResponse();
		exampleRoleResponse.setCode("test:authority");
		exampleRoleResponse.setName("testAuthority");
		exampleRoleResponse.setDescription("this is an example authority");
		RoleResponse exampleRoleResponse2 = new RoleResponse();
		exampleRoleResponse2.setCode("ROLE_role");
		exampleRoleResponse2.setName("testRole");
		exampleRoleResponse2.setDescription("this is an example role");
		return CollectionUtil.newHashSet(exampleRoleResponse, exampleRoleResponse2);
	}
}
