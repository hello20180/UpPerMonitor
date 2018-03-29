package com.hnzy.per.wdjk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hnzy.per.base.dao.BaseDao;
import com.hnzy.per.wdjk.pojo.QgState;

public interface QgStateDao extends BaseDao<QgState> {

	public int getTotal(@Param("xqName")String xqName, @Param("qgId")String qgId);

	public List<QgState> findCondition(@Param("offset")int offset, @Param("limit")int limit,@Param("xqName")String xqName,@Param("qgId")String qgId);

	public QgState findById(@Param("id")Integer id);
	public QgState findQGId(@Param("id")String id);

}
