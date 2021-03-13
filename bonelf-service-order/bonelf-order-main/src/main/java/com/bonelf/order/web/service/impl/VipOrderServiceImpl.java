/*
 * Copyright (c) 2020. Bonelf.
 */

package com.bonelf.order.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bonelf.order.web.domain.entity.ProductOrder;
import com.bonelf.order.web.mapper.ProductOrderMapper;
import com.bonelf.order.web.service.ProductOrderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bonelf
 * @since 2020-12-01 16:27
 */
@Service
public class VipOrderServiceImpl extends ServiceImpl<ProductOrderMapper, ProductOrder> implements ProductOrderService {

	/**
	 * 订单超时
	 * @param orderId
	 */
	@Override
	public void orderCancel(Long orderId) {
		//Feign查询支付服务 确定支付状态未支付
		//关闭订单
	}
}

