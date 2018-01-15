package com.xuanli.oepcms.util;

import java.util.UUID;

public class StringUtil {
	public static boolean isNotEmpty(String str) {
		if (null != str && str.length() > 0 && !"null".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEmpty(String str) {
		if (null != str && str.trim().length() > 0 && !"null".equals(str) && !"undefined".equals(str)) {
			return false;
		} else {
			return true;
		}
	}

	public static String getEncoding(String str) {
		if (StringUtil.isEmpty(str))
			return str;
		String[] encodeArr = new String[] { "ISO-8859-1", "GB2312", "GBK", "UTF-8", "UTF-16" };
		try {
			for (String encode : encodeArr) {
				if (str.equals(new String(str.getBytes(encode), encode))) {
					return encode;
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
