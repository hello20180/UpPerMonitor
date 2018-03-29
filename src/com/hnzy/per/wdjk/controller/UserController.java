package com.hnzy.per.wdjk.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hnzy.per.util.MD5Util;
import com.hnzy.per.util.Pagination;
import com.hnzy.per.util.PropertyUtil;
import com.hnzy.per.util.StringUtil;
import com.hnzy.per.wdjk.pojo.Rz;
import com.hnzy.per.wdjk.pojo.User;
import com.hnzy.per.wdjk.service.RzService;
import com.hnzy.per.wdjk.service.UserService;

@Controller
@RequestMapping("UserCon")
public class UserController
{
	private String msg;
	@Autowired
	private UserService userService;
	@Autowired
	private RzService rzService;
	//分页显示数据
	@RequestMapping("find")
	public ModelAndView find(@RequestParam(value = "pageNum", required = false) String pageNum,
			@RequestParam(value = "limit", required = false) String limit)
	{
		ModelAndView mav = new ModelAndView();
		Pagination<User> page = userService.findAll(pageNum, limit);
		mav.setViewName("userList");
		mav.addObject("page", page);
		return mav;
	}
	// 按条件查询
	 @RequestMapping("List")
	 @ResponseBody
	 public JSONObject findCondition(@RequestParam(value="pageNum",required=false)String pageNum,@RequestParam(value="limit",required=false)String limit,@RequestParam(value="name",required=false)String name) throws
	 Exception {
	 name=new String(name.getBytes("ISO-8859-1"),"utf-8")+"";
	 Pagination<User> page = new Pagination<User>();
	 JSONObject jsonObject=new JSONObject();
	 page=userService.findCondition(pageNum,limit,"%"+name+"%");
	 jsonObject.put("Condition",page);
	 return jsonObject;
	
	 }
	 //到添加页面
	 @RequestMapping("toInsert")
	 public String toInsert()
	 {
		return "userAdd";
	 }
	 //添加
	 @RequestMapping("Insert")
	 public String Insert(HttpSession session,User user) throws UnsupportedEncodingException
	 {
		 
		 if(user!=null&&user.getName()!=null&&user.getName()!=""&&user.getPass()!=null&&user.getPass()!=""){
				user.setPass(MD5Util.string2MD5(user.getPass()));
				user.setName(new String(user.getName().getBytes("ISO-8859-1"),"utf-8")+"");
				user.setCreatedTime(new Date());
				user.setTrueName(new String(user.getTrueName().getBytes("ISO-8859-1"),"utf-8")+"");
				userService.Insert(user);
				//向日志表添加操做
				 Rz rz=new Rz();
					rz.setCzr((String)session.getAttribute("userName"));//获取操作人
					rz.setCz("添加用户名为:"+user.getName()+"的信息");//获取操作内容
					rz.setCzsj(new Date());//获取操作时间
					rzService.insert(rz);
				
				return "usersuccess";
		 }
		return "usersuccess";
	 }
	 //到修改页面
	 @RequestMapping("toUpdate")
	 public String toUpdate(@Param("id")Integer id,User user,HttpServletRequest request)
	 {
		 user = this.userService.findUserById(id);
		 if (id != 0 && id != null) {
				user = this.userService.findUserById(id);
				request.setAttribute("u",user);
			}
		return "userEdit";
	 }
	 //修改
	 @RequestMapping("Update")
	 public String Update(HttpSession session,User user,@Param("id")Integer id,@Param("pass")String pass) throws UnsupportedEncodingException
	 {
		 if (id != null && id != 0) {
			 if (user != null) {
				 if(pass.equals(userService.findUserById(id).getPass())==false)
				 {
					 user.setPass(MD5Util.string2MD5(pass));
				 }
				 user.setCreatedTime(new Date());
				 userService.updateUser(user);
				//向日志表添加操做
				 Rz rz=new Rz();
					rz.setCzr((String)session.getAttribute("userName"));//获取操作人
					rz.setCz("修改用户名为:"+user.getName()+"的信息");//获取操作内容
					rz.setCzsj(new Date());//获取操作时间
					rzService.insert(rz);
				 return "usersuccess";
			 }
		 }
		return "userEdit";
		 
	 }
	 //删除
	 @RequestMapping("Delete")
	 public String Delete(@Param("ids")String ids)
	 {
		 userService.delete(ids);
			return "redirect:find.action";
		 
	 }
	
