package com.hnzy.per.wdjk.pojo;

import java.io.Serializable;
import java.util.Date;

public class Rz implements Serializable{
	public int id;
	public String czr;
	public String cz;
	public Date czsj;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getCz() {
		return cz;
	}
	public void setCz(String cz) {
		this.cz = cz;
	}
	public Date getCzsj() {
		return czsj;
	}
	public void setCzsj(Date czsj) {
		this.czsj = czsj;
	}
	
	

}
