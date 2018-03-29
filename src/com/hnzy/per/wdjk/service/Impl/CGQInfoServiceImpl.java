package com.hnzy.per.wdjk.service.Impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import com.hnzy.per.util.ExcelUtil;
import com.hnzy.per.util.Pagination;
import com.hnzy.per.wdjk.dao.CGQInfoDao;
import com.hnzy.per.wdjk.dao.RzDao;
import com.hnzy.per.wdjk.pojo.CGQInfo;
import com.hnzy.per.wdjk.pojo.Rz;
import com.hnzy.per.wdjk.service.CGQInfoService;
@Service
public class CGQInfoServiceImpl implements CGQInfoService
{
	@Autowired
	private CGQInfoDao cgqInfoDao;
	@Autowired 
	private RzDao rzDao;
	@Autowired  
	private HttpSession session;  

	@Autowired  
	private HttpServletRequest request; 

	


	//传感器
	/**
	 * @author Administrator 列表显示并分页
	 */
	@Override
	public Pagination<CGQInfo> findAll(String pageNum, String limit)
	{
		// 把分页数据转换成int类型
				int pageIndex = Integer.parseInt(pageNum == null ? "1" : pageNum);
				int pageSize = Integer.parseInt(limit == null ? "15" : limit);
				Pagination<CGQInfo> page = new Pagination<CGQInfo>();
				// 从数据库查询出一共有多少条数据
				page.setTotal(cgqInfoDao.getTotalNum());
				// 计算出一共有多少页
				int totalPage = (page.getTotal() % pageSize) == 0 ? (page.getTotal() / pageSize)
						: (page.getTotal() / pageSize + 1);
				// 计算出从哪一条数据开始查询
				int offset = (pageIndex - 1) * pageSize;
				page.setPageIndex(pageIndex);
				page.setPageSize(pageSize);
				page.setOffset(offset);
				page.setTotalPage(totalPage);
				page.setItems(cgqInfoDao.findAll(offset, pageSize));
				return page;
	}

