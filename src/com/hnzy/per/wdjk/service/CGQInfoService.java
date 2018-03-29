package com.hnzy.per.wdjk.service;

import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.ibatis.annotations.Param;

import com.hnzy.per.util.Pagination;
import com.hnzy.per.wdjk.pojo.CGQInfo;

public interface CGQInfoService
{
//传感器
	/**
	 * @author Administrator 列表显示并分页
	 */
	public Pagination<CGQInfo> findAll(String pageNum, String limit);
	/**
	 * @author Administrator 根据条件查询
	 */
	public Pagination<CGQInfo> findCondition(String pageNum, String limit, String xqName, String cgqId,String name);
	/**
	 * @author Administrator 添加
	 */
	public void Insert(CGQInfo cgqInfo);
	/**
	 * @author Administrator 根据id查询
	 */
	public CGQInfo findById(Serializable id);
	/**
	 * @author Administrator 修改
	 */
	public void update(CGQInfo cgqInfo);
	/**
	 * @author Administrator 删除
	 */
	public void delete(String ids);
	
//温度实时
	
	/**
	 * @author Administrator 列表显示并分页
	 */
	public Pagination<CGQInfo> findAllWd(String pageNum, String limit);

	/**
	 * @author Administrator 按条件查询并分页
	 */
	public Pagination<CGQInfo> findConditionWd(@Param("pageNum")String pageNum, @Param("limit")String limit,@Param("xqName")String xqName);
	/**
	 * @author Administrator 按条件查询
	 */
	public List<CGQInfo> findByNameWd(String xqName);
	/**
	 * @author Administrator 按条件查询后导出到excel
	 */	
	public void exportExcel(List<CGQInfo> wdInfos, ServletOutputStream outputStream) throws Exception;


	  /**
     * 饼形图
     * @return
     */
	public int findWDA(String xqName);
	public int findWDB(String xqName);
	public int findWDC(String xqName);
	public int findWDD(String xqName);
	  /**
     * 散点图
     * @return
     */
	public List<CGQInfo> chartSearchSd(String xqName);
	  /**
     * 根据id查找具体信息
     * @return
     */
	public CGQInfo findSd(int id);
}
