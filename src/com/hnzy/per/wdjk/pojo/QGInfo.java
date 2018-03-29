package com.hnzy.per.wdjk.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 *	区管表
 */
public class QGInfo implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id;
	private String qgId;//区管id
	private String xqId;
	private String xqName;//小区名字
	private Integer buildNO;//楼栋号
	private String qgAd;//区管安装位置
	private String beiZhu;//备注
	private Date recordTime;//采集时间 
	private String ms;
	private String wxAd;
	private String swwd;
	private CGQInfo cgqInfo;
	private XqInfo xq;
	

	public XqInfo getXq()
	{
		return xq;
	}

	public void setXq(XqInfo xq)
	{
		this.xq = xq;
	}

	public String getXqId()
	{
		return xqId;
	}

	public void setXqId(String xqId)
	{
		this.xqId = xqId;
	}


	public QGInfo()
	{
		super();
	}

	public String getWxAd()
	{
		return wxAd;
	}
	public void setWxAd(String wxAd)
	{
		this.wxAd = wxAd;
	}

	public String getSwwd()
	{
		return swwd;
	}
	public void setSwwd(String swwd)
	{
		this.swwd = swwd;
	}
	public String getMs()
	{
		return ms;
	}

	public void setMs(String ms)
	{
		this.ms = ms;
	}

	public Date getRecordTime()
	{
		return recordTime;
	}

	public void setRecordTime(Date recordTime)
	{
		this.recordTime = recordTime;
	}

	public CGQInfo getCgqInfo()
	{
		return cgqInfo;
	}

	public void setCgqInfo(CGQInfo cgqInfo)
	{
		this.cgqInfo = cgqInfo;
	}

	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getQgId()
	{
		return qgId;
	}
	public void setQgId(String qgId)
	{
		this.qgId = qgId;
	}

	public String getQgAd()
	{
		return qgAd;
	}
	public void setQgAd(String qgAd)
	{
		this.qgAd = qgAd;
	}

	public String getXqName()
	{
		return xqName;
	}

	public void setXqName(String xqName)
	{
		this.xqName = xqName;
	}

	public Integer getBuildNO()
	{
		return buildNO;
	}

	public void setBuildNO(Integer buildNO)
	{
		this.buildNO = buildNO;
	}

	public String getBeiZhu()
	{
		return beiZhu;
	}

	public void setBeiZhu(String beiZhu)
	{
		this.beiZhu = beiZhu;
	}
	
	
}