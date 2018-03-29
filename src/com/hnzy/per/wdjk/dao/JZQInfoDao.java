package com.hnzy.per.wdjk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hnzy.per.base.dao.BaseDao;
import com.hnzy.per.wdjk.pojo.JZQInfo;

public interface JZQInfoDao extends BaseDao<JZQInfo>
{
	
	/**
	 * @author Administrator 查询集中器Id
	 */
	public List< JZQInfo> findJzqId();
	/**
	 * @author Administrator 按条件查询总数
	 */
	public int getTotal(@Param("jzqAd") String jzqAd);
	/**
	 * @author Administrator 按条件查询并分页
	 */
	public List<JZQInfo> findCondition(@Param("offset")int offset, @Param("limit")int limit,@Param("jzqAd")String jzqAd);
	


}
