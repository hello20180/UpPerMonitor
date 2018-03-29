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
<title>传感器信息</title>
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
<link href="page/css/bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="page/js/bootstrap.js"></script>
</head>
<script type="text/javascript">
/*页面加载就开始执行js*/
$(document).ready(function() {
	$("#xqNameId").change(
	function(){	
	  $.get("CGQCon/findcgqId.action?xqName="+ $("#xqNameId").val(),function(data) {
		var dd=data;
		var d=dd.xqlist;
		$("#ids option:gt(0)").remove();
		for(var i=0;i<d.length;i++){
			var cgqId=d[i].cgq.cgqId;
			var opt="<option value='"+cgqId+"'>"+cgqId+"</option>";
			$("#ids").append(opt);
		}
		});
	});
});
	//搜索
	function searchInfo(pageIndex,pageSize) {
		debugger;
		var xqName = $('#xqNameId').val();
		var cgqId = $('#ids').val();
		var name = $('#nameId').val().trim();
		if(pageSize==null||pageSize==""){
			var pageSize=$("#pageSize").val();
		}
		var html = "";
		$.ajax({
			url :"CGQCon/List.action",
			async : false,
			dataType : "json",
			data:{pageNum:pageIndex,limit:pageSize,xqName:xqName,cgqId:cgqId,name:name},
			success : function(data) {
				$("#tablediv").empty();
				var dd=eval(data);
				var d = dd.Condition.items;
				html += "<table id='tableSort' class='table table-border table-bordered table-bg table-hover table-striped table-sort'>";
				html += "<thead>"
				html += "<tr  class='text-c'>"
 				html += "<th>"
 				html += "</th>"
				html += "<th> 传感器ID"
				html += "</th>"
				html += "<th> 所属小区"
				html += "</th>"
				html += "<th> 安装位置"
				html += "</th>"
				html += "<th> 用户名称"
				html += "</th>"
				html += "<th> 联系方式"
				html += "</th>"
				html += "<th> 备注信息"
				html += "</th>"
				html += "</tr>"
				html += "</thead>"
				html += "<tbody class='text-center' id='cgq'>"
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
					if (d[i].beiZhu == undefined) {
						var beiZhu = "";
					} else {
						var beiZhu = d[i].beiZhu;
					}

					html += "<tr class='text-c'>";
					html += "<td><input type='checkbox' value='"+id+"' name='checkboxes'/></td>";
					html += "<td>" + cgqId + "</td>";
					html += "<td>" + xqName + "</td>";
					html += "<td>" + xqName + ""+buildNO+"号楼"+cellNO+"单元"+houseNO+"室"+"</td>";
					html += "<td>" + name + "</td>";
					html += "<td>" + tel + "</td>";
					html += "<td>" + beiZhu + "</td>";
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
	/*添加*/
	function add(title, url, w, h){
		debugger;
		layer_show(title, url, w, h);
	}
	/*修改*/
	function update(title,url, w, h){
		
		var ckbs=$("#cgq input[type=checkbox]:checked");
		if(ckbs.length==0){
			alert("请选择要修改的信息");
			return false;
		}
		var id=ckbs.val();
		var url2=url+id;
		layer_show(title, url2, w, h);
	}
	/*删除*/
	function del() {
		var ids = "";
		var r = document.getElementsByName("checkboxes");
		for (var i = 0; i < r.length; i++) {
			if (r[i].checked) {
				ids += r[i].value + ",";
			}
		}
		if (ids == "") { 
               layer.alert('请选择您要删除的项!');    
			return false;
		}
		var id = ids.substring(0, ids.length - 1);
		layer.confirm('您确定要删除选定项？',function(index){
	location.href = "CGQCon/Delete.action?ids=" + id; 
	$(obj).parents("tr").remove();
	layer.msg('已删除!',{icon:1,time:4000});
});	}
</script>
<body>
	<nav class="breadcrumb"> <i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span> 信息管理 
	<span class="c-gray en">&gt;</span>传感器信息 </nav>
	<div class="page-container">

		<div class="panel-body">
			<label for="xqNameId">选择小区</label> 
			<select id="xqNameId" name="xqName" class="input-text" style="width: 150px; margin-right: 15px;">
				<c:if test="${!empty cgq }">
					<option>--选择小区名称--</option>
					<c:forEach items="${cgq}" var="cgq">
						<option value="${cgq.xqName}">${cgq.xqName}</option>
					</c:forEach>
				</c:if>
			</select> 
			&nbsp;&nbsp;&nbsp; 
			<label for="id">传感器id</label> 
			<select id="ids" name="cgqId"  class="input-text" style="width: 150px; margin-right: 15px;">
				<option >--选择传感器--</option>
			</select>
		 	&nbsp;&nbsp;&nbsp; 
			<label for="nameId">用户名称</label> 
			<input class="input-text" style="width: 150px; margin-right: 15px;" placeholder="用户名称"
						id="nameId" name="name">
			<input type="button" onclick="searchInfo()" value="搜索" class="btn btn-success" />
		 
		</div>
 		<button type="button" class="btn btn-success" onclick="add('添加','CGQCon/toInsert.action','800','500')">添加</button> 
			&nbsp;&nbsp;
		<button type="button" class="btn btn-success" onClick="update('修改','CGQCon/toUpdate.action?id=','800','500')">修改</button>
	&nbsp;&nbsp;
 	 <button type="button" class="btn btn-success" onClick="del()">删除</button> 
	&nbsp;&nbsp;
			<hr />

		<div id="tablediv">
			<table id="tableSort"
				class="table table-border table-bordered table-bg table-hover table-striped table-sort">
				<thead>
					<tr class="text-c">
						<th></th>
						<th>传感器ID</th>
						<th>所属小区</th>
						<th>安装位置</th>
						<th>用户名称</th>
						<th>联系方式</th>
						<th>备注信息</th>
					</tr>
				</thead>
				<tbody id="cgq">
					<c:forEach var="page" items="${page.items}">
						<tr class="text-c">
							<td><input type="checkbox" value="${page.id }" name="checkboxes"/></td>
							<td>${page.cgqId}</td>
							<td>${page.xqName}</td>
							<td>${page.xqName}${page.buildNO}号楼${page.cellNO}单元${page.houseNO}室</td>
							<td>${page.name}</td>
							<td>${page.tel}</td>
							<td>${page.beiZhu}</td>
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
	<script type="text/javascript" src="page/lib/jquery/1.9.1/jquery.min.js"></script>
		<script type="text/javascript" src="page/lib/layer/2.4/layer.js"></script>
		<script type="text/javascript" src="page/static/h-ui.admin/js/H-ui.admin.js"></script>
		<script type="text/javascript" src="page/static/h-ui/js/H-ui.min.js"></script>
		<script type="text/javascript" src="page/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
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