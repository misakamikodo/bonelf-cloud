package com.bonelf.auth.core.exception;

import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 异常处理(示例)
 * @author bonelf
 * @date 2021/5/31 14:47
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
		httpServletResponse.setContentType("application/json;charset=UTF-8");
		httpServletResponse.setStatus(403);
		String error;
		if (e instanceof BadCredentialsException ||
				e instanceof UsernameNotFoundException) {
			error = "账户名或者密码输入错误!";
		} else if (e instanceof LockedException) {
			error = "账户被锁定，请联系管理员!";
		} else if (e instanceof CredentialsExpiredException) {
			error = "密码过期，请联系管理员!";
		} else if (e instanceof AccountExpiredException) {
			error = "账户过期，请联系管理员!";
		} else if (e instanceof DisabledException) {
			error = "账户被禁用，请联系管理员!";
		} else {
			error = "登录失败!";
		}
		httpServletResponse.getWriter().write("{\"message\":\"" + error + "\"}");
	}
}
