package com.bonelf.test.web.controller.api;

import com.bonelf.frame.core.domain.Result;
import com.bonelf.frame.core.exception.BonelfException;
import com.bonelf.frame.core.exception.enums.CommonBizExceptionEnum;
import com.bonelf.frame.web.controller.BaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = {"示例接口"})
@RestController
@RequestMapping("/noAuth/example")
public class ExampleController extends BaseController {

	/**
	 * http://127.0.0.1:8080/bonelf/noAuth/example/test
	 * http://127.0.0.1:9999/bonelf/test/noAuth/example/test
	 * TODO 处理 Missing request header 'Authorization-Flag' for method parameter of type String 异常
	 * @return
	 */
	@GetMapping("/test")
	public Result<String> test(){
		return Result.ok("ok");
	}

	@GetMapping("/integer")
	public Integer integer(){
		return 1;
	}

	@GetMapping("/exception")
	public void exception(){
		throw new BonelfException(CommonBizExceptionEnum.SERVER_ERROR, "测试");
	}
}
