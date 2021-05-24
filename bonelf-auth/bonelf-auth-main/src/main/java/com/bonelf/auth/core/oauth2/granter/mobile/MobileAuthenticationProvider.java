/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.auth.core.oauth2.granter.mobile;

import com.bonelf.auth.core.oauth2.granter.base.BaseApiAuthenticationProvider;
import com.bonelf.frame.cloud.security.token.BaseApiAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 手机验证码登录provider
 */
public class MobileAuthenticationProvider extends BaseApiAuthenticationProvider {
    public MobileAuthenticationProvider(UserDetailsService userDetailsService) {
        super(userDetailsService);
    }

    @Override
    protected Class<? extends BaseApiAuthenticationToken> authenticationToken() {
        return MobileAuthenticationToken.class;
    }
}