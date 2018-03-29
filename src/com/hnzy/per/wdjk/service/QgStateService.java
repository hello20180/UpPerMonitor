package com.hnzy.per.wdjk.service;

import java.util.List;

import com.hnzy.per.util.Pagination;
import com.hnzy.per.wdjk.pojo.QgState;

public interface QgStateService {
	/**
	 * @author Administrator 列表显示并分页
	 */
	public Pagination<QgState> findAll(String pageNum, String limit);

	public Pagination<QgState> findCondition(String pageNum, String limit, String xqName, String qgId);

	public QgState findById(Integer id);
	public QgState findQGId(String id);

	public void update(QgState qgState);

}