	/**
	 * @author Administrator 根据条件查询
	 */
	@Override
	public Pagination<CGQInfo> findCondition(String pageNum, String limit, String xqName, String cgqId,String name)
	{
		// TODO Auto-generated method stub
		// 把分页数据转换成int类型
		int pageIndex = Integer.parseInt(pageNum == null ? "1" : pageNum);
		int pageSize = Integer.parseInt(limit == null ? "15" : limit);
		Pagination<CGQInfo> page = new Pagination<CGQInfo>();
		//从数据库查询出一共有多少条数据
		page.setTotal(cgqInfoDao.getTotal(xqName,cgqId,name));
		//计算出一共有多少页
		int totalPage=(page.getTotal()%pageSize)==0?(page.getTotal()/pageSize):(page.getTotal()/pageSize+1);
		//计算出从哪一条数据开始查询
		int offset=(pageIndex-1)*pageSize;
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		page.setOffset(offset);
		page.setTotalPage(totalPage);
		page.setItems(cgqInfoDao.findCondition(page.getOffset(), page.getPageSize(), xqName,cgqId,name));
		return page;
	}
	/**
	 * @author Administrator 添加
	 */
	@Override
	public void Insert(CGQInfo cgqInfo)
	{
		cgqInfoDao.Insert(cgqInfo);
	}
	/**
	 * @author Administrator 根据id查询
	 */
	@Override
	public CGQInfo findById(Serializable id)
	{
		return cgqInfoDao.findById(id);
	}
	/**
	 * @author Administrator 修改
	 */
	@Override
	public void update(CGQInfo cgqInfo)
	{
		cgqInfoDao.update(cgqInfo);
	}
	/**
	 * @author Administrator 删除
	 */
	@Override
	public void delete(String ids)
	{	
		
		
		String[] sids = ids.split(",");
		for (String id : sids) {
			//向日志表添加操做
			String cString=cgqInfoDao.findById(id).getCgqId();
			Rz rz=new Rz();
			rz.setCzr((String)session.getAttribute("userName"));//获取操作人
			rz.setCz("删除传感器ID为："+cString+"的记录");//获取操作内容
			rz.setCzsj(new Date());//获取操作时间
			rzDao.Insert(rz);
			cgqInfoDao.delete(Integer.parseInt(id));
		}
	}
	
//温度实时
	/**
	 * @author Administrator 列表
	 */
	@Override
	public Pagination<CGQInfo> findAllWd(String pageNum, String limit)
	{
		// 把分页数据转换成int类型
		int pageIndex = Integer.parseInt(pageNum == null ? "1" : pageNum);
		int pageSize = Integer.parseInt(limit == null ? "15" : limit);
		Pagination<CGQInfo> page = new Pagination<CGQInfo>();
		// 从数据库查询出一共有多少条数据
		page.setTotal(cgqInfoDao.getTotalNumWd());
		// 计算出一共有多少页
		int totalPage = (page.getTotal() % pageSize) == 0 ? (page.getTotal() / pageSize)
				: (page.getTotal() / pageSize + 1);
		// 计算出从哪一条数据开始查询
		int offset = (pageIndex - 1) * pageSize;
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		page.setOffset(offset);
		page.setTotalPage(totalPage);
		page.setItems(cgqInfoDao.findAllWd(offset, pageSize));
		return page;
	}
	/**
	 * @author Administrator 按条件查询
	 */
	@Override
	public Pagination<CGQInfo> findConditionWd(String pageNum, String limit, String xqName)
	{
		// TODO Auto-generated method stub
		// 把分页数据转换成int类型
		int pageIndex = Integer.parseInt(pageNum == null ? "1" : pageNum);
		int pageSize = Integer.parseInt(limit == null ? "15" : limit);
		Pagination<CGQInfo> page = new Pagination<CGQInfo>();
		//从数据库查询出一共有多少条数据
		page.setTotal(cgqInfoDao.getTotalWd(xqName));
		//计算出一共有多少页
		int totalPage=(page.getTotal()%pageSize)==0?(page.getTotal()/pageSize):(page.getTotal()/pageSize+1);
		//计算出从哪一条数据开始查询
		int offset=(pageIndex-1)*pageSize;
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		page.setOffset(offset);
		page.setTotalPage(totalPage);
		page.setItems(cgqInfoDao.findConditionWd(page.getOffset(), page.getPageSize(), xqName));
		return page;
	}
	/**
	 * @author Administrator 按条件查询导出全部
	 */
	@Override
	public List<CGQInfo> findByNameWd(String xqName)
	{
		return cgqInfoDao.findByNameWd(xqName);
	}
	/**
	 * @author Administrator 按条件查询后导出到excel
	 */
	@Override
	public void exportExcel(List<CGQInfo> wdInfos, ServletOutputStream outputStream) throws Exception
	{
		ExcelUtil.exportExcel(wdInfos,outputStream);
		
	}

	  /**
     * 按条件查询温度
     * @return
     */
	@Override
	public int findWDA(String xqName)
	{
		return cgqInfoDao.findWDA(xqName);
	}

	@Override
	public int findWDB(String xqName)
	{
		return cgqInfoDao.findWDB(xqName);
	}

	@Override
	public int findWDC(String xqName)
	{
		return cgqInfoDao.findWDC(xqName);
	}

	@Override
	public int findWDD(String xqName)
	{
		return cgqInfoDao.findWDD(xqName);
	}
	  /**
     * 散点图
     * @return
     */
	@Override
	public List<CGQInfo> chartSearchSd(String xqName)
	{
		return cgqInfoDao.chartSearchSd(xqName);
	}
	  /**
     * 按散点id查
     * @return
     */
	@Override
	public CGQInfo findSd(int id)
	{
		return cgqInfoDao.findSd(id);
	}


}
