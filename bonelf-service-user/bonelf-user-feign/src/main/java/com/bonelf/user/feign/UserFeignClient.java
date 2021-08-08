package com.bonelf.user.feign;

import com.bonelf.frame.cloud.feign.FeignConfig;
import com.bonelf.frame.core.constant.ServiceNameConstant;
import com.bonelf.frame.core.constant.UsernameType;
import com.bonelf.frame.core.domain.Result;
import com.bonelf.user.feign.domain.request.RegisterUserRequest;
import com.bonelf.user.feign.domain.response.RoleResponse;
import com.bonelf.user.feign.domain.response.UserResponse;
import com.bonelf.user.feign.factory.UserFeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

/**
 * <p>
 * 签权服务, fallback = UserClientFallback.class
 * </p>
 * @author bonelf
 * @since 2020/11/17 15:37
 */
@FeignClient(contextId = "userFeignClient",
        value = ServiceNameConstant.USER_SERVICE,
        configuration = FeignConfig.class,
        fallbackFactory = UserFeignFallbackFactory.class)
public interface UserFeignClient {

    /**
     * 查询用户
     * @param uniqueId
     * @return
     */
    @GetMapping(value = "/bonelf/user/v1/getUser")
    Result<UserResponse> getUserByUniqueId(@RequestParam("uniqueId") String uniqueId,
                                           @RequestParam(value = "idType", required = false) UsernameType[] idType);

    /**
     * 用户角色
     * @param userId
     * @return
     */
    @GetMapping(value = "/bonelf/role/v1")
    Result<Set<RoleResponse>> queryRolesByUserId(@RequestParam("userId") Long userId);

    /**
     * 注册
     * @param phone
     * @return
     */
    @PostMapping(value = "/bonelf/user/v1/registerByPhone")
    Result<UserResponse> registerByPhone(@RequestParam("phone") String phone);

    /**
     * 微信注册
     * @param registerUser
     * @return
     */
    @PostMapping(value = "/bonelf/user/v1/registerByOpenId")
    Result<UserResponse> registerByOpenId(@RequestBody RegisterUserRequest registerUser);

    @PostMapping(value = "/bonelf/user/v1/registerByMail")
    Result<UserResponse> registerByMail(@RequestParam("mail") String mail);
}
