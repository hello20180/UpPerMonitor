package com.hnzy.per.wdjk.service;

import java.io.Serializable;
import java.util.List;

import com.hnzy.per.util.Pagination;
import com.hnzy.per.wdjk.pojo.QGInfo;

public interface QgInfoService
{
	/**
	 * @author Administrator 列表显示并分页
	 */
	public Pagination<QGInfo> findAll(String pageNum, String limit);
	public Pagination<QGInfo> findAll1(String pageNum, String limit);
	/**
	 * @author Administrator 根据条件查询
	 */
	public Pagination<QGInfo> findCondition(String pageNum, String limit,String xqName);
	public Pagination<QGInfo> findCondition1(String pageNum, String limit, String xqName, String qgId);
	/**
	 * @author Administrator 添加
	 */
	public void Insert(QGInfo qgInfo);
	/**
	 * @author Administrator 根据id查询
	 */
	public QGInfo findById(Serializable id);
	public QGInfo findById1(Serializable id);
	/**
	 * @author Administrator 修改
	 */
	public void update(QGInfo qgInfo);
	/**
	 * @author Administrator 删除
	 */
	public void delete(String ids);
	/**
	 * @author Administrator 根据区管id查询IP和端口
	 */
	public QGInfo findQGId(String qgId);

	
	



}
