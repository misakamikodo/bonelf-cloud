package com.bonelf.user.web.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("门店添加")
public class ShopDTO {
	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String shopName;
	/**
	 * 纬度
	 */
	@ApiModelProperty("纬度")
	private BigDecimal lat;

	/**
	 * 经度
	 */
	@ApiModelProperty(value = "经度")
	private BigDecimal lng;
}
