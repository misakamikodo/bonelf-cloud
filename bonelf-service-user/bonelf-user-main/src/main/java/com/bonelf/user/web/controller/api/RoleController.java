package com.bonelf.user.web.controller.api;

import com.bonelf.frame.core.domain.Result;
import com.bonelf.user.feign.domain.response.RoleResponse;
import com.bonelf.user.web.service.RoleService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * <p>
 * yon接口
 * </p>
 * @author bonelf
 * @since 2020/10/30 9:29
 */
@RestController
@RequestMapping("/role")
@Slf4j
@Api(tags = "角色接口")
public class RoleController {
	@Autowired
	private RoleService roleService;

	@GetMapping(value = "/v1")
	public Result<Set<RoleResponse>> getUser(@RequestParam Long userId) {
		return Result.ok(roleService.getRoleByUserId(userId));
	}
}
