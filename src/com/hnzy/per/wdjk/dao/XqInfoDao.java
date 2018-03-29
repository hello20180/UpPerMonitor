package com.hnzy.per.wdjk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hnzy.per.base.dao.BaseDao;
import com.hnzy.per.wdjk.pojo.XqInfo;

public interface XqInfoDao extends BaseDao<XqInfo>
{
	/**
	 * @author Administrator 查询每个小区的平均温度及经纬度
	 */
	public List<XqInfo> findwd();
	/**
	 * @author Administrator 获取小区名称
	 */
	public List<XqInfo> findXqName();
	/**
	 * @author Administrator 根据物业名字获取小区名称
	 */
	public List<XqInfo> findXqId(String wyName);
	/**
	 * @author Administrator 根据小区名字获取传感器id
	 */
	public List<XqInfo> findcgqId(String xqName);
	/**
	 * @author Administrator 根据小区名字获取区管id
	 */
	public List<XqInfo> findqgId(String xqName);
	public List<XqInfo> findqg433(String xqName);
	/**
	 * @author Administrator 按条件查询总数
	 */
	public int getTotal(@Param("wyName")String wyName,@Param("xqName") String xqName);
	/**
	 * @author Administrator 按条件查询并分页
	 */
	public List<XqInfo> findCondition(@Param("offset")int offset, @Param("limit")int limit, @Param("wyName")String wyName, @Param("xqName")String xqName);
	/**
	 * @author Administrator 根据小区名字获取小区id
	 */
	public List<XqInfo> findxqId(String xqName);
	
	/**
	 * @author Administrator 按条件查询总数
	 */
	public int getTotalIP(@Param("xqName") String xqName);
	/**
	 * @author Administrator 按条件查询并分页
	 */
	public List<XqInfo> findConditionIP(@Param("offset")int offset, @Param("limit")int limit,@Param("xqName")String xqName);
	/**
	 * @author Administrator 修改IP
	 */
	public void updateIP(XqInfo xqInfo);
	


}
