package com.bonelf.common.base.security.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.bonelf.frame.core.auth.domain.RegisterUserAO;
import com.bonelf.frame.core.auth.domain.Role;
import com.bonelf.frame.core.auth.domain.User;
import com.bonelf.frame.core.auth.service.AuthUserService;
import com.bonelf.frame.core.constant.UsernameType;
import com.bonelf.frame.core.constant.enums.YesOrNotEnum;
import com.bonelf.frame.core.domain.Result;
import com.bonelf.frame.core.exception.BonelfException;
import com.bonelf.user.feign.UserFeignClient;
import com.bonelf.user.feign.domain.request.RegisterUserRequest;
import com.bonelf.user.feign.domain.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Primary
@Service("defaultAuthUserService")
public class AuthUserServiceImpl implements AuthUserService {

	@Autowired
	private UserFeignClient userFeignClient;

	/**
	 * 根据用户唯一标识获取用户信息
	 * FIXME 此处添加缓存
	 * @param uniqueId uniqueId
	 * @return
	 */
	@Override
	//@Cacheable(value = "#id", condition = "#result.getSuccess()")
	public User getByUniqueId(String uniqueId, UsernameType idType) {
		return getByUniqueId(uniqueId, new UsernameType[]{idType});
	}

	/**
	 * 根据用户唯一标识获取用户信息,没有的话则注册
	 * @param uniqueId
	 * @param idType
	 * @param userMsg
	 * @return
	 */
	@Override
	public User getByUniqueIdOrElseRegister(String uniqueId, UsernameType idType, RegisterUserAO userMsg) {
		return getByUniqueId(uniqueId, new UsernameType[]{idType});
	}

	@Override
	public User getByUniqueId(String uniqueId, UsernameType[] idTypes) {
		Result<UserResponse> user = userFeignClient.getUserByUniqueId(uniqueId, idTypes);
		if (!user.getSuccess()) {
			throw BonelfException.builder(user.getMessage()).code(user.getCode()).msg(user.getMessage()).build();
		}
		User userResult = getUserFromUserResp(user);
		return userResult;
	}

	@Override
	public User registerByPhone(String phone) {
		Result<UserResponse> userResp = userFeignClient.registerByPhone(phone);
		if (!userResp.getSuccess() || userResp.getResult() == null) {
			throw new UsernameNotFoundException("register fail");
		}
		User userResult = getUserFromUserResp(userResp);
		return userResult;
	}

	/**
	 * 封装用户
	 * @param userResp
	 * @return
	 */
	private User getUserFromUserResp(Result<UserResponse> userResp) {
		User userResult = new User();
		BeanUtil.copyProperties(userResp.getResult(), userResult);
		userResult.setEnabled(YesOrNotEnum.N.getCode().equals(userResp.getResult().getStatus()));
		userResult.setAccountNonExpired(true);
		userResult.setCredentialsNonExpired(true);
		userResult.setAccountNonLocked(YesOrNotEnum.N.getCode().equals(userResp.getResult().getStatus()));
		userResult.setRoles(userResp.getResult().getRoles().stream()
				.map(item -> BeanUtil.copyProperties(item, Role.class))
				.collect(Collectors.toSet()));
		return userResult;
	}

	@Override
	public User registerByMail(String mail) {
		Result<UserResponse> userResp = userFeignClient.registerByMail(mail);
		if (!userResp.getSuccess() || userResp.getResult() == null) {
			throw new UsernameNotFoundException("register fail");
		}
		User userResult = getUserFromUserResp(userResp);
		return userResult;
	}

	@Override
	public User registerByOpenId(RegisterUserAO registerUser) {
		Result<UserResponse> userResp = userFeignClient.registerByOpenId(BeanUtil.copyProperties(registerUser, RegisterUserRequest.class));
		if (userResp.getSuccess()) {
			User userResult = getUserFromUserResp(userResp);
			return userResult;
		} else {
			//注册失败
			throw new InvalidGrantException("注册失败");
		}
	}
}
