/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.system.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bonelf.frame.base.util.redis.RedisUtil;
import com.bonelf.system.constant.CacheConstant;
import com.bonelf.system.web.domain.entity.SpuClick;
import com.bonelf.system.web.domain.vo.ChartVO;
import com.bonelf.system.web.mapper.SpuClickMapper;
import com.bonelf.system.web.service.SpuClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SpuClickServiceImpl extends ServiceImpl<SpuClickMapper, SpuClick> implements SpuClickService {

	@Autowired
	private RedisUtil redisUtil;

	@Override
	public void incrClick(Long spuId, int count) {
		redisUtil.incr(CacheConstant.SPU_CLICK, count);
		//如果需要统计每个商品的点击量/查询量使用hash
		//redisUtil.hincr(CacheConstant.SPU_CLICK_HASH, String.valueOf(spuId), count);
	}

	@Override
	public void sumClick() {
		Integer count = (Integer)redisUtil.get(CacheConstant.SPU_CLICK);
		SpuClick spuClick = new SpuClick();
		spuClick.setClickDate(LocalDate.now());
		spuClick.setClickCount(count);
		this.save(spuClick);
		//如果需要统计每个商品的点击量/查询量使用hash
		//redisUtil.<String, Integer>hmget(CacheConstant.SPU_CLICK_HASH).forEach((key, value) -> {
		//	Long spuId = Long.parseLong(key);
		//
		//	redisUtil.hdel(CacheConstant.SPU_CLICK_HASH, key);
		//});
	}

	@Override
	public List<ChartVO> chart(LocalDate startDate, LocalDate endDate) {
		return this.baseMapper.selectQueryChart(startDate, endDate);
	}
}
