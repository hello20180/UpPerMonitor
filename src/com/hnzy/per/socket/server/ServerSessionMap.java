package com.hnzy.per.socket.server;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.mina.core.session.IoSession;

import com.hnzy.per.socket.util.MapUtils;


public class ServerSessionMap {

	private static ServerSessionMap sessionMap = null;

	private Map<String, IoSession> map = new HashMap<String, IoSession>();

	private ServerSessionMap() {
	}

	public static ServerSessionMap getInstance() { 
		if (sessionMap == null) {
			synchronized (ServerSessionMap.class) {    
	               if (sessionMap == null) {    
	            	   sessionMap = new ServerSessionMap();   
	               }    
	            }    
		}
		return sessionMap;
	}

	public void add(String clientIp, IoSession session) {
		map.put(clientIp, session);
	}

	public IoSession get(String clientIp) {
		return map.get(clientIp);
	}

	@SuppressWarnings("rawtypes")
	public void remove(IoSession session) {
		Map<Object,Object> keepMaps=new HashMap<>();
		for(Object key:keepMaps.keySet()){
			if (((Entry) key).getValue() == session) {
			keepMaps.remove(key);
			}
		}  
	}
	
	/**
	 * @Description: 发送消息到客户端
	 */
	public boolean sendMessage(String[] keys, byte[] message) {
		for (String key : keys) {
			IoSession session = get(key);
			if (session == null) {
				return false;
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			MapUtils.getMapUtils().add("time",df.format(new Date()));
			System.out.println("---------time--"+MapUtils.getMapUtils().get("time"));
			session.write(message);
		}
		return true;
	}
}
