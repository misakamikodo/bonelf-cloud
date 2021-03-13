/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.bonelf.user.constant;

import com.bonelf.frame.core.constant.BonelfConstant;

public interface CacheConstant {

	/**
	 * 验证码 businessType phone
	 */
	@Deprecated
	String LOGIN_VERIFY_CODE = BonelfConstant.PROJECT_NAME + ":%s:%s";

	String SHOP_GEO =  BonelfConstant.PROJECT_NAME + ":shopGeo";
}
