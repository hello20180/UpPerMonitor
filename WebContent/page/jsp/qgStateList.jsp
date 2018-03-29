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
<title>区管状态信息</title>
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
<script type="text/javascript">
/*页面加载就开始执行js*/
$(document).ready(function() {
	$("#xqNameId").change(
	function(){	
	  $.get("QgTat/findqgId.action?xqName="+ $("#xqNameId").val(),function(data) {
		var dd=data;
		var d=dd.qglist;
		$("#qgId option:gt(0)").remove();
		for(var i=0;i<d.length;i++){
			var qgId=d[i].qg.qgId;
			var opt="<option value='"+qgId+"'>"+qgId+"</option>";
			$("#qgId").append(opt);
		}
		});
	});
});

/*修改*/
function updateQG(){
	var ckbs=$("#qg input[type=checkbox]:checked");
	if(ckbs.length==0){
		alert("请选择要修改的信息");
		return false;
	}
	var ids=[];
	$.each(ckbs,function(index,data){
		ids[index]=$(data).val();
	})
	var apiContentStr="";
	for(var i=0;i<ids.length;i++){
			 apiContentStr=ids[i];
			$.ajax({
				url:"QgTat/upqg.action",
				async:false,
				dataType:"json",
				data:{	
					"ids":apiContentStr
				},
				success:function(data){
					var msg=data.js;
					if(msg=="0"){
						alert("修改区管"+data.qgID+"的成功!")
					}
					if(msg=="1"){
						alert("修改区管"+data.qgID+"的失败!")
					}
// 					if(msg=="2"){
// 						alert("区管"+data.qgID+"的超时");
// 					}
					if(msg=="3"){
						alert("区管"+data.qgID+"通信失败")
					}
				}
				
			});
	}	
searchInfo();
}

//测距模式
function cj(){
	var ckbs=$("#qg input[type=checkbox]:checked");
	var apiContentStr;
	if(ckbs.length==0){
		alert("请选择要修改的区管");
		return false;
	}
	var ids=[];
	$.each(ckbs,function(index,data){
		ids[index]=$(data).val();
	})
	var apiContentStr="";
	for(var i=0;i<ids.length;i++){
   			 apiContentStr=ids[i];
   			$.ajax({
   				url:"QgTat/cjms.action",
   				async:false,
   				dataType:"json",
   				data:{	
   					"ids":apiContentStr
   				},
   				success:function(data){
   					var msg=data.js;
   					if(msg=="0"){
   						alert("修改区管"+data.qgID+"的模式成功!")
   					}
   					if(msg=="1"){
   						alert("修改区管"+data.qgID+"的模式失败!")
   					}
   					if(msg=="2"){
   						alert("区管"+data.qgID+"的模式超时");
   					}
   					if(msg=="3"){
   						alert("区管"+data.qgID+"通信失败")
   					}
   				}
   				
   			});
   	}	
	searchInfo();
}


//收发模式
function sfms(){
	var ckbs=$("#qg input[type=checkbox]:checked");
	var apiContentStr;
	if(ckbs.length==0){
		alert("请选择要修改的区管");
		return false;
	}
	var ids=[];
	$.each(ckbs,function(index,data){
		ids[index]=$(data).val();
	})
	var apiContentStr="";
	for(var i=0;i<ids.length;i++){
   			 apiContentStr=ids[i];
   			$.ajax({
   				url:"QgTat/sfms.action",
   				async:false,
   				dataType:"json",
   				data:{	
   					"ids":apiContentStr,
   				},
   				success:function(data){
   					var msg=data.js;
   					if(msg=="0"){
   						alert("修改区管"+data.qgID+"的模式成功!")
   					}
   					if(msg=="1"){
   						alert("修改区管"+data.qgID+"的模式失败!")
   					}
   					if(msg=="2"){
   						alert("区管"+data.qgID+"的模式超时");
   					}
   					if(msg=="3"){
   						alert("区管"+data.qgID+"通信失败")
   					}
   					
   				}
   			});
   	}	
	searchInfo();
}

