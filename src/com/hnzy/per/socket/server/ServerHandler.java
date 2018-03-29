package com.hnzy.per.socket.server;

import java.net.SocketAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hnzy.per.socket.util.CzUtil;
import com.hnzy.per.socket.util.DatabaseUtil;
import com.hnzy.per.socket.util.MapUtils;
import com.hnzy.per.socket.util.Utils;

public class ServerHandler extends IoHandlerAdapter
{
	PreparedStatement ps;
	ResultSet rst;
	int rs = 0;

	PreparedStatement Sqlps;
	ResultSet Sqlrst;
	int Sqlrs = 0;

	private final static Logger log = LoggerFactory.getLogger(ServerHandler.class);
	ServerSessionMap sessionMap = ServerSessionMap.getInstance();
	// 日志文件
	private static Log logs = LogFactory.getLog(ServerHandler.class);

	/**
	 * 当一个新客户端连接后触发此方法
	 * 
	 */
	@Override
	public void sessionCreated(IoSession session)
	{
		log.info("服务器创建{}链路成功!", session.getRemoteAddress());
	}

	/**
	 * 当连接打开时调用
	 */
	@Override
	public void sessionOpened(IoSession session) throws Exception
	{
		log.info("服务器打开了{}的连接，Session ID为{}", session.getRemoteAddress(), session.getId());
		SocketAddress remoteAddress = (SocketAddress) session.getRemoteAddress();
		String clientIp = remoteAddress.toString();
		sessionMap.add(clientIp, session);
		int port = 0;
		String Ip = null;
		String id = null;
		DatabaseUtil dbUtil = DatabaseUtil.getInstance();
		Connection connc = dbUtil.getConnection();

		// 获取区管IP
		for (int i = 0; i < clientIp.length(); i++)
		{
			String[] ipPortString = clientIp.split(":");
			String iP = ipPortString[0];

			String[] ip = iP.split("/");
			port = Integer.valueOf(ipPortString[1]);
			Ip = ip[1];
		}
		SimpleDateFormat Sdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 获取发送的时间
		String time = Sdate.format(new Date());

		// 查找区管ID
		String sqlcx = "select q.QgId from qginfo q,xqinfo x where q.XqId=x.XqId and x.IP='" + Ip + "'";
		ps = connc.prepareStatement(sqlcx);
		rst = ps.executeQuery();
		int col = rst.getMetaData().getColumnCount();
		while (rst.next())
		{
			id = rst.getString("QgId");

			// 如果区管ID不为空
			if (id != null)
			{
				String sql = "update qginfo set RecordTime='" + time + "' where QgId='" + id + "'";
				ps = connc.prepareStatement(sql);
				rs = ps.executeUpdate();
			}
		}
		ps.close();
		rst.close();
		connc.close();
	}

	/**
	 * 当实现IOHandlerer的类抛出异常时调用
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception
	{
		cause.printStackTrace();
		log.info("{}出现异常{}", session.getRemoteAddress(), cause);
		sessionMap.remove(session);
	}

	/**
	 * 当接受了一个消息时调用
	 */
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception
	{
		byte[] base = (byte[]) message;
		String stringMR = Utils.bytesToHexString(base);
		String ml = null;
		for (int i = 0; i < stringMR.length() - 1; i++)
		{
			ml = stringMR.substring(4, 6);
		}
		// 传感器接收温度的方法
		if (ml.equals("a0"))
		{
			cgq(base, session);

		}

		// 区管接收心跳包的方法
		if (ml.equals("a1"))
		{
			qg(base, session);
		}

		// 读取区管工作模式
		if (ml.equals("a3"))
		{
			duqgms(base, session);
		}

		// 修改区管工作模式
		if (ml.equals("a5"))
		{
			updateqgms(base, session);
		}

		// 修改区管433无线地址
		if (ml.equals("a7"))
		{
			updateqg433(base, session);
		}
		// 修改区管433无线地址
		if (ml.equals("a9"))
		{
			updateqgId(base, session);
		}

	}

