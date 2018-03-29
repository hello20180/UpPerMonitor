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
import com.hnzy.per.wdjk.pojo.Rz;
import com.hnzy.per.wdjk.pojo.XqInfo;
import com.hnzy.per.wdjk.service.RzService;
import com.hnzy.per.wdjk.service.XqInfoService;

@Controller
@RequestMapping("XqCon")
public class XqInfoController
{
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
			xqList=xqInfoService.findWyName();
			Pagination<XqInfo> page = xqInfoService.findAll(pageNum, limit);
			mav.setViewName("xqList");
			mav.addObject("xqlist", xqList);
			mav.addObject("page", page);
			return mav;
		}
		//根据物业名称获取小区名
		@RequestMapping("findXqId")
		@ResponseBody
		public JSONObject findXqId(String wyName) throws UnsupportedEncodingException{
			wyName=new String(wyName.getBytes("ISO-8859-1"),"utf-8")+"";
			xqList=xqInfoService.findXqId(wyName);
			JSONObject jsonObject=new JSONObject() ;
			if(xqList!=null){
				jsonObject.put("xqli", xqList);
			}else{
				jsonObject.put("fail", null);
			}
			return jsonObject;
		}
		// 按条件查询
			 @RequestMapping("List")
			 @ResponseBody
			 public JSONObject findCondition(@RequestParam(value="pageNum",required=false)String pageNum,@RequestParam(value="limit",required=false)String limit,@RequestParam(value="wyName",required=false)String wyName,@RequestParam(value="xqName",required=false)String xqName) throws
			 Exception {
			 wyName=new String(wyName.getBytes("ISO-8859-1"),"utf-8")+"";
			 xqName=new String(xqName.getBytes("ISO-8859-1"),"utf-8")+"";

			 Pagination<XqInfo> page = new Pagination<XqInfo>();
			 JSONObject jsonObject=new JSONObject();
			 page=xqInfoService.findCondition(pageNum,limit,wyName,xqName);
			 jsonObject.put("Condition",page);
			 return jsonObject;
			
			 }
		 //到添加页面
		 @RequestMapping("toInsert")
		 public String toInsert()
		 {
			return "xqAdd";
		 }
		 //添加
		 @RequestMapping("Insert")
		 public String Insert(XqInfo xqInfo)
		 {
			 xqInfoService.Insert(xqInfo);
			return "xqsuccess";
		 }
		 //到修改页面
		 @RequestMapping("toUpdate")
		 public String toUpdate(HttpSession session,@Param("id")Integer id,XqInfo xqInfo,HttpServletRequest request)
		 {
			 xqInfo = this.xqInfoService.findById(id);
			 if (id != 0 && id != null) {
					//向日志表添加操做
				 Rz rz=new Rz();
					rz.setCzr((String)session.getAttribute("userName"));//获取操作人
					rz.setCz("添加小区名为:"+xqInfo.getXqName()+"的信息");//获取操作内容
					rz.setCzsj(new Date());//获取操作时间
					rzService.insert(rz);
					xqInfo = this.xqInfoService.findById(id);
					request.setAttribute("xq",xqInfo);
				}
			return "xqEdit";
		 }
		 //修改
		 @RequestMapping("Update")
		 public String Update(HttpSession session,XqInfo xqInfo,@Param("id")Integer id)
		 {
			 if (id != null && id != 0) {
				 if (xqInfo != null) {
					 xqInfoService.update(xqInfo);
						//向日志表添加操做
					 Rz rz=new Rz();
						rz.setCzr((String)session.getAttribute("userName"));//获取操作人
						rz.setCz("修改小区名为:"+xqInfo.getXqName()+"的信息");//获取操作内容
						rz.setCzsj(new Date());//获取操作时间
						rzService.insert(rz);
					 return "xqsuccess";
				 }
			 }
			return "xqEdit";
			 
		 }
		 //删除
		 @RequestMapping("Delete")
		 public String Delete(@Param("ids")String ids)
		 {
			 xqInfoService.delete(ids);
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
