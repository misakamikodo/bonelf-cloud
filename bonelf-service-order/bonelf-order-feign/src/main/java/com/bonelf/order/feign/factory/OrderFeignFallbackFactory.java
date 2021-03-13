package com.bonelf.order.feign.factory;

import com.bonelf.order.feign.OrderFeignClient;
import com.bonelf.order.feign.fallback.OrderServiceFallback;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderFeignFallbackFactory implements FallbackFactory<OrderFeignClient> {

    @Override
    public OrderFeignClient create(Throwable throwable) {
        OrderServiceFallback fallback = new OrderServiceFallback();
        fallback.setCause(throwable);
        return fallback;
    }
}