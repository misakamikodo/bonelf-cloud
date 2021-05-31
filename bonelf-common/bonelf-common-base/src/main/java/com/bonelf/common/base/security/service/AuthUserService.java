package com.bonelf.common.base.security.service;

import com.bonelf.common.base.security.domain.User;
import com.bonelf.frame.core.constant.UniqueIdType;
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
public interface AuthUserService {

    /**
     * 根据用户唯一标识获取用户信息
     * @param uniqueId
     * @return
     */
	User getByUniqueId(String uniqueId, UniqueIdType idType);

	User getByUniqueId(String uniqueId, UniqueIdType[] idType);

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

	/**
	 * 根据用户唯一标识获取用户信息,没有的话则注册
	 * 不可使用，因为还没进行验证码之类的校验
	 * @param uniqueId
	 * @return
	 */
	@Deprecated
	User getByUniqueIdOrElseRegister(String uniqueId, UniqueIdType idType, RegisterUserRequest userMsg);
}
