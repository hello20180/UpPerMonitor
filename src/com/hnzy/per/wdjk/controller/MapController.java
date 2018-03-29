package com.hnzy.per.wdjk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hnzy.per.wdjk.pojo.XqInfo;
import com.hnzy.per.wdjk.service.XqInfoService;

import net.sf.json.JSONArray;


@Controller
@RequestMapping("map")
public class MapController
{
	@Autowired
	private XqInfoService xqInfoService;
	private List<XqInfo> xqList;
	@RequestMapping("main")
	public ModelAndView main()
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("map");
		return mav;
		
	}
	//在地图上显示标记并显示平均温度
		@RequestMapping("findwd")
		@ResponseBody
		public JSONObject findwd(){
			 xqList=xqInfoService.findwd();
			 
			 System.out.println(xqList.size());
			 JSONObject jsonObject=new JSONObject();
//			jsonArray.add(JSONArray.fromObject(xqList));
			if(xqList!=null){
				jsonObject.put("maplist", xqList);
			}else{
				jsonObject.put("fail", null);
			}
			return jsonObject;
		}

			
	
}
