package com.bonelf.test.web.domain.vo;

import com.bonelf.frame.core.constant.enums.YesOrNotEnum;
import com.bonelf.frame.core.dict.enums.DbDict;
import com.bonelf.frame.core.dict.enums.DictField;
import com.bonelf.frame.core.dict.enums.EnumDict;
import com.bonelf.frame.core.dict.enums.TableDict;
import com.bonelf.test.web.domain.entity.Test;
import lombok.Data;

import java.util.List;

/**
 * example:{"cmdId":2,"message":"你好","data":{"hello":"你好","world","0"}}
 * @author bonelf
 */
@Data
public class TestDictVO {
	@DbDict(value = "test", cached = false)
	private String hello = "123";

	private String helloName;

	@DbDict(value = "test", cached = true)
	private String helloCached = "123";

	private String helloCachedName;

	@EnumDict(YesOrNotEnum.class)
	private Integer world = 1;

	private String worldName;

	private String nihao = null;

	private List<?> shijie = null;

	@TableDict(Test.class)
	private Integer table = 1;

	private String tableName;

	@DictField
	private TestDictPropertyVO test = new TestDictPropertyVO();
}
