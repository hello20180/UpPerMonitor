package com.hnzy.per.wdjk.service;

import java.io.Serializable;
import java.util.List;

import com.hnzy.per.util.Pagination;
import com.hnzy.per.wdjk.pojo.JZQInfo;

public interface JZQInfoService
{
	/**
	 * @author Administrator 查询集中器Id
	 */
	public List< JZQInfo> findJzqId();
	/**
	 * @author Administrator 列表显示并分页
	 */
	Pagination<JZQInfo> findAll(String pageNum, String limit);
	/**
	 * @author Administrator 根据条件查询
	 */
	public Pagination<JZQInfo> findCondition(String pageNum, String limit,String xqName);
	/**
	 * @author Administrator 添加
	 */
	public void Insert(JZQInfo jzqInfo);
	/**
	 * @author Administrator 根据id查询
	 */
	public JZQInfo findById(Serializable id);
	/**
	 * @author Administrator 修改
	 */
	public void update(JZQInfo jzqInfo);
	/**
	 * @author Administrator 删除
	 */
	public void delete(String ids);


}