	/**
	 * 传感器的温度=====更新和插入
	 */
	public void cgq(byte[] base, IoSession session) throws SQLException
	{
		log.info("服务器打开了{}的连接，Session ID为{}", session.getRemoteAddress());
		SocketAddress remoteAddress = (SocketAddress) session.getRemoteAddress();
		String clientIp = remoteAddress.toString();
		sessionMap.add(clientIp, session);
		int port = 0;
		String Ip = null;
		DatabaseUtil dbUtil = DatabaseUtil.getInstance();
		Connection connc = dbUtil.getConnection();
		// 获取区管IP
		for (int i = 0; i < clientIp.length(); i++)
		{
			String[] ipPortString = clientIp.split(":");
			String iP = ipPortString[0];
			String[] ip = iP.split("/");
			port = Integer.valueOf(ipPortString[1]);
			Ip = ip[1];
		}
		// 接收数据
		String stringH = Utils.bytesToHexString(base);
		// 转换为大写
		String stringHandler = CzUtil.Uppercase(stringH).toString();
		// 截取效验数据
		String jy = CzUtil.getJy(stringHandler);
		// 判断开始和结束
		String start = null;
		String end = null;
		start = stringHandler.charAt(0) + "" + stringHandler.charAt(1);
		end = stringHandler.charAt(stringHandler.length() - 2) + "" + stringHandler.charAt(stringHandler.length() - 1);
		// 判断和校验
		String je = CzUtil.getJe(stringHandler);
		if (start.equals("F0") && end.equals("FF") && je.equals("" + jy + ""))
		{
			String Id = stringHandler.substring(6, 10);
			// 把十六进制数，转换为十进制
			int cgqId = Integer.parseInt("" + Id + "", 16);
			String WD = stringHandler.substring(10, 12);
			// 把十六进制数，转换为十进制
			int wd = Integer.parseInt("" + WD + "", 16);
			String DL = stringHandler.substring(14, 16);
			// 把十六进制数，转换为十进制
			int dL = Integer.parseInt("" + DL + "", 16);
			SimpleDateFormat Sdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 获取发送的时间
			String time = Sdate.format(new Date());
			String xxip = null;
			String xqip = "select IP from xqinfo where XqName=(select XqName from cgqinfo where CgqId='" + cgqId + "')";
			ps = connc.prepareStatement(xqip);
			rst = ps.executeQuery();
			int col = rst.getMetaData().getColumnCount();
			while (rst.next())
			{
				xxip = rst.getString("IP");
				System.out.println("xxid++++=" + xxip);
				System.out.println("Ip=======" + Ip);
				// 如果小区ID相等就执行
				if (Ip.equals(xxip))
				{
					// 更新
					String Upsql = "update cgqinfo set WD='" + wd + "',DL='" + dL + "',RecordTime='" + time
							+ "' where CgqId='" + cgqId + "'";
					ps = connc.prepareStatement(Upsql);
					rs = ps.executeUpdate();
					// 插入
					String Insql = "insert into historycgq (CgqId,WD,DL,RecordTime)values ('" + cgqId + "','" + wd
							+ "'," + "'" + dL + "','" + time + "')";
					ps = connc.prepareStatement(Insql);
					ps.execute();
					System.out.println("成功");
				}
			}
			connc.close();
		}
	}

