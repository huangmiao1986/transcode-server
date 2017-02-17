package com.transcode.server.util;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	private static final char[] bcdLookup = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	// 字符串转字符串
	public static final String bytesToHexStr(byte[] bcd) {
		StringBuffer s = new StringBuffer(bcd.length * 2);

		for (int i = 0; i < bcd.length; i++) {
			s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
			s.append(bcdLookup[bcd[i] & 0x0f]);
		}

		return s.toString();
	}

	public static final byte[] toBytes(String s) {
		byte[] bytes;
		bytes = new byte[s.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}

	public static String getUTF8String(String str) {
		try {
			return new String(str.getBytes("ISO-8859-1"), "UTF-8");
		}// try
		catch (UnsupportedEncodingException e) {
			return null;
		}// catch
	}

	// 兼容iphone emoj类字符, XMLLightweightParser文件解析问题
	public static String proExtraChar(String str) {
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char ch = chars[i];
			if (ch < 0x20 && ch != 0x9 && ch != 0xA && ch != 0xD && ch != 0x0) {
				chars[i] = 0x20;
			}
		}
		return new String(chars);
	}

	public static String filterSymbol(String str) {

		if (str == null || str.length() == 0)
			return "";

		str = proExtraChar(str);

		Pattern p1 = Pattern.compile("&");
		String temp = p1.matcher(str).replaceAll("&amp;");

		Pattern p2 = Pattern.compile(">");
		temp = p2.matcher(temp).replaceAll("&gt;");

		Pattern p3 = Pattern.compile("<");
		temp = p3.matcher(temp).replaceAll("&lt;");

		Pattern p4 = Pattern.compile("\"");
		temp = p4.matcher(temp).replaceAll("&quot;");

		Pattern p5 = Pattern.compile("\'");
		temp = p5.matcher(temp).replaceAll("&apos;");

		return temp;
	}

	public static boolean isNotBlankNullstr(String str) {
		if (str == null) {
			return false;
		} else if ("".equalsIgnoreCase(str.trim())) {
			return false;
		} else if ("null".equalsIgnoreCase(str.trim())) {
			return false;
		}
		return true;
	}

	public static String checkString(String s, String ss) {
		if (s == null) {
			if (ss == null)
				return "";
			return ss;
		} else
			return s;
	}

	public static boolean isNotBlank(String str) {
		if (str == null) {
			return false;
		} else if ("".equalsIgnoreCase(str.trim())) {
			return false;
		} else if ("null".equalsIgnoreCase(str.trim())) {
			return false;
		}
		return true;
	}

	public static boolean isBlank(String str) {
		if (str == null) {
			return true;
		} else if ("".equalsIgnoreCase(str.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断不是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotNumeric(String str) {
		return !isNumeric(str);
	}

	public static String checkNumericString(String s, String ss) {
		if (isBlank(s) || isNotNumeric(s)) {
			if (isBlank(ss) || isNotNumeric(ss))
				return "0";
			return ss;
		} else {
			return s;
		}
	}

	/**
	 * 判断是否UserID超过最大值,预留到Integer.MAX_VALUE=2147483647
	 */
	public static boolean checkMaxID(String userId) {
		final long tpl = Integer.MAX_VALUE;
		long cur = Long.parseLong(userId);
		if (cur < tpl) {
			return true;
		}
		return false;
	}

	/**
	 * 验证邮箱名有效性
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 判断userid是否合法
	 * 
	 * @param userId
	 * @return
	 */
	public static boolean checkUserId(String userId) {
		if (isBlank(userId)) {
			return false;
		}
		// 判断长度,不超过11位
		if (userId.length() > 11 || userId.length() < 5) {
			return false;
		}
		// 判断字符串是否为数字
		return userId.matches("^[0-9]*$");
	}
	
	public static boolean IsNotUserId(String userId) {
		return IsUserId(userId)?false:true;
	}
	public static boolean IsUserId(String userId) {
		if (isBlank(userId)) {
			return false;
		}
		// 判断长度,不超过11位
		if (userId.length() > 11 || userId.length() < 5) {
			return false;
		}
		// 判断字符串是否为数字
		return userId.matches("^[0-9]*$");
	}
	
	public static void main(String[] args) {
		System.out.println((UUID.randomUUID().toString()+System.currentTimeMillis()).length());
	}
}
