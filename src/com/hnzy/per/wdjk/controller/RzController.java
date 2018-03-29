package com.hnzy.per.wdjk.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hnzy.per.util.Pagination;
import com.hnzy.per.wdjk.pojo.Rz;
import com.hnzy.per.wdjk.service.RzService;

@Controller
@RequestMapping("RzController")
public class RzController {

	@Autowired
	private RzService rzService;
	private List<Rz> listRz;
	//显示并分页
	@RequestMapping("findAll")
	public ModelAndView findAll(HttpServletRequest request,@RequestParam(value = "pageNum", required = false) String pageNum,
			@RequestParam(value = "limit", required = false)String limit){
	int role=(int) request.getSession().getAttribute("role");
	String name= (String) request.getSession().getAttribute("userName");
	if(role==1){
		ModelAndView modelAndView=new ModelAndView();
		Pagination<Rz> page = rzService.findCzr(pageNum, limit, name);
		modelAndView.addObject("page",page);
		modelAndView.setViewName("Rz");
		return modelAndView;
		
	}else{
		ModelAndView modelAndView=new ModelAndView();
		Pagination<Rz> page=rzService.findAll(pageNum, limit);
		modelAndView.addObject("page",page);
		modelAndView.setViewName("Rz");
		return modelAndView;
	}
		
	}
//	查询并返回列表页面
	@ResponseBody
	@RequestMapping("findRz")
	public JSONObject findRz(HttpServletRequest request,@RequestParam(value = "pageNum", required = false) String pageNum,
			@RequestParam(value = "limit", required = false) String limit,@Param("czr")String czr){
		JSONObject jsonObject=new JSONObject();
	    ModelAndView mav = new ModelAndView();
		 
		 int role=(int) request.getSession().getAttribute("role");
		 String name= (String) request.getSession().getAttribute("userName");	 	 
		 if(role==1){
				Pagination<Rz> page = rzService.findCzr(pageNum, limit, name);
				mav.addObject("page",page);
				jsonObject.put("Condition",page);
				return jsonObject;
			}else{
				Pagination<Rz> page = rzService.findCzr(pageNum, limit, czr);
				mav.addObject("page", page);
				jsonObject.put("Condition",page);
				 return jsonObject;
			}
		
		
	}
	public List<Rz> getListRz() {
		return listRz;
	}
	public void setListRz(List<Rz> listRz) {
		this.listRz = listRz;
	}
	
}
