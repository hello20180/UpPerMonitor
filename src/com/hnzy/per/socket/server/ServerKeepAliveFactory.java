package com.hnzy.per.socket.server;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hnzy.per.socket.util.Constants;


/**
 * 被动心跳机制
 */
public class ServerKeepAliveFactory implements KeepAliveMessageFactory {

	private final static Logger log = LoggerFactory.getLogger(ServerKeepAliveFactory.class);

	/*
     * 
     *返回给客户端的心跳包数据 return 返回结果才是客户端收到的心跳包数据
     */
	@Override
	public Object getRequest(IoSession session) {
    return Constants.HEARTBEAT_REQUEST.getBytes();
	}

    /*
     * 
     *"接受到的客户端数据包"
     */
	@Override
	public Object getResponse(IoSession session, Object message) {
		
		return Constants.HEARTBEAT_RESPONSE.getBytes();
	}

	 /*
     * 
     * @see
     * 判断是否是客户端发送来的的心跳包此判断影响 KeepAliveRequestTimeoutHandler实现类
     * 判断是否心跳包发送超时
     */
	@Override
	public boolean isRequest(IoSession session, Object message) {

		if (message.equals(Constants.HEARTBEAT_REQUEST)) {
			log.info("服务器收到{}的心跳请求!", session.getRemoteAddress());
			return true;
		} else {
			return false;
		}
	}

    /*
     * 
           判断发送信息是否是心跳数据包此判断影响 KeepAliveRequestTimeoutHandler实现类
     * 判断是否心跳包发送超时
     */
	@Override
	public boolean isResponse(IoSession session, Object message) {

		return false;
	}

}
