package com.hnzy.per.wdjk.service;

import java.io.Serializable;
import java.util.List;

import com.hnzy.per.util.Pagination;
import com.hnzy.per.wdjk.pojo.QGInfo;
import com.hnzy.per.wdjk.pojo.XqInfo;

public interface XqInfoService
{
	/**
	 * @author Administrator 查询每个小区的平均温度及经纬度
	 */
	public List<XqInfo> findwd();
	/**
	 * @author Administrator 列表显示并分页
	 */
	public Pagination<XqInfo> findAll(String pageNum, String limit);
	/**
	 * @author Administrator 获取小区名称
	 */
	public List<XqInfo> findXqName();
	/**
	 * @author Administrator 根据小区名字获取传感器id
	 */
	public List<XqInfo> findcgqId(String xqName);
	/**
	 * @author Administrator 获取物业名称
	 */
	public List<XqInfo> findWyName();
	/**
	 * @author Administrator 根据物业名字获取小区名字
	 */
	public List<XqInfo> findXqId(String wyName);
	/**
	 * @author Administrator 根据小区名字获取区管id
	 */
	public List<XqInfo> findqgId(String xqName);
	public List<XqInfo> findqg433(String xqName);
	/**
	 * @author Administrator 根据条件查询
	 */
	public Pagination<XqInfo> findCondition(String pageNum, String limit, String wyName, String xqName);
	/**
	 * @author Administrator 添加
	 */
	public void Insert(XqInfo xqInfo);
	/**
	 * @author Administrator 根据id查询
	 */
	public XqInfo findById(Serializable id);
	/**
	 * @author Administrator 修改
	 */
	public void update(XqInfo xqInfo);
	/**
	 * @author Administrator 删除
	 */
	public void delete(String ids);
	/**
	 * @author Administrator 根据小区名字获取小区id
	 */
	public List<XqInfo> findxqId(String xqName);
	/**
	 * @author Administrator 根据条件查询IP
	 */
	public Pagination<XqInfo> findConditionIP(String pageNum, String limit, String xqName);
	/**
	 * @author Administrator 修改IP
	 */
	public void updateIP(XqInfo xqInfo);
	




}
