<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
 <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
html{height:100%}
body{height:100%;margin:0px;padding:0px}
#container{height:100%}
</style>
  <!--引入地图包，地图包网址的ak属性是你在百度地图开放平台上申请的秘钥-->
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=8tTBRfxZ7ZfZZkKhBs4D8en3gULn9AMo"></script>
  <!--引入jquery-->
  <script src="http://lib.sinaapp.com/js/jquery/1.9.1/jquery-1.9.1.min.js"></script>
<title>地址解析</title>

</head>
<body>
<div id="container"></div>
<script type="text/javascript">

$(document).ready(function(){

var map = new BMap.Map("container"); // 创建地图实例
// var point = new BMap.Point("义马市"); // 创建点坐标 郑州市坐标113.631349, 34.753488
map.centerAndZoom("义马市新区客运站", 15); // 初始化地图，设置中心点坐标和地图级别
 map.enableScrollWheelZoom();
map.addControl(new BMap.NavigationControl());
map.addControl(new BMap.ScaleControl());
map.addControl(new BMap.OverviewMapControl());
map.addControl(new BMap.MapTypeControl());
var dw=new BMap.GeolocationControl({enableAutoLocation:true});//自动定位控件
map.addControl(dw);
var dwxx=dw.GeolocationControl;
 dw.addEventListener("locationSuccess",function(){
alert(dwxx);
});
dw.addEventListener("locationError",function(){
alert("定位失败");
}); 
	$.ajax({
		url :"findwd.action",
		async : false,
		dataType : "json",
		success : function(data) {
			var d = data.maplist;
				var pointArray = new Array();
			for(var i=0;i<d.length;i++){
				var marker = new BMap.Marker(new BMap.Point(d[i].lng, d[i].lat)); // 创建点
				map.addOverlay(marker);    //增加点
				var labe = new BMap.Label(d[i].xqName+d[i].cgq.wd+"℃",{offset:new BMap.Size(20,-10)});
				marker.setLabel(labe);
				pointArray[i] = new BMap.Point(d[i].lng, d[i].lat);
			}
		}
		});
})



</script>
</body>

</html>
