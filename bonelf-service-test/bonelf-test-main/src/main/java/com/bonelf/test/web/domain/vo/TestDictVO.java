package com.bonelf.test.web.domain.vo;

import com.bonelf.frame.core.constant.enums.YesOrNotEnum;
import com.bonelf.frame.core.dict.annotation.*;
import com.bonelf.frame.core.dict.enums.DictType;
import com.bonelf.test.util.TestUtil;
import com.bonelf.test.web.domain.entity.Test;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
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

	@FuncDict(value = TestUtil.class, method = "funcDict")
	private String func = "test";

	private String funcName;

	@RemoteDict(value = "http://test/bonelf/noAuth/remoteDict")
	private String remote = "test";

	private String remoteName;

	private Integer aopDict = 1;

	private String aopDictName;

	@DictWrapper(type = DictType.enums,
			payload = "value=com.bonelf.frame.core.constant.enums.YesOrNotEnum",
			fieldSeq = "test")
	private Map<String, Object> dictWrapper = new HashMap<String, Object>() {{
		put("test", 1);
	}};

	@DictWrappers(
			@DictWrapper(type = DictType.enums,
					payload = "value=com.bonelf.frame.core.constant.enums.YesOrNotEnum",
					fieldSeq = "test")
	)
	private List<Map<String, Object>> dictWrappers = new ArrayList<Map<String, Object>>() {{
		add(new HashMap<String, Object>() {{
			put("test", 1);
		}});
	}};

	@DictField
	private TestDictPropertyVO test = new TestDictPropertyVO();
}
