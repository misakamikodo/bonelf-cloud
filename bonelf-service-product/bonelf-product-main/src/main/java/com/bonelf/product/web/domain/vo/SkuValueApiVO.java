package com.bonelf.product.web.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Objects;

/**
 * <p>
 * 信息
 * </p>
 * @author bonelf
 * @since 2020/11/13 14:06
 */
@Data
public class SkuValueApiVO {
	/**
	 * 属性
	 */
	private Long skuValueId;
	/**
	 * 属性名称
	 */
	private String skuValueName;
	/**
	 * 是否可用
	 */
	@JsonProperty("isVisible")
	private Boolean visible;
	/**
	 * 置灰原因
	 */
	private String invisibleReason;


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		SkuValueApiVO that = (SkuValueApiVO)o;
		return skuValueId.equals(that.skuValueId) &&
				Objects.equals(skuValueName, that.skuValueName) &&
				Objects.equals(visible, that.visible);
	}

	@Override
	public int hashCode() {
		return Objects.hash(skuValueId, skuValueName, visible);
	}
}
