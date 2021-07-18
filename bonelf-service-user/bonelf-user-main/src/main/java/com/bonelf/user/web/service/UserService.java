package com.bonelf.user.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bonelf.frame.core.constant.UsernameType;
import com.bonelf.user.web.domain.dto.AccountLoginDTO;
import com.bonelf.user.web.domain.dto.WechatLoginDTO;
import com.bonelf.user.web.domain.entity.User;
import com.bonelf.user.web.domain.vo.LoginVO;
import me.chanjar.weixin.common.error.WxErrorException;

import java.util.Map;
import java.util.Set;

public interface UserService extends IService<User> {
	/**
	 * 权限
	 * @param userId
	 * @return
	 */
	Map<String, Set<String>> getApiUserRolesAndPermission(Long userId);

	/**
	 * 微信登录
	 * @return
	 */
	LoginVO login(WechatLoginDTO wechatLoginDto) throws WxErrorException;

	/**
	 * 账号密码的登录
	 * @return
	 */
	LoginVO loginByAccount(AccountLoginDTO dto);

	/**
	 * 根据类型获取用户
	 * @param uniqueId
	 * @param idTypes
	 * @return
	 */
	User getUserByType(String uniqueId, UsernameType[] idTypes);
}
