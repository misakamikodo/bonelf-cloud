package com.bonelf.order.web.controller.api;

import com.bonelf.frame.core.domain.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productOrder")
public class ProductOrderController {

	@ApiOperation("创建订单")
	@GetMapping("/createOrder")
	public Result<?> createOrder() {
		return Result.ok();
	}

	@ApiOperation("创建详情")
	@GetMapping("/{orderId}")
	public Result<?> getOrderById(@ApiParam("订单编号") @PathVariable String orderId) {
		return Result.ok();
	}
}
