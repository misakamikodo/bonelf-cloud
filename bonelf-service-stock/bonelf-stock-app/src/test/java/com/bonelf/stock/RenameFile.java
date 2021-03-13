package com.bonelf.stock;

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

	public static void main(String[] args) {
		{
			final String path = "E:\\chenchengyuan\\bonelf\\bonelf-cloud\\bonelf-service-order";
			final Map<String, String> CHANGE_MAP = new HashMap<String, String>() {{
				put("Stock", "Order");
				put("stock", "order");
			}};
			renameFile(path, CHANGE_MAP);
		}
		{
			final String path = "E:\\chenchengyuan\\bonelf\\bonelf-cloud\\bonelf-service-pay";
			final Map<String, String> CHANGE_MAP = new HashMap<String, String>() {{
				put("Stock", "Pay");
				put("stock", "pay");
			}};
			renameFile(path, CHANGE_MAP);
		}
		{
			final String path = "E:\\chenchengyuan\\bonelf\\bonelf-cloud\\bonelf-service-product";
			final Map<String, String> CHANGE_MAP = new HashMap<String, String>() {{
				put("Stock", "Product");
				put("stock", "product");
			}};
			renameFile(path, CHANGE_MAP);
		}
		{
			final String path = "E:\\chenchengyuan\\bonelf\\bonelf-cloud\\bonelf-service-promotion";
			final Map<String, String> CHANGE_MAP = new HashMap<String, String>() {{
				put("Stock", "Promotion");
				put("stock", "promotion");
			}};
			renameFile(path, CHANGE_MAP);
		}
		{
			final String path = "E:\\chenchengyuan\\bonelf\\bonelf-cloud\\bonelf-service-system";
			final Map<String, String> CHANGE_MAP = new HashMap<String, String>() {{
				put("Stock", "System");
				put("stock", "system");
			}};
			renameFile(path, CHANGE_MAP);
		}
		{
			final String path = "E:\\chenchengyuan\\bonelf\\bonelf-cloud\\bonelf-service-search";
			final Map<String, String> CHANGE_MAP = new HashMap<String, String>() {{
				put("Stock", "Search");
				put("stock", "search");
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
