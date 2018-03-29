package com.hnzy.per.wdjk.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WdHistory implements Serializable {
	private int id;
	private String cgqId;//传感器id
	private Integer wd;//温度
	private Integer sd;//温度
	private Integer dl;//电量

	private String time;
	private CGQInfo cgq;
	public WdHistory()
	{
		super();
	}
	public CGQInfo getCgq() {
		return cgq;
	}
	public void setCgq(CGQInfo cgq) {
		this.cgq = cgq;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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

	public String getCgqId() {
		return cgqId;
	}
	public void setCgqId(String cgqId) {
		this.cgqId = cgqId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString()
	{
		return "WdHistory [id=" + id + ", cgqId=" + cgqId + ", wd=" + wd + ", dl=" + dl + ", time=" + time + ", cgq="
				+ cgq + "]";
	}
	
	
}
