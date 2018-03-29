<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>添加传感器</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css"
	href="page/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css"
	href="page/static/h-ui.admin/css/H-ui.admin.css" />
</head>

<body>
	<div class="page-container">
		<form class="form form-horizontal" id="form-user-pwd"
			action="CGQCon/Insert.action" method="post">
		
					<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>所属小区:
				</label>
				<div style="float: left;" class="formControls col-5">
					<select id="xqNameId" name="xqName" class="input-text">
					<c:if test="${!empty cgq }">
					<option>--选择小区名称--</option>
					<c:forEach items="${cgq}" var="cgq">
						<option value="${cgq.xqName}">${cgq.xqName}</option>
					</c:forEach>
				</c:if>
			</select> 
				</div>
				<div id="v3" style="float: left; margin-left: 20px;"></div>
			</div>
				<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>433地址：
				</label>
				<div style="float: left;" class="formControls col-5">
					<select name="wxAd" id="Ad433Id" class="input-text" >
				<option>--433地址--</option>
			</select>
				</div>
				<div id="v2" style="float: left; margin-left: 20px;"></div>
			</div>
	<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>传感器ID：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input required autofocus type="text" class="input-text"
						name="cgqId" id="cgqId">
				</div>
				<div id="v1" style="float: left; margin-left: 20px;"></div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red"></span>楼栋号：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" id="buildNOId"
						name="buildNO">
				</div>
				<span id="v4" style="float: left; margin-left: 20px;"></span>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red"></span>单元号：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" id="cellNOId"
						name="cellNO">
				</div>
				<span id="v5" style="float: left; margin-left: 20px;"></span>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red"></span>户号：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" id="houseNOId"
						name="houseNO">
				</div>
				<span id="v6" style="float: left; margin-left: 20px;"></span>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red"></span>用户名：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" id="nameId"
						name="name">
				</div>
				<span id="v7" style="float: left; margin-left: 20px;"></span>
			</div>

			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red"></span>联系方式：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" id="telId"
						name="tel">
				</div>
				<span id="v8" style="float: left; margin-left: 20px;"></span>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red"></span>备注信息：
				</label>
				<div style="float: left;" class="formControls col-5">
					<textarea id="beiZhuId" class="input-text" name="beiZhu"></textarea>
				</div>
				<span id="v9" style="float: left; margin-left: 20px;"></span>
			</div>
			<div class="row cl">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
					<input class="btn btn-primary radius" type="submit"
						value="&nbsp;&nbsp;添&nbsp;加&nbsp;&nbsp;">
				</div>
			</div>
		</form>
	</div>

	<script type="text/javascript"
		src="page/lib/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript" src="page/lib/layer/2.4/layer.js"></script>
	<script type="text/javascript"
		src="page/lib/validform/2.2.0/validate.min.js"></script>
	<script type="text/javascript"
		src="page/lib/validform/2.2.0/validate-methods.js"></script>
	<script type="text/javascript" src="page/static/h-ui/js/H-ui.min.js"></script>
	<script type="text/javascript"
		src="page/static/h-ui.admin/js/H-ui.admin.js"></script>
	<script type="text/javascript">
	/*页面加载就开始执行js*/
	$(document).ready(function() {
		debugger;
		$("#xqNameId").change(
		function(){	
		  $.get("CGQCon/findqgId.action?xqName="+ $("#xqNameId").val(),function(data) {
			var dd=data;
			var d=dd.qglist;
			$("#Ad433Id option:gt(0)").remove();
			for(var i=0;i<d.length;i++){
				var ad433=d[i].qg.wxAd;
				var opt="<option value='"+ad433+"'>"+ad433+"</option>";
				$("#Ad433Id").append(opt);
			}
			});
		});
	});
	
		$(function() {
			$("#form-user-pwd").validate({
				rules : {
					'cgqId' : {
						required : true,
						minlength : 1,
					},
					
					'tel' : {
						minlength : 11,
						maxlength:15,
					},
				},
				messages : {
					'cgqId' : {
						required : "请输入传感器id !",
						minlength : jQuery.format ("传感器id不能小于{0}个字 符!")
					},
					'tel' : {
						required : "请输入联系方式!",
						minlength : jQuery.format("电话不能少于{0}个字 符!"),
						maxlength : jQuery.format("电话不能多于{0}个字 符!")
					}
				},
			});
		});
	</script>
</body>
</html>
