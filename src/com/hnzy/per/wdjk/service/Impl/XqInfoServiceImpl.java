package com.hnzy.per.wdjk.service.Impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hnzy.per.util.Pagination;
import com.hnzy.per.wdjk.dao.RzDao;
import com.hnzy.per.wdjk.dao.XqInfoDao;
import com.hnzy.per.wdjk.pojo.QGInfo;
import com.hnzy.per.wdjk.pojo.Rz;
import com.hnzy.per.wdjk.pojo.XqInfo;
import com.hnzy.per.wdjk.service.XqInfoService;
@Service
public class XqInfoServiceImpl implements XqInfoService
{
	@Autowired 
	private XqInfoDao xqInfoDao;
	@Autowired 
	private RzDao rzDao;
	@Autowired  
	private HttpSession session;  

	@Autowired  
	private HttpServletRequest request; 
	//查询所有并分页
	@Override
	public Pagination<XqInfo> findAll(String pageNum, String limit)
	{
		// 把分页数据转换成int类型
		int pageIndex = Integer.parseInt(pageNum == null ? "1" : pageNum);
		int pageSize = Integer.parseInt(limit == null ? "15" : limit);
		Pagination<XqInfo> page = new Pagination<XqInfo>();
		// 从数据库查询出一共有多少条数据
		page.setTotal(xqInfoDao.getTotalNum());
		// 计算出一共有多少页
		int totalPage = (page.getTotal() % pageSize) == 0 ? (page.getTotal() / pageSize)
				: (page.getTotal() / pageSize + 1);
		// 计算出从哪一条数据开始查询
		int offset = (pageIndex - 1) * pageSize;
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		page.setOffset(offset);
		page.setTotalPage(totalPage);
		page.setItems(xqInfoDao.findAll(offset, pageSize));
		return page;
	}
	//获取小区
	public List<XqInfo> findXqName()
	{
		return xqInfoDao.findXqName();
	}
	//根据小区名称获取传感器id
	@Override
	public List<XqInfo> findcgqId(String xqName)
	{
		return xqInfoDao.findcgqId(xqName);
	}
	//查询物业名字
	@Override
	public List<XqInfo> findWyName()
	{
		return xqInfoDao.find();
	}
//根据物业名称查询小区名称
	@Override
	public List<XqInfo> findXqId(String wyName)
	{
		return xqInfoDao.findXqId(wyName);
	}
	//根据小区名称获取区管id
	@Override
	public List<XqInfo> findqgId(String xqName)
	{
		return xqInfoDao.findqgId(xqName);
	}

//根据条件查询
	@Override
	public Pagination<XqInfo> findCondition(String pageNum, String limit, String wyName, String xqName)
	{
				// 把分页数据转换成int类型
				int pageIndex = Integer.parseInt(pageNum == null ? "1" : pageNum);
				int pageSize = Integer.parseInt(limit == null ? "15" : limit);
				Pagination<XqInfo> page = new Pagination<XqInfo>();
				//从数据库查询出一共有多少条数据
				page.setTotal(xqInfoDao.getTotal(wyName,xqName));
				//计算出一共有多少页
				int totalPage=(page.getTotal()%pageSize)==0?(page.getTotal()/pageSize):(page.getTotal()/pageSize+1);
				//计算出从哪一条数据开始查询
				int offset=(pageIndex-1)*pageSize;
				page.setPageIndex(pageIndex);
				page.setPageSize(pageSize);
				page.setOffset(offset);
				page.setTotalPage(totalPage);
				page.setItems(xqInfoDao.findCondition(page.getOffset(), page.getPageSize(),wyName,xqName));
				return page;
	}
	//添加
	@Override
	public void Insert(XqInfo xqInfo)
	{
		xqInfoDao.Insert(xqInfo);
	}
//根据id查询
	@Override
	public XqInfo findById(Serializable id)
	{
		return xqInfoDao.findById(id);
	}
//修改
	@Override
	public void update(XqInfo xqInfo)
	{
		xqInfoDao.update(xqInfo);
	}
//删除
	@Override
	public void delete(String ids)
	{
		String[] sids = ids.split(",");
		for (String id : sids) {
			//向日志表添加操做
			String xqString=xqInfoDao.findById(id).getXqName();
			Rz rz=new Rz();
			rz.setCzr((String)session.getAttribute("userName"));//获取操作人
			rz.setCz("删除小区名为："+xqString+"信息");//获取操作内容
			rz.setCzsj(new Date());//获取操作时间
			rzDao.Insert(rz);
			xqInfoDao.delete(Integer.parseInt(id));
		}
	}
	@Override
	public List<XqInfo> findxqId(String xqName)
	{
		return xqInfoDao.findxqId(xqName);
	}
	@Override
	public Pagination<XqInfo> findConditionIP(String pageNum, String limit, String xqName)
	{
		// 把分页数据转换成int类型
		int pageIndex = Integer.parseInt(pageNum == null ? "1" : pageNum);
		int pageSize = Integer.parseInt(limit == null ? "15" : limit);
		Pagination<XqInfo> page = new Pagination<XqInfo>();
		//从数据库查询出一共有多少条数据
		page.setTotal(xqInfoDao.getTotalIP("%"+xqName+"%"));
		//计算出一共有多少页
		int totalPage=(page.getTotal()%pageSize)==0?(page.getTotal()/pageSize):(page.getTotal()/pageSize+1);
		//计算出从哪一条数据开始查询
		int offset=(pageIndex-1)*pageSize;
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		page.setOffset(offset);
		page.setTotalPage(totalPage);
		page.setItems(xqInfoDao.findConditionIP(page.getOffset(), page.getPageSize(),"%"+xqName+"%"));
		return page;
	}
	@Override
	public void updateIP(XqInfo xqInfo)
	{
		xqInfoDao.updateIP(xqInfo);
	}
	@Override
	public List<XqInfo> findqg433(String xqName)
	{
		return xqInfoDao.findqg433(xqName);
	}
	@Override
	public List<XqInfo> findwd()
	{
		return xqInfoDao.findwd();
	}

	

}
