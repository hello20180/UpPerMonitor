package com.hnzy.per.wdjk.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hnzy.per.util.Pagination;
import com.hnzy.per.wdjk.pojo.CGQInfo;
import com.hnzy.per.wdjk.pojo.Rz;
import com.hnzy.per.wdjk.pojo.XqInfo;
import com.hnzy.per.wdjk.service.CGQInfoService;
import com.hnzy.per.wdjk.service.RzService;
import com.hnzy.per.wdjk.service.XqInfoService;

@Controller
@RequestMapping("CGQCon")
public class CGQController
{
	@Autowired
	private CGQInfoService cgqInfoService;
	@Autowired
	private XqInfoService xqInfoService;
	private List<XqInfo> xqList;
	@Autowired
	private RzService rzService;

	//获取小区名字并分页显示数据
	@RequestMapping("find")
	public ModelAndView find(@RequestParam(value = "pageNum", required = false) String pageNum,
			@RequestParam(value = "limit", required = false) String limit)
	{
		ModelAndView mav = new ModelAndView();
		xqList=xqInfoService.findXqName();
		Pagination<CGQInfo> page = cgqInfoService.findAll(pageNum, limit);
		mav.setViewName("cgqList");
		mav.addObject("cgq", xqList);
		mav.addObject("page", page);
		return mav;
	}
	//根据小区名称获取区管433
		@RequestMapping("findqgId")
		@ResponseBody
		public JSONObject findqgId(String xqName) throws UnsupportedEncodingException{
			 xqName=new String(xqName.getBytes("ISO-8859-1"),"utf-8")+"";
			xqList=xqInfoService.findqg433(xqName);
			JSONObject jsonObject=new JSONObject() ;
			if(xqList!=null){
				jsonObject.put("qglist", xqList);
			}else{
				jsonObject.put("fail", null);
			}
			return jsonObject;
		}
	//根据小区名称获取传感器id
	@RequestMapping("findcgqId")
	@ResponseBody
	public JSONObject findcgqId(String xqName) throws UnsupportedEncodingException{
		 xqName=new String(xqName.getBytes("ISO-8859-1"),"utf-8")+"";
		xqList=xqInfoService.findcgqId(xqName);
		JSONObject jsonObject=new JSONObject() ;
		if(xqList!=null){
			jsonObject.put("xqlist", xqList);
		}else{
			jsonObject.put("fail", null);
		}
		return jsonObject;
	}
	// 按条件查询
		 @RequestMapping("List")
		 @ResponseBody
		 public JSONObject findCondition(@RequestParam(value="pageNum",required=false)String pageNum,@RequestParam(value="limit",required=false)String limit,@RequestParam(value="xqName",required=false)String xqName,@RequestParam(value="cgqId",required=false)String cgqId,@RequestParam(value="name",required=false)String name) throws
		 Exception
		 {
		 xqName=new String(xqName.getBytes("ISO-8859-1"),"utf-8")+"";
		 cgqId=new String(cgqId.getBytes("ISO-8859-1"),"utf-8")+"";
		 name=new String(name.getBytes("ISO-8859-1"),"utf-8")+"";
		 Pagination<CGQInfo> page = new Pagination<CGQInfo>();
		 JSONObject jsonObject=new JSONObject();
		 page=cgqInfoService.findCondition(pageNum,limit,xqName,cgqId,name);
		 jsonObject.put("Condition",page);
		 return jsonObject;
		
		 }
		 //到添加页面
		 @RequestMapping("toInsert")
		 public String toInsert(HttpServletRequest request)
		 {
			 xqList=xqInfoService.findXqName();
			 request.setAttribute("cgq", xqList);
			return "cgqAdd";
		 }
		 //添加
		 @RequestMapping("Insert")
		 public String Insert(CGQInfo cgqInfo,HttpSession session)
		 {
			 cgqInfoService.Insert(cgqInfo);
			//向日志表添加操做
			 Rz rz=new Rz();
				rz.setCzr((String)session.getAttribute("userName"));//获取操作人
				rz.setCz("添加传感器:"+cgqInfo.getCgqId());//获取操作内容
				rz.setCzsj(new Date());//获取操作时间
				rzService.insert(rz);
			 
			return "cgqsuccess";
		 }
		 //到修改页面
		 @RequestMapping("toUpdate")
		 public String toUpdate(@Param("id")Integer id,CGQInfo cgqInfo,HttpServletRequest request)
		 {
			 if (id != 0 && id != null) {
					cgqInfo = this.cgqInfoService.findById(id);
					request.setAttribute("cgq",cgqInfo);
				}
			return "cgqEdit";
		 }
		 //修改
		 @RequestMapping("Update")
		 public String Update(CGQInfo cgqInfo,@Param("id")Integer id,HttpSession session)
		 {
			 if (id != null && id != 0) {
				 if (cgqInfo != null) {
					 cgqInfoService.update(cgqInfo);
						//向日志表添加操做
					 Rz rz=new Rz();
						rz.setCzr((String)session.getAttribute("userName"));//获取操作人
						rz.setCz("修改传感器:"+cgqInfo.getCgqId()+"的信息");//获取操作内容
						rz.setCzsj(new Date());//获取操作时间
						rzService.insert(rz);
					 return "cgqsuccess";
				 }
			 }
			return "cgqEdit";
			 
		 }
		 //删除
		 @RequestMapping("Delete")
		 public String Delete(@Param("ids")String ids,CGQInfo cgqInfo)
		 {
			 cgqInfoService.delete(ids);
				return "redirect:find.action";
			 
		 }

		public List<XqInfo> getXqList()
		{
			return xqList;
		}
		public void setXqList(List<XqInfo> xqList)
		{
			this.xqList = xqList;
		}

		 
}
