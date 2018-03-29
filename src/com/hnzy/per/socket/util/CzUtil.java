package com.hnzy.per.socket.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CzUtil {

	// 发送 把十六进制数，转换为十进制相加
	public static int ZH(String ja) {
		int jia = 0;
		for (int i = 0; i < ja.length() - 4; i++) {
			int j = Integer.parseInt("" + ja.charAt(i) + "" + ja.charAt(i + 1) + "", 16);
			i++;
			jia = j + jia;
		}
		return jia;
	}

	// 接受 把十六进制数，转换为十进制相加
	public static int FsZh(String ja) {
		int jia = 0;
		for (int i = 0; i < ja.length(); i++) {
			int j = Integer.parseInt("" + ja.charAt(i) + "" + ja.charAt(i + 1) + "", 16);
			i++;
			jia = j + jia;
		}
		return jia;
	}

	// 截取校验数据
	public static String jq(String stringHandler) {
		String jy = null;
		for (int i = 0; i < stringHandler.length() - 1; i++) {
			jy = stringHandler.charAt(stringHandler.length() - 4) + ""
					+ stringHandler.charAt(stringHandler.length() - 3);
		}
		return jy;
	}

	// 和校验
	public static String jyh(String ja) {
		int jia = 0;
		// 把十六进制数，转换为十进制相加
		for (int i = 0; i < ja.length() - 1; i++) {
			int j = Integer.parseInt("" + ja.charAt(i) + "" + ja.charAt(i + 1) + "", 16);
			i++;
			jia = j + jia;
		}
		// 十进制转换为十六进制
		String hex = Integer.toHexString(jia);
		// 截取相加结果后两位
		String je = null;
		for (int j = 0; j < hex.length() - 1; j++) {
			je = hex.charAt(hex.length() - 2) + "" + hex.charAt(hex.length() - 1);
		}
		return je;
	}

	// 将小写字母转换为大写字母
	public static StringBuffer Uppercase(String stringH) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < stringH.length(); i++) {
			char c = stringH.charAt(i);
			if (!Character.isDigit(c)) {
				sb.append(Character.toUpperCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb;
	}

	// 时间相减
	public static String getSubtract(String minTime, String maxTime) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startTime = format.parse(minTime);
			Date endTime = format.parse(maxTime);
			Calendar cmax = Calendar.getInstance();
			cmax.setTime(endTime);
			Calendar cmin = Calendar.getInstance();
			cmin.setTime(startTime);
			if (cmax.after(cmin)) {
				long passed = cmax.getTimeInMillis() - cmin.getTimeInMillis();
				int hour = (int) (passed / (1000 * 60 * 60));
				int min = (int) ((passed % (1000 * 60 * 60)) / (1000 * 60));
				int second = (int) (((passed % (1000 * 60 * 60)) % (1000 * 60)) / 1000);
				String result = (hour < 9 ? "0" + hour : hour + "");
				result += ":" + (min < 9 ? "0" + min : min + "");
				result += ":" + (second < 9 ? "0" + second : second + "");

				return result;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 将时间转换为秒
	public static int transform(String time) {
		String temp[] = time.split(":");
		int hours = Integer.valueOf(temp[0]);
		int minutes = Integer.valueOf(temp[1]);
		int seconds = Integer.valueOf(temp[2]);
		int allSeconds = hours * 60 * 60 + minutes * 60 + seconds;
		return allSeconds;
	}

	private static int parse(char c) {
		if (c >= 'a')
			return (c - 'a' + 10) & 0x0f;
		if (c >= 'A')
			return (c - 'A' + 10) & 0x0f;
		return (c - '0') & 0x0f;
	}

	// 解码
	public static byte[] jm(String mString) {
		byte[] b = new byte[mString.length() / 2];
		int j = 0;
		for (int i = 0; i < b.length; i++) {
			char c0 = mString.charAt(j++);
			char c1 = mString.charAt(j++);
			b[i] = (byte) ((parse(c0) << 4) | parse(c1));

		}
		return b;
	}

	// 截取效验数据
	public static String getJy(String stringHandler) {
		String jy = null;
		for (int i = 0; i < stringHandler.length() - 1; i++) {
			jy = stringHandler.charAt(stringHandler.length() - 4) + ""
					+ stringHandler.charAt(stringHandler.length() - 3);
		}
		return jy;
	}

	public static String getTimerJe(String stringHandler) {
		// 判断和校验
		// 把十六进制数，转换为十进制相加
		int jia = CzUtil.FsZh(stringHandler);
		// 十进制转换为十六进制
		String hex = Integer.toHexString(jia);

		StringBuffer stringBuffer = new StringBuffer();
		if (hex != null) {
			for (int i = 0; i < hex.length(); i++) {
				char c = hex.charAt(i);
				if (!Character.isDigit(c)) {
					stringBuffer.append(Character.toUpperCase(c));
				} else {
					stringBuffer.append(c);
				}
			}
		}
		String sH = stringBuffer.toString();
		// 截取相加结果后两位
		String je = null;
		je = sH.charAt(sH.length() - 2) + "" + sH.charAt(sH.length() - 1);
		return je;
	}

	public static String getJe(String stringHandler) {
		// 判断和校验
		// 把十六进制数，转换为十进制相加
		int jia = CzUtil.ZH(stringHandler);
		// 十进制转换为十六进制
		String hex = Integer.toHexString(jia);

		StringBuffer stringBuffer = new StringBuffer();
		if (hex != null) {
			for (int i = 0; i < hex.length(); i++) {
				char c = hex.charAt(i);
				if (!Character.isDigit(c)) {
					stringBuffer.append(Character.toUpperCase(c));
				} else {
					stringBuffer.append(c);
				}
			}
		}
		String sH = stringBuffer.toString();
		// 截取相加结果后两位
		String je = null;
		je = sH.charAt(sH.length() - 2) + "" + sH.charAt(sH.length() - 1);
		return je;
	}
}
