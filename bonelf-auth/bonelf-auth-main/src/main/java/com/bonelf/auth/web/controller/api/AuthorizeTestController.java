package com.bonelf.auth.web.controller.api;

import com.bonelf.frame.core.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * auth权限测试
 * @author ccy
 * @date 2021/5/29 15:07
 */
@Slf4j
@RestController
@RequestMapping("/authorize")
public class AuthorizeTestController {

	@PreAuthorize("hasPermission('/hello', 'read')")
	@RequestMapping("/permission")
	public Result<String> hasPermission(){
		return Result.ok("ok");
	}

	@PreAuthorize("hasAuthority('test:admin')")
	@RequestMapping("/admin")
	public Result<String> role(){
		return Result.ok("ok");
	}

	@PreAuthorize("hasAuthority('test:authority')")
	@RequestMapping("/role")
	public Result<String> admin(){
		return Result.ok("ok");
	}

	@PreAuthorize("hasAuthority('test:authority') or hasAuthority('test:admin')")
	@RequestMapping("/adminOrRole")
	public Result<String> adminOrRole(){
		return Result.ok("ok");
	}

	/**
	 * hasRole自动的将前面加上了ROLE_这个字符串，导致做权限判断的时候需要加上ROLE_
	 * @return
	 */
	@PreAuthorize("hasRole('role')")
	@RequestMapping("/hasRole")
	public Result<String> hasRole(){
		return Result.ok("ok");
	}
}
