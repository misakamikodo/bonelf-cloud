package com.bonelf.user.web.domain.vo;

import com.bonelf.user.web.domain.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * 登录返回信息
 * </p>
 * @author bonelf
 * @since 2020/11/3 16:17
 */
@Data
@Builder
@ApiModel("登录返回对象")
public class LoginVO {
	@ApiModelProperty("用户凭据")
	private String token;
	@ApiModelProperty("凭据类型")
	private String tokenType;
	@ApiModelProperty(value = "过期时长", notes = "超过最多两倍最少一倍的这段时间没有登录即失效需重新登录，" +
			"若有登录行为永远不会过期")
	private Long expiresIn;
	@ApiModelProperty("登录信息")
	private User user;
}
