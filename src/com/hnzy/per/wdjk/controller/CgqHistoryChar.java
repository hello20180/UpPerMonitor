package com.hnzy.per.wdjk.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hnzy.per.util.LineDataModel;
import com.hnzy.per.wdjk.pojo.WdHistory;
import com.hnzy.per.wdjk.pojo.XqInfo;
import com.hnzy.per.wdjk.service.WdhistoryService;
import com.hnzy.per.wdjk.service.XqInfoService;

@Controller
@RequestMapping("CgqChart")
public class CgqHistoryChar {

	@Autowired
	private WdhistoryService wdhistoryService;
	@Autowired
	private XqInfoService xqInfoService;

	private List<WdHistory> wdHistories;
	private List<XqInfo> xqInfos;
	//获取所有的小区
	@RequestMapping("find")
	public String find(HttpServletRequest request){	

		xqInfos=xqInfoService.findXqName();
		request.setAttribute("xqInfos",xqInfos );
		return "historyChart";		
	}
	//根据小区名称获取传感器id
	@RequestMapping("findcgqId")
	@ResponseBody
	public JSONObject findcgqId(String xqName) throws UnsupportedEncodingException{
		 xqName=new String(xqName.getBytes("ISO-8859-1"),"utf-8")+"";
		xqInfos=xqInfoService.findcgqId(xqName);
		JSONObject jsonObject=new JSONObject() ;
		if(xqInfos!=null){
			jsonObject.put("xqlist", xqInfos);
		}else{
			jsonObject.put("fail", null);
		}
		return jsonObject;
	}

	//搜索出现图
	@RequestMapping("/goHistoryLine")
	public String goHistoryChart(HttpServletRequest request,@Param("cgqId")String cgqId) {
		try {
			cgqId=	new String(cgqId.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		WdHistory wdHistory=new WdHistory();
		wdHistory.setCgqId(cgqId);
		wdHistories = wdhistoryService.findChar(wdHistory);
		for(int i=0;i<wdHistories.size();i++)
		{
			String cgq= wdHistories.get(i).getCgqId();
			request.setAttribute("cgq", cgq);
		}
		xqInfos=xqInfoService.findXqName();
		request.setAttribute("xqInfos",xqInfos );
		return "historyChart";
	}

 //曲线图
   
	@RequestMapping("/historyLine")
	@ResponseBody
	public Map<String,Object> historyLine(String cgqId,HttpServletRequest request) throws ParseException { 
	
		String strDate ="2017-08-09 10:23:10";
		//String date = DateUtil.get2WeekAgoDateStr(DateUtil.YYYYMMDDHHMMSS);
		WdHistory wdHistory = new WdHistory();
		wdHistory.setCgqId(cgqId);
		wdHistory.setTime(strDate);;;
		wdHistories = this.wdhistoryService.findHistoryLine(wdHistory);
		List<LineDataModel> lines = new ArrayList<LineDataModel>();
		int count = wdHistories.size();
		Object[] wd = new Object[count];
		Object[] sd = new Object[count];
		Object[] dl = new Object[count];
		Object[] xy = new Object[count];
		for (int i = 0; i < count; i++) {
			WdHistory wdh = wdHistories.get(i);
			Integer WD=wdh.getWd();
			Integer SD=wdh.getSd();
			Integer DL=wdh.getDl();
			String record = wdh.getTime();
			record = record.substring(2, 16);
			wd[i] = WD;
			sd[i] = SD;
			dl[i] = DL;
			xy[i] = record;
		}
		LineDataModel model = new LineDataModel();
		model.setName("传感器温度");
		model.setData(wd);
		lines.add(model);
		
		LineDataModel mode3 = new LineDataModel();
		mode3.setName("传感器湿度");
		mode3.setData(sd);
		lines.add(mode3);
		
		LineDataModel mode2 = new LineDataModel();
		mode2.setName("传感器电量");
		mode2.setData(dl);
		lines.add(mode2);

		Map<String, Object> json = new HashMap<String, Object>();
		json.put("xy", xy);
		json.put("data", lines);
		return json;
	}

	public List<WdHistory> getWdHistories()
	{
		return wdHistories;
	}
	public void setWdHistories(List<WdHistory> wdHistories)
	{
		this.wdHistories = wdHistories;
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