	/**
	 * 区管状态在线时间
	 */
	public void qg(byte[] base, IoSession session) throws SQLException
	{
		log.info("服务器打开了{}的连接，Session ID为{}", session.getRemoteAddress());
		SocketAddress remoteAddress = (SocketAddress) session.getRemoteAddress();
		String clientIp = remoteAddress.toString();
		sessionMap.add(clientIp, session);
		int port = 0;
		String Ip = null;
		DatabaseUtil dbUtil = DatabaseUtil.getInstance();
		Connection connc = dbUtil.getConnection();
		// 获取区管IP
		for (int i = 0; i < clientIp.length(); i++)
		{
			String[] ipPortString = clientIp.split(":");
			String iP = ipPortString[0];

			String[] ip = iP.split("/");
			port = Integer.valueOf(ipPortString[1]);
			Ip = ip[1];
		}
		// 接收数据
		String stringH = Utils.bytesToHexString(base);
		// 转换为大写
		String stringHandler = CzUtil.Uppercase(stringH).toString();
		// 截取效验数据
		String jy = CzUtil.getJy(stringHandler);
		// 判断开始和结束
		String start = null;
		String end = null;
		start = stringHandler.charAt(0) + "" + stringHandler.charAt(1);
		end = stringHandler.charAt(stringHandler.length() - 2) + "" + stringHandler.charAt(stringHandler.length() - 1);
		// 判断和校验
		String je = CzUtil.getJe(stringHandler);
		String qgI = stringHandler.substring(6, 10);
		// 把十六进制数，转换为十进制
		int qgId = Integer.parseInt("" + qgI + "", 16);
		String wd = stringHandler.substring(10, 12);
		int swwd = Integer.parseInt("" + wd + "", 16);
		SimpleDateFormat Sdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 获取发送的时间
		String time = Sdate.format(new Date());
		
		System.out.println("发送的时间======"+time);
		System.out.println("je======"+je);
		System.out.println("jy======"+jy);
		if (start.equals("F0") && end.equals("FF") && je.equals("" + jy + ""))
		{
			System.out.println("字头======"+start);
			System.out.println("字尾======"+end);
			System.out.println("qgId======"+qgId);
			String xxip = null;
			String xqip = "select XqId,IP from xqinfo  where XqId=(select XqId from qginfo where QgId='" + qgId + "')";
			ps = connc.prepareStatement(xqip);
			rst = ps.executeQuery();
			int col = rst.getMetaData().getColumnCount();
			while (rst.next())
			{
				xxip = rst.getString("IP");
				System.out.println("xxid++++=" + xxip);
				System.out.println("Ip=======" + Ip);
				// 如果小区ID相等就执行
				if (Ip.equals(xxip))
				{
					// 更新
					String sql = "update qginfo set RecordTime='" + time + "',SWWD='" + swwd + "' where QgId='" + qgId
							+ "'";
					ps = connc.prepareStatement(sql);
					rs = ps.executeUpdate();
					System.out.println("成功");
				}
			}
		}
		connc.close();
	}

	/**
	 * 读取区管工作模式
	 */
	public void duqgms(byte[] base, IoSession session) throws SQLException, ClassNotFoundException
	{
		log.info("服务器打开了{}的连接，Session ID为{}", session.getRemoteAddress());
		SocketAddress remoteAddress = (SocketAddress) session.getRemoteAddress();
		String clientIp = remoteAddress.toString();
		sessionMap.add(clientIp, session);
		int port = 0;
		String Ip = null;
		DatabaseUtil dbUtil = DatabaseUtil.getInstance();
		Connection connc = dbUtil.getConnection();
		// 获取区管IP
		for (int i = 0; i < clientIp.length(); i++)
		{
			String[] ipPortString = clientIp.split(":");
			String iP = ipPortString[0];

			String[] ip = iP.split("/");
			port = Integer.valueOf(ipPortString[1]);
			Ip = ip[1];
		}

		logs.info("读取区管工作模式接收数据：" + Utils.bytesToHexString(base));
		int sj = 0;
		// 接收数据
		String stringH = Utils.bytesToHexString(base);
//		SimpleDateFormat Sdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		// 获取发送的时间
//
//		 String fTime = MapUtils.getMapUtils().get("time");
//		String jTime = Sdate.format(new Date());
//		 String sumTime = CzUtil.getSubtract(fTime, jTime);
//		 if (sumTime == null)
//		 {
//		 sumTime = "00:00:01";
//		 }
//		 if (sumTime != null)
//		 {
//		 sj = CzUtil.transform(sumTime);
//		 System.out.println("-----"+sj);
//		 // 判断时间是否超时
//		 if (sj < 3)
//		 {
//		 if (stringH != null && jTime != null)
//		 {
		 if (stringH != null)
		 {
		// 转换为大写
		String stringHandler = CzUtil.Uppercase(stringH).toString();
		// 截取效验数据
		String jy = CzUtil.getJy(stringHandler);
		// 判断开始和结束
		String start = null;
		String end = null;
		start = stringHandler.charAt(0) + "" + stringHandler.charAt(1);
		end = stringHandler.charAt(stringHandler.length() - 2) + "" + stringHandler.charAt(stringHandler.length() - 1);
		// 截取区管ID
		String sub = stringHandler.substring(6, 10);
		// 把十六进制数，转换为十进制
		int qgID = Integer.parseInt("" + sub + "", 16);

		// 截取333ID
		String Ad = stringHandler.substring(10, 12);
		// 把十六进制数，转换为十进制
		int Ad433 = Integer.parseInt("" + Ad + "", 16);
		// 截取333ID
		String Ad1 = stringHandler.substring(12, 14);
		// 把十六进制数，转换为十进制
		int Ad4331 = Integer.parseInt("" + Ad1 + "", 16);
		String Ad3 = algorismToHEXString(Ad4331,2);
		String wxAd=Ad433+Ad3;
		
		
		String je = CzUtil.getJe(stringHandler);
		String xxip = null;
		String xqip = "select XqId,IP from xqinfo  where XqId=(select XqId from qginfo where QgId='" + qgID + "')";
		ps = connc.prepareStatement(xqip);
		rst = ps.executeQuery();
		int col = rst.getMetaData().getColumnCount();
		while (rst.next())
		{
			xxip = rst.getString("IP");
			System.out.println("xxid++++=" + xxip);
			System.out.println("Ip=======" + Ip);
			// 如果小区ID相等就执行
			if (Ip.equals(xxip))
			{
				if (start.equals("F0") && end.equals("FF") && je.equals("" + jy + ""))
				{
					// 截取模式
					String ms = stringHandler.substring(14, 16);
					if (ms.equals("0E"))
					{
						MapUtils.getMapUtils().add("ms", "数据收发传输模式");
						logs.info("读取0e：" + "0E");
						logs.info("读取Ad433：" + wxAd);
					} 
					if (ms.equals("E0"))
					{
						MapUtils.getMapUtils().add("ms", "测距模式");
						logs.info("读取E0：" + "E0");
						logs.info("读取Ad433：" + wxAd);
					}
					logs.info("读取区管工作模式成功：" + stringHandler);
					logs.info("读取区管Ad433成功：" + wxAd);
					MapUtils.getMapUtils().add("suc", "success");
					MapUtils.getMapUtils().add("qgID", "" + qgID + "");
					MapUtils.getMapUtils().add("Ad433", "" + wxAd + "");
					
				} else
				{
					logs.info("读取区管工作模式失败");
					logs.info("读取区管Ad433失败");
					MapUtils.getMapUtils().add("sb", "fail");
					MapUtils.getMapUtils().add("qgID", "" + qgID + "");
					MapUtils.getMapUtils().add("Ad433", "" + wxAd + "");
					
				}
			} else
			{
				logs.info("读取区管工作模式失败");
				logs.info("读取区管Ad433失败");
				MapUtils.getMapUtils().add("sb", "fail");
				MapUtils.getMapUtils().add("qgID", "" + qgID + "");
				MapUtils.getMapUtils().add("Ad433", "" + wxAd + "");
				
			}
		}
		ps.close();
		connc.close();
		 }
//		 } else
//		 {
//		 logs.info("读取区管工作模式超时");
//		 MapUtils.getMapUtils().add("cs", "超时");
//		 }
//		 }
	}

