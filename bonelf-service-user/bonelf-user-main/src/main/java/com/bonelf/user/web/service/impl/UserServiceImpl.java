package com.bonelf.user.web.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.HexUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bonelf.cicada.util.CipherCryptUtil;
import com.bonelf.cicada.util.Md5CryptUtil;
import com.bonelf.frame.base.property.BonelfProperties;
import com.bonelf.frame.base.util.redis.RedisUtil;
import com.bonelf.frame.core.constant.AuthConstant;
import com.bonelf.frame.core.constant.BonelfConstant;
import com.bonelf.frame.core.constant.CommonCacheConstant;
import com.bonelf.frame.core.constant.UsernameType;
import com.bonelf.frame.core.exception.BonelfException;
import com.bonelf.frame.core.exception.enums.CommonBizExceptionEnum;
import com.bonelf.user.constant.CacheConstant;
import com.bonelf.user.web.domain.dto.AccountLoginDTO;
import com.bonelf.user.web.domain.dto.WechatLoginDTO;
import com.bonelf.user.web.domain.entity.User;
import com.bonelf.user.web.domain.vo.LoginVO;
import com.bonelf.user.web.mapper.UserMapper;
import com.bonelf.user.web.service.UserService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private WxMaService wxMaService;

	@Autowired
	private BonelfProperties bonelfProperties;

	@Override
	public Map<String, Set<String>> getApiUserRolesAndPermission(Long userId) {
		Map<String, Set<String>> permission = new HashMap<>(2);
		permission.put("roles", CollectionUtil.newHashSet("example"));
		permission.put("permissions", CollectionUtil.newHashSet("api:user:example"));
		return permission;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public LoginVO loginByAccount(AccountLoginDTO dto) {
		User user = this.baseMapper.selectOneByPhone(dto.getUsername());
		if (dto.getVerifyCode() == null) {
			if (user == null) {
				throw new BonelfException(CommonBizExceptionEnum.DB_RESOURCE_NULL, "用户");
			}
			//密码登录
			String password;
			try {
				password = CipherCryptUtil.decrypt(dto.getPassword(), dto.getUsername(), AuthConstant.FRONTEND_SALT_CRYPTO);
			} catch (Exception e) {
				e.printStackTrace();
				throw new BonelfException(CommonBizExceptionEnum.DECRYPT_ERROR);
			}
			//if (!Pattern.matches(RegexpConstant.NUMBERS_AND_LETTERS, password)) {
			//	throw new BonelfException(BizExceptionEnum.REQUEST_INVALIDATE_EMPTY, "请输入6-16位数字,字母组成的密码");
			//}
			if (!Md5CryptUtil.encrypt(password, AuthConstant.DATABASE_SALT_MD5).equals(Md5CryptUtil.encrypt(password, AuthConstant.DATABASE_SALT_MD5))) {
				throw new BonelfException(CommonBizExceptionEnum.REQUEST_INVALIDATE_EMPTY, "密码不正确");
			}
			this.baseMapper.update(new User(), Wrappers.<User>lambdaUpdate().set(User::getLastLoginTime, LocalDateTime.now()).eq(User::getUserId, user.getUserId()));
		} else {
			//验证码登录
			String trueVerifyCode = (String)redisUtil.get(String.format(CacheConstant.LOGIN_VERIFY_CODE, "login", dto.getUsername()));
			if (!dto.getVerifyCode().equals(trueVerifyCode)) {
				throw new BonelfException(CommonBizExceptionEnum.REQUEST_INVALIDATE_EMPTY, "验证码错误");
			}
			if (user == null) {
				//注册
				user = new User();
				//if (Pattern.matches(RegexpConstant.VALIDATE_PHONE, dto.getUsername())) {
				user.setPhone(dto.getUsername());
				//}
				user.setPassword(Md5CryptUtil.encrypt(user.getPhone().substring(user.getPhone().length() - 6), AuthConstant.DATABASE_SALT_MD5));
				user.setLastLoginTime(LocalDateTime.now());
				user.setAvatar(bonelfProperties.getBaseUrl() + BonelfConstant.DEFAULT_AVATAR_PATH);
				this.baseMapper.insert(user);
				user.setNickname("用户" + HexUtil.toHex(user.getUserId()));
				this.baseMapper.updateById(user);
			} else {
				this.baseMapper.update(new User(), Wrappers.<User>lambdaUpdate().set(User::getLastLoginTime, LocalDateTime.now()).eq(User::getUserId, user.getUserId()));
			}
			redisUtil.del(CacheConstant.LOGIN_VERIFY_CODE, user.getPhone());
		}
		//String token = JwtTokenUtil.generateToken(user.getUserId(), user.getPhone(),  UserTypeEnum.API_SHIRO_REALM.getRealmName());
		String token = "";
		//存储token 刷新token用 初始的对应关系为 自己对自己
		redisUtil.set(String.format(CommonCacheConstant.API_USER_TOKEN_PREFIX, user.getUserId()), token, AuthConstant.REFRESH_SECOND);

		return LoginVO.builder()
				.token(token)
				.tokenType(AuthConstant.TOKEN_PREFIX.trim())
				.expiresIn(AuthConstant.EXPIRATION_SECOND)
				.user(user)
				.build();
	}

	/**
	 * 根据类型获取用户
	 * @param uniqueId
	 * @param idTypes
	 * @return
	 */
	@Override
	public User getUserByType(String uniqueId, UsernameType[] idTypes) {
		if(idTypes == null || idTypes.length == 0){
			return this.getOne(Wrappers.<User>lambdaQuery()
					.eq(User::getUserId, uniqueId).or()
					.eq(User::getUsername, uniqueId).or()
					.eq(User::getPhone, uniqueId).or()
					.eq(User::getMail, uniqueId).or()
					.eq(User::getOpenId, uniqueId).orderByDesc(User::getUpdateTime).last("limit 1"));
		} else {
			LambdaQueryWrapper<User> lqw = Wrappers.<User>lambdaQuery()
					.orderByDesc(User::getUpdateTime).last("limit 1");
			for (int i = 0; i < idTypes.length - 1; i++) {
				UsernameType idType = idTypes[i];
				switch (idType){
					case id:
						lqw = lqw.eq(User::getUserId, uniqueId).or();
						break;
					case username:
						lqw = lqw.eq(User::getUsername, uniqueId).or();
						break;
					case mail:
						lqw = lqw.eq(User::getMail, uniqueId).or();
						break;
					case phone:
						lqw = lqw.eq(User::getPhone, uniqueId).or();
						break;
					case openId:
						lqw = lqw.eq(User::getOpenId, uniqueId).or();
						break;
					case unionId:
						lqw = lqw.eq(User::getUnionId, uniqueId).or();
						break;
					default:
						throw new BonelfException(String.format("unknown type %s of username", idType.toString()));
				}
			}
			switch (idTypes[idTypes.length - 1]){
				case id:
					lqw.eq(User::getUserId, uniqueId);
					break;
				case username:
					lqw.eq(User::getUsername, uniqueId);
					break;
				case mail:
					lqw.eq(User::getMail, uniqueId);
					break;
				case phone:
					lqw.eq(User::getPhone, uniqueId);
					break;
				case openId:
					lqw.eq(User::getOpenId, uniqueId);
					break;
				case unionId:
					lqw.eq(User::getUnionId, uniqueId);
					break;
				default:
					throw new BonelfException(String.format("unknown type %s of username", uniqueId.toString()));
			}
			return this.getOne(lqw);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public LoginVO login(WechatLoginDTO wechatLoginDto) throws WxErrorException {
		// 获取微信用户session
		WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(wechatLoginDto.getCode());
		if (null == session) {
			throw new BonelfException(CommonBizExceptionEnum.THIRD_FAIL, "session获取失败");
		}
		WxMaUserInfo wxUserInfo = wxMaService.getUserService().getUserInfo(session.getSessionKey(), wechatLoginDto.getEncryptedData(), wechatLoginDto.getIv());
		if (null == wxUserInfo) {
			throw new BonelfException(CommonBizExceptionEnum.THIRD_FAIL, "无法找到用户信息");
		}
		String openId = wxUserInfo.getOpenId();
		String unionId = wxUserInfo.getUnionId();
		User user = this.baseMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getOpenId, openId).orderByDesc(User::getUpdateTime).last("limit 1"));
		if (user == null) {
			// 注册用户
			// 解密手机号码信息
			WxMaPhoneNumberInfo wxMaPhoneNumberInfo = wxMaService.getUserService().getPhoneNoInfo(session.getSessionKey(),
					wechatLoginDto.getEncryptedData(), wechatLoginDto.getIv());
			if (wxMaPhoneNumberInfo == null || !StringUtils.hasText(wxMaPhoneNumberInfo.getPhoneNumber())) {
				// 解密手机号码信息错误
				throw new BonelfException(CommonBizExceptionEnum.THIRD_FAIL, "解析手机号失败");
			}
			String phone = wxMaPhoneNumberInfo.getPhoneNumber();
			user = new User();
			user.setPhone(phone);
			user.setOpenId(openId);
			user.setUnionId(unionId);
			user.setLastLoginTime(LocalDateTime.now());
			user.setCountry(wxUserInfo.getCountry());
			user.setCity(wxUserInfo.getCity());
			user.setProvince(wxUserInfo.getProvince());
			user.setAvatar(wxUserInfo.getAvatarUrl());
			user.setGender(Byte.parseByte(wxUserInfo.getGender()));
			user.setLanguage(wxUserInfo.getLanguage());
			user.setNickname(wxUserInfo.getNickName());
			this.baseMapper.insert(user);
		} else {
			this.baseMapper.update(new User(),
					Wrappers.<User>lambdaUpdate()
							.set(User::getLastLoginTime, LocalDateTime.now())
							.eq(User::getUserId, user.getUserId()));
		}
		//String token = JwtTokenUtil.generateToken(user.getUserId(), user.getPhone(),  UserTypeEnum.API_SHIRO_REALM.getRealmName());
		String token = "";
		//存储token 刷新token用 初始的对应关系为 自己对自己
		redisUtil.set(String.format(CommonCacheConstant.API_USER_TOKEN_PREFIX, user.getUserId()), token, AuthConstant.REFRESH_SECOND);
		return LoginVO.builder()
				.token(token)
				.tokenType(AuthConstant.TOKEN_PREFIX.trim())
				.expiresIn(AuthConstant.EXPIRATION_SECOND)
				.user(user)
				.build();
	}
}
