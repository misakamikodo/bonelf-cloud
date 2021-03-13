package com.bonelf.test.web.domain.vo;

import com.bonelf.frame.core.constant.enums.YesOrNotEnum;
import com.bonelf.frame.core.dict.DbDict;
import com.bonelf.frame.core.dict.DictField;
import com.bonelf.frame.core.dict.EnumDict;
import lombok.Data;

import java.util.List;

/**
 * example:{"cmdId":2,"message":"你好","data":{"hello":"你好","world","0"}}
 * @author bonelf
 */
@Data
public class TestDictVO {
	@DbDict(value = "test", cached = true)
	private String hello = "123";

	private String helloName;

	@EnumDict(YesOrNotEnum.class)
	private Integer world = 1;

	private String worldName;

	private String nihao = null;

	private List<?> shijie = null;

	@DictField
	private TestDictPropertyVO test = new TestDictPropertyVO();
}
