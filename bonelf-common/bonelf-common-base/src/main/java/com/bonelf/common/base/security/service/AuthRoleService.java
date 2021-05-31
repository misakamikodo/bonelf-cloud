package com.bonelf.common.base.security.service;

import com.bonelf.user.feign.domain.response.Role;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 * 签权服务
 * </p>
 * @author bonelf
 * @since 2020/11/17 15:37
 */
@Service
public interface AuthRoleService {

    Set<Role> queryUserRolesByUserId(Long userId);

}
