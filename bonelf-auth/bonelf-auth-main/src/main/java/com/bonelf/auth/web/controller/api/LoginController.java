package com.bonelf.auth.web.controller.api;

import com.bonelf.auth.web.domain.dto.OpenIdLoginDTO;
import com.bonelf.auth.web.domain.dto.PswLoginDTO;
import com.bonelf.auth.web.domain.dto.VerifyCodeLoginDTO;
import com.bonelf.frame.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录接口重定向
 * @author ccy
 * @date 2021/4/29 16:37
 */
@Slf4j
@Api(tags = {"登录接口"})
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

	@ApiOperation(value = "验证码登录")
	@PostMapping("/byVerifyCode")
	public String byVerifyCode(@Validated VerifyCodeLoginDTO dto, HttpServletRequest request) {
		return "redirect:/auth/oauth/token";
	}

	@ApiOperation(value = "微信登录")
	@PostMapping("/byOpenId")
	public String byOpenId(@Validated OpenIdLoginDTO dto) {
		return "ok";
	}

	@ApiOperation(value = "密码登录")
	@PostMapping("/byPsw")
	public String byPsw(PswLoginDTO dto) {
		return "ok";
	}

}