	/**
	 * 修改区管工作模式
	 */
	public void updateqgms(byte[] base, IoSession session) throws SQLException, ClassNotFoundException
	{
		log.info("服务器打开了{}的连接，Session ID为{}", session.getRemoteAddress());
		SocketAddress remoteAddress = (SocketAddress) session.getRemoteAddress();
		String clientIp = remoteAddress.toString();
		sessionMap.add(clientIp, session);
		int port = 0;
		String Ip = null;
		DatabaseUtil dbUtil = DatabaseUtil.getInstance();
		Connection connc = dbUtil.getConnection();
		// 获取区管IP
		for (int i = 0; i < clientIp.length(); i++)
		{
			String[] ipPortString = clientIp.split(":");
			String iP = ipPortString[0];

			String[] ip = iP.split("/");
			port = Integer.valueOf(ipPortString[1]);
			Ip = ip[1];
		}

		logs.info("执行修改工作模式指令接收数据：" + Utils.bytesToHexString(base));
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		// 获取发送的时间
//		String fTime = MapUtils.getMapUtils().get("time");
//		String jTime = df.format(new Date());
//		int sj = 0;
//		String sumTime = CzUtil.getSubtract(fTime, jTime);
//		if (sumTime == null)
//		{
//			sumTime = "00:00:01";
//		}
//		if (sumTime != null)
//		{
//			sj = CzUtil.transform(sumTime);
//			// 判断时间是否超时
//			if (sj < 3)
//			{
				// 转换为十六进制
				String stringH = Utils.bytesToHexString(base);
				if (stringH != null)
				{
					String stringHandler = CzUtil.Uppercase(stringH).toString();
					// 截取效验数据
					String jy = CzUtil.getJy(stringHandler);
					// 判断开始和结束
					String start = null;
					String end = null;
					start = stringHandler.charAt(0) + "" + stringHandler.charAt(1);
					end = stringHandler.charAt(stringHandler.length() - 2) + ""
							+ stringHandler.charAt(stringHandler.length() - 1);
					String je = CzUtil.getJe(stringHandler);
					String string = MapUtils.getMapUtils().get("param");

					if (string != null)
					{
						// 截取状态
						String Status = stringHandler.substring(stringHandler.length() - 6, stringHandler.length() - 4);
						// 截取区管ID
						String sub = stringHandler.substring(6, 10);
						// 把十六进制数，转换为十进制
						int qgId = Integer.parseInt("" + sub + "", 16);
						String xxip = null;
						String xqip = "select XqId,IP from xqinfo where XqId=(select XqId from qginfo where QgId='"
								+ qgId + "')";
						ps = connc.prepareStatement(xqip);
						rst = ps.executeQuery();
						int col = rst.getMetaData().getColumnCount();
						while (rst.next())
						{
							xxip = rst.getString("IP");
							System.out.println("xxid++++=" + xxip);
							System.out.println("Ip=======" + Ip);
							// 如果小区ID相等就执行
							if (Ip.equals(xxip))
							{
								if (start.equals("F0") && end.equals("FF") && je.equals("" + jy + ""))
								{
									if (string.equals("sfms"))
									{
										logs.info("修改为收发数据模式：" + stringHandler);
										if (Status.equals("0E"))
										{
											String sql = "update qginfo set MS='收发数据模式' where QgId='" + qgId + "'";
											ps = connc.prepareStatement(sql);
											rs = ps.executeUpdate();
											logs.info("修改是否成功====" + rs);
											if (rs == 1)
											{
												MapUtils.getMapUtils().add("sf", "1");
												MapUtils.getMapUtils().add("sfms", "success");
											} else
											{
												MapUtils.getMapUtils().add("sb", "fail");
											}
											ps.close();
											MapUtils.getMapUtils().add("sub", "" + qgId + "");
											MapUtils.getMapUtils().add("Status", "收发数据模式");
											logs.info("修改工作模式为收发数据模式成功");
										} else
										{
											MapUtils.getMapUtils().add("sub", "" + qgId + "");
											MapUtils.getMapUtils().add("sb", "fail");
											logs.info("修改工作模式为收发数据模式失败");
										}

									}
									if (string.equals("cjms"))
									{
										logs.info("修改为测距模式：" + stringHandler);
										if (Status.equals("E0"))
										{
											String sql = "update qginfo set MS='测距模式' where QgId='" + qgId + "'";
											ps = connc.prepareStatement(sql);
											rs = ps.executeUpdate();
											if (rs == 1)
											{
												MapUtils.getMapUtils().add("cj", "1");
												MapUtils.getMapUtils().add("cjms", "success");
											} else
											{
												MapUtils.getMapUtils().add("sb", "fail");
											}
											ps.close();
											MapUtils.getMapUtils().add("sub", "" + qgId + "");
											MapUtils.getMapUtils().add("Status", "测距模式");
											logs.info("修改是否成功====" + rs);
											logs.info("修改工作模式为测距模式成功");
										} else
										{
											MapUtils.getMapUtils().add("sub", "" + qgId + "");
											MapUtils.getMapUtils().add("sb", "fail");
											logs.info("修改工作模式为测距模式失败");
										}
									}
								} else
								{
									MapUtils.getMapUtils().add("sb", "fail");
									logs.info("修改工作模式失败");
								}
							}
						}
						connc.close();
					}else
				{
					MapUtils.getMapUtils().add("sb", "fail");
					logs.info("修改工作模式失败");
				}
			} else
			{
				MapUtils.getMapUtils().add("sb", "fail");
				logs.info("修改工作模式失败");
			}
//		} else
//		{
//			MapUtils.getMapUtils().add("cs", "超时");
//			logs.info("超时");
//		}
//	}

	}

