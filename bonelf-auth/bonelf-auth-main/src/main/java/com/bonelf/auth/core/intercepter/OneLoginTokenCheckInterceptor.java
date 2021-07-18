package com.bonelf.auth.core.intercepter;

import com.bonelf.auth.constant.CacheConstant;
import com.bonelf.frame.core.constant.AuthConstant;
import com.bonelf.frame.core.exception.BonelfException;
import com.bonelf.frame.core.exception.enums.AuthExceptionEnum;
import com.bonelf.frame.web.security.domain.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 限制只有一个客户端允许登录
 * 若其他设备登录，此客户端token失效
 * {@link org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter decode 参数获取}
 * @author ccy
 * @date 2021/5/20 17:51
 * @see com.bonelf.auth.core.aop.AuthTokenAspect
 */
@Slf4j
public class OneLoginTokenCheckInterceptor implements HandlerInterceptor {
	private RedisTemplate<Object, Object> redisTemplate;

	public OneLoginTokenCheckInterceptor(RedisTemplate<Object, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader(AuthConstant.HEADER);
		if (token != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			// log.info("authentication = " + JSON.toJSONString(authentication));
			// 结果1
			AuthUser principal = (AuthUser)authentication.getPrincipal();
			// 结果2
			// log.info("username = " + JSON.toJSONString(authentication.getPrincipal()));
			// String principal = (String)authentication.getPrincipal();
			if (authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
				OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)authentication.getDetails();
				// log.info("token = " + details.getTokenValue());
				// String redisToken = (String)redisTemplate.opsForValue().get(CacheConstant.TOKEN + principal.getUsername());
				String redisToken = (String)redisTemplate.opsForValue()
						.get(CacheConstant.TOKEN + principal.getUserId());
				if (!Objects.equals(redisToken, details.getTokenValue())) {
					throw new BonelfException(AuthExceptionEnum.LOGIN_REPLACE);
				}
			}
		}
		return true;
	}
}
