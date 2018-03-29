package com.hnzy.per.wdjk.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hnzy.per.socket.server.ServerHandler;
import com.hnzy.per.socket.server.ServerSessionMap;
import com.hnzy.per.socket.util.CzUtil;
import com.hnzy.per.socket.util.MapUtils;
import com.hnzy.per.util.Pagination;
import com.hnzy.per.wdjk.pojo.CGQInfo;
import com.hnzy.per.wdjk.pojo.QGInfo;
import com.hnzy.per.wdjk.pojo.QgState;
import com.hnzy.per.wdjk.pojo.Rz;
import com.hnzy.per.wdjk.pojo.XqInfo;
import com.hnzy.per.wdjk.service.QgInfoService;
import com.hnzy.per.wdjk.service.QgStateService;
import com.hnzy.per.wdjk.service.RzService;
import com.hnzy.per.wdjk.service.XqInfoService;

@Controller
@RequestMapping("QgTat")
public class QgStateController
{
	private final static Logger log = LoggerFactory.getLogger(QgStateController.class);
	ServerSessionMap sessionMap = ServerSessionMap.getInstance();

	@Autowired
	private QgInfoService qginfoService;

	@Autowired
	private XqInfoService xqInfoService;
	@Autowired
	private RzService rzService;
	private List<QgState> listQgState;
	private List<XqInfo> xqlist;
	private List<XqInfo> xqList;
	String param = null;
	
	//列表页面
	@RequestMapping("find")
	public ModelAndView find(@RequestParam(value = "pageNum", required = false) String pageNum,
			@RequestParam(value = "limit", required = false) String limit)
	{
		ModelAndView mav = new ModelAndView();
		xqlist = xqInfoService.findXqName();
		mav.addObject("xqlist", xqlist);
		Pagination<QGInfo> page = new Pagination<QGInfo>();
		page=qginfoService.findAll1(pageNum, limit);
		mav.setViewName("qgStateList");
		mav.addObject("page", page);
		return mav;
	}

	// 根据小区名称获取区管id
	@RequestMapping("findqgId")
	@ResponseBody
	public JSONObject findqgId(String xqName) throws UnsupportedEncodingException
	{
		xqName = new String(xqName.getBytes("ISO-8859-1"), "utf-8") + "";
		xqList = xqInfoService.findqgId(xqName);
		JSONObject jsonObject = new JSONObject();
		if (xqList != null)
		{
			jsonObject.put("qglist", xqList);
		} else
		{
			jsonObject.put("fail", null);
		}
		return jsonObject;
	}

	// 按条件查询
	@RequestMapping("List")
	@ResponseBody
	public JSONObject findCondition(@RequestParam(value = "pageNum", required = false) String pageNum,
			@RequestParam(value = "limit", required = false) String limit,
			@RequestParam(value = "xqName", required = false) String xqName,
			@RequestParam(value = "qgId", required = false) String qgId) throws Exception
	{
		xqName = new String(xqName.getBytes("ISO-8859-1"), "utf-8") + "";
		qgId = new String(qgId.getBytes("ISO-8859-1"), "utf-8") + "";
		Pagination<QGInfo> page = new Pagination<QGInfo>();
		JSONObject jsonObject = new JSONObject();
		page = qginfoService.findCondition1(pageNum, limit, xqName, qgId);
		jsonObject.put("Condition", page);
		return jsonObject;

	}
	
	//到修改433页面
	@RequestMapping("toUpdate433")
	 public String toUpdate(@Param("id")Integer id,QGInfo qginfo,HttpServletRequest request)
	 {
		 if (id != 0 && id != null) {
			 qginfo = qginfoService.findById(id);
			 request.setAttribute("Ad",qginfo);
			}
		return "qg433Edit";
	 }
	 
