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
@RequestMapping("/test/authorize")
public class AuthorizeTestController {

	@PreAuthorize("hasRole('test:admin')")
	@RequestMapping("/admin")
	public Result<String> role(){
		return Result.ok("ok");
	}

	@PreAuthorize("hasRole('test:role')")
	@RequestMapping("/role")
	public Result<String> admin(){
		return Result.ok("ok");
	}

	@PreAuthorize("hasRole('test:role') or hasRole('test:admin')")
	@RequestMapping("/adminOrRole")
	public Result<String> adminOrRole(){
		return Result.ok("ok");
	}
}
