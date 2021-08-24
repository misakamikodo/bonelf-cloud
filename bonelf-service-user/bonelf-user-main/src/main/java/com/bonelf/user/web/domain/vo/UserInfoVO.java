package com.bonelf.user.web.domain.vo;

import lombok.Data;

/**
 * 用户信息VO
 * @author ccy
 * @date 2021/8/24 15:15
 */
@Data
public class UserInfoVO {
	private String[] roles = new String[]{"admin"};
	private String[] roleNames = new String[]{"管理员"};
	private String introduction = "我是超级管理员";
	private String avatar = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";
	private String name = "超级用户";
}
