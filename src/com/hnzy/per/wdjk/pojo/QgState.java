package com.hnzy.per.wdjk.pojo;

import java.io.Serializable;
import java.util.Date;

public class QgState implements Serializable {
	private int id;
	private String qgId;
	private String qgIp;
	private String portNum;
	private String swwd;
	private Date recordTime;
	private QGInfo qg;

	public QGInfo getQg()
	{
		return qg;
	}
	public void setQg(QGInfo qg)
	{
		this.qg = qg;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQgId() {
		return qgId;
	}
	public void setQgId(String qgId) {
		this.qgId = qgId;
	}
	public String getQgIp() {
		return qgIp;
	}
	public void setQgIp(String qgIp) {
		this.qgIp = qgIp;
	}
	public String getPortNum() {
		return portNum;
	}
	public void setPortNum(String portNum) {
		this.portNum = portNum;
	}
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	public String getSwwd() {
		return swwd;
	}
	public void setSwwd(String swwd) {
		this.swwd = swwd;
	}

}
