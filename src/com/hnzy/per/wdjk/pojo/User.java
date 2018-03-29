package com.hnzy.per.wdjk.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 *	用户表
 */
public class User implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id;//id
	private String name;//用户名
	private String pass;//密码
	private String trueName;
	private int state;
	private int role;
	private Date createdTime;
	
	public User()
	{
		super();
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getPass()
	{
		return pass;
	}
	public void setPass(String pass)
	{
		this.pass = pass;
	}
	public String getTrueName()
	{
		return trueName;
	}
	public void setTrueName(String trueName)
	{
		this.trueName = trueName;
	}
	public int getState()
	{
		return state;
	}
	public void setState(int state)
	{
		this.state = state;
	}
	public int getRole()
	{
		return role;
	}
	public void setRole(int role)
	{
		this.role = role;
	}
	public Date getCreatedTime()
	{
		return createdTime;
	}
	public void setCreatedTime(Date createdTime)
	{
		this.createdTime = createdTime;
	}
	
}
