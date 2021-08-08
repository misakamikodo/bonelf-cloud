package com.bonelf.auth.feign.fallback;

import com.bonelf.auth.feign.ExampleFeignClient;
import com.bonelf.frame.core.domain.Result;
import lombok.Setter;

/**
 * <p>
 * feign请求失败返回
 * </p>
 * @author bonelf
 * @since 2020/10/6 0:21
 */
public class ExampleFeignFallback implements ExampleFeignClient {

    @Setter
    private Throwable cause;

    @Override
    public Result<?> example(String example) {
        return null;
    }
}
