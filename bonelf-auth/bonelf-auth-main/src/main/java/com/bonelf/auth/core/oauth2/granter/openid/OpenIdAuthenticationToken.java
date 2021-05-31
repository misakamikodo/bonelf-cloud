/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.auth.core.oauth2.granter.openid;

import com.bonelf.frame.web.security.BaseApiAuthenticationToken;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;

/**
 * 微信登录Token认证类
 * 这里可以不继承 BaseApiAuthenticationToken
 */
@Getter
@Setter
public class OpenIdAuthenticationToken extends BaseApiAuthenticationToken {
    /**
     * 在下面添加自定义内容
     * @see com.bonelf.auth.core.oauth2.converter.CustomTokenEnhancer
     */
    private String payload = "this is an example payload data";

    public OpenIdAuthenticationToken(Authentication authenticationToken) {
        super(authenticationToken);
    }
}
