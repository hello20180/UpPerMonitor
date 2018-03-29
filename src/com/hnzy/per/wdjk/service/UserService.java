package com.hnzy.per.wdjk.service;

import java.io.Serializable;

import com.hnzy.per.util.Pagination;
import com.hnzy.per.wdjk.pojo.User;

public interface UserService
{
	/**
	 * @author Administrator 列表显示
	 */
	public Pagination<User> findAll(String pageNum, String limit);
	/**
	 * @author Administrator
	 *验证账号唯一性
	 */
	public User findUserByNameAndMD5(String name, String pass);
	/**
	 * @author Administrator
	 *根据id查询
	 */
	public User findUserById(Serializable id);
	/**
	 * @author Administrator
	 *修改用户
	 */
	public void updateUser(User userLogin);
	/**
	 * @author Administrator
	 *修改用户状态
	 */
	public void updateState(User user);
	/**
	 * @author Administrator
	 *添加
	 */
	public void Insert(User user);
	/**
	 * @author Administrator
	 *删除
	 */
	public void delete(String ids);
	
	public Pagination<User> findCondition(String pageNum, String limit, String name);
}
