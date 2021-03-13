package com.bonelf.user.web.domain.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bonelf.frame.core.domain.CommonUser;
import com.bonelf.frame.web.domain.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * Api用户
 * </p>
 * @author bonelf
 * @since 2020/10/12 16:57
 */
@Data
@TableName(value = "bnf_user")
public class User extends BaseEntity implements Serializable, CommonUser {

	@TableId(value = "user_id", type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "1321998667393658881")
	private Long userId;

	/**
	 * 手机
	 */
	@TableField(value = "phone")
	@ApiModelProperty(value = "手机")
	private String phone;

	/**
	 * 手机
	 */
	@TableField(value = "username")
	@ApiModelProperty(value = "用户名")
	private String username;
	/**
	 * 邮箱
	 */
	@TableField(value = "mail")
	@ApiModelProperty(value = "邮箱")
	private String mail;

	/**
	 * unionId
	 */
	@TableField(value = "union_id")
	private String unionId;

	/**
	 * openId
	 */
	@TableField(value = "open_id")
	private String openId;

	/**
	 * 密码 暂且取openId加密值做密码
	 */
	@JSONField(serialize = false)
	@TableField(value = "user_password")
	@ApiModelProperty(value = "密码 暂且取openId加密值做密码")
	private String password;

	/**
	 * 昵称
	 */
	@TableField(value = "nickname")
	@ApiModelProperty(value = "昵称")
	private String nickname;

	/**
	 * 头像链接
	 */
	@TableField(value = "avatar")
	@ApiModelProperty(value = "头像链接")
	private String avatar;

	/**
	 * 性别 0 未设置 1男 2女
	 */
	@TableField(value = "gender")
	@ApiModelProperty(value = "性别 0 未设置 1男 2女")
	private Byte gender;

	/**
	 * 国家
	 */
	@TableField(value = "country")
	@ApiModelProperty(value = "国家")
	private String country;

	/**
	 * 省份
	 */
	@TableField(value = "province")
	@ApiModelProperty(value = "省份")
	private String province;

	/**
	 * 城市
	 */
	@TableField(value = "city")
	@ApiModelProperty(value = "城市")
	private String city;

	/**
	 * 语言
	 */
	@TableField(value = "user_language")
	@ApiModelProperty(value = "语言")
	private String language;

	/**
	 * 上次登录时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(value = "last_login_time")
	private LocalDateTime lastLoginTime;
	/**
	 * status
	 */
	@TableField(value = "user_status")
	private Integer status;

	@Override
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String getUsername() {
		return phone;
	}
}
