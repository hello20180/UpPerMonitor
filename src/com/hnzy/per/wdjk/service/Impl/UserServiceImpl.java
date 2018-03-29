package com.hnzy.per.wdjk.service.Impl;

import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hnzy.per.util.Pagination;
import com.hnzy.per.wdjk.dao.RzDao;
import com.hnzy.per.wdjk.dao.UserDao;
import com.hnzy.per.wdjk.pojo.Rz;
import com.hnzy.per.wdjk.pojo.User;
import com.hnzy.per.wdjk.service.UserService;
@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserDao userDao;
	@Autowired 
	private RzDao rzDao;
	@Autowired  
	private HttpSession session;  

	@Autowired  
	private HttpServletRequest request; 
	//显示所有数据
	@Override
	public Pagination<User> findAll(String pageNum, String limit)
	{
		// 把分页数据转换成int类型
				int pageIndex = Integer.parseInt(pageNum == null ? "1" : pageNum);
				int pageSize = Integer.parseInt(limit == null ? "15" : limit);
				Pagination<User> page = new Pagination<User>();
				// 从数据库查询出一共有多少条数据
				page.setTotal(userDao.getTotalNum());
				// 计算出一共有多少页
				int totalPage = (page.getTotal() % pageSize) == 0 ? (page.getTotal() / pageSize)
						: (page.getTotal() / pageSize + 1);
				// 计算出从哪一条数据开始查询
				int offset = (pageIndex - 1) * pageSize;
				page.setPageIndex(pageIndex);
				page.setPageSize(pageSize);
				page.setOffset(offset);
				page.setTotalPage(totalPage);
				page.setItems(userDao.findAll(offset, pageSize));
				return page;
	}
	//按条件查询
	@Override
	public Pagination<User> findCondition(String pageNum, String limit, String name)
	{
		// TODO Auto-generated method stub
		// 把分页数据转换成int类型
		int pageIndex = Integer.parseInt(pageNum == null ? "1" : pageNum);
		int pageSize = Integer.parseInt(limit == null ? "15" : limit);
		Pagination<User> page = new Pagination<User>();
		//从数据库查询出一共有多少条数据
		page.setTotal(userDao.getTotal("%"+name+"%"));
		//计算出一共有多少页
		int totalPage=(page.getTotal()%pageSize)==0?(page.getTotal()/pageSize):(page.getTotal()/pageSize+1);
		//计算出从哪一条数据开始查询
		int offset=(pageIndex-1)*pageSize;
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		page.setOffset(offset);
		page.setTotalPage(totalPage);
		page.setItems(userDao.findCondition(page.getOffset(), page.getPageSize(),"%"+name+"%"));
		return page;
}
//验证账号的唯一性
	@Override
	public User findUserByNameAndMD5(String name, String pass)
	{
		return userDao.findUserByNameAndMD5(name,pass);
	}
	//根据ID查询
	@Override
	public User findUserById(Serializable id)
	{
		return userDao.findById(id);
	}
	//修改用户
	@Override
	public void updateUser(User userLogin)
	{
		userDao.update(userLogin);
	}
	@Override
	public void updateState(User user)
	{
		userDao.updateState(user);
	}
	@Override
	public void Insert(User user)
	{
		userDao.Insert(user);
	}
	
	@Override
	public void delete(String ids)
	{
		
		String[] sids = ids.split(",");
		for (String id : sids) {
			String users=userDao.findById(id).getName();
			//向日志表添加操做
			Rz rz=new Rz();
			rz.setCzr((String)session.getAttribute("userName"));//获取操作人
			rz.setCz("删除用户名为:"+users+"的信息");//获取操作内容
			rz.setCzsj(new Date());//获取操作时间
			rzDao.Insert(rz);
			userDao.delete(Integer.parseInt(id));
		}
	}


}
