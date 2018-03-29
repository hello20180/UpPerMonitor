package com.hnzy.per.wdjk.controller;

import java.io.UnsupportedEncodingException;
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
import com.hnzy.per.wdjk.pojo.QGInfo;
import com.hnzy.per.wdjk.pojo.XqInfo;
import com.hnzy.per.wdjk.service.QgInfoService;
import com.hnzy.per.wdjk.service.RzService;
import com.hnzy.per.wdjk.service.XqInfoService;

@Controller
@RequestMapping("QgCon")
public class QgInfoController
{
	@Autowired
	private QgInfoService qgInfoService;
	@Autowired
	private XqInfoService xqInfoService;
	@Autowired
	private RzService rzService;
	private List<XqInfo> xqlist;

	//获取小区名字并分页显示数据
		@RequestMapping("find")
		public ModelAndView find(@RequestParam(value = "pageNum", required = false) String pageNum,
				@RequestParam(value = "limit", required = false) String limit)
		{
			ModelAndView mav = new ModelAndView();
			xqlist=xqInfoService.findXqName();
			Pagination<QGInfo> page = qgInfoService.findAll(pageNum, limit);
			mav.setViewName("qgList");
			mav.addObject("xqlist", xqlist);
			mav.addObject("page", page);
			return mav;
		}
		
		// 按条件查询
			 @RequestMapping("List")
			 @ResponseBody
			 public JSONObject findCondition(@RequestParam(value="pageNum",required=false)String pageNum,@RequestParam(value="limit",required=false)String limit,@RequestParam(value="xqName",required=false)String xqName) throws
			 Exception {
			 xqName=new String(xqName.getBytes("ISO-8859-1"),"utf-8")+"";
			 Pagination<QGInfo> page = new Pagination<QGInfo>();
			 JSONObject jsonObject=new JSONObject();
			 page=qgInfoService.findCondition(pageNum,limit,xqName);
			 jsonObject.put("Condition",page);
			 return jsonObject;
			
			 }
		 //到添加页面
		 @RequestMapping("toInsert")
		 public String toInsert(HttpServletRequest request)
		 {
			 xqlist=xqInfoService.findXqName();
			 request.setAttribute("xq", xqlist);
			return "qgAdd";
		 }
		 
		// 根据小区名称获取小区id
			@RequestMapping("findxqId")
			@ResponseBody
			public JSONObject findxqId(String xqName) throws UnsupportedEncodingException
			{
				xqName = new String(xqName.getBytes("ISO-8859-1"), "utf-8") + "";
				xqlist = xqInfoService.findxqId(xqName);
				JSONObject jsonObject = new JSONObject();
				if (xqlist != null)
				{
					jsonObject.put("xqI", xqlist);
				} else
				{
					jsonObject.put("fail", null);
				}
				return jsonObject;
			}
		 
		 
		 //添加
		 @RequestMapping("Insert")
		 public String Insert(HttpSession session,QGInfo qgInfo)
		 {	//向日志表添加操做
//			 Rz rz=new Rz();
//				rz.setCzr((String)session.getAttribute("userName"));//获取操作人
//				rz.setCz("添加区管ID:"+qgInfo.getQgId()+"的信息");//获取操作内容
//				rz.setCzsj(new Date());//获取操作时间
//				rzService.insert(rz);
			 qgInfoService.Insert(qgInfo);
			return "qgsuccess";
		 }
		 //到修改页面
		 @RequestMapping("toUpdate")
		 public String toUpdate(@Param("id")Integer id,QGInfo qgInfo,HttpServletRequest request)
		 {
			 
			 qgInfo = this.qgInfoService.findById(id);
			 if (id != 0 && id != null) {
					qgInfo = this.qgInfoService.findById(id);
					request.setAttribute("qg",qgInfo);
				}
			return "qgEdit";
		 }
		 //修改
		 @RequestMapping("Update")
		 public String Update(HttpSession session,QGInfo qgInfo,@Param("id")Integer id)
		 {
			 if (id != null && id != 0) {
				 if (qgInfo != null) {
//					//向日志表添加操做
//					 Rz rz=new Rz();
//						rz.setCzr((String)session.getAttribute("userName"));//获取操作人
//						rz.setCz("修改区管ID:"+qgInfo.getQgId()+"的信息");//获取操作内容
//						rz.setCzsj(new Date());//获取操作时间
//						rzService.insert(rz);
					 qgInfoService.update(qgInfo);
					 return "qgsuccess";
				 }
			 }
			return "qgEdit";
			 
		 }
		 //删除
		 @RequestMapping("Delete")
		 public String Delete(@Param("ids")String ids)
		 {
			 qgInfoService.delete(ids);
				return "redirect:find.action";
			 
		 }

		public List<XqInfo> getXqlist()
		{
			return xqlist;
		}

		public void setXqlist(List<XqInfo> xqlist)
		{
			this.xqlist = xqlist;
		}

		 
}
