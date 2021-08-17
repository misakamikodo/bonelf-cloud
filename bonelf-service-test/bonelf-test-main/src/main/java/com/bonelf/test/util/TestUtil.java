package com.bonelf.test.util;

import java.util.HashMap;
import java.util.Map;

/**
 * ~~
 * @author ccy
 * @date 2021/8/15 23:30
 */
public class TestUtil {

	public static Map<String, Object> remoteDict(String jsonStr) {
		return new HashMap<String, Object>() {{
			put("test", "testOk");
		}};
	}

	public static String funcDict(String key) {
		return "testOk";
	}
}