	/**
	 * 修改区管433无线地址
	 */
	public void updateqg433(byte[] base, IoSession session) throws SQLException, ClassNotFoundException
	{
		log.info("服务器打开了{}的连接，Session ID为{}", session.getRemoteAddress());
		SocketAddress remoteAddress = (SocketAddress) session.getRemoteAddress();
		String clientIp = remoteAddress.toString();
		sessionMap.add(clientIp, session);
		int port = 0;
		String Ip = null;
		DatabaseUtil dbUtil = DatabaseUtil.getInstance();
		Connection connc = dbUtil.getConnection();
		// 获取区管IP
		for (int i = 0; i < clientIp.length(); i++)
		{
			String[] ipPortString = clientIp.split(":");
			String iP = ipPortString[0];

			String[] ip = iP.split("/");
			port = Integer.valueOf(ipPortString[1]);
			Ip = ip[1];
		}
		logs.info("执行修改工作模式指令接收数据：" + Utils.bytesToHexString(base));
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		 // 获取发送的时间
//		 String fTime = MapUtils.getMapUtils().get("time");
//		 String jTime = df.format(new Date());
//		 int sj = 0;
//		 String sumTime = CzUtil.getSubtract(fTime, jTime);
//		 if (sumTime == null)
//		 {
//		 sumTime = "00:00:01";
//		 }
//		 if (sumTime != null)
//		 {
//		 sj = CzUtil.transform(sumTime);
//		 // 判断时间是否超时
//		 if (sj < 3)
//		 {
		// 转换为十六进制
		String stringH = Utils.bytesToHexString(base);
		if (stringH != null)
		{
			String stringHandler = CzUtil.Uppercase(stringH).toString();
			// 截取效验数据
			String jy = CzUtil.getJy(stringHandler);
			// 判断开始和结束
			String start = null;
			String end = null;
			start = stringHandler.charAt(0) + "" + stringHandler.charAt(1);
			end = stringHandler.charAt(stringHandler.length() - 2) + ""
					+ stringHandler.charAt(stringHandler.length() - 1);
			String je = CzUtil.getJe(stringHandler);
			 String string = MapUtils.getMapUtils().get("param");
//			 String wxAd = MapUtils.getMapUtils().get("wxAd");
			 if (string != null)
			 {
			// 截取状态
			String Status = stringHandler.substring(stringHandler.length() - 6, stringHandler.length() - 4);
			// 截取区管ID
			String sub = stringHandler.substring(6, 10);
			// 把十六进制数，转换为十进制
			int qgId = Integer.parseInt("" + sub + "", 16);
			// 截取333ID
			String Ad = stringHandler.substring(10, 12);
			// 把十六进制数，转换为十进制
			int Ad433 = Integer.parseInt("" + Ad + "", 16);
			// 截取333ID
			String Ad1 = stringHandler.substring(12, 14);
			// 把十六进制数，转换为十进制
			int Ad4331 = Integer.parseInt("" + Ad1 + "", 16);
			String Ad3 = algorismToHEXString(Ad4331,2);
			String wxAd=Ad433+Ad3;
			 if (string.equals("upd"))
			 {
			String xxip = null;
			String xqip = "select XqId,IP from xqinfo where XqId=(select XqId from qginfo where QgId='" + qgId + "')";
			ps = connc.prepareStatement(xqip);
			rst = ps.executeQuery();
			int col = rst.getMetaData().getColumnCount();
			while (rst.next())
			{
				xxip = rst.getString("IP");
				System.out.println("xxid++++=" + xxip);
				System.out.println("Ip=======" + Ip);
				// 如果小区ID相等就执行
				if (Ip.equals(xxip))
				{
					if (start.equals("F0") && end.equals("FF") && je.equals("" + jy + ""))
					{
//						String AD=null;
						logs.info("修改无线地址模块：" + stringHandler);
						if (Status.equals("01"))
						{
							String sql = "update qginfo set Ad433='" + wxAd + "' where QgId='" + qgId + "'";
							ps = connc.prepareStatement(sql);
							rs = ps.executeUpdate();
							logs.info("修改是否成功====" + rs);

							MapUtils.getMapUtils().add("upd", "success");
							MapUtils.getMapUtils().add("sub", "" + qgId + "");
							MapUtils.getMapUtils().add("Ad", "" + wxAd + "");
							logs.info("修改无线地址成功");
						} else if (Status.equals("00"))
						{
//							 String sqls = "select Ad433 from qginfo where QgId='" + qgId + "'";
//							 Sqlps = connc.prepareStatement(sqls);
//							 Sqlrst = Sqlps.executeQuery();
//							 while (Sqlrst.next())
//							 {
//							 AD = Sqlrst.getString("Ad433");
//							 }
							String sql = "update qginfo set Ad433='" + wxAd + "' where QgId='" + qgId + "'";
							ps = connc.prepareStatement(sql);
							rs = ps.executeUpdate();
							logs.info("修改是否成功====" + rs);
							MapUtils.getMapUtils().add("sub", "" + qgId + "");
							MapUtils.getMapUtils().add("Ad", "" + wxAd + "");
							MapUtils.getMapUtils().add("sb", "fail");
							logs.info("修改无线地址失败");
						} else
						{
							MapUtils.getMapUtils().add("sb", "fail");
							logs.info("修改无线地址失败");
						}
					} else
					{
						MapUtils.getMapUtils().add("sb", "fail");
						logs.info("修改无线地址失败");
					}
				} else
				{
					MapUtils.getMapUtils().add("sb", "fail");
					logs.info("修改无线地址失败");
				}
			}
			ps.close();
			connc.close();
			 }
			 } else
			 {
			 MapUtils.getMapUtils().add("sb", "fail");
			 logs.info("修改无线地址失败");
			 }
		}
//		 } else
//		 {
//		 MapUtils.getMapUtils().add("cs", "超时");
//		 logs.info("超时");
//		 }
//		 }
	}

