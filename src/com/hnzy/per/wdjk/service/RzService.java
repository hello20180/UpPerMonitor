package com.hnzy.per.wdjk.service;


import com.hnzy.per.util.Pagination;
import com.hnzy.per.wdjk.pojo.Rz;

public interface RzService {
	/**
	 * @author Administrator 按条件查询并分页
	 */
	public Pagination<Rz> findCzr(String pageNum, String limit,String czr);
	//查询所有并分页
	public Pagination<Rz> findAll(String pageNum, String limit);
	public void insert(Rz rz);
		
}
