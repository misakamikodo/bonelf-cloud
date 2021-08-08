package com.bonelf.user.web.controller.api;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bonelf.frame.base.property.BonelfProperties;
import com.bonelf.frame.base.util.redis.RedisUtil;
import com.bonelf.frame.core.constant.BonelfConstant;
import com.bonelf.frame.core.constant.UsernameType;
import com.bonelf.frame.core.domain.Result;
import com.bonelf.frame.core.exception.enums.CommonBizExceptionEnum;
import com.bonelf.frame.web.controller.BaseApiController;
import com.bonelf.user.constant.enums.UserStatusEnum;
import com.bonelf.user.constant.exception.UserExceptionEnum;
import com.bonelf.user.feign.domain.response.UserResponse;
import com.bonelf.user.web.domain.dto.AccountLoginDTO;
import com.bonelf.user.web.domain.dto.WechatLoginDTO;
import com.bonelf.user.web.domain.dto.WechatRegisterUserDTO;
import com.bonelf.user.web.domain.entity.User;
import com.bonelf.user.web.domain.vo.LoginVO;
import com.bonelf.user.web.service.RoleService;
import com.bonelf.user.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * yon接口
 * </p>
 * @author bonelf
 * @since 2020/10/30 9:29
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "用户接口")
public class UserController extends BaseApiController<UserService, User> {
	@Autowired
	private UserService userService;
	@Autowired
	private BonelfProperties bonelfProperties;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RedisUtil redisUtil;

	@Deprecated
	@ApiOperation("账号密码登录")
	@PostMapping(value = "/v1/loginByAccount")
	public Result<LoginVO> loginByAccount(@Validated @RequestBody AccountLoginDTO accountLoginDto) {
		return Result.ok(userService.loginByAccount(accountLoginDto));
	}

	@Deprecated
	@ApiOperation("微信登录")
	@PostMapping(value = "/v1/wxLogin")
	public Result<LoginVO> wxLogin(@Validated @RequestBody WechatLoginDTO wechatLoginDto) throws WxErrorException {
		LoginVO user = userService.login(wechatLoginDto);
		return Result.ok(user);
	}

	/*===========================Feign ===========================*/

	@GetMapping(value = "/v1/getUser")
	public Result<UserResponse> getUser(@RequestParam("uniqueId") String uniqueId,
										@RequestParam(value = "idType", required = false) UsernameType[] idType) {
		User user = userService.getUserByType(uniqueId, idType);
		if (user == null) {
			return Result.error(CommonBizExceptionEnum.DB_RESOURCE_NULL, "用户");
		}
		UserResponse resp = BeanUtil.copyProperties(user, UserResponse.class);
		resp.setRoles(roleService.getRoleByUserId(resp.getUserId()));
		return Result.ok(resp);
	}

	@PostMapping(value = "/v1/registerByPhone")
	public Result<User> registerByPhone(@RequestParam("phone") String phone) {
		User past = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getPhone, phone));
		Result<User> errorCheckResult = registerUserCheck(past);
		if (errorCheckResult != null) {
			return errorCheckResult;
		}
		User user = new User();
		user.setAvatar(bonelfProperties.getBaseUrl() + BonelfConstant.DEFAULT_AVATAR_PATH);
		user.setPhone(phone);
		user.setLastLoginTime(LocalDateTime.now());
		userService.save(user);
		userService.update(Wrappers.<User>lambdaUpdate().set(User::getNickname, "手机用户").eq(User::getUserId, user.getUserId()));
		return Result.ok(user);
	}

	@PostMapping(value = "/v1/registerByMail")
	public Result<User> registerByMail(@RequestParam("mail") String mail) {
		User past = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getMail, mail));
		Result<User> errorCheckResult = registerUserCheck(past);
		if (errorCheckResult != null) {
			return errorCheckResult;
		}
		User user = new User();
		user.setAvatar(bonelfProperties.getBaseUrl() + BonelfConstant.DEFAULT_AVATAR_PATH);
		user.setMail(mail);
		user.setLastLoginTime(LocalDateTime.now());
		userService.save(user);
		userService.update(Wrappers.<User>lambdaUpdate().set(User::getNickname, "邮箱用户").eq(User::getUserId, user.getUserId()));
		return Result.ok(user);
	}

	/**
	 * 注册用户合理性检查
	 * @param past
	 * @return
	 */
	private Result<User> registerUserCheck(User past) {
		if (past != null) {
			if (UserStatusEnum.FREEZE.getCode().equals(past.getStatus())) {
				return Result.error(UserExceptionEnum.FREEZE_USER);
			}
			return Result.error(UserExceptionEnum.ALREADY_REGISTER);
		}
		return null;
	}

	@PostMapping(value = "/v1/registerByOpenId")
	public Result<User> registerByOpenId(@RequestBody WechatRegisterUserDTO wechatRegisterUserDto) {
		User past = userService.getOne(Wrappers.<User>lambdaQuery()
				.eq(User::getOpenId, wechatRegisterUserDto.getOpenId()).or()
				.eq(User::getUnionId, wechatRegisterUserDto.getUnionId()).last("limit 1"));
		Result<User> errorCheckResult = registerUserCheck(past);
		if (errorCheckResult != null) {
			return errorCheckResult;
		}
		User user = BeanUtil.copyProperties(wechatRegisterUserDto, User.class);
		if (user.getAvatar() == null) {
			user.setAvatar(bonelfProperties.getBaseUrl() + BonelfConstant.DEFAULT_AVATAR_PATH);
		}
		user.setLastLoginTime(LocalDateTime.now());
		userService.save(user);
		if (user.getNickname() == null) {
			userService.update(Wrappers.<User>lambdaUpdate().set(User::getNickname, "手机用户").eq(User::getUserId, user.getUserId()));
		}
		return Result.ok(user);
	}

	@Deprecated
	@PostMapping(value = "/v1/getPermission")
	public Result<Map<String, Set<String>>> getPermission(@RequestParam Long userId) {
		return Result.ok(userService.getApiUserRolesAndPermission(userId));
	}
}
