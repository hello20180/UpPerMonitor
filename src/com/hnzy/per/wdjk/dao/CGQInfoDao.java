package com.hnzy.per.wdjk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hnzy.per.base.dao.BaseDao;
import com.hnzy.per.wdjk.pojo.CGQInfo;


public interface CGQInfoDao extends BaseDao<CGQInfo>
{
//传感器
	/**
	 * @author Administrator 按条件查询总数
	 */
	public int getTotal(@Param(value="xqName")String xqName,@Param(value="cgqId") String cgqId, @Param(value="name") String name);
	/**
	 * @author Administrator 按条件查询并分页
	 */
	public List<CGQInfo> findCondition(@Param(value="offset")int offset, @Param(value="limit") int limit,@Param(value="xqName")String xqName,@Param(value="cgqId") String cgqId, @Param(value="name") String name);
	
//温度实时
	/**
	 * @author Administrator 列表显示并分页
	 */
	public List<CGQInfo> findAllWd(@Param("offset")int offset ,@Param("limit")int limit);
	
	/**
	 * @author Administrator 按条件查询并分页
	 */
	public List<CGQInfo> findConditionWd(@Param(value="offset")int offset, @Param(value="limit") int limit,@Param(value="xqName")String xqName);
	/**
	 * 得到总数
	 * @return
	 */
	public int getTotalNumWd(); 
	/**
     * 按条件查询记录总数
     * @return
     */
	public int getTotalWd(@Param("xqName") String xqName);
	  /**
     * 按条件查询导出
     * @return
     */
	public List<CGQInfo> findByNameWd(@Param("xqName")String xqName);
		

	  /**
     * 饼形图
     * @return
     */
	public int findWDA(@Param("xqName")String xqName);
	public int findWDB(@Param("xqName")String xqName);
	public int findWDC(@Param("xqName")String xqName);
	public int findWDD(@Param("xqName")String xqName);

	  /**
     *散点图
     * @return
     */
	public List<CGQInfo> chartSearchSd(@Param("xqName")String xqName);
	
	public CGQInfo findSd(int id);
	
}
