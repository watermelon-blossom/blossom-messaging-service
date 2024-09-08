package com.watermelon.chat.utils;

public class UrlParsingUtil {
	public static String getRoomOfUrl(String url) {
		int pos = url.indexOf("/room/");
		if (pos == -1)
			return null;
		int start = pos + 6;
		int end = url.indexOf('?', start);
		if (end == -1)
			end = url.length();
		return url.substring(start, end);
	}
}