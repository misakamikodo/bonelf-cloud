package com.bonelf.user.feign.fallback;

import com.bonelf.frame.core.constant.UsernameType;
import com.bonelf.frame.core.domain.Result;
import com.bonelf.user.feign.UserFeignClient;
import com.bonelf.user.feign.domain.request.RegisterUserRequest;
import com.bonelf.user.feign.domain.response.RoleResponse;
import com.bonelf.user.feign.domain.response.UserResponse;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * 签权服务
 * </p>
 * @author bonelf
 * @since 2020/11/17 15:37
 */
// @Component
public class UserFeignClientFallback implements UserFeignClient {

	@Setter
	private Throwable cause;

	// @Autowired
	// private PasswordEncoder passwordEncoder;

	@Override
	public Result<UserResponse> getUserByUniqueId(String uniqueId, UsernameType[] idType) {
		// FIXME: 2020/11/19 超时报错返回error
		//User user = new User();
		//user.setEnabled(true);
		//user.setAccountNonExpired(true);
		//user.setAccountNonLocked(true);
		//user.setCredentialsNonExpired(true);
		//user.setMobile("13758233010");
		//user.setNickname("nickname");
		//user.setPassword("123456");
		//user.setUsername("13758233010");
		//user.setOpenId("test-open-id");
		//user.setVerifyCode("980826");
		//user.setUserId(-1L);
		//return Result.ok(user);
        return Result.error();
	}

	@Override
	public Result<Set<RoleResponse>> queryRolesByUserId(Long userId) {
		// FIXME: 2020/11/19 超时报错返回error
		return Result.ok(new HashSet<RoleResponse>());
	}

	@Override
	public Result<UserResponse> registerByPhone(String phone) {
		return Result.error();
	}

	@Override
	public Result<UserResponse> registerByMail(String mail) {
		return Result.error();
	}

	@Override
	public Result<UserResponse> registerByOpenId(RegisterUserRequest registerUser) {
		return Result.error();
	}
}
