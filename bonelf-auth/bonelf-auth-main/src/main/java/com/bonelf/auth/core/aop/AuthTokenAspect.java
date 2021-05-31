/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.auth.core.aop;

import com.bonelf.auth.constant.CacheConstant;
import com.bonelf.frame.core.domain.Result;
import com.bonelf.frame.core.exception.enums.CommonBizExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * Oauth2.0返回结果封装
 * </p>
 * @see com.bonelf.auth.core.intercepter.OneLoginTokenCheckInterceptor
 * @author bonelf
 * @since 2020/11/19 14:02
 */
@Slf4j
@Component
@Aspect
public class AuthTokenAspect {
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	/**
	 * 定义切点Pointcut
 	 */
	@Pointcut("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
	public void restAop() {
	}

	//@Pointcut("execution(* org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator.translate(..))")
	//public void unauthorized() {
	//}

	@Around("restAop()")
	public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
		// 放行
		Result<OAuth2AccessToken> response;
		Object proceed = pjp.proceed();
		if (proceed != null) {
			ResponseEntity<OAuth2AccessToken> responseEntity = (ResponseEntity<OAuth2AccessToken>)proceed;
			OAuth2AccessToken body = responseEntity.getBody();
			if (responseEntity.getStatusCode().is2xxSuccessful()) {
				// 存储token 做单客户端登录用,如果不需要请删除
				if (body != null) {
					String userId = String.valueOf(body.getAdditionalInformation().get("user_id"));
					log.debug("==即将把下面信息放入redis,用于单客户端登录==\nuserId = {}\ntoken = {}\nexpiresIn = {}",
							userId, body.getValue(), body.getExpiresIn());
					redisTemplate.opsForValue().set(CacheConstant.TOKEN + userId,
							body.getValue(),
							body.getExpiresIn() + 1,
							TimeUnit.SECONDS);
				}
				response = Result.ok(body);
			} else {
				response = Result.error(CommonBizExceptionEnum.OAUTH_ERROR);
				response.setResult(body);
			}
		} else {
			response = Result.error();
		}
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);
	}
}
