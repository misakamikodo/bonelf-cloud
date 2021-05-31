package com.bonelf.auth.config.permission;

import com.bonelf.frame.web.security.domain.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * 权限
 * {@link org.springframework.security.access.prepost.PreAuthorize 实现注解}
 * @author ccy
 * @date 2021/5/31 14:54
 */
@Slf4j
@Configuration
public class CustomPermissionEvaluator implements PermissionEvaluator {
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		boolean accessable = false;
		if (authentication.getPrincipal() instanceof AuthUser) {
			AuthUser user = (AuthUser)authentication.getPrincipal();
			log.info("验证 permission: " + permission);
			return true;
		}
		return accessable;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		return false;
	}
}
