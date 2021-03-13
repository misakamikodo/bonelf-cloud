package com.bonelf.user.web.domain.response;

import com.bonelf.frame.core.domain.CommonUser;
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
public class UserResponse implements Serializable, CommonUser {

	@ApiModelProperty(value = "1321998667393658881")
	private Long userId;

	/**
	 * 手机
	 */
	@ApiModelProperty(value = "手机")
	private String phone;

	/**
	 * 自定义账号
	 */
	@ApiModelProperty(value = "自定义账号")
	private String username;

	private String unionId;

	private String openId;

	/**
	 * 密码 暂且取openId加密值做密码
	 */
	@ApiModelProperty(value = "密码 暂且取openId加密值做密码")
	private String password;

	/**
	 * 昵称
	 */
	@ApiModelProperty(value = "昵称")
	private String nickname;

	/**
	 * 头像链接
	 */
	@ApiModelProperty(value = "头像链接")
	private String avatar;

	/**
	 * 性别 0 未设置 1男 2女
	 */
	@ApiModelProperty(value = "性别 0 未设置 1男 2女")
	private Byte gender;

	/**
	 * 上次登录时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastLoginTime;
	/**
	 * status
	 */
	private Integer status;
}
