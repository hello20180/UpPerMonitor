package com.hnzy.per.wdjk.pojo;

import java.io.Serializable;

public class JZQInfo implements Serializable 
{
	private int id;
	private String jzqId;
	private String jzqIp;
	private String portNum;
	private String jzqAd;
	public JZQInfo()
	{
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getJzqId()
	{
		return jzqId;
	}
	public void setJzqId(String jzqId)
	{
		this.jzqId = jzqId;
	}
	public String getJzqIp()
	{
		return jzqIp;
	}
	public void setJzqIp(String jzqIp)
	{
		this.jzqIp = jzqIp;
	}
	public String getPortNum()
	{
		return portNum;
	}
	public void setPortNum(String portNum)
	{
		this.portNum = portNum;
	}
	public String getJzqAd()
	{
		return jzqAd;
	}
	public void setJzqAd(String jzqAd)
	{
		this.jzqAd = jzqAd;
	}
	
}