	//修改状态
	@RequestMapping("upState")
	public String upda2UserSta(HttpServletRequest request,User user,Integer state,@Param("id")Integer id) {
		Date date=new Date();
		SimpleDateFormat myDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String date1 = DateUtil
//				.getCurrentDateStr(DateUtil.YYYYMMDDHHMMSS);
//		String msm = PropertyUtil.getProperty("Updateusers");
	
		String states = request.getParameter("states").trim();
		state = Integer.parseInt(states);

			if (user != null) {
				user.setState(state);
				this.userService.updateState(user);
				return "userStateSuccess";
			}

			return null;
	}
	
	//登录
	@RequestMapping("login")
	public String signin(HttpSession session, HttpServletRequest request,User userLogin) throws UnsupportedEncodingException {
		Date date=new Date();
		SimpleDateFormat myDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
//		String date = DateUtil
//				.getCurrentDateStr(DateUtil.YYYYMMDDHHMMSS);
		
		if (StringUtil.isNotEmpty(userLogin.getName()) && StringUtil.isNotEmpty(userLogin.getPass())) {
			userLogin.setPass(MD5Util.string2MD5(userLogin.getPass()));

			userLogin = userService.findUserByNameAndMD5(userLogin.getName(), userLogin.getPass());
			//userLogin.setName(new String(userLogin.getName().getBytes("ISO-8859-1"),"utf-8")+"");
			if (userLogin != null) {
				if (userLogin.getState() == 0) { // 账户已启用
					request.getSession().setAttribute("admins", userLogin);
					request.getSession().setAttribute("userName", userLogin.getName());
					request.getSession().setAttribute("role", userLogin.getRole());
					//向日志表添加操做
					 Rz rz=new Rz();
						rz.setCzr((String)session.getAttribute("userName"));//获取操作人
						rz.setCz("登录成功");//获取操作内容
						rz.setCzsj(new Date());//获取操作时间
						rzService.insert(rz);
					return "index";
				} else {
					msg = PropertyUtil.Accountdisabled;
				}
			} else {
				msg = PropertyUtil.Informationerror;
			}
		} else {
			msg = PropertyUtil.Informationempty;
		}
		request.setAttribute("msg", msg);
		return "signin";
	}
	//退出
	@RequestMapping("signout")
	public String signout(HttpServletRequest request,@ModelAttribute("User") User user, SessionStatus sessionStatus){
		//Date转String
		Date date=new Date();
		SimpleDateFormat myDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//向日志表添加操做
		Rz rz=new Rz();
		rz.setCzr((String)request.getSession().getAttribute("userName"));//获取操作人
		rz.setCz("退出系统");//获取操作内容
		rz.setCzsj(new Date());//获取操作时间
		rzService.insert(rz);
//		String date = DateUtil
//				.getCurrentDateStr(DateUtil.YYYYMMDDHHMMSS);
		sessionStatus.setComplete(); 
		request.getSession().invalidate();
			
		return "signin";
		
	}
	//到重置密码页面
	@RequestMapping("changepwd")
	public String changepwd(@Param("id")Integer id,User userLogin,HttpServletRequest request)
	{
		
		if (id != 0 && id != null) {
			userLogin = this.userService.findUserById(id);
			request.setAttribute("userpass",userLogin);
		}
		return "editpwd";
	}
	//重置密码
	@RequestMapping("updapwd")
	public String updapwd(@Param("id")Integer id,@Param("pass")String pass,User userLogin,HttpServletRequest request) {

		if (id != null && id != 0 && StringUtil.isNotEmpty(pass)) {
			userLogin = this.userService.findUserById(id);
			if (userLogin != null) {
				userLogin.setPass(MD5Util.string2MD5(pass));
				userLogin.setCreatedTime(new Date());
				this.userService.updateUser(userLogin);
				// --写入用户日志--
				 Rz rz=new Rz();
					rz.setCzr((String)request.getSession().getAttribute("userName"));//获取操作人
					rz.setCz("重置了密码");//获取操作内容
					rz.setCzsj(new Date());//获取操作时间
					rzService.insert(rz);
				return "pwdsuccess";
			}
		}
		return "editpwd";
	}
	
	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}


}
