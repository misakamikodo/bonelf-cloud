package com.bonelf.user.web.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bonelf.frame.core.domain.Result;
import com.bonelf.frame.web.controller.BaseApiController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * yon接口
 * </p>
 * @author bonelf
 * @since 2020/10/30 9:29
 */
@RestController
@RequestMapping("/role")
@Slf4j
@Api(tags = "角色接口")
public class RoleController extends BaseApiController {

	@GetMapping(value = "/v1")
	public Result<JSONArray> getUser(@RequestParam Long userId) {
		JSONArray json = new JSONArray();
		JSONObject temp = new JSONObject();
		temp.put("code", "test:role");
		temp.put("name", "testRole");
		temp.put("description", "this is an example role");
		json.add(temp);
		return Result.ok(json);
	}
}
