/**
 * <p>
 * 签权服务
 * 以下配置需要添加新的
 * {@link com.bonelf.auth.config.WebServerSecurityConfig#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)}
 * {@link com.bonelf.auth.config.AuthorizationServerConfig#tokenGranter(org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer)}
 * 数据库需要添加grant_type
 * </p>
 * @author bonelf
 * @since 2020/11/17 14:19
 */
package com.bonelf.auth.core.oauth2;