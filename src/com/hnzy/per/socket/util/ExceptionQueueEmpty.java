package com.hnzy.per.socket.util;

public class ExceptionQueueEmpty extends Exception
{
	   public ExceptionQueueEmpty() {
		   
	    }
	 
	    // Constructor with parameters
	    public ExceptionQueueEmpty(String mag) {
	        System.out.println(mag);
	    }
}
