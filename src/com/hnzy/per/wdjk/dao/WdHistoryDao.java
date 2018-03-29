package com.hnzy.per.wdjk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hnzy.per.base.dao.BaseDao;
import com.hnzy.per.wdjk.pojo.CGQInfo;
import com.hnzy.per.wdjk.pojo.WdHistory;


public interface WdHistoryDao extends BaseDao<WdHistory>
{
//温度历史
	
	/**
	 * @author Administrator 列表显示并分页
	 */
	public List<WdHistory> findLS(@Param("offset")int offset ,@Param("limit")int limit);

	/**
	 * @author Administrator 按条件查询并分页
	 */
	public List<WdHistory> findLSCondition(@Param(value="offset")int offset, @Param(value="limit") int limit,@Param(value="xqName")String xqName);
	/**
	 * @author Administrator 按条件查询
	 */
	public List<WdHistory> findByNameLS(@Param("xqName")String xqName);
	/**
	 * 得到总数
	 * @return
	 */
	public int LSTotalNum();
	  /**
     * 按条件查询记录总数
     * @return
     */
	public int LSTotal(@Param("xqName") String xqName);
//图
	  /**
   * 按条件查询
   * @return
   */
	public List<WdHistory> findChar(WdHistory wdHistory);
	  /**
   * 折线图
   * @return
   */
	public List<WdHistory> findHistoryLine(WdHistory wdHistory);

	
}
