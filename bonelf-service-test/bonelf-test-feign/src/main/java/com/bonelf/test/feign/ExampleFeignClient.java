package com.bonelf.test.feign;

import com.bonelf.frame.cloud.feign.FeignConfig;
import com.bonelf.frame.core.constant.ServiceNameConstant;
import com.bonelf.frame.core.domain.Result;
import com.bonelf.test.feign.factory.ExampleFeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * 订单远程服务调用
 * </p>
 * @author bonelf
 * @since 2020/10/5 21:12
 */
@FeignClient(contextId = "exampleFeignClient",
		value = ServiceNameConstant.EXAMPLE,
		configuration = FeignConfig.class,
		fallbackFactory = ExampleFeignFallbackFactory.class)
public interface ExampleFeignClient {
	//定义要调用的方法的路径
	@GetMapping("/bonelf/v1/example/{example}")
	Result<?> example(@PathVariable("example") String example);
}