	/**
	 * 修改区管ID
	 */
	public void updateqgId(byte[] base, IoSession session) throws SQLException, ClassNotFoundException
	{
		log.info("服务器打开了{}的连接，Session ID为{}", session.getRemoteAddress());
		SocketAddress remoteAddress = (SocketAddress) session.getRemoteAddress();
		String clientIp = remoteAddress.toString();
		sessionMap.add(clientIp, session);
		int port = 0;
		String Ip = null;
		DatabaseUtil dbUtil = DatabaseUtil.getInstance();
		Connection connc = dbUtil.getConnection();
		// 获取区管IP
		for (int i = 0; i < clientIp.length(); i++)
		{
			String[] ipPortString = clientIp.split(":");
			String iP = ipPortString[0];

			String[] ip = iP.split("/");
			port = Integer.valueOf(ipPortString[1]);
			Ip = ip[1];
		}
		logs.info("执行修改区管id模式指令接收数据：" + Utils.bytesToHexString(base));
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		 // 获取发送的时间
//		 String fTime = MapUtils.getMapUtils().get("time");
//		 String jTime = df.format(new Date());
//		 int sj = 0;
//		 String sumTime = CzUtil.getSubtract(fTime, jTime);
//		 if (sumTime == null)
//		 {
//		 sumTime = "00:00:01";
//		 }
//		 if (sumTime != null)
//		 {
//		 sj = CzUtil.transform(sumTime);
//		 // 判断时间是否超时
//		 if (sj < 3)
//		 {
		// 转换为十六进制
		String stringH = Utils.bytesToHexString(base);
		if (stringH != null)
		{
			String stringHandler = CzUtil.Uppercase(stringH).toString();
			// 截取效验数据
			String jy = CzUtil.getJy(stringHandler);
			// 判断开始和结束
			String start = null;
			String end = null;
			start = stringHandler.charAt(0) + "" + stringHandler.charAt(1);
			end = stringHandler.charAt(stringHandler.length() - 2) + ""
					+ stringHandler.charAt(stringHandler.length() - 1);
			String je = CzUtil.getJe(stringHandler);
			 String string = MapUtils.getMapUtils().get("param");
			 String qgId = MapUtils.getMapUtils().get("qgId");
			 if (string != null&& qgId!=null)
			 {
			// 截取状态
			String Status = stringHandler.substring(stringHandler.length() - 6, stringHandler.length() - 4);
			// 截取区管ID
			String sub = stringHandler.substring(6, 10);
			// 把十六进制数，转换为十进制
			int qgIds = Integer.parseInt("" + sub + "", 16);

			 if (string.equals("upqg"))
			 {
			String xxip = null;
			String xqip = "select XqId,IP from xqinfo where XqId=(select XqId from qginfo where QgId='" + qgId + "')";
			ps = connc.prepareStatement(xqip);
			rst = ps.executeQuery();
			int col = rst.getMetaData().getColumnCount();
			while (rst.next())
			{
				xxip = rst.getString("IP");
				System.out.println("xxid++++=" + xxip);
				System.out.println("Ip=======" + Ip);
				// 如果小区ID相等就执行
				if (Ip.equals(xxip))
				{
					if (start.equals("F0") && end.equals("FF") && je.equals("" + jy + ""))
					{
						logs.info("修改区管Id：" + stringHandler);
						if (Status.equals("01"))
						{
							String sql = "update qginfo set QgId='" + qgIds + "' where QgId='" + qgId + "'";
							ps = connc.prepareStatement(sql);
							rs = ps.executeUpdate();
//							String qgii=null;
//							String qgI = "select QgId from cgqinfo where QgId='" + qgId + "'";
//							ps = connc.prepareStatement(qgI);
//							rst = ps.executeQuery();
//							int col1 = rst.getMetaData().getColumnCount();
//							while (rst.next())
//							{
//								qgii = rst.getString("QgId");
//								if(qgii!=null){
//								String sqls = "update cgqinfo set QgId='" + sub + "' where QgId='" + qgId + "'";
//								Sqlps = connc.prepareStatement(sqls);
//								Sqlrs = Sqlps.executeUpdate();
//								}
//							}
//							Sqlps.close();
							logs.info("修改是否成功====" + rs);
							MapUtils.getMapUtils().add("upqg", "success");
							MapUtils.getMapUtils().add("qgId", "" + qgIds + "");
							logs.info("修改区管Id成功");
						} else if (Status.equals("00"))
						{
							String sql = "update qginfo set QgId='" + qgId + "' where QgId='" + qgId + "'";
							ps = connc.prepareStatement(sql);
							rs = ps.executeUpdate();
							logs.info("修改是否成功====" + rs);
							MapUtils.getMapUtils().add("qgId", "" + qgId + "");
							MapUtils.getMapUtils().add("sb", "fail");
							logs.info("修改区管Id失败");
						}
					} else
					{
						MapUtils.getMapUtils().add("sb", "fail");
						logs.info("修改区管Id失败");
					}
				} else
				{
					MapUtils.getMapUtils().add("sb", "fail");
					logs.info("修改区管Id失败");
				}
			}
			
			ps.close();
			connc.close();
			 }

			 } else
			 {
			 MapUtils.getMapUtils().add("sb", "fail");
			 logs.info("修改无线地址失败");
			 }
		}
//		 } else
//		 {
//		 MapUtils.getMapUtils().add("cs", "超时");
//		 logs.info("超时");
//		 }
//		 }
	}

