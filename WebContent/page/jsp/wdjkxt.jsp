<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>温度监控系统</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css"
	href="page/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css"
	href="page/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css"
	href="page/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css"
	href="page/static/h-ui.admin/skin/blue/skin.css" />
</head>
<script type="text/javascript">
	function searchInfo(pageIndex,pageSize) {
		debugger;
		var xqName = $('#xqNameId').val();
		if(pageSize==null||pageSize==""){
			var pageSize=$("#pageSize").val();
		}
		var html = "";
		$.ajax({
			url :"WDInfo/List.action",
			async : false,
			dataType : "json",
			data:{pageNum:pageIndex,limit:pageSize,xqName:xqName},
			success : function(data) {
				$("#tablediv").empty();
				var dd=eval(data);
				var d = dd.Condition.items;
				html += "<table id='tableSort' class='table table-border table-bordered table-bg table-hover table-striped table-sort'>";
				html += "<thead>"
				html += "<tr  class='text-c'>"
 				html += "<th> 序号"
 				html += "</th>"
 				html += "<th> 传感器ID"
 				html += "</th>"
				html += "<th> 小区名称"
				html += "</th>"
				html += "<th> 地址信息"
				html += "</th>"
				html += "<th> 温度"
				html += "</th>"
				html += "<th> 电量"
				html += "</th>"
				html += "<th> 采集日期"
				html += "</th>"
				html += "<th> 用户信息"
				html += "</th>"
				html += "<th> 联系方式"
				html += "</th>"
				html += "</tr>"
				html += "</thead>"
				html += "<tbody class='text-center'>"
				for (var i = 0; i < d.length; i++) {
					var id = d[i].id;

					if (d[i].cgqId == undefined) {
						var cgqId = "";
					} else {
						var cgqId = d[i].cgqId;
					}
					
					if (d[i].xqName == undefined) {
						var xqName = "";
					} else {
						var xqName = d[i].xqName;
					}

					if (d[i].buildNO == undefined) {
						var buildNO = "";
					} else {
						var buildNO = d[i].buildNO;
					}
					
					if (d[i].cellNO == undefined) {
						var cellNO = "";
					} else {
						var cellNO = d[i].cellNO;
					}
					
					if (d[i].houseNO == undefined) {
						var houseNO = "";
					} else {
						var houseNO = d[i].houseNO;
					}
					
					if (d[i].wd == undefined) {
						var wd = "";
					} else {
						var wd = d[i].wd;
					}	
					
					if (d[i].dl == undefined) {
						var dl = "";
					} else {
						var dl = d[i].dl;
					}


					if (d[i].recordTime == undefined) {
						var recordTime = "";
					} else {
						var recordTime = FormatDate(d[i].recordTime);
					}

					if (d[i].name == undefined) {
						var name = "";
					} else {
						var name = d[i].name;
					}

					if (d[i].tel == undefined) {
						var tel = "";
					} else {
						var tel = d[i].tel;
					}

					html += "<tr class='text-c'>";
					html += "<td>" + id + "</td>";
					html += "<td><a href='LsCon/goHistoryLine.action?cgqId="+d[i].cgqId+"&xqName="+d[i].xqName+"&buildNO="+d[i].buildNO+"&cellNO="+d[i].cellNO+"&houseNO="+d[i].houseNO+"'><font color=blue>" + cgqId + "</font></a></td>";
					html += "<td>" + xqName + "</td>";
					html += "<td>" + xqName + ""+buildNO+"号楼"+cellNO+"单元"+houseNO+"室"+"</td>";
					html += "<td>" + wd + "</td>";
					html += "<td>" + dl + "%"+"</td>";
					html += "<td title='"+recordTime+"'>" + recordTime + "</td>";
					html += "<td>" + name + "</td>";
					html += "<td>" + tel + "</td>";
					html += "</tr>";
				}
				html += "</tbody>"
				html += "</table>";
				$("#tablediv").append(html);
				$("#total").text(data.Condition.total);
				$("#pageIndex").text(data.Condition.pageIndex);
				$("#totalPage").text(data.Condition.totalPage);
			}

		})
	}
	function FormatDate(strTime) {
		var date = new Date(strTime);
		return date.getFullYear() + "-" + (date.getMonth() + 1) + "-"
				+ date.getDate() + " " + date.getHours() + ":"
				+ date.getMinutes() + ":" + date.getSeconds();
	}
</script>
<script type="text/javascript">
	//导出
	function doExportExcel() {
		debugger;
		var xqName = $('#xqNameId').val();
		window.open("WDInfo/doExportExcel.action?xqName=" + xqName);
	}
