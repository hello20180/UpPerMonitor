package com.hnzy.per.wdjk.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hnzy.per.util.Pagination;
import com.hnzy.per.wdjk.dao.RzDao;
import com.hnzy.per.wdjk.pojo.Rz;
import com.hnzy.per.wdjk.service.RzService;
@Service
public class RzServiceImpl implements RzService{

	@Autowired
	private RzDao rzDao;

	//查询所有并分页
		@Override
		public Pagination<Rz> findAll(String pageNum, String limit)
		{
			// 把分页数据转换成int类型
			int pageIndex = Integer.parseInt(pageNum == null ? "1" : pageNum);
			int pageSize = Integer.parseInt(limit == null ? "15" : limit);
			Pagination<Rz> page = new Pagination<Rz>();
			// 从数据库查询出一共有多少条数据
			page.setTotal(rzDao.getTotalNum());
			// 计算出一共有多少页
			int totalPage = (page.getTotal() % pageSize) == 0 ? (page.getTotal() / pageSize)
					: (page.getTotal() / pageSize + 1);
			// 计算出从哪一条数据开始查询
			int offset = (pageIndex - 1) * pageSize;
			page.setPageIndex(pageIndex);
			page.setPageSize(pageSize);
			page.setOffset(offset);
			page.setTotalPage(totalPage);
			page.setItems(rzDao.findAll(offset, pageSize));
			return page;
		}
	@Override
	public Pagination<Rz> findCzr(String pageNum, String limit, String czr) {
		// 把分页数据转换成int类型
		int pageIndex = Integer.parseInt(pageNum == null ? "1" : pageNum);
		int pageSize = Integer.parseInt(limit == null ? "15" : limit);
		Pagination<Rz> page = new Pagination<Rz>();
		//从数据库查询出一共有多少条数据
		page.setTotal(rzDao.CzCount(czr));
		//计算出一共有多少页
		int totalPage=(page.getTotal()%pageSize)==0?(page.getTotal()/pageSize):(page.getTotal()/pageSize+1);
		//计算出从哪一条数据开始查询
		int offset=(pageIndex-1)*pageSize;
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		page.setOffset(offset);
		page.setTotalPage(totalPage);
		page.setItems(rzDao.findCzr(page.getOffset(),page.getPageSize(), czr));
		return page;
	}
	@Override
	public void insert(Rz rz)
	{
		rzDao.Insert(rz);
	}
	

}
