package com.bonelf.auth.config;

import com.bonelf.auth.core.oauth2.granter.mail.MailAuthenticationProvider;
import com.bonelf.auth.core.oauth2.granter.mobile.MobileAuthenticationProvider;
import com.bonelf.auth.core.oauth2.granter.openid.OpenIdAuthenticationProvider;
import com.bonelf.auth.core.pswencoder.Md5PasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * <p>
 * 签权服务
 * </p>
 * @author bonelf
 * @since 2020/11/17 15:37
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class WebServerSecurityConfig extends WebSecurityConfigurerAdapter {
	/**
	 * @see com.bonelf.auth.core.oauth2.service.CustomUserDetailsService 账密登录
	 */
	@Autowired(required = false)
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;
	@Autowired(required = false)
	@Qualifier("mobileUserDetailsService")
	private UserDetailsService mobileUserDetailsService;
	@Autowired(required = false)
	@Qualifier("openidUserDetailsService")
	private UserDetailsService openidUserDetailsService;
	@Autowired(required = false)
	@Qualifier("mailUserDetailsService")
	private UserDetailsService mailUserDetailsService;

	/**
	 * @description 其他请在网关bonelf.anno-url过滤
	 * @author bonelf
	 * @date 2020-11-18 16:20
	 */
	@Override
	public void configure(WebSecurity web) {
		web.ignoring()
				.antMatchers(HttpMethod.OPTIONS, "/**")
				.antMatchers("/swagger-ui/index.html")
				.antMatchers("/oauth/token_key")
				.antMatchers("/login/**")
				.antMatchers("/v2/api-docs");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.debug("HttpSecurity configure method");
		http.csrf().disable();
		// 这个可以继承重写 从而自定义返回 还有错误处理类.failureHandler(AuthenticationFailureHandler );
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setTargetUrlParameter("redirectTo");
		http.authorizeRequests()
				.antMatchers("/assets/**").permitAll()
				.antMatchers("/actuator/**").permitAll()
				.antMatchers("/login").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin().loginPage("/login")
				.successHandler(successHandler).and()
				.logout().logoutUrl("/logout")
				.and()
				.httpBasic().and()
				.csrf().disable();
	}

	/**
	 * 注入自定义的userDetailsService实现，获取用户信息，设置密码加密方式
	 * 多种验证方式
	 * @param authenticationManagerBuilder
	 * @throws Exception
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		if (userDetailsService != null) {
			// 设置密码登录的AuthenticationProvider
			authenticationManagerBuilder
					.userDetailsService(userDetailsService)
					.passwordEncoder(passwordEncoder());
		}
		if (mobileUserDetailsService != null) {
			// 设置手机验证码登录的AuthenticationProvider
			authenticationManagerBuilder.authenticationProvider(mobileAuthenticationProvider());
		}
		if (mailUserDetailsService != null) {
			// 设置邮箱验证码登录的AuthenticationProvider
			authenticationManagerBuilder.authenticationProvider(mailAuthenticationProvider());
		}
		if (openidUserDetailsService != null) {
			// 设置微信登录的AuthenticationProvider
			authenticationManagerBuilder.authenticationProvider(openIdAuthenticationProvider());
		}
	}

	/**
	 * 将 AuthenticationManager 注册为 bean , 方便配置 oauth server 的时候使用
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// return new BCryptPasswordEncoder();
		return new Md5PasswordEncoder();
	}


	/**
	 * 对原始字符不进行加密
	 */
	// @Bean
	// public PasswordEncoder passwordEncoder() {
	// 	return new PasswordEncoder() {
	// 		@Override
	// 		public String encode(CharSequence rawPassword) {
	// 			return rawPassword.toString();
	// 		}
	//
	// 		@Override
	// 		public boolean matches(CharSequence rawPassword, String encodedPassword) {
	// 			return Objects.equals(rawPassword.toString(), encodedPassword);
	// 		}
	// 	};
	// }

	/**
	 * 创建手机验证码登录的AuthenticationProvider
	 * @return mobileAuthenticationProvider
	 */
	@Bean
	public MobileAuthenticationProvider mobileAuthenticationProvider() {
		MobileAuthenticationProvider mobileAuthenticationProvider = new MobileAuthenticationProvider(this.mobileUserDetailsService);
		mobileAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return mobileAuthenticationProvider;
	}

	@Bean
	public MailAuthenticationProvider mailAuthenticationProvider() {
		MailAuthenticationProvider mailAuthenticationProvider = new MailAuthenticationProvider(this.mailUserDetailsService);
		mailAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return mailAuthenticationProvider;
	}

	@Bean
	public OpenIdAuthenticationProvider openIdAuthenticationProvider() {
		OpenIdAuthenticationProvider openIdAuthenticationProvider = new OpenIdAuthenticationProvider(this.openidUserDetailsService);
		openIdAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return openIdAuthenticationProvider;
	}
}