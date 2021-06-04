package com.bonelf.user.web.controller.api;

import com.bonelf.frame.base.util.redis.RedisUtil;
import com.bonelf.frame.core.domain.Result;
import com.bonelf.user.constant.CacheConstant;
import com.bonelf.user.web.domain.dto.ShopDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Metrics;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商店接口
 * </p>
 * @author bonelf
 * @since 2020/10/30 9:29
 */
@RestController("BonelfShopController")
@RequestMapping("/shop")
@Slf4j
@Api(tags = "商店接口")
public class ShopController {
	@Autowired
	private RedisUtil redisUtil;

	@PostMapping("/add")
	@ApiOperation("添加")
	public Result<?> add(@RequestBody ShopDTO shopDto) {
		// FIXME: 2020/12/1 ShopId
		redisUtil.gAdd(CacheConstant.SHOP_GEO, shopDto.getShopName(), shopDto.getLng().doubleValue(), shopDto.getLat().doubleValue());
		return Result.ok();
	}

	@RequestMapping(value = "/page", method = {RequestMethod.POST, RequestMethod.GET})
	@ApiOperation("分页查询")
	public Result<?> list(@RequestParam Double lat, @RequestParam Double lng) {
		// FIXME: 2020/12/1 ShopId
		//return Result.ok(redisUtil.gRadius(CacheConstant.SHOP_GEO, lat, lng, 1000000D, Metrics.NEUTRAL, "desc"));
		return Result.ok(redisUtil.gPage(1, 10, CacheConstant.SHOP_GEO, lat, lng, 1000D, Metrics.KILOMETERS, "desc", "NearBy1000"));
	}
}
