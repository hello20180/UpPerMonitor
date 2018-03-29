package com.hnzy.per.socket.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {
	 private static DatabaseUtil dbUtil;   
	    private String drivers;   
	    private String url;   
	    private String username;   
	    private String password;   
	    //加反斜线就表示在默认包目录，不加则表示在与当前类同路径去查找该属性文件   
	    private static String FILE_PATH_NAME = "/jdbc.properties";   
	    private void init() {   
	        try {   
	            InputStream in = getClass().getResourceAsStream(FILE_PATH_NAME);   
	            Properties props = new Properties();   
	           
	            props.load(in);   
	            in.close();   
	            drivers = props.getProperty("jdbc.driver");   
	            url = props.getProperty("jdbc.url");   
	            username = props.getProperty("jdbc.user");   
	            password = props.getProperty("jdbc.password");   
	        } catch (IOException e) {   
	            e.printStackTrace();   
	        }   
	           
	    }   
	       
	    private DatabaseUtil() {   
	        init();   
	    }   
	    public static DatabaseUtil getInstance() {   
	        if(dbUtil == null) {   
	            dbUtil = new DatabaseUtil();   
	        }   
	        return dbUtil;    
	    }   
	    public Connection getConnection() {   
	           
	        Connection conn = null;   
	        try {   
	            Class.forName(drivers);   
	            conn = DriverManager.getConnection(url, username, password);   
	        } catch (SQLException e) {   
	            e.printStackTrace();   
	        } catch (ClassNotFoundException e) {   
	            e.printStackTrace();   
	        }   
	        return conn;   
	    }

		/**
		 * 关闭连接
		 * @param pstmt
		 * @param conn
		 * @throws SQLException
		 */
		public static void close(PreparedStatement pstmt, Connection conn) throws SQLException{
			if(pstmt!=null){
				pstmt.close();
				if(conn!=null){
					conn.close();
				}
			}
		}  
}
