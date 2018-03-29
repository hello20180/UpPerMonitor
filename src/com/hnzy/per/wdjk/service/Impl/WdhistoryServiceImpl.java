package com.hnzy.per.wdjk.service.Impl;

import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hnzy.per.util.ExcelUtil;
import com.hnzy.per.util.Pagination;
import com.hnzy.per.wdjk.dao.WdHistoryDao;
import com.hnzy.per.wdjk.pojo.WdHistory;
import com.hnzy.per.wdjk.service.WdhistoryService;
@Service
public class WdhistoryServiceImpl implements WdhistoryService
{
	@Autowired
	private WdHistoryDao wdHistoryDao;

//温度历史
	/**
	 * @author Administrator 列表
	 */
	@Override
	public Pagination<WdHistory> findLS(String pageNum, String limit)
	{
		// 把分页数据转换成int类型
		int pageIndex = Integer.parseInt(pageNum == null ? "1" : pageNum);
		int pageSize = Integer.parseInt(limit == null ? "15" : limit);
		Pagination<WdHistory> page = new Pagination<WdHistory>();
		// 从数据库查询出一共有多少条数据
		page.setTotal(wdHistoryDao.LSTotalNum());
		// 计算出一共有多少页
		int totalPage = (page.getTotal() % pageSize) == 0 ? (page.getTotal() / pageSize)
				: (page.getTotal() / pageSize + 1);
		// 计算出从哪一条数据开始查询
		int offset = (pageIndex - 1) * pageSize;
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		page.setOffset(offset);
		page.setTotalPage(totalPage);
		page.setItems(wdHistoryDao.findLS(offset, pageSize));
		return page;
	}
	/**
	 * @author Administrator 按条件查询
	 */
	@Override
	public Pagination<WdHistory> findLSCondition(String pageNum, String limit, String xqName)
	{
		// TODO Auto-generated method stub
				// 把分页数据转换成int类型
				int pageIndex = Integer.parseInt(pageNum == null ? "1" : pageNum);
				int pageSize = Integer.parseInt(limit == null ? "15" : limit);
				Pagination<WdHistory> page = new Pagination<WdHistory>();
				//从数据库查询出一共有多少条数据
				page.setTotal(wdHistoryDao.LSTotal(xqName));
				//计算出一共有多少页
				int totalPage=(page.getTotal()%pageSize)==0?(page.getTotal()/pageSize):(page.getTotal()/pageSize+1);
				//计算出从哪一条数据开始查询
				int offset=(pageIndex-1)*pageSize;
				page.setPageIndex(pageIndex);
				page.setPageSize(pageSize);
				page.setOffset(offset);
				page.setTotalPage(totalPage);
				page.setItems(wdHistoryDao.findLSCondition(page.getOffset(), page.getPageSize(), xqName));
				return page;
	}
	/**
	 * @author Administrator 按条件查询所有并导出
	 */
	@Override
	public List<WdHistory> findByNameLS(String xqName)
	{
		return wdHistoryDao.findByNameLS(xqName);
	}
	/**
	 * @author Administrator 按条件查询后导出到excel
	 */
	@Override
	public void exportExcelWdHis(List<WdHistory> wdhis, ServletOutputStream outputStream) throws Exception
	{
		ExcelUtil.exportExcelHis(wdhis,outputStream);
	}
//图	
	@Override
	public List<WdHistory> findChar(WdHistory wdHistory)
	{
		return wdHistoryDao.findChar(wdHistory);
	}
	@Override
	public List<WdHistory> findHistoryLine(WdHistory wdHistory)
	{
		return wdHistoryDao.findHistoryLine(wdHistory);
	}


}
