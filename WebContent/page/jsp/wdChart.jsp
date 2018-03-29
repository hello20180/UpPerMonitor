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
<script type="text/javascript" src="page/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="page/js/bootstrap.js"></script>
<script type="text/javascript" src="page/js/jquery-2.2.2.js"></script>
<link href="page/css/bootstrap.css" rel="stylesheet">
<script src="page/js/jquery-2.2.2.min.js"></script>
<script src="page/js/highcharts.js"></script> 
<style type="text/css">
span {
	font-weight:bold;
	color:#ff9955;
	}

#top{
padding-top: 10px;
}
</style>

</head>

<body>
<div class="panel-body"  style="width: 99%; height: 90%; position: absolute; overflow:auto;">
	<form action="" id="MainForm">
	<div id="top">
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
		<input onclick="chaxun()" type="button" class="btn btn-success" value="搜索" /> &nbsp;&nbsp;
		</div>
		<hr />

		<br />
		<div>
			<table align="right"
				style="overflow-x: hidden; overflow-y: auto; height: 200px; width: 20%;" border="0">
				<tr >
					<td align="center" colspan="2" >开度分析</td>
				</tr>
				<tr>
					<td align="left">
						<input name="wd" type="checkbox" checked="checked" value="1" />温度40以上</td>
					<td align="left"><span id="chartA"></span></td>
				</tr>

				<tr>
					<td align="left">
						<input name="wd" type="checkbox" checked="checked" value="2" />温度30-39
					</td>
					<td align="left"><span id="chartB"></span></td>
				</tr>

				<tr>
					<td align="left">
						<input name="wd" type="checkbox" checked="checked" value="3" />温度15-29
					</td>
					<td align="left"><span id="chartC"></span></td>
				</tr>

				<tr>
					<td align="left">
						<input name="wd" type="checkbox" checked="checked" value="4" />温度15以下
					</td>
					<td align="left"><span id="chartD"></span></td>
				</tr>
			</table>
		</div>

<div id="container"
				style="width: 500px; height: 400px; margin: 0 auto"></div>
		<script type="text/javascript">
			var chart;
			$(function(){
				$(document).ready(function() {
					chart = new Highcharts.Chart({
						chart : {
							renderTo : 'container',
							plotBackgroundColor : null,
							plotBorderWidth : null,
							plotShadow : false
							},
						title : {
							text : '传感器温度分析图'
							},
						tooltip : {
							pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
							},
						 credits: {  
				                    enabled:false  
				                }, 
						plotOptions : {
							pie : {
								allowPointSelect : true,
								cursor : 'pointer',
								dataLabels : {
									enabled : true,
									color : '#000000',
									connectorColor : '#000000',
									format : '<b>{point.name}</b>: {point.percentage:.1f} %'
									},
								showInLegend : true
								}
							},
						series : [ {
							type : 'pie',
							name : '所占比例',
							} ]
					});
			});
				//调用查询，加载数据
			chaxun();
	});
	function chaxun() {
		var arr = [];
		var xqName = $('#xqNameId').val();
		var box = document.getElementsByName("wd");
		var objArray = box.length;
		var apiContentStr = "";
		for (var i = 0; i < objArray; i++) {
			if (box[i].checked == true) {
				apiContentStr += box[i].value + ",";
			}
		}
		apiContentStr = apiContentStr.substring(0,apiContentStr.length - 1);
		$.ajax({
			type : 'get',
			url : "Chart/cgqWd.action?xqName=" + xqName+ "&apiContentStr="+ apiContentStr,//请求数据的地址
			beforeSend : function(XMLHttpRequest) {
				$('#loading').show();
				$('#contentDIV').hide();
			},
			success : function(data) {
				$('#contentDIV').show();
				$('#loading').hide();
			 	 $("#chartA").html(data.chartA+"户")
			 	 $("#chartB").html(data.chartB+"户")
	        	 $("#chartC").html(data.chartC+"户")
	        	 $("#chartD").html(data.chartD+"户")
	     
				chart.series[0].setData(data.data);//数据填充到highcharts上面 
			},
			error : function(e) {
				alert("不好意思，请要访问的图标跑到火星去了。。。。");
			}
		});
	};
		</script>

	</form>
</div>
</body>
</html>