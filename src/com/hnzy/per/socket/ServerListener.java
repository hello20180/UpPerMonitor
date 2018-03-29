package com.hnzy.per.socket;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hnzy.per.socket.server.ServerSocket;


public class ServerListener implements ServletContextListener{
	 private final static Logger log = LoggerFactory.getLogger(ServerListener.class);
	 private ServerSocket server;
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		server.stopServer();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		server=new ServerSocket();
		boolean flag=server.startServer();
		if (flag) {
			log.info("--服务器Socket已经启动成功！");
		}else{
			log.info("服务器Socket启动失败---");
		}
	}

}
