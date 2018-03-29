package com.hnzy.per.wdjk.service.Impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hnzy.per.util.Pagination;
import com.hnzy.per.wdjk.dao.JZQInfoDao;
import com.hnzy.per.wdjk.dao.RzDao;
import com.hnzy.per.wdjk.pojo.JZQInfo;
import com.hnzy.per.wdjk.pojo.QGInfo;
import com.hnzy.per.wdjk.pojo.Rz;
import com.hnzy.per.wdjk.service.JZQInfoService;
@Service
public class JZQInfoServiceImpl implements JZQInfoService
{
	@Autowired 
	private JZQInfoDao jzqInfoDao;
	@Autowired 
	private RzDao rzDao;
	@Autowired  
	private HttpSession session;  

	@Autowired  
	private HttpServletRequest request; 

	@Override
	public Pagination<JZQInfo> findAll(String pageNum, String limit)
	{
		// 把分页数据转换成int类型
		int pageIndex = Integer.parseInt(pageNum == null ? "1" : pageNum);
		int pageSize = Integer.parseInt(limit == null ? "15" : limit);
		Pagination<JZQInfo> page = new Pagination<JZQInfo>();
		// 从数据库查询出一共有多少条数据
		page.setTotal(jzqInfoDao.getTotalNum());
		// 计算出一共有多少页
		int totalPage = (page.getTotal() % pageSize) == 0 ? (page.getTotal() / pageSize)
				: (page.getTotal() / pageSize + 1);
		// 计算出从哪一条数据开始查询
		int offset = (pageIndex - 1) * pageSize;
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		page.setOffset(offset);
		page.setTotalPage(totalPage);
		page.setItems(jzqInfoDao.findAll(offset, pageSize));
		return page;
	}

	@Override
	public Pagination<JZQInfo> findCondition(String pageNum, String limit, String jzqAd)
	{
		// TODO Auto-generated method stub
		// 把分页数据转换成int类型
		int pageIndex = Integer.parseInt(pageNum == null ? "1" : pageNum);
		int pageSize = Integer.parseInt(limit == null ? "15" : limit);
		Pagination<JZQInfo> page = new Pagination<JZQInfo>();
		//从数据库查询出一共有多少条数据
		page.setTotal(jzqInfoDao.getTotal("%"+jzqAd+"%"));
		//计算出一共有多少页
		int totalPage=(page.getTotal()%pageSize)==0?(page.getTotal()/pageSize):(page.getTotal()/pageSize+1);
		//计算出从哪一条数据开始查询
		int offset=(pageIndex-1)*pageSize;
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		page.setOffset(offset);
		page.setTotalPage(totalPage);
		page.setItems(jzqInfoDao.findCondition(page.getOffset(), page.getPageSize(),"%"+jzqAd+"%"));
		return page;
	}

	@Override
	public void Insert(JZQInfo jzqInfo)
	{
		jzqInfoDao.Insert(jzqInfo);
	}

	@Override
	public JZQInfo findById(Serializable id)
	{
		return jzqInfoDao.findById(id);
	}

	@Override
	public void update(JZQInfo jzqInfo)
	{
		jzqInfoDao.update(jzqInfo);
	}

	@Override
	public void delete(String ids)
	{
		String[] sids = ids.split(",");
		for (String id : sids) {
			//向日志表添加操做
			String qString=jzqInfoDao.findById(id).getJzqId();
			Rz rz=new Rz();
			rz.setCzr((String)session.getAttribute("userName"));//获取操作人
			rz.setCz("删除集中器Id为："+qString+"信息");//获取操作内容
			rz.setCzsj(new Date());//获取操作时间
			rzDao.Insert(rz);
			jzqInfoDao.delete(Integer.parseInt(id));
			
		}
	}

	@Override
	public List<JZQInfo> findJzqId()
	{
		return jzqInfoDao.findJzqId();
	}

}
