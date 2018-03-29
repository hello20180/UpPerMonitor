package com.hnzy.per.wdjk.controller;


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
import com.hnzy.per.wdjk.service.JZQInfoService;
import com.hnzy.per.wdjk.service.RzService;
import com.hnzy.per.wdjk.service.XqInfoService;

@Controller
@RequestMapping("JzqCon")
public class JZQInfoController
{
	@Autowired
	private JZQInfoService jzqInfoService;
	@Autowired
	private XqInfoService xqService;
	
	@Autowired
	private RzService rzService;

	//分页显示数据
		@RequestMapping("find")
		public ModelAndView find(@RequestParam(value = "pageNum", required = false) String pageNum,
				@RequestParam(value = "limit", required = false) String limit)
		{
			ModelAndView mav = new ModelAndView();
			Pagination<XqInfo> page = xqService.findAll(pageNum, limit);
			mav.setViewName("jzqList");
			mav.addObject("page", page);
			return mav;
		}
		
		// 按条件查询
			 @RequestMapping("List")
			 @ResponseBody
			 public JSONObject findCondition(@RequestParam(value="pageNum",required=false)String pageNum,@RequestParam(value="limit",required=false)String limit,@RequestParam(value="xqName",required=false)String xqName) throws
			 Exception {
				 xqName=new String(xqName.getBytes("ISO-8859-1"),"utf-8")+"";
			 Pagination<XqInfo> page = new Pagination<XqInfo>();
			 JSONObject jsonObject=new JSONObject();
			 page=xqService.findConditionIP(pageNum,limit,"%"+xqName+"%");
			 jsonObject.put("Condition",page);
			 return jsonObject;
			
			 }

		 //到修改页面
		 @RequestMapping("toUpdate")
		 public String toUpdate(@Param("id")Integer id,XqInfo xqInfo,HttpServletRequest request)
		 {
			 xqInfo = this.xqService.findById(id);
			 if (id != 0 && id != null) {
					xqInfo = this.xqService.findById(id);
					request.setAttribute("jzq",xqInfo);
				}
			return "jzqEdit";
		 }
		 //修改
		 @RequestMapping("Update")
		 public String Update(HttpSession session,XqInfo xqInfo,@Param("id")Integer id)
		 {
			 if (id != null && id != 0) {
				 if (xqInfo != null) {
//					//向日志表添加操做
//					 Rz rz=new Rz();
//						rz.setCzr((String)session.getAttribute("userName"));//获取操作人
//						rz.setCz("修改区管ID:"+jzqInfo.getJzqId()+"的信息");//获取操作内容
//						rz.setCzsj(new Date());//获取操作时间
//						rzService.insert(rz);
					 xqService.updateIP(xqInfo);
					 return "jzqsuccess";
				 }
			 }
			return "jzqEdit";
			 
		 }
//		 //删除
//		 @RequestMapping("Delete")
//		 public String Delete(@Param("ids")String ids)
//		 {
//			 jzqInfoService.delete(ids);
//				return "redirect:find.action";
//			 
//		 }
//		 //到添加页面
//		 @RequestMapping("toInsert")
//		 public String toInsert(HttpServletRequest request)
//		 {
//			return "jzqAdd";
//		 }
//		 //添加
//		 @RequestMapping("Insert")
//		 public String Insert(HttpSession session,JZQInfo jzqInfo)
//		 {	//向日志表添加操做
////			 Rz rz=new Rz();
////				rz.setCzr((String)session.getAttribute("userName"));//获取操作人
////				rz.setCz("添加集中器ID:"+jzqInfo.getJzqId()+"的信息");//获取操作内容
////				rz.setCzsj(new Date());//获取操作时间
////				rzService.insert(rz);
//				jzqInfoService.Insert(jzqInfo);
//			return "jzqsuccess";
//		 }
}
