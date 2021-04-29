package com.bonelf.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.bonelf.auth.domain.User;
import com.bonelf.auth.service.UserService;
import com.bonelf.frame.core.constant.enums.YesOrNotEnum;
import com.bonelf.frame.core.domain.Result;
import com.bonelf.frame.core.exception.BonelfException;
import com.bonelf.user.feign.UserFeignClient;
import com.bonelf.user.feign.domain.request.RegisterUserRequest;
import com.bonelf.user.feign.domain.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

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
	public User getByUniqueId(String uniqueId) {
		Result<UserResponse> user = userFeignClient.getUserByUniqueId(uniqueId);
		if (!user.getSuccess()) {
			// throw new BonelfException(CommonBizExceptionEnum.BUSY);
			throw BonelfException.builder().code("500").msg(user.getMessage()).build();
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
	public User registerByOpenId(RegisterUserRequest registerUser) {
		Result<UserResponse> userResp = userFeignClient.registerByOpenId(registerUser);
		if (userResp.getSuccess()) {
			User userResult = getUserFromUserResp(userResp);
			return userResult;
		} else {
			//注册失败
			throw new InvalidGrantException("注册失败");
		}
	}
}
