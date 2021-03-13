/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.system.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bonelf.system.web.domain.entity.SpuClick;
import com.bonelf.system.web.domain.vo.ChartVO;

import java.time.LocalDate;
import java.util.List;

public interface SpuClickService extends IService<SpuClick> {

	/**
	 * 增加点击量
	 * @return
	 */
	void incrClick(Long spuId, int count);

	/**
	 * 增加点击量
	 * @return
	 */
	void sumClick();

	/**
	 * 点击量图表
	 * @return
	 */
	List<ChartVO> chart(LocalDate startDate, LocalDate endDate);
}
