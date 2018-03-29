package com.hnzy.per.wdjk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hnzy.per.base.dao.BaseDao;
import com.hnzy.per.wdjk.pojo.QGInfo;
import com.hnzy.per.wdjk.pojo.User;


public interface UserDao extends BaseDao<User>
{
	/**
	 * @author Administrator
	 *验证账号唯一性
	 */
	public User findUserByNameAndMD5(@Param("name")String name, @Param("pass")String pass);
	/**
	 * @author Administrator
	 *修改用户状态
	 */
	public void updateState(User user);
	/**
	 * @author Administrator 按条件查询总数
	 */
	public int getTotal(@Param("name")String name);
	/**
	 * @author Administrator 按条件查询并分页
	 */
	public List<User> findCondition(@Param("offset")int offset, @Param("limit")int limit,@Param("name")String name);
}

