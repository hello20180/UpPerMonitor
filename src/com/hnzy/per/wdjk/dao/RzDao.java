package com.hnzy.per.wdjk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hnzy.per.base.dao.BaseDao;
import com.hnzy.per.wdjk.pojo.Rz;

public interface RzDao extends BaseDao<Rz> {
	/**
	 * @author Administrator 按条件查询并分页
	 */
	public List<Rz> findCzr(@Param("offset")int offset, @Param("limit")int limit,@Param("czr")String czr);
	//查询一供有多少数据
	public int CzCount(@Param("czr")String czr);
}
