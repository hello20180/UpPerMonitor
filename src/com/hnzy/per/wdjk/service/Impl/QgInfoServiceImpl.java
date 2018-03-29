package com.hnzy.per.wdjk.service.Impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hnzy.per.socket.util.MapUtils;
import com.hnzy.per.util.Pagination;
import com.hnzy.per.wdjk.dao.QgInfoDao;
import com.hnzy.per.wdjk.dao.RzDao;
import com.hnzy.per.wdjk.pojo.QGInfo;
import com.hnzy.per.wdjk.pojo.Rz;
import com.hnzy.per.wdjk.service.QgInfoService;
@Service
public class QgInfoServiceImpl implements QgInfoService
{
	@Autowired 
	private QgInfoDao qgInfoDao;
	@Autowired 
	private RzDao rzDao;
	@Autowired  
	private HttpSession session;  

	@Autowired  
	private HttpServletRequest request; 
	//查询所有并分页
	@Override
	public Pagination<QGInfo> findAll(String pageNum, String limit)
	{
		// 把分页数据转换成int类型
		int pageIndex = Integer.parseInt(pageNum == null ? "1" : pageNum);
		int pageSize = Integer.parseInt(limit == null ? "15" : limit);
		Pagination<QGInfo> page = new Pagination<QGInfo>();
		// 从数据库查询出一共有多少条数据
		page.setTotal(qgInfoDao.getTotalNum());
		// 计算出一共有多少页
		int totalPage = (page.getTotal() % pageSize) == 0 ? (page.getTotal() / pageSize)
				: (page.getTotal() / pageSize + 1);
		// 计算出从哪一条数据开始查询
		int offset = (pageIndex - 1) * pageSize;
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		page.setOffset(offset);
		page.setTotalPage(totalPage);
		page.setItems(qgInfoDao.findAll(offset, pageSize));
		return page;
	}

//根据条件查询
	@Override
	public Pagination<QGInfo> findCondition(String pageNum, String limit,String xqName)
	{
		// TODO Auto-generated method stub
				// 把分页数据转换成int类型
				int pageIndex = Integer.parseInt(pageNum == null ? "1" : pageNum);
				int pageSize = Integer.parseInt(limit == null ? "15" : limit);
				Pagination<QGInfo> page = new Pagination<QGInfo>();
				//从数据库查询出一共有多少条数据
				page.setTotal(qgInfoDao.getTotal(xqName));
				//计算出一共有多少页
				int totalPage=(page.getTotal()%pageSize)==0?(page.getTotal()/pageSize):(page.getTotal()/pageSize+1);
				//计算出从哪一条数据开始查询
				int offset=(pageIndex-1)*pageSize;
				page.setPageIndex(pageIndex);
				page.setPageSize(pageSize);
				page.setOffset(offset);
				page.setTotalPage(totalPage);
				page.setItems(qgInfoDao.findCondition(page.getOffset(), page.getPageSize(),xqName));
				return page;
	}
	//添加
	@Override
	public void Insert(QGInfo qgInfo)
	{
		qgInfoDao.Insert(qgInfo);
	}
//根据id查询
	@Override
	public QGInfo findById(Serializable id)
	{
		return qgInfoDao.findById(id);
	}
//修改
	@Override
	public void update(QGInfo qgInfo)
	{
		qgInfoDao.update(qgInfo);
	}
//删除
	@Override
	public void delete(String ids)
	{
	  
		
		String[] sids = ids.split(",");
		for (String id : sids) {
			//向日志表添加操做
			String qString=qgInfoDao.findById(id).getQgId();
			Rz rz=new Rz();
			rz.setCzr((String)session.getAttribute("userName"));//获取操作人
			rz.setCz("删除区管Id为："+qString+"信息");//获取操作内容
			rz.setCzsj(new Date());//获取操作时间
			rzDao.Insert(rz);
			qgInfoDao.delete(Integer.parseInt(id));
			
		}
	}

	@Override
	public Pagination<QGInfo> findCondition1(String pageNum, String limit, String xqName, String qgId)
	{
		
		// 把分页数据转换成int类型
		int pageIndex = Integer.parseInt(pageNum == null ? "1" : pageNum);
		int pageSize = Integer.parseInt(limit == null ? "15" : limit);
		Pagination<QGInfo> page = new Pagination<QGInfo>();
		//从数据库查询出一共有多少条数据
		page.setTotal(qgInfoDao.getTotal1(xqName,qgId));
		//计算出一共有多少页
		int totalPage=(page.getTotal()%pageSize)==0?(page.getTotal()/pageSize):(page.getTotal()/pageSize+1);
		//计算出从哪一条数据开始查询
		int offset=(pageIndex-1)*pageSize;
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		page.setOffset(offset);
		page.setTotalPage(totalPage);
		page.setItems(qgInfoDao.findCondition1(page.getOffset(), page.getPageSize(),xqName,qgId));
		return page;
	}

	@Override
	public Pagination<QGInfo> findAll1(String pageNum, String limit)
	{
		// 把分页数据转换成int类型
				int pageIndex = Integer.parseInt(pageNum == null ? "1" : pageNum);
				int pageSize = Integer.parseInt(limit == null ? "15" : limit);
				Pagination<QGInfo> page = new Pagination<QGInfo>();
				// 从数据库查询出一共有多少条数据
				page.setTotal(qgInfoDao.getTotalNum());
				// 计算出一共有多少页
				int totalPage = (page.getTotal() % pageSize) == 0 ? (page.getTotal() / pageSize)
						: (page.getTotal() / pageSize + 1);
				// 计算出从哪一条数据开始查询
				int offset = (pageIndex - 1) * pageSize;
				page.setPageIndex(pageIndex);
				page.setPageSize(pageSize);
				page.setOffset(offset);
				page.setTotalPage(totalPage);
				page.setItems(qgInfoDao.findAll1(offset, pageSize));
				return page;
	}

	@Override
	public QGInfo findQGId(String qgId)
	{
		return qgInfoDao.findQGId(qgId);
	}

	@Override
	public QGInfo findById1(Serializable id)
	{
		return qgInfoDao.findById1(id);
	}


	

}
