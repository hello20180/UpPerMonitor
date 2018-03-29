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
import com.alibaba.fastjson.JSONObject;
import com.hnzy.per.wdjk.pojo.CGQInfo;
import com.hnzy.per.wdjk.pojo.XqInfo;
import com.hnzy.per.wdjk.service.CGQInfoService;
import com.hnzy.per.wdjk.service.XqInfoService;

@Controller
@RequestMapping("WDSD")
public class WDSDChartController
{
	@Autowired
	private CGQInfoService cgqInfoService;
	@Autowired
	private XqInfoService xqInfoService;

	private List<CGQInfo> cgqInfos;
	private List<XqInfo> xqInfos;

	// 获取所有的小区
	@RequestMapping("find")
	public String find(HttpServletRequest request)
	{

		xqInfos = xqInfoService.findXqName();
		request.setAttribute("xqInfos", xqInfos);
		return "wdChartSd";
	}
	
	//散点图
	@RequestMapping("chartSearchSd")
	@ResponseBody
	public Map<String, Object>chartSearchSd(CGQInfo cgqInfo,HttpServletResponse response,HttpServletRequest request,@Param("xqName")String xqName) throws UnsupportedEncodingException{
		xqName=new String(xqName.getBytes("ISO-8859-1"),"utf-8")+"";
		cgqInfos=cgqInfoService.chartSearchSd(xqName);
		Map<String,Object> map=new HashMap<String,Object>(); 
		   JSONArray members = new JSONArray();
		   for(int i=0;i<cgqInfos.size();i++){
			   JSONArray member=new JSONArray();
			   member.add(cgqInfos.get(i).getId());
			   member.add(cgqInfos.get(i).getWd());
			   members.add(member);
		   }
		  map.put("data",members);
		return map;
	}

	//根据id查找具体信息
	@RequestMapping("findSd")
	@ResponseBody
	public JSONObject findSd(int id,CGQInfo cgqInfo){
		cgqInfo=cgqInfoService.findSd(id);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("cgqId", cgqInfo.getCgqId());
		jsonObject.put("xqName", cgqInfo.getXqName());
		jsonObject.put("buildNO", cgqInfo.getBuildNO());
		jsonObject.put("cellNO", cgqInfo.getCellNO());
		jsonObject.put("houseNO", cgqInfo.getHouseNO());
		jsonObject.put("wd", cgqInfo.getWd());
		jsonObject.put("dl", cgqInfo.getDl());
		return jsonObject;
		
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
