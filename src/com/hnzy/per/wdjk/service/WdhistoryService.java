package com.hnzy.per.wdjk.service;

import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.ibatis.annotations.Param;

import com.hnzy.per.util.Pagination;
import com.hnzy.per.wdjk.pojo.WdHistory;

public interface WdhistoryService
{

//温度历史
	
	/**
	 * @author Administrator 列表显示并分页
	 */
	public Pagination<WdHistory> findLS(String pageNum, String limit);

	/**
	 * @author Administrator 按条件查询并分页
	 */
	public Pagination<WdHistory> findLSCondition(@Param("pageNum")String pageNum, @Param("limit")String limit,@Param("xqName")String xqName);
	/**
	 * @author Administrator 按条件查询
	 */
	public List<WdHistory> findByNameLS(String xqName);

	/**
	 * @author Administrator 按条件查询后导出到excel
	 */	
	public void exportExcelWdHis(List<WdHistory> wdhis, ServletOutputStream outputStream) throws Exception;
	
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
