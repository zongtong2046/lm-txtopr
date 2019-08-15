package cn.lmlxj.txtopr.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringUtils.
 * 
 * @author maben586@163.com
 * @version StringUtils V1.0 2018-2-2 下午3:53:17
 */
public final class StringUtils {

	public static boolean equals(String a, String b) {
		if (a == null) {
			return b == null;
		}
		return a.equals(b);
	}

	public static boolean equalsIgnoreCase(String a, String b) {
		if (a == null) {
			return b == null;
		}
		return a.equalsIgnoreCase(b);
	}

	public static boolean isEmpty(String value) {
		if (value == null || value.trim().length() == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}

	public static String format(String tmpl, Object... args) {
		if (args == null || args.length < 1) {
			return tmpl;
		}
		for (int i = 0; i < args.length; i++) {
			tmpl = tmpl.replace("{" + i + "}", args[i] != null ? args[i].toString() : "null");
		}
		return tmpl;
	}

	@SuppressWarnings("rawtypes")
	public static String format(String tmpl, Map map) {
		if (map == null || map.size() < 1) {
			return tmpl;
		}
		Pattern pattern = Pattern.compile("\\{([\\s\\S]+?)\\}");
		Matcher matcher = pattern.matcher(tmpl);
		while (matcher.find()) {
			if (matcher.groupCount() > 0) {
				String key = matcher.group(1);
				if (map.get(key) != null) {
					tmpl = tmpl.replace("{" + key + "}", map.get(key).toString());
				}
			}
		}
		return tmpl;
	}
}