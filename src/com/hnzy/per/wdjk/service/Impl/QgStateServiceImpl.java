package com.hnzy.per.wdjk.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hnzy.per.util.Pagination;
import com.hnzy.per.wdjk.dao.QgStateDao;
import com.hnzy.per.wdjk.pojo.QgState;
import com.hnzy.per.wdjk.service.QgStateService;

@Service
public class QgStateServiceImpl implements QgStateService{
	@Autowired
	private QgStateDao qgStateDao;

	@Override
	public Pagination<QgState> findAll(String pageNum, String limit) {
		// 把分页数据转换成int类型
				int pageIndex = Integer.parseInt(pageNum == null ? "1" : pageNum);
				int pageSize = Integer.parseInt(limit == null ? "15" : limit);
				Pagination<QgState> page = new Pagination<QgState>();
				// 从数据库查询出一共有多少条数据
				page.setTotal(qgStateDao.getTotalNum());
				// 计算出一共有多少页
				int totalPage = (page.getTotal() % pageSize) == 0 ? (page.getTotal() / pageSize)
						: (page.getTotal() / pageSize + 1);
				// 计算出从哪一条数据开始查询
				int offset = (pageIndex - 1) * pageSize;
				page.setPageIndex(pageIndex);
				page.setPageSize(pageSize);
				page.setOffset(offset);
				page.setTotalPage(totalPage);
				page.setItems(qgStateDao.findAll(offset, pageSize));
				return page;
	}

	@Override
	public Pagination<QgState> findCondition(String pageNum, String limit, String xqName, String qgId) {
		
		// 把分页数据转换成int类型
		int pageIndex = Integer.parseInt(pageNum == null ? "1" : pageNum);
		int pageSize = Integer.parseInt(limit == null ? "15" : limit);
		Pagination<QgState> page = new Pagination<QgState>();
		//从数据库查询出一共有多少条数据
		page.setTotal(qgStateDao.getTotal(xqName,qgId));
		//计算出一共有多少页
		int totalPage=(page.getTotal()%pageSize)==0?(page.getTotal()/pageSize):(page.getTotal()/pageSize+1);
		//计算出从哪一条数据开始查询
		int offset=(pageIndex-1)*pageSize;
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		page.setOffset(offset);
		page.setTotalPage(totalPage);
		page.setItems(qgStateDao.findCondition(page.getOffset(), page.getPageSize(),xqName,qgId));
		return page;
	}

	@Override
	public QgState findById(Integer id)
	{
		return qgStateDao.findById(id);
	}

	@Override
	public QgState findQGId(String id)
	{
		return qgStateDao.findQGId(id);
	}

	@Override
	public void update(QgState qgState)
	{
		qgStateDao.update(qgState);
	}


}
