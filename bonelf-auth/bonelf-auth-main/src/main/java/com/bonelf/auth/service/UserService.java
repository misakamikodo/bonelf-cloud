package com.bonelf.auth.service;

import com.bonelf.auth.domain.User;
import com.bonelf.user.feign.domain.request.RegisterUserRequest;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 签权服务
 * </p>
 * @author bonelf
 * @since 2020/11/17 15:37
 */
@Service
public interface UserService {

    /**
     * 根据用户唯一标识获取用户信息
     * @param uniqueId
     * @return
     */
	User getByUniqueId(String uniqueId);

	/**
	 * 注册
	 * @param phone
	 * @return
	 */
	User registerByPhone(String phone);
	/**
	 * 注册
	 * @param mail
	 * @return
	 */
	User registerByMail(String mail);

	/**
	 * 微信注册
	 * @param registerUser
	 * @return
	 */
	User registerByOpenId(RegisterUserRequest registerUser);
}
