/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.pay.web.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 折线图表格
 * </p>
 * @author bonelf
 * @since 2020/10/23 15:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChartVO {
	/**
	 * 行
	 */
	private String row;

	/**
	 * 列
	 */
	private String col;
}

