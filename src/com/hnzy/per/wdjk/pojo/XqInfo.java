package com.hnzy.per.wdjk.pojo;

import java.io.Serializable;
/**
 * @author Administrator
 * 小区表
 */
public class XqInfo implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String xqId;
	private String xqName;
	private String xqAd;
	private String wyName;
	private String wyTel;
	private String beiZhu;
	private String ip;
	private String portNum;
	private String lng;
	private String lat;
	private CGQInfo cgq;
	private QGInfo qg;
 
	public String getIp()
	{
		return ip;
	}
	public void setIp(String ip)
	{
		this.ip = ip;
	}
	public String getPortNum()
	{
		return portNum;
	}
	public void setPortNum(String portNum)
	{
		this.portNum = portNum;
	}
	public QGInfo getQg()
	{
		return qg;
	}
	public void setQg(QGInfo qg)
	{
		this.qg = qg;
	}
	public CGQInfo getCgq()
	{
		return cgq;
	}
	public void setCgq(CGQInfo cgq)
	{
		this.cgq = cgq;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getXqId()
	{
		return xqId;
	}
	public void setXqId(String xqId)
	{
		this.xqId = xqId;
	}
	public String getXqName()
	{
		return xqName;
	}
	public void setXqName(String xqName)
	{
		this.xqName = xqName;
	}
	public String getXqAd()
	{
		return xqAd;
	}
	public void setXqAd(String xqAd)
	{
		this.xqAd = xqAd;
	}
	public String getWyName()
	{
		return wyName;
	}
	public void setWyName(String wyName)
	{
		this.wyName = wyName;
	}
	public String getWyTel()
	{
		return wyTel;
	}
	public void setWyTel(String wyTel)
	{
		this.wyTel = wyTel;
	}
	public String getBeiZhu()
	{
		return beiZhu;
	}
	public void setBeiZhu(String beiZhu)
	{
		this.beiZhu = beiZhu;
	}
	public String getLng()
	{
		return lng;
	}
	public void setLng(String lng)
	{
		this.lng = lng;
	}
	public String getLat()
	{
		return lat;
	}
	public void setLat(String lat)
	{
		this.lat = lat;
	}
	
}
