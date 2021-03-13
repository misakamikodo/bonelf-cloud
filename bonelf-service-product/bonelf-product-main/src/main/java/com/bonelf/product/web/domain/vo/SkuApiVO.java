package com.bonelf.product.web.domain.vo;

import com.bonelf.product.web.domain.entity.Sku;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 库存匹配信息
 * </p>
 * @author bonelf
 * @since 2020/11/13 14:13
 */
@Data
public class SkuApiVO {
	/**
	 * 库存等等可用的skuValue，即不完全匹配的身下没有匹配的SkuValueId的集合
	 */
	private Set<Long> visibleSkuValueIds;
	/**
	 * 不可用的SkuValue和对应不可用的理由
	 */
	private List<SkuValueApiVO> invisibleSku;
	/**
	 * 完全匹配的规格信息 当这个值不为空的时候就显示支付信息并置量Sku信息
	 */
	private Sku chosenSku;
}
