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
<title>操作记录</title>
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
<!-- Bootstrap -->
<script type="text/javascript" src="page/js/jquery-1.9.1.min.js"></script>
<link href="page/css/bootstrap.css" rel="stylesheet"/>
<link href="page/css/bootstrap-datetimepicker.min.css"/>
<script type="text/javascript" src="page/js/bootstrap.js"></script>
<script type="text/javascript" src="page/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript">
//下一页
function nextPage (){
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
//上一页
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


//搜索
function searchInfo(pageIndex,pageSize) {
	var czr = $('#czr').val();
	if(pageSize==null||pageSize==""){
		var pageSize=$("#pageSize").val();
	}
	var html = "";
	$.ajax({
		url :"RzController/findRz.action",
		async : false,
		dataType : "json",
		data:{pageNum:pageIndex,limit:pageSize,czr:czr,},
		success : function(data) {
			$("#tablediv").empty();
			var dd=eval(data);
			var d = dd.Condition.items;
			html += "<table id='tableSort' class='table table-border table-bordered table-bg table-hover table-striped table-sort'>";
			html += "<thead>"
			html += "<tr  class='text-c'>"
			html += "<th>"
			html += "</th>"
			html += "<th> 操作人"
			html += "</th>"
			html += "<th> 操作"
			html += "</th>"
			html += "<th> 操作时间"
			html += "</th>"
			html += "</tr>"
			html += "</thead>"
			html += "<tbody class='text-center' id='qginfo'>"
			for (var i = 0; i < d.length; i++) {
					var id = d[i].id;

				if (d[i].id == undefined) {
					var id = "";
				} else {
					var id = d[i].id;
				}
				if (d[i].czr == undefined) {
					var czr = "";
				} else {
					var czr = d[i].czr;
				}

				if (d[i].cz == undefined) {
					var cz = "";
				} else {
					var cz = d[i].cz;
				}
				
				if (d[i].czsj == undefined) {
					var czsj = "";
				} else {
					var czsj = FormatDate(d[i].czsj);
				}
				html += "<tr class='text-c'>";
				html += "<td><input type='checkbox' value='"+id+"' name='checkboxes'/></td>";
				html += "<td>" + czr + "</td>";
				html += "<td>" + cz+ "</td>";
				html += "<td>" + czsj + "</td>";
				html += "</tr>";
			}
			html += "</tbody>"
			html += "</table>";
			$("#tablediv").append(html);
			$("#total").text(data.Condition.total);
			$("#pageIndex").text(data.Condition.pageIndex);
			$("#totalPage").text(data.Condition.totalPage);
		}

	});
}
function FormatDate(strTime) {
	var date = new Date(strTime);
	return date.getFullYear() + "-" + (date.getMonth() + 1) + "-"
			+ date.getDate() + " " + date.getHours() + ":"
			+ date.getMinutes() + ":" + date.getSeconds();
}
</script>
</head>
<body>
<nav class="breadcrumb"> <i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span> 信息管理 
	<span class="c-gray en">&gt;</span>区管信息 </nav>
	<div class="page-container">

 		<div class="panel-body">
 			操作人：<input name="czr" id="czr" class="input-text" style="width: 150px; margin-right: 15px;" placeholder="操作人">		
 			<input type="button" onclick="searchInfo()" value="搜索" class="btn btn-success" />
 		</div> 
 		
			<hr />

		<div id="tablediv">
			<table id="tableSort"
				class="table table-border table-bordered table-bg table-hover table-striped table-sort">
				<thead>
					<tr class="text-c">
					<th></th>
						<th>操作人</th>
						<th>操作</th>
						<th>操作时间</th>
					</tr>
				</thead>
				<tbody id="qginfo">
					<c:forEach var="page" items="${page.items}">
						<tr class="text-c">
							<td><input type="checkbox" value="${page.id }" name="checkboxes"/></td>
							<td>${page.czr}</td>
							<td>${page.cz}</td>
							<td><fmt:formatDate value="${page.czsj}"
											pattern="yyyy-MM-dd HH:mm:ss" /></td>
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

</script>
</body>
</html>