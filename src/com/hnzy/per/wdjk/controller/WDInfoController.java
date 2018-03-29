package com.hnzy.per.wdjk.controller;

import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

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
import com.hnzy.per.wdjk.pojo.XqInfo;
import com.hnzy.per.wdjk.service.CGQInfoService;
import com.hnzy.per.wdjk.service.XqInfoService;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("WDInfo")
public class WDInfoController
{
	@Autowired
	private CGQInfoService cgqInfoService;
	private List<CGQInfo> cgqlist;
	@Autowired
	private XqInfoService xqInfoService;
	private List<XqInfo> xqList;

	// 进入列表页面时 下拉列表获取小区名字
	@RequestMapping("find")
	public ModelAndView find(@RequestParam(value = "pageNum", required = false) String pageNum,
			@RequestParam(value = "limit", required = false) String limit)
	{
		ModelAndView mav = new ModelAndView();
		xqList=xqInfoService.findXqName();
		Pagination<CGQInfo> page = cgqInfoService.findAllWd(pageNum, limit);
		mav.setViewName("wdjkxt");
		mav.addObject("cgq", xqList);
		mav.addObject("page", page);
		return mav;
	}

	// 按条件查询
	 @RequestMapping("List1")
	 public ModelAndView findCondition1(@RequestParam(value="pageNum",required=false)String pageNum,@RequestParam(value="limit",required=false)String limit,@RequestParam(value="xqName")String xqName) throws Exception
	 {
	 xqName=new String(xqName.getBytes("ISO-8859-1"),"utf-8")+"";
	 ModelAndView mav = new ModelAndView();
	 xqList=xqInfoService.findXqName();
	 Pagination<CGQInfo> page = new Pagination<CGQInfo>();
	 page=cgqInfoService.findConditionWd(pageNum,limit,xqName);
	 mav.addObject("cgq", xqList);
	 mav.setViewName("wdjkxt");
	 mav.addObject("page", page);
	 return mav;
	
	 }
	

	// 按条件查询
	 @RequestMapping("List")
	 @ResponseBody
	 public JSONObject findCondition(@RequestParam(value="pageNum",required=false)String pageNum,@RequestParam(value="limit",required=false)String limit,@RequestParam(value="xqName")String xqName) throws Exception
	 {
	 xqName=new String(xqName.getBytes("ISO-8859-1"),"utf-8")+"";
	 Pagination<CGQInfo> page = new Pagination<CGQInfo>();
	 JSONObject jsonObject=new JSONObject();
	 page=cgqInfoService.findConditionWd(pageNum,limit,xqName);
	 jsonObject.put("Condition",page);
	 return jsonObject;
	
	 }
	// 导出到Excel
	@RequestMapping("doExportExcel")
	public void doExportExcel(HttpServletResponse response, @Param("xqName") String xqName) throws Exception
	{
		xqName = new String(xqName.getBytes("ISO-8859-1"), "utf-8") + "";
		// 告诉浏览器要弹出的文档类型
		response.setContentType("application/x-execl");
		// 告诉浏览器这个文档作为附件给别人下载（放置浏览器不兼容，文件要编码）
		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String("温度监控系统信息.xls".getBytes(), "ISO-8859-1"));
		// 获取输出流
		ServletOutputStream outputStream = response.getOutputStream();
		cgqInfoService.exportExcel(cgqInfoService.findByNameWd(xqName), outputStream);
		if (outputStream != null)
		{
			outputStream.close();
		}

	}



		// 下方get()、set()

	public List<CGQInfo> getCgqlist()
	{
		return cgqlist;
	}


	public void setCgqlist(List<CGQInfo> cgqlist)
	{
		this.cgqlist = cgqlist;
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