//读区管工作模式
function du(){
	var ckbs=$("#qg input[type=checkbox]:checked");
	if(ckbs.length==0){
		var txt=  "请选择要读取的区管";
		window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		return false;
	}
	if(ckbs.length>1){
		alert("对不起一次只能读取一个");
		return false;
	}
	var id=ckbs.val();
	var qgId=ckbs.parent().next().text();
	var ms=ckbs.parent().next().next().next().next().next().next().next().text();
	$.ajax({
		url:"QgTat/du.action",
		async:false,
		dataType:"json",
		data:{id:id},
		success:function(data){
			var msg=data.js;
			if(msg=="0"){
				alert("区管ID :"+data.qgID+"模式为:"+data.ms+"  433地址为:"+data.Ad433)
			}
			if(msg=="1"){
				alert("读取"+data.qgID+"模式失败!")
			}
			if(msg=="3"){
				alert(data.qgID+"通信失败!")
			}
		}
	});
	searchInfo();
}

//搜索
function searchInfo(pageIndex,pageSize) {
	debugger;
	var xqName = $('#xqNameId').val();
	var qgId = $('#qgId').val();
	if(pageSize==null||pageSize==""){
		var pageSize=$("#pageSize").val();
	}
	var html = "";
	$.ajax({
		url :"QgTat/List.action",
		async : false,
		dataType : "json",
		data:{pageNum:pageIndex,limit:pageSize,xqName:xqName,qgId:qgId},
		success : function(data) {
			debugger;
			$("#tablediv").empty();
			var dd=eval(data);
			var d = dd.Condition.items;
			html += "<table id='tableSort' class='table table-border table-bordered table-bg table-hover table-striped table-sort'>";
			html += "<thead>"
			html += "<tr  class='text-c'>"
			html += "<th>"
			html += "</th>"
			html += "<th> 区管ID"
			html += "</th>"
			html += "<th> 小区Id"
			html += "</th>"
			html += "<th> IP"
			html += "</th>"
			html += "<th> 端口号"
			html += "</th>"
			html += "<th> 所属小区"
			html += "</th>"
			html += "<th> 安装位置"
			html += "</th>"
			html += "<th> 室外温度"
			html += "</th>"
			html += "<th> 模式"
			html += "</th>"
			html += "<th> 433无线地址"
			html += "</th>"
			html += "<th> 接收时间"
			html += "</th>"
			html += "</tr>"
			html += "</thead>"
			html += "<tbody class='text-center' id='qg'>"
			for (var i = 0; i < d.length; i++) {
					var id = d[i].id;

				if (d[i].qgId == undefined) {
					var qgId = "";
				} else {
					var qgId = d[i].qgId;
				}
				if (d[i].xqId == undefined) {
					var xqId = "";
				} else {
					var xqId = d[i].xqId;
				}
		
				if (d[i].xq.ip == undefined) {
					
					var ip = "";
				} else {
					var ip = d[i].xq.ip;
				}

				if (d[i].xq.portNum == undefined) {
					var portNum = "";
				} else {
					var portNum = d[i].xq.portNum;
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
				if (d[i].qgAd == undefined) {
					var qgAd = "";
				} else {
					var qgAd = d[i].qgAd;
				}
				if (d[i].swwd == undefined) {
					var swwd = "";
				} else {
					var swwd = d[i].swwd;
				}
				if (d[i].ms == undefined) {
					var ms = "";
				} else {
					var ms = d[i].ms;
				}
				if (d[i].wxAd == undefined) {
					var wxAd = "";
				} else {
					var wxAd = d[i].wxAd;
				}
				if (d[i].recordTime == undefined) {
					var recordTime = "";
				} else {
					var recordTime = FormatDate(d[i].recordTime);
				}
				html += "<tr class='text-c'>";
				html += "<td><input type='checkbox' value='"+id+"' name='checkboxes'/></td>";
				html += "<td>" + qgId + "</td>";
				html += "<td>" + xqId + "</td>";
				html += "<td>" + ip + "</td>";
				html += "<td>" + portNum+"</td>";
				html += "<td>" + xqName + "</td>";
				html += "<td>" + xqName+""+buildNO+"号楼"+qgAd+"</td>";
				html += "<td>" + swwd+"</td>";
				html += "<td>" + ms+"</td>";
				html += "<td>" + wxAd+"</td>";
				html += "<td>" + recordTime + "</td>";
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
/*修改*/
function updateAd(title,url, w, h){
	var ckbs=$("#qg input[type=checkbox]:checked");
	if(ckbs.length==0){
		alert("请选择要修改的信息");
		return false;
	}
	var id=ckbs.val();
	var url2=url+id;
	layer_show(title, url2, w, h);
}

</script>
</head>
<body>
<nav class="breadcrumb"> <i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span> 信息管理 
	<span class="c-gray en">&gt;</span>区管信息 </nav>
	<div class="page-container">

 		<div class="panel-body">
 			<label for="xqNameId">小区名称</label>
 			<select id="xqNameId" name="xqName" class="input-text" style="width: 150px; margin-right: 15px;"> 
				<c:if test="${!empty xqlist}">
 					<option>--选择小区名称--</option>
					<c:forEach items="${xqlist}" var="xqlist">
						<option value="${xqlist.xqName}">${xqlist.xqName}</option>
					</c:forEach>
				</c:if>
 			</select> 
 				&nbsp;&nbsp;&nbsp;
 		  	<label for="qgId">区管ID</label> 
			<select name="qgId" id="qgId" class="input-text" style="width: 150px; margin-right: 15px;">
				<option>--选择区管ID--</option>
			</select>
 			
 			<input type="button" onclick="searchInfo()" value="搜索" class="btn btn-success" />
		 
 		</div> 
 		<button type="button" class="btn btn-success" onclick="du()">读取工作模式</button> 
			&nbsp;&nbsp;
		<button type="button" class="btn btn-success" onClick="sfms()">收发模式</button>
	&nbsp;&nbsp;
		<button type="button" class="btn btn-success" onClick="cj()">测距模式</button>
	&nbsp;&nbsp;
		<button type="button" class="btn btn-success" onClick="updateAd('修改433','QgTat/toUpdate433.action?id=','500','300')">修改433</button>
	&nbsp;&nbsp;
		<button type="button" class="btn btn-success" onClick="updateQG()">修改区管Id</button>
	&nbsp;&nbsp;
			<hr />

		<div id="tablediv">
			<table id="tableSort"
				class="table table-border table-bordered table-bg table-hover table-striped table-sort">
				<thead>
					<tr class="text-c">
						<th></th>
						<th>区管ID</th>
						<th>小区Id</th>
						<th>IP</th>
						<th>端口号</th>
						<th>所属小区</th>
						<th>安装位置</th>
						<th>室外温度</th>
						<th>模式</th>
						<th>433无线地址</th>
						<th>接收时间</th>
					</tr>
				</thead>
				<tbody id="qg">
					<c:forEach var="page" items="${page.items}">
						<tr class="text-c">
							<td><input type="checkbox" value="${page.id }" name="checkboxes"/></td>
							<td>${page.qgId}</td>
							<td>${page.xqId}</td>
							<td>${page.xq.ip}</td>
							<td>${page.xq.portNum}</td>
							<td>${page.xqName}</td>
							<td>${page.xqName}${page.buildNO}号楼${page.qgAd}</td>
							<td>${page.swwd}</td>
							<td>${page.ms}</td>
							<td>${page.wxAd}</td>
							<td><fmt:formatDate value="${page.recordTime}"
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
					<button type="button" onClick="prevPage()" class="btn btn-primary btn-sm">上一页</button>
					<button type="button" onClick="nextPage()" class="btn btn-primary btn-sm">下一页</button>

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