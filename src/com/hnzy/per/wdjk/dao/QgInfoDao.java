package com.hnzy.per.wdjk.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hnzy.per.base.dao.BaseDao;
import com.hnzy.per.wdjk.pojo.QGInfo;

public interface QgInfoDao extends BaseDao<QGInfo>
{
	/**
	 * @author Administrator 按条件查询总数
	 */
	public int getTotal(@Param("xqName") String xqName);
	/**
	 * @author Administrator 按条件查询并分页
	 */
	public List<QGInfo> findCondition(@Param("offset")int offset, @Param("limit")int limit,@Param("xqName")String xqName);
//状态
	/**
	 * @author Administrator 按条件查询总数
	 */

	public int getTotal1(@Param("xqName")String xqName, @Param("qgId")String qgId);
	/**
	 * @author Administrator 按条件查询并分页
	 */
	public List<QGInfo> findCondition1(@Param("offset")int offset, @Param("limit")int limit,@Param("xqName")String xqName,@Param("qgId")String qgId);
	public int getTotalNum1();
	public List<QGInfo> findAll1(@Param("offset")int offset ,@Param("limit")int limit);
	/**
	 * @author Administrator 根据区管id查询IP和端口
	 */
	public QGInfo findQGId(@Param("qgId")String qgId);
	public QGInfo findById1(Serializable ids);
}
