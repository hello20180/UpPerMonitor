package com.hnzy.per.socket.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {
	
	private static MapUtils MapUtils = null;

	private Map<Object, String> map = new HashMap<Object, String>();

	private MapUtils() {
	}

	public static MapUtils getMapUtils() { 
		if (MapUtils == null) {
	            	   MapUtils = new MapUtils();   
		}
		return MapUtils;
	}
	public void add(String key, String value) {
		map.put(key, value);
	}
	public String get(String key) {
		return map.get(key);
	}
}
