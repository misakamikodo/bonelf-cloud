package com.bonelf.auth.config;

import com.bonelf.auth.core.intercepter.OneLoginTokenCheckInterceptor;
import com.bonelf.frame.web.config.AbstractWebMvcConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * web服务配置
 **/
@Configuration
public class WebMvcConfig extends AbstractWebMvcConfig {
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		// 校验用户token拦截器
		registry.addInterceptor(new OneLoginTokenCheckInterceptor(redisTemplate))
				.excludePathPatterns("/oauth/**", "/login/**")
				.addPathPatterns("/**");
	}
}
