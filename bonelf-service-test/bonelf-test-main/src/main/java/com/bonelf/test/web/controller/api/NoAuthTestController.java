package com.bonelf.test.web.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bonelf.frame.core.dict.annotation.DictWrapper;
import com.bonelf.frame.core.dict.annotation.DictWrappers;
import com.bonelf.frame.core.dict.enums.DictType;
import com.bonelf.frame.core.domain.Result;
import com.bonelf.frame.web.controller.BaseController;
import com.bonelf.test.web.domain.dto.TestConverterDTO;
import com.bonelf.test.web.domain.vo.TestConverterVO;
import com.bonelf.test.web.domain.vo.TestDictVO;
import com.bonelf.test.web.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Slf4j
@Api(tags = {"测试接口"})
@RestController
@RequestMapping("/noAuth")
//@DefaultProperties( threadPoolKey="xxx" )
public class NoAuthTestController extends BaseController {
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private TestService testService;

	// @ApiOperation(value = "testLogin")
	// @GetMapping("/testLogin")
	// public Result<?> testLogin() {
	// 	System.out.println("User:" + JSON.toJSONString(SecurityContextHolder.getContext().getAuthentication()));
	// 	return Result.ok();
	// }

	@ApiOperation(value = "testConverter")
	@GetMapping("/testConverter")
	public Result<TestConverterVO> testConverter() {
		return Result.ok(new TestConverterVO());
	}

	@ApiOperation(value = "testConverter")
	@PostMapping("/testConverter")
	public Result<TestConverterDTO> testConverterPost(@Validated @RequestBody TestConverterDTO testConverterDto) {
		log.debug("\n" + JSON.toJSONString(testConverterDto, SerializerFeature.PrettyFormat));
		return Result.ok(testConverterDto);
	}

	@PostMapping("/testSeata")
	public Result<Object> testSeata() {
		return Result.ok(testService.testSeata());
	}

	@GetMapping("/remoteDict")
	public Result<Object> remoteDict() {
		return Result.ok(new HashMap<String, Object>() {{
			put("test", "TestOK");
		}});
	}

	/**
	 * <p>
	 * restTemplate 方法调用服务测试
	 * </p>
	 * @author bonelf
	 * @since 2020/10/5 21:57
	 */
	// @ApiOperation(value = "restTemplateTest")
	// @GetMapping("/restTemplate")
	// public String restTemplateTest() {
	// 	Map<String, Object> params = new HashMap<>(1);
	// 	params.put("orderId", "123");
	// 	return (String)restTemplate.getForObject("http://order-service/productOrder/getOrderById" + "?orderId={orderId}",
	// 			Result.class,
	// 			params).getResult();
	// }

	/**
	 * <p>
	 * feign请求测试
	 * </p>
	 * @author bonelf
	 * @since 2020/10/5 21:57
	 */
	// @GetMapping("/orderFeign")
	// public String orderFeign() {
	// 	return (String)orderFeignClient.getProductOrderById("123").getResult();
	// }

	// @GetMapping("/userFeign")
	// public Result<?> userFeign() {
	// 	return userFeignClient.getUserByUniqueId("1328231759195709441");
	// }
	@DictWrappers(
			@DictWrapper(type = DictType.enums,
					payload = "value=com.bonelf.frame.core.constant.enums.YesOrNotEnum",
					fieldSeq = "aopDict")
	)
	@ApiOperation(value = "testDict")
	@GetMapping("/testDict")
	public Result<TestDictVO> testDict() {
		return Result.ok(new TestDictVO());
	}

	/**
	 * <p>
	 * feign fallback方法2请求测试（测试时使用@FeignClient(ServiceNameConstant.ORDER_SERVICE)，
	 * 需要App上有@EnableCircuitBreaker注解）
	 * </p>
	 * @author bonelf
	 * @since 2020/10/5 21:57
	 */
	// @GetMapping("/orderFeignMethodFallback")
	// @SentinelResource(blockHandler = "orderFeign2Back")
	// public String orderFeign2() {
	// 	return (String)orderFeignClient.getProductOrderById("123").getResult();
	// }

}
