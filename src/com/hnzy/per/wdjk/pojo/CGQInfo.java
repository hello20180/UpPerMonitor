package com.hnzy.per.wdjk.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Administrator
 *	传感器表
 */
public class CGQInfo implements Serializable
{

	private static final long serialVersionUID = 1L;
	private int id;
	private String cgqId;//传感器id
	private String ad433;//433 区管表中的433
	private String xqName;
	private Integer buildNO;
	private Integer cellNO;
	private Integer houseNO;
	private String name;//联系人
	private String tel;//联系电话
	private Integer wd;//温度
	private Integer sd;//温度
	private Integer dl;//电量
	private String recordTime1;//采集时间
	private Date recordTime;//采集时间
	private String beiZhu;//备注

	public CGQInfo()
	{
		super();
	}


	public String getRecordTime1()
	{
		return recordTime1;
	}


	public void setRecordTime1(String recordTime1)
	{
		this.recordTime1 = recordTime1;
	}


	public String getAd433()
	{
		return ad433;
	}


	public void setAd433(String ad433)
	{
		this.ad433 = ad433;
	}


	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getCgqId()
	{
		return cgqId;
	}
	public void setCgqId(String cgqId)
	{
		this.cgqId = cgqId;
	}

	public String getXqName()
	{
		return xqName;
	}

	public void setXqName(String xqName)
	{
		this.xqName = xqName;
	}


	public Integer getWd() {
		return wd;
	}
	public void setWd(Integer wd) {
		this.wd = wd;
	}
	public Integer getSd() {
		return sd;
	}
	public void setSd(Integer sd) {
		this.sd = sd;
	}
	public Integer getDl() {
		return dl;
	}
	public void setDl(Integer dl) {
		this.dl = dl;
	}

	public Integer getBuildNO()
	{
		return buildNO;
	}

	public void setBuildNO(Integer buildNO)
	{
		this.buildNO = buildNO;
	}

	public Integer getCellNO()
	{
		return cellNO;
	}

	public void setCellNO(Integer cellNO)
	{
		this.cellNO = cellNO;
	}

	public Integer getHouseNO()
	{
		return houseNO;
	}
	public void setHouseNO(Integer houseNO)
	{
		this.houseNO = houseNO;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getTel()
	{
		return tel;
	}
	public void setTel(String tel)
	{
		this.tel = tel;
	}
	public String getBeiZhu()
	{
		return beiZhu;
	}
	public void setBeiZhu(String beiZhu)
	{
		this.beiZhu = beiZhu;
	}

	
	public Date getRecordTime()
	{
		return recordTime;
	}
	public void setRecordTime(Date recordTime)
	{
		this.recordTime = recordTime;
	}
}
