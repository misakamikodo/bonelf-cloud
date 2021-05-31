/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.common.base.security.domain;


import com.bonelf.user.feign.domain.response.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false )
@NoArgsConstructor
public class User implements Serializable {
    public static final Long serialVersionUID = -1L;
    /**
     * userId
     */
    private Long userId;
    /**
     * 手机 手机注册、微信登录获取
     */
    private String phone;
    /**
     * 邮箱
     */
    private String mail;
    /**
     * 账号 自动生成、用户注册
     */
    private String username;
    /**
     * openId 微信登录
     */
    private String openId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 密码 账号、手机登录
     */
    private String password;
    /**
     * 短信、邮箱验证码 手机登录
     */
    private String verifyCode;
    /**
     * 是否可用
     */
    private Boolean enabled;

    /**
     * 账户是否没过期
     */
    private Boolean accountNonExpired;

    /**
     * 密码是否没过期
     */
    private Boolean credentialsNonExpired;

    /**
     * 账户是否没冻结
     */
    private Boolean accountNonLocked;

    /**
     * 角色
     */
    private Set<Role> roles;
}