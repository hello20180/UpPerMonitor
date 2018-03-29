package com.hnzy.per.wdjk.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.hnzy.per.wdjk.pojo.CGQInfo;
import com.hnzy.per.wdjk.pojo.XqInfo;
import com.hnzy.per.wdjk.service.CGQInfoService;
import com.hnzy.per.wdjk.service.XqInfoService;
@Controller
@RequestMapping("Chart")
public class WDChartController
{
	@Autowired
	private CGQInfoService cgqInfoService;
	@Autowired
	private XqInfoService xqInfoService;

	private List<CGQInfo> cgqInfos;
	private List<XqInfo> xqInfos;
	//获取所有的小区
		@RequestMapping("find")
		public String find(HttpServletRequest request){	

			xqInfos=xqInfoService.findXqName();
			request.setAttribute("xqInfos",xqInfos );
			return "wdChart";		
		}

		// 阀门开度
		@RequestMapping("/cgqWd")
		@ResponseBody
		public Map<String, Object> cgqWd(HttpServletResponse response, HttpServletRequest request,@Param("xqName")String xqName,int apiContentStr[])
				throws UnsupportedEncodingException
		{
			xqName = new String(xqName.getBytes("ISO-8859-1"), "utf-8") + "";
		
			Map<String, Object> map = new HashMap<String, Object>();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			JSONArray members = new JSONArray();
			for (int i = 0; i < apiContentStr.length; i++)
			{
				if (apiContentStr[i] == 1)
				{
					JSONArray member = new JSONArray();
					//开度100
					int chartA = cgqInfoService.findWDA(xqName);
					member.add("温度40以上");
					member.add(chartA);
					members.add(member);
					map.put("chartA",chartA );
				}
				if (apiContentStr[i] == 2)
				{
					//开度75以上
					int chartB = cgqInfoService.findWDB(xqName);
					JSONArray member = new JSONArray();
					request.setAttribute("chartB", chartB);
					member.add("温度30-39");
					member.add(chartB);
					members.add(member);
					map.put("chartB",chartB );
				}
				if (apiContentStr[i] == 3)
				{
					//开度55-75
					int chartC = cgqInfoService.findWDC(xqName);
					JSONArray member = new JSONArray();
					request.setAttribute("chartC", chartC);
					member.add("温度15-29");
					member.add(chartC);
					members.add(member);
					map.put("chartC",chartC );
				}
				if (apiContentStr[i] == 4)
				{
					//开度35-55
					int chartD = cgqInfoService.findWDD(xqName);
					JSONArray member = new JSONArray();
					request.setAttribute("chartD", chartD);
					member.add("温度15以下");
					member.add(chartD);
					members.add(member);
					map.put("chartD",chartD );
				}
				

			}
			map.put("data", members);
			return map;

		}

		public List<CGQInfo> getCgqInfos()
		{
			return cgqInfos;
		}

		public void setCgqInfos(List<CGQInfo> cgqInfos)
		{
			this.cgqInfos = cgqInfos;
		}

		public List<XqInfo> getXqInfos()
		{
			return xqInfos;
		}

		public void setXqInfos(List<XqInfo> xqInfos)
		{
			this.xqInfos = xqInfos;
		}

}
