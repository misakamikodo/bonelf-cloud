package com.bonelf.common.base.security.service.impl;

import com.bonelf.common.base.security.service.AuthRoleService;
import com.bonelf.user.feign.UserFeignClient;
import com.bonelf.user.feign.domain.response.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthRoleServiceImpl implements AuthRoleService {

    @Autowired
    private UserFeignClient organizationProvider;

    @Override
    public Set<Role> queryUserRolesByUserId(Long userId) {
        return organizationProvider.queryRolesByUserId(userId).getResult();
    }

}