	 //发送指令修改433无线地址
	@RequestMapping("upd")
	@ResponseBody
		public JSONObject update(QGInfo qginfo,@Param("ids") Integer ids,@Param("wxAd") String wxAd) {
		JSONObject sfString = null;
		// 区管ID
		qginfo= qginfoService.findById1(ids);
		sfString = up(qginfo,wxAd);
		return sfString;
			
		}
	
	public JSONObject up(QGInfo qginfo,String wxAd) {
		
		param = "upd";
		MapUtils.getMapUtils().add("param", param);
		MapUtils.getMapUtils().add("wxAd", wxAd);
		String qgId = qginfo.getQgId();
		String wx=wxAd.substring(0, 2);
		String wxA=wxAd.substring(2, 4);
//		 把qgId转换为int类型
		int fInteger = Integer.valueOf(qgId);
		int AdInteger = Integer.valueOf(wx);
		int AdInteger1 = Integer.valueOf(wxA);
		
		// qgId十进制转换为十六进制位数不足在前补0
		String qgd =algorismToHEXString(fInteger,4);
		String Ad = algorismToHEXString(AdInteger,2);
		String Ad1 = algorismToHEXString(AdInteger1,2);
		// 区管IP
		String ip = qginfo.getXq().getIp();
		// 端口号
		String port = qginfo.getXq().getPortNum();
		// IP地址和端口号
		String pt = "/" + ip + ":" + port;
		// fmId十进制
		String ja ="F007A6"+Ad+Ad1;
		log.info("读传感器地址发送数据："+ja);
		boolean sessionmap = cz(qgd,ja, pt);
//		boolean sessionmap = cz1(ja, pt);
		try {
			Thread.sleep(1000);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = new JSONObject();
		if (sessionmap == true &&  MapUtils.getMapUtils().get("upd")!=null && MapUtils.getMapUtils().get("upd").equals("success")) {
			String qgID= MapUtils.getMapUtils().get("sub");
			jsonObject.put("qgID", qgID);
			jsonObject.put("js", "0");
			MapUtils.getMapUtils().add("upd", null);
			return jsonObject;
		} else if (sessionmap == true &&  MapUtils.getMapUtils().get("sb")!=null && MapUtils.getMapUtils().get("sb").equals("fail")) {
			String qgID= MapUtils.getMapUtils().get("sub");
			jsonObject.put("qgID", qgID);
			jsonObject.put("js", "1");
			MapUtils.getMapUtils().add("sb", null);
			return jsonObject;
		}
		else {
			jsonObject.put("qgID", qgId);
			jsonObject.put("js", "2");
		}
		return jsonObject;
	}

	 //发送指令区管Id
	@RequestMapping("upqg")
	@ResponseBody
		public JSONObject updateQg(QGInfo qginfo,@Param("ids") Integer ids) {
		JSONObject sfString = null;
		// 区管ID
		qginfo= qginfoService.findById1(ids);
		sfString = upQg(qginfo);
		return sfString;
			
		}
	
	public JSONObject upQg(QGInfo qginfo) {
		
		param = "upqg";
		MapUtils.getMapUtils().add("param", param);
		String qgId = qginfo.getQgId();
		MapUtils.getMapUtils().add("qgId", qgId);
		// 把qgId转换为int类型
		int fInteger = Integer.valueOf(qgId);
		// qgId十进制转换为十六进制位数不足在前补0
		String qgd =algorismToHEXString(fInteger,4);
	
		// 区管IP
		String ip = qginfo.getXq().getIp();
		// 端口号
		String port = qginfo.getXq().getPortNum();
		// IP地址和端口号
		String pt = "/" + ip + ":" + port;
		// fmId十进制
		String ja ="F007A8"+qgd;
		log.info("读传感器地址发送数据："+ja);
		boolean sessionmap = cz(qgd,ja, pt);
//		boolean sessionmap = cz1(ja, pt);
		try {
			Thread.sleep(1000);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		JSONObject jsonObject = new JSONObject();
		if (sessionmap == true &&  MapUtils.getMapUtils().get("upqg")!=null && MapUtils.getMapUtils().get("upqg").equals("success")) {
			String qgID= MapUtils.getMapUtils().get("qgId");
			jsonObject.put("qgID", qgID);
			jsonObject.put("js", "0");
			MapUtils.getMapUtils().add("upqg", null);
			return jsonObject;
		} else if (sessionmap == true &&  MapUtils.getMapUtils().get("sb")!=null && MapUtils.getMapUtils().get("sb").equals("fail")) {
			jsonObject.put("qgID", qgId);
			jsonObject.put("js", "1");
			MapUtils.getMapUtils().add("sb", null);
			return jsonObject;
		}  
		else {
			jsonObject.put("qgID", qgId);
			jsonObject.put("js", "2");
		}
		return jsonObject;
	}
	
	
	
	
	// 抽取相同部分
	public boolean cz1(String ja, String pt) {
		// 把十六进制数，转换为十进制相加
		int jia = CzUtil.FsZh(ja);
		// 十进制转换为十六进制
		String hex = Integer.toHexString(jia);
		
		StringBuffer stringBuffer = new StringBuffer();
		// 转换为大写
		if (hex != null) {
			for (int i = 0; i < hex.length(); i++) {
				char c = hex.charAt(i);
				if (!Character.isDigit(c)) {
					stringBuffer.append(Character.toUpperCase(c));
				} else {
					stringBuffer.append(c);
				}
			}
		}
		String sH = stringBuffer.toString();
		// 截取相加结果后两位
		String je = null;
		for (int j = 0; j < sH.length() - 1; j++) {
			je = sH.charAt(sH.length() - 2) + "" + sH.charAt(sH.length() - 1);
		}
		String[] keys = new String[] { pt };
		String mString =ja + "" + je + "FF";
		// 解码
		byte[] b = CzUtil.jm(mString);
		ServerSessionMap sessionMap = ServerSessionMap.getInstance();
		boolean sessionmap = sessionMap.sendMessage(keys, b);
		return sessionmap;
	}
	
	// 测距模式
	@RequestMapping("cjms")
	@ResponseBody
	public JSONObject cjms(QGInfo qginfo,@Param("ids") Integer ids) {
		JSONObject sfString = null;
		// 区管ID
		qginfo= qginfoService.findById1(ids);
		sfString = cj(qginfo);
		return sfString;
	}

	public JSONObject cj(QGInfo qginfo) {
		
		param = "cjms";
		MapUtils.getMapUtils().add("param", param);
		String qgId = qginfo.getQgId();
		// 把qgId转换为int类型
		int fInteger = Integer.valueOf(qgId);
		// qgId十进制转换为十六进制位数不足在前补0
		String qgd =algorismToHEXString(fInteger,4);
		// 区管IP
		String ip = qginfo.getXq().getIp();
		// 端口号
		String port = qginfo.getXq().getPortNum();
		// IP地址和端口号
		String pt = "/" + ip + ":" + port;
		// fmId十进制
		String ja ="F006A4E0";
		log.info("读传感器地址发送数据："+ja);
		boolean sessionmap = cz(qgd,ja, pt);
//		boolean sessionmap = cz1(ja, pt);
		try {
			Thread.sleep(1000);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = new JSONObject();
		if (sessionmap == true &&  MapUtils.getMapUtils().get("cjms")!=null && MapUtils.getMapUtils().get("cjms").equals("success")) {
			String qgID= MapUtils.getMapUtils().get("sub");
			jsonObject.put("qgID", qgID);
			jsonObject.put("js", "0");
			MapUtils.getMapUtils().add("cjms", null);
			return jsonObject;
		} else if (sessionmap == true &&  MapUtils.getMapUtils().get("sb")!=null && MapUtils.getMapUtils().get("sb").equals("fail")) {
			String qgID= MapUtils.getMapUtils().get("sub");
			jsonObject.put("qgID", qgID);
			jsonObject.put("js", "1");
			MapUtils.getMapUtils().add("sb", null);
			return jsonObject;
		} 
//			else if (sessionmap == true && MapUtils.getMapUtils().get("cs")!=null  && MapUtils.getMapUtils().get("cs").equals("超时")
//			|| sessionmap == true && MapUtils.getMapUtils().get("cjms") == null) {
//			jsonObject.put("qgID", qgId);
//			jsonObject.put("js", "2");
//			MapUtils.getMapUtils().add("cs", null);
//			return jsonObject;
//		} 
			else {
			jsonObject.put("qgID", qgId);
			jsonObject.put("js", "3");
		}
		return jsonObject;
	}
	
	// 修改为收发数据模式
	@RequestMapping("sfms")
	@ResponseBody
	public JSONObject sfms(QGInfo qginfo,@Param("ids") Integer ids) {
		JSONObject sfString = null;
		// 区管ID
		qginfo= qginfoService.findById1(ids);
		sfString = sf(qginfo);
		return sfString;

	}

	public JSONObject sf(QGInfo qginfo) {
		param = "sfms";
		MapUtils.getMapUtils().add("param", param);
		String qgId = qginfo.getQgId();
		// 把qgId转换为int类型
		int fInteger = Integer.valueOf(qgId);
		// qgId十进制转换为十六进制位数不足在前补0
		String qgd =algorismToHEXString(fInteger,4);
		// 区管IP
		String ip = qginfo.getXq().getIp();
		// 端口号
		String port = qginfo.getXq().getPortNum();
	
		// IP地址和端口号
		String pt = "/" + ip + ":" + port;
		// fmId十进制
		String ja ="F006A40E";
		log.info("读传感器地址发送数据："+ja);
		boolean sessionmap = cz(qgd,ja, pt);
//		boolean sessionmap = cz1(ja, pt);
		try {
			Thread.sleep(1000);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = new JSONObject();
		if (sessionmap == true &&  MapUtils.getMapUtils().get("sfms")!=null && MapUtils.getMapUtils().get("sfms").equals("success")) {
			String qgID= MapUtils.getMapUtils().get("sub");
			jsonObject.put("qgID", qgID);
			jsonObject.put("js", "0");
			MapUtils.getMapUtils().add("sfms", null);
			return jsonObject;
		} else if (sessionmap == true &&  MapUtils.getMapUtils().get("sb")!=null && MapUtils.getMapUtils().get("sb").equals("fail")) {
			String qgID= MapUtils.getMapUtils().get("sub");
			jsonObject.put("qgID", qgID);
			jsonObject.put("js", "1");
			MapUtils.getMapUtils().add("sb", null);
			return jsonObject;
		}else {
			jsonObject.put("qgID", qgId);
			jsonObject.put("js", "3");
		}
		return jsonObject;
	}

	// 读取区管工作模式
	@RequestMapping("du")
	@ResponseBody
	public JSONObject du(QGInfo qginfo,@Param("id") String id) {
		qginfo= qginfoService.findById1(id);
		String qgId=qginfo.getQgId();
		// 把qgId转换为int类型
		int fInteger = Integer.valueOf(qgId);
		// qgId十进制转换为十六进制位数不足在前补0
		String qgd =algorismToHEXString(fInteger,4);
		
		// 区管IP
		String ip = qginfo.getXq().getIp();
		// 端口号
		String port = qginfo.getXq().getPortNum();
				// IP地址和端口号
				String pt = "/" + ip + ":" + port;
				String ja ="F005A2";
				log.info("读传感器地址发送数据："+ja);
				boolean sessionmap = cz(qgd,ja, pt);
//				boolean sessionmap = cz1(ja, pt);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				JSONObject jsonObject = new JSONObject();
				System.out.println("MapUtils====="+ MapUtils.getMapUtils().get("suc"));
				if(sessionmap == true && MapUtils.getMapUtils().get("suc")!=null && MapUtils.getMapUtils().get("suc").equals("success")){
					MapUtils.getMapUtils().add("suc", null);
					jsonObject.put("js", "0");
					String qgID= MapUtils.getMapUtils().get("qgID");
					String ms = MapUtils.getMapUtils().get("ms");
					String Ad433= MapUtils.getMapUtils().get("Ad433");
					jsonObject.put("ms", ms);
					jsonObject.put("qgID", qgID);
					log.info("qgID======="+qgID);
					jsonObject.put("ms", ms);
					jsonObject.put("Ad433", Ad433);
					log.info("Ad433======="+Ad433);
					return jsonObject;
				}else if (sessionmap == true &&  MapUtils.getMapUtils().get("sb")!=null && MapUtils.getMapUtils().get("sb").equals("fail")) {
					MapUtils.getMapUtils().add("sb", null);
					jsonObject.put("js", "1");
					String qgID= MapUtils.getMapUtils().get("qgID");
					jsonObject.put("qgID", qgID);
					return jsonObject;
				}else {
					jsonObject.put("qgID", qgId);
					jsonObject.put("js", "3");
				}
				
				return jsonObject;
	}

	// 抽取相同部分
	public boolean cz(String qgd,String ja, String pt) {
		// 把十六进制数，转换为十进制相加
		int jia = CzUtil.FsZh(ja);
		// 十进制转换为十六进制
		String hex = Integer.toHexString(jia);
		
		StringBuffer stringBuffer = new StringBuffer();
		// 转换为大写
		if (hex != null) {
			for (int i = 0; i < hex.length(); i++) {
				char c = hex.charAt(i);
				if (!Character.isDigit(c)) {
					stringBuffer.append(Character.toUpperCase(c));
				} else {
					stringBuffer.append(c);
				}
			}
		}
		String sH = stringBuffer.toString();
		// 截取相加结果后两位
		String je = null;
		for (int j = 0; j < sH.length() - 1; j++) {
			je = sH.charAt(sH.length() - 2) + "" + sH.charAt(sH.length() - 1);
		}
		String[] keys = new String[] { pt };
		String mString =qgd+""+ ja + "" + je + "FF";
		System.out.println("mString======="+mString);
		// 解码
		byte[] b = CzUtil.jm(mString);
		ServerSessionMap sessionMap = ServerSessionMap.getInstance();
		boolean sessionmap = sessionMap.sendMessage(keys, b);
		return sessionmap;
	}
/**
 * 将十进制转为指定长度的十六进制字符串
 * algorism 十进制数字
 * maxlength转换后的十六进制字符串
 * 
 * return 转换后的结果
 * 
 * **/
	public static String algorismToHEXString(int algorism,int maxLength)
	{
		String result="";
		result=Integer.toHexString(algorism);
		if (result.length()%2==1)
		{
			result="0"+result;
		}
		return patchHexString(result.toUpperCase(),maxLength);
		
	}
	
	/**
	 * Hex字符串前补0，主要用于长度位数不足
	 * str 需要补充长度的十六进制字符串
	 * maxlength 补充后十六进制字符串的长度
	 * return 补充后的结果
	 * */

	private static String patchHexString(String str, int maxLength)
	{
		String temp="";
		for (int i = 0; i < maxLength-str.length(); i++)
		{
			temp="0"+temp;
		}
		str=(temp+str).substring(0, maxLength);
		return str;
	}

	public List<XqInfo> getXqList()
	{
		return xqList;
	}

	public void setXqList(List<XqInfo> xqList)
	{
		this.xqList = xqList;
	}

	public List<QgState> getListQgState()
	{
		return listQgState;
	}

	public void setListQgState(List<QgState> listQgState)
	{
		this.listQgState = listQgState;
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
