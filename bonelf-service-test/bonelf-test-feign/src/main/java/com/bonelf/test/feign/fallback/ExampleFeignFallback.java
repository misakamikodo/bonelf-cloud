package com.bonelf.test.feign.fallback;

import com.bonelf.frame.core.domain.Result;
import com.bonelf.test.feign.ExampleFeignClient;
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
