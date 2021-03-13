package com.bonelf.system.web.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * <p>
 * 看需求增加商品编号
 * </p>
 * @author Chenyuan
 * @since 2020/12/1 10:45
 */
@ApiModel(value = "点击量表")
@Data
@NoArgsConstructor
@TableName(value = "bnf_spu_click")
public class SpuClick {
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "")
	private Long id;

	@TableField(value = "click_date")
	@ApiModelProperty(value = "访问日期")
	private LocalDate clickDate;

	@TableField(value = "click_count")
	@ApiModelProperty(value = "访问数量")
	private Integer clickCount;
}