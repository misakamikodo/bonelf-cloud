package com.bonelf.demo.feign.factory;

import com.bonelf.demo.feign.ExampleFeignClient;
import com.bonelf.demo.feign.fallback.ExampleFeignFallback;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExampleFeignFallbackFactory implements FallbackFactory<ExampleFeignClient> {

    @Override
    public ExampleFeignClient create(Throwable throwable) {
        ExampleFeignFallback fallback = new ExampleFeignFallback();
        log.error("Feign error", throwable);
        fallback.setCause(throwable);
        return fallback;
    }
}