</script>
<body>
	<nav class="breadcrumb"> <i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span> 设备监控 
	<span class="c-gray en">&gt;</span>实时监控 </nav>
	<div class="page-container">

		<div class="panel-body">
			<label for="xqNameId">选择小区</label> 
			<select id="xqNameId" name="xqName">
				<c:if test="${!empty cgq }">
					<option>--选择小区名称--</option>
					<c:forEach items="${cgq}" var="cgq">
						<option value="${cgq.xqName}">${cgq.xqName}</option>
					</c:forEach>
				</c:if>
			</select> &nbsp;&nbsp;&nbsp; 
			<input type="button" onclick="searchInfo()" value="搜索" class="btn btn-success" /> 
			<input type="button" value="导出" class="btn btn-success" onclick="doExportExcel(1)" />
		</div>
		<hr>

		<div id="tablediv">
			<table id="tableSort"
				class="table table-border table-bordered table-bg table-hover table-striped table-sort">
				<thead>
					<tr class="text-c">
						<th>序号</th>
						<th>传感器ID</th>
						<th>小区名称</th>
						<th>地址信息</th>
						<th>温度</th>
						<th>电量</th>
						<th>采集日期</th>
						<th>用户信息</th>
						<th>联系方式</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="page" items="${page.items}">
						<tr class="text-c">
 							<td>${page.id }</td> 
 							<td><a href="LsCon/goHistoryLine.action?cgqId=${page.cgqId }&xqName=${page.xqName}&buildNO=${page.buildNO}&cellNO=${page.cellNO}&houseNO=${page.houseNO}"><font color=blue>${page.cgqId }</font></a></td>
							<td>${page.xqName}</td>
							<td>${page.xqName}${page.buildNO}号楼${page.cellNO}单元${page.houseNO}室</td>
							<td>${page.wd }</td>
							<td>${page.dl}%</td>
							<td title="<fmt:formatDate value="${page.recordTime}" pattern="yyyy-MM-dd HH:mm:ss" />"><fmt:formatDate value="${page.recordTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${page.name}</td>
							<td>${page.tel}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div>
		<div class="row">
				<div class="col-sm-4 col-sm-offset-8">
				共<span id="total">${page.total }</span>条数据 &nbsp;&nbsp;&nbsp;
				<select id="pageSize">
						<option value="15">15</option>
						<option value="30">30</option>
					</select>
				<span id="pageIndex"> ${page.pageIndex }</span>/<span
						id="totalPage">${page.totalPage }</span>页
					<button type="button" onClick="prevPage()"
						class="btn btn-primary btn-sm">上一页</button>
					<button type="button" onClick="nextPage()"
						class="btn btn-primary btn-sm">下一页</button>

				</div>
			</div>
</div>
	<script type="text/javascript"
			src="page/lib/jquery/1.9.1/jquery.min.js"></script>
		<script type="text/javascript" src="page/lib/layer/2.4/layer.js"></script>
		<script type="text/javascript" src="page/static/h-ui/js/H-ui.min.js"></script>
		<script type="text/javascript"
			src="page/static/h-ui.admin/js/H-ui.admin.js"></script>
		<script type="text/javascript"
			src="page/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
	<script type="text/javascript">
$('.table-sort').dataTable({
		//"aaSorting": [[ 0, "asc" ]],//默认第几个排序
		"bLengthChange" : false, //改变每页显示数据数
		"bStateSave" : false,//状态保存
		"bPaginate" : false, //翻页功能
		"aoColumnDefs" : [
		//{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
		{
			"orderable" : false,
			"aTargets" : [ 0 ]
		} // 制定列不参与排序
		]
	});
function nextPage(){
	var pageIndex=parseInt($("#pageIndex").text());
	var totalPage=parseInt($("#totalPage").text());
	var pageSize=parseInt($("#pageSize").val());
	if(pageIndex==totalPage){
		return false;
	}
	if((pageIndex+1)>totalPage){
		pageIndex=1;
	}else{
		pageIndex+=1;
	}
	searchInfo(pageIndex,pageSize);
}
function prevPage(){
	var pageIndex=parseInt($("#pageIndex").text());
	var totalPage=parseInt($("#totalPage").text());
	var pageSize=parseInt($("#pageSize").val());
	if(pageIndex==1){
		return false;
	}
	if((pageIndex-1)==0){
		pageIndex=1;
	}else{
		pageIndex-=1;
	}
	searchInfo(pageIndex,pageSize);

}

</script>
</body>
</html>