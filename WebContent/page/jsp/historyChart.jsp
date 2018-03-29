<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="page/css/fmhistorypage.css" />
<link rel="stylesheet" type="text/css"
	href="page/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css"
	href="page/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css"
	href="page/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css"
	href="page/static/h-ui.admin/skin/blue/skin.css" />
<link rel="stylesheet" type="text/css"
	href="page/lib/my-iconfont/1.0.8/iconfont.css" />
<style type="text/css">
.charts_grid {
	width: 90%;
	height: 90%;
	border: 1px solid #CCCCCC;
	margin: 20px;
}
</style>
<script src="page/js/selectmenu.js"></script>
<script type="text/javascript" src="page/js/jquery-2.2.2.js"></script>
<script src="page/js/jquery-2.2.2.min.js"></script>
<script src="page/echarts-3.5.3/echarts.min.js"></script>
<script type="text/javascript"> 
/*页面加载就开始执行js*/
$(document).ready(function() {
	$("#xqNameId").change(
	function(){	
	  $.get("CgqChart/findcgqId.action?xqName="+ $("#xqNameId").val(),function(data) {
		var dd=data;
		var d=dd.xqlist;
		$("#cgqId option:gt(0)").remove();
		for(var i=0;i<d.length;i++){
			var cgqId=d[i].cgq.cgqId;
			var opt="<option value='"+cgqId+"'>"+cgqId+"</option>";
			$("#cgqId").append(opt);
		}
		});
	});
	
});

function getSelectText() {
	var xqName = $('#xqNameId').val();
	var cgqId = $('#cgqId').val();
 	if(cgqId==="--选择传感器--"){
 		alert("--请选择传感器--");
 	}else{
	location.href = "CgqChart/goHistoryLine.action?cgqId="+cgqId;
	}
}
 </script>
</head>
<body>
	<div class="panel-body">

		<label for="xqNameId">选择小区</label> 
			<select id="xqNameId" name="xqName" class="input-text" style="width: 150px; margin-right: 15px;">
				
				<c:if test="${!empty xqInfos }">
				<option>--选择小区名称--</option>
					<c:forEach items="${xqInfos}" var="xqInfos">
						<option value="${xqInfos.xqName}">${xqInfos.xqName}</option>
					</c:forEach>
				</c:if>
			</select> 
			&nbsp;&nbsp;&nbsp; 
			<label for="cgqId">传感器id</label> 
			<select name="cgqId" id="cgqId" class="input-text" style="width: 150px; margin-right: 15px;">
				<option>--选择传感器--</option>
			</select> 
<!-- <input name="cgqId" id="cgqId" type="text" class="input-text" style="width: 150px; margin-right: 15px;"> -->
<button type="button"  onclick="getSelectText();" class="btn btn-success radius"> 
				<i class="Hui-iconfont">&#xe665;</i> 搜索
			</button>
			
			</div>
<br>
<br>


<div id="history" class="charts_grid" style="width: 1200px; height: 550px; margin: 0 auto"></div>
</body>
<script src="page/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
window.onresize = function(){
myChart.resize();
}
</script>
<script type="text/javascript">
var date = new Date();
var time = date.toLocaleDateString();
var myChart = echarts.init(document.getElementById('history'));
var option = {
title: {
    text: '传感器温度记录',   
    subtext: time
},
tooltip: {
    trigger: 'axis'
},
legend: {
    data:[] 
},
toolbox: {
    show: true,
    feature: {
        magicType: {type: ['line', 'bar']},
        saveAsImage: {}
    }
},
dataZoom: [
    {   type: 'slider', 
        start: 10,      
        end: 60       
    },
    {    type: 'inside',
        start: 10,  
        end: 60  
    }
],
xAxis:  {
    type: 'category',
    boundaryGap: false,
    data: []
},
yAxis: {
    type: 'value',
    axisLabel: {
        formatter: '{value} '
    },
    splitNumber:10
},
series: []
};
myChart.setOption(option);
myChart.showLoading();
var seriess=new Array();
var xAxiss=new Array();
var legends=new Array();
var cgqId = $('#cgqId').val();
$.ajax({
	type:'GET',
	async: true,
	url:'CgqChart/historyLine.action',
	data:{cgqId:"${cgq}"},
	dataType:'json',
	success:function(result) {
		console.log(result.xy);
		xAxiss=result.xy;
	 $.each(result.data, function(index, comment){
	   legends[index]=comment.name;
	        seriess.push({
            name: comment.name,
            type : 'line',
            data: comment.data,
            markPoint: {
            data: [
                {type: 'max', name: '最大值'},
                {type: 'min', name: '最小值'}
            ]
        },
            markLine: {
            data: [
                {type: 'average', name: '平均值'}
            ]
        }
           });
      });
      myChart.hideLoading();
	  myChart.setOption({
		    legend:{
		        data : legends
		    },
			xAxis : {
				data : xAxiss
			},
			series : seriess
		});
    },
	error : function(errorMsg) {
        myChart.hideLoading();
		alert("图表请求数据失败!");
	}
	});
</script>

</html>