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
<title>用户信息</title>
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

	//搜索
	function searchInfo(pageIndex,pageSize) {
		var name = $('#nameId').val();
		if(pageSize==null||pageSize==""){
			var pageSize=$("#pageSize").val();
		}
		var html = "";
		$.ajax({
			url :"UserCon/List.action",
			async : false,
			dataType : "json",
			data:{pageNum:pageIndex,limit:pageSize,name:name,},
			success : function(data) {
				$("#tablediv").empty();
				var dd=eval(data);
				var d = dd.Condition.items;
				html += "<table id='tableSort' class='table table-border table-bordered table-bg table-hover table-striped table-sort'>";
				html += "<thead>"
				html += "<tr  class='text-c'>"
 				html += "<th>"
 				html += "</th>"
				html += "<th> 用户名"
				html += "</th>"
				html += "<th> 真实姓名"
				html += "</th>"
				html += "<th> 密码"
				html += "</th>"
				html += "<th> 权限"
				html += "</th>"
				html += "<th> 状态"
				html += "</th>"
				html += "<th> 操作"
				html += "</th>"
				html += "<th> 创建时间"
				html += "</th>"
				html += "</tr>"
				html += "</thead>"
				html += "<tbody class='text-center' id='userinfo'>"
				for (var i = 0; i < d.length; i++) {
 					var id = d[i].id;

					if (d[i].name == undefined) {
						var name = "";
					} else {
						var name = d[i].name;
					}
					if (d[i].trueName == undefined) {
						var trueName = "";
					} else {
						var trueName = d[i].trueName;
					}

					if (d[i].pass == undefined) {
						var pass = "";
					} else {
						var pass = d[i].pass;
					}
					
					if (d[i].role == 0) {
						var role = "管理员";
					} else {
						var role = "工作人员";
					}
					
					if (d[i].state == 0) {
						var state = "已启用";
					} else {
						var state = "已停用";
					}
					
					if (d[i].createdTime == undefined) {
						var createdTime = "";
					} else {
						var createdTime = FormatDate(d[i].createdTime);
					}
					html += "<tr class='text-c'>";
					html += "<td><input type='checkbox' value='"+id+"' name='checkboxes'/></td>";
					html += "<td>" + name + "</td>";
					html += "<td>" + trueName + "</td>";
					html += "<td>" + pass + "</td>";
					html += "<td>" + role + "</td>";
					html += "<td class='td-status'><span class='badge badge-secondary radius'>" + state + "</td>";
					html += "<td class='td-manage'>"
					if(d[i].state==0){
					html +="<a style='text-decoration:none' onClick='admin_stop(this,"+d[i].id+")' href='javascript:;' title='账户已启用'><i class='Hui-iconfont' style='color: #009900;'>&#xe615;</i></a>&nbsp;&nbsp;<span class='pipe'>|</span>";
					}
					if(d[i].state==1){
					html +="<a style='text-decoration:none' onClick='admin_start(this,"+d[i].id+")' href='javascript:;' title='账户已停用'><i class=' Hui-iconfont' style='color: #CC0033;'>&#xe631;</i></a>&nbsp;&nbsp;<span class='pipe'>|</span>";
					}
			
					html +="</td>";
					html += "<td title='"+createdTime+"'>" + createdTime + "</td>";
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
	//时间格式化
	function FormatDate(strTime) {
		var date = new Date(strTime);
		return date.getFullYear() + "-" + (date.getMonth() + 1) + "-"
				+ date.getDate() + " " + date.getHours() + ":"
				+ date.getMinutes() + ":" + date.getSeconds();
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
		layer_show(title, url, w, h);
	}
	/*修改*/
	function update(title,url, w, h){
		
		var ckbs=$("#userinfo input[type=checkbox]:checked");
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
	location.href = "UserCon/Delete.action?ids=" + id; 
	$(obj).parents("tr").remove();
	layer.msg('已删除!',{icon:1,time:4000});
});	}
//停用	
	function admin_stop(obj,id){
	
		layer.confirm('确认要停用吗？',function(index){
			location.href = "UserCon/upState.action?id=" + id + "&states="+1;
			$(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_start(this,id)" href="javascript:;" title="账户已启用" style="text-decoration:none"><i class="Hui-iconfont">&#xe631;</i></a>');
			$(obj).parents("tr").find(".td-status").html('<span class="label label-default radius">已停用</span>'); 
			$(obj).remove();
			layer.msg('已停用!',{icon: 5,time:4000});
		});
	}
	//启用
	function admin_start(obj,id){
		layer.confirm('确认要启用吗？',function(index){
			location.href = "UserCon/upState.action?id=" + id + "&states="+0; 
			$(obj).parents("tr").find(".td-manage").prepend('<a onClick="admin_stop(this,id})" href="javascript:;" title="账户已停用" style="text-decoration:none"><i class="Hui-iconfont">&#xe615;</i></a>');
			$(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已启用</span>');
			$(obj).remove();
			layer.msg('已启用!', {icon: 6,time:4000});
		});
	}	
</script>
<body>
	<nav class="breadcrumb"> <i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span> 信息管理 <span class="c-gray en">&gt;</span>用户信息
	</nav>
	<div class="page-container">

		<div class="panel-body">
			<label for="nameId">用户名称</label> 
			<input id="nameId" name="name" class="input-text" style="width: 150px; margin-right: 15px;" /> <input
				type="button" onclick="searchInfo()" value="搜索"
				class="btn btn-success" />

		</div>
		<button type="button" class="btn btn-success"
			onclick="add('添加','UserCon/toInsert.action','800','500')">添加</button>
		&nbsp;&nbsp;
		<button type="button" class="btn btn-success"
			onClick="update('修改','UserCon/toUpdate.action?id=','800','500')">修改</button>
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
						<th>用户名</th>
						<th>真实姓名</th>
						<th>密码</th>
						<th>权限</th>
						<th>状态</th>
						<th>操作</th>
						<th>创建时间</th>
					</tr>
				</thead>
				<tbody id="userinfo">
					<c:forEach var="page" items="${page.items}">
						<tr class="text-c">
							<td><input type="checkbox" value="${page.id }"
								name="checkboxes" /></td>
							<td>${page.name}</td>
							<td>${page.trueName}</td>
							<td>${page.pass}</td>
							<c:if test="${page.role==0}">
								<td>管理员</td>
							</c:if>
							<c:if test="${page.role==1}">
								<td>工作人员</td>
							</c:if>
							<c:if test="${page.state==0}">
								<td class="td-status"><span
									class="badge badge-secondary radius">已启用</span></td>
							</c:if>
							<c:if test="${page.state==1}">
								<td class="td-status"><span
									class="badge badge-default radius">已停用</span></td>
							</c:if>
							<td class="td-manage">
							<c:if test="${page.state==0}">
									<a style="text-decoration: none"
										onClick="admin_stop(this,${page.id})" href="javascript:;"
										title="账户已启用"><i class="Hui-iconfont"
										style="color: #009900;">&#xe615;</i> </a>&nbsp;&nbsp;<span
										class="pipe">|</span>
								</c:if> 
								<c:if test="${page.state==1}">
									<a style="text-decoration: none"
										onClick="admin_start(this,${page.id})" href="javascript:;"
										title="账户已停用"><i class="Hui-iconfont"
										style="color: #CC0033;">&#xe631;</i> </a>&nbsp;&nbsp;<span
										class="pipe">|</span>
								</c:if></td>
							<td
								title="<fmt:formatDate value="${page.createdTime}" pattern="yyyy-MM-dd HH:mm:ss" />"><fmt:formatDate
									value="${page.createdTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>

						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div>
		<div class="row">
			<div class="col-sm-4 col-sm-offset-8">
				共<span id="total">${page.total }</span>条数据 &nbsp;&nbsp;&nbsp; <select
					id="pageSize">
					<option value="15">15</option>
					<option value="30">30</option>
				</select> <span id="pageIndex"> ${page.pageIndex }</span>/<span
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
	<script type="text/javascript"
		src="page/lib/jquery.contextmenu/jquery.contextmenu.r2.js"></script>
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