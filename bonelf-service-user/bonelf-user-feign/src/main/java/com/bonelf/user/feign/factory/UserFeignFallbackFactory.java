package com.bonelf.user.feign.factory;

import com.bonelf.user.feign.UserFeignClient;
import com.bonelf.user.feign.fallback.UserFeignClientFallback;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserFeignFallbackFactory implements FallbackFactory<UserFeignClient> {

    @Override
    public UserFeignClient create(Throwable throwable) {
        UserFeignClientFallback fallback = new UserFeignClientFallback();
        log.error("Feign error", throwable);
        fallback.setCause(throwable);
        return fallback;
    }
}