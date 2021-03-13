package com.bonelf.user;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * TODO Edit file description there
 * @author ccy
 * @date 2021-1-21 15:12
 */
public class RenameFile {
	private static final String path = "E:\\chenchengyuan\\temp";
	private static final Map<String, String> CHANGE_MAP = new HashMap<String, String>() {{
		put("User", "Test");
		put("user", "test");
	}};

	public static void main(String[] args) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		for (File listFile : Objects.requireNonNull(file.listFiles())) {
			renameFile(listFile);
		}
	}

	private static void renameFile(File file) {
		if (file.isDirectory()) {
			for (File file1 : Objects.requireNonNull(file.listFiles())) {
				renameFile(file1);
			}
		}
		CHANGE_MAP.forEach((from, to) -> {
			if (file.getName().contains(from)) {
				File destFile = new File(replaceLast(file.getPath(), from, to));
				System.out.println(file.renameTo(destFile));
			}
		});
	}

	public static String replaceLast(String text, String regex, String replacement) {
		return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
	}

}
