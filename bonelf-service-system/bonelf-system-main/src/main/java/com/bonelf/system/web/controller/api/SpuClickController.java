/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.system.web.controller.api;

import cn.hutool.core.date.DatePattern;
import com.bonelf.frame.core.domain.Result;
import com.bonelf.system.web.domain.vo.ChartVO;
import com.bonelf.system.web.service.SpuClickService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/spuClick")
public class SpuClickController {
	@Autowired
	private SpuClickService spuClickService;

	@ApiOperation(value = "折线图表格查询", httpMethod = "GET")
	@GetMapping(value = "/chart")
	public Result<List<ChartVO>> table(@ApiParam(name = "startDate", value = "开始时间") @RequestParam(value = "startDate", required = false) LocalDate startDate,
									   @ApiParam(name = "endDate", value = "结束时间") @RequestParam(value = "endDate", required = false) LocalDate endDate) {
		if (startDate == null) {
			startDate = LocalDate.now().minusDays(7);
		}
		if (endDate == null) {
			endDate = LocalDate.now();
		}
		//和datetime比较 时间+1天
		endDate = endDate.plusDays(1);
		List<ChartVO> data = spuClickService.chart(startDate, endDate);
		return Result.ok(wrap(data, startDate, endDate));
	}

	/**
	 * 补充空日期
	 * @param data 日期
	 * @return
	 */
	private List<ChartVO> wrap(List<ChartVO> data, LocalDate startDate, LocalDate endDate) {
		List<ChartVO> result = new ArrayList<>();
		if (data.size() == 0) {
			result.add(new ChartVO(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN).format(LocalDate.now()), "0"));
			return result;
		}
		//空数据 从小到大排序的法 也可以使用迭代器在原来的列表中添加
		int dateSpan = (startDate == null ? LocalDate.parse(data.get(0).getRow()) : startDate)
				.until(endDate == null ? LocalDate.parse(data.get(data.size() - 1).getRow()) : endDate).getDays();
		int pointer = 0;
		for (int i = 0; i < dateSpan; i++) {
			LocalDate date = LocalDate.parse(data.get(0).getRow()).plus(i, ChronoUnit.DAYS);
			boolean getTargetDate = false;
			for (int k = pointer; k < data.size(); k++) {
				ChartVO chartVo = data.get(k);
				if (chartVo == null) {
					continue;
				}
				if (LocalDate.parse(chartVo.getRow()).compareTo(date) == 0) {
					getTargetDate = true;
					//因为是排序后的结果，下次不用从头开始
					pointer = k;
					result.add(chartVo);
					break;
				}
			}
			if (!getTargetDate) {
				result.add(new ChartVO(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN).format(date), "0"));
			}
		}
		return result;
	}
}
