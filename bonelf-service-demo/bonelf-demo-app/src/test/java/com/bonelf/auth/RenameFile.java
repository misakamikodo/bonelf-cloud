package com.bonelf.auth;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * TODO Edit file description there
 * @author bonelf
 * @date 2021-1-21 15:12
 */
public class RenameFile {

	public static void main(String[] args) {
		{
			final String path = "E:\\chenchengyuan\\bonelf\\bonelf-cloud\\bonelf-service-stock";
			final Map<String, String> CHANGE_MAP = new HashMap<String, String>() {{
				put("Demo", "Stock");
				put("demo", "stock");
			}};
			renameFile(path, CHANGE_MAP);
		}
	}

	private static void renameFile(String path, Map<String, String> CHANGE_MAP) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}

		for (File listFile : Objects.requireNonNull(file.listFiles())) {
			renameFile(listFile, CHANGE_MAP);
		}
	}

	private static void renameFile(File file, Map<String, String> map) {
		if (file.isDirectory()) {
			for (File file1 : Objects.requireNonNull(file.listFiles())) {
				renameFile(file1, map);
			}
		}
		map.forEach((from, to) -> {
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