	/**
	 * 当连接进入空闲状态时调用̬
	 */
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception
	{
		log.info("当前连接{}处于空闲状态:{}", session.getRemoteAddress(), status);
	}

	/**
	 * 当消息已经送给客户端后触发此方法
	 */
	@Override
	public void messageSent(IoSession session, Object message) throws Exception
	{
		log.info("服务器发送给{}的消息: {}", session.getRemoteAddress(), message);
	}

	/**
	 * 当关闭时调用
	 */
	@Override
	public void sessionClosed(IoSession session) throws Exception
	{
		@SuppressWarnings("deprecation")
		CloseFuture closeFuture = session.close(true);
		closeFuture.addListener(new IoFutureListener<IoFuture>()
		{
			public void operationComplete(IoFuture future)
			{
				if (future instanceof CloseFuture)
				{
					((CloseFuture) future).setClosed();
					log.info("sessionClosed CloseFuture setClosed-->{},", future.getSession().getId());
				}
			}
		});
		sessionMap.remove(session);
		log.info("关闭当前session：{}#{}...已移除", session.getId(), session.getRemoteAddress());
	}
	/**
	 * 将十进制转为指定长度的十六进制字符串
	 * algorism 十进制数字
	 * maxlength转换后的十六进制字符串
	 * 
	 * return 转换后的结果
	 * 
	 * **/
		public static String algorismToHEXString(int algorism,int maxLength)
		{
			String result="";
			result=Integer.toHexString(algorism);
			if (result.length()%2==1)
			{
				result="0"+result;
			}
			return patchHexString(result.toUpperCase(),maxLength);
			
		}
		
		/**
		 * Hex字符串前补0，主要用于长度位数不足
		 * str 需要补充长度的十六进制字符串
		 * maxlength 补充后十六进制字符串的长度
		 * return 补充后的结果
		 * */

		private static String patchHexString(String str, int maxLength)
		{
			String temp="";
			for (int i = 0; i < maxLength-str.length(); i++)
			{
				temp="0"+temp;
			}
			str=(temp+str).substring(0, maxLength);
			return str;
		}
}
