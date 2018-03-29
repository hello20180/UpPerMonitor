package com.hnzy.per.socket.util;

import java.util.Collection;
import java.util.Queue;

public class Utils {
	/**
	 * 判断对象是否为空<br />
	 * 不为空返回true，为空返回false
	 * 
	 * @param o
	 * @return
	 */
	public static boolean notEmpty(Object o) {
		boolean notEmpty = false;
		if (o instanceof String) {
			String s = (String) o;
			if (s != null && !"".equals(s) && !"undefined".equals(s) && !"null".equals(s)) {
				notEmpty = true;
			}
		} else if (o instanceof Collection) {
			Collection<?> c = (Collection<?>) o;
			if (c != null && c.size() > 0) {
				notEmpty = true;
			}
		} else if (o instanceof Object[]) {
			Object[] arr = (Object[]) o;
			if (arr != null && arr.length > 0) {
				notEmpty = true;
			}
		} else if (o != null) {
			return true;
		}
		return notEmpty;
	}

	/**
	 * byte[]转换成16进制字符串
	 * 
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 字符串转换为16进制字符串
	 * 
	 */
	public static String stringToHexString(String strPart) {
		String hexString = "";
		for (int i = 0; i < strPart.length(); i++) {
			int ch = (int) strPart.charAt(i);
			String strHex = Integer.toHexString(ch);
			hexString = hexString + strHex;
		}
		return hexString;
	}
	
	/**
	 * 16进制字符串转换为byte[]
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase().replace(" ", "");
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}
	
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
}
