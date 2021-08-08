package com.bonelf.common.base.security.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.bonelf.frame.core.auth.domain.Role;
import com.bonelf.frame.core.auth.service.AuthRoleService;
import com.bonelf.user.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Primary
@Service("defaultAuthRoleService")
public class AuthRoleServiceImpl implements AuthRoleService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public Set<Role> queryUserRolesByUserId(Long userId) {
        return userFeignClient.queryRolesByUserId(userId).getResult().stream()
                .map(item-> BeanUtil.copyProperties(item, Role.class)).collect(Collectors.toSet());
    }

}
