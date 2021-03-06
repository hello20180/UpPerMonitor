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

<title>修改传感器</title>

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
			action="CGQCon/Update.action" method="post">
			<input type="hidden" name="id" id="id" value="${cgq.id}" />
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red"></span>传感器ID：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" readonly="readonly" 
						name="cgqId" id="cgqId" value="${cgq.cgqId}" >
				</div>
				<div id="v1" style="float: left; margin-left: 20px;"></div>
			</div>
				<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red"></span>433地址：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" readonly="readonly" 
						name="ad433" id="Ad433Id" value="${cgq.ad433}" >
				</div>
				<div id="v2" style="float: left; margin-left: 20px;"></div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red"></span>所属小区: 
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" id="xqNameId" name="xqName" readonly="readonly" value="${cgq.xqName}">
				</div>
				<div id="v3" style="float: left; margin-left: 20px;"></div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red"></span>楼栋号：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" id="buildNOId"
						name="buildNO" value="${cgq.buildNO}">
				</div>
				<span id="v4" style="float: left; margin-left: 20px;"></span>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red"></span>单元号：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" id="cellNOId"
						name="cellNO" value="${cgq.cellNO}">
				</div>
				<span id="v5" style="float: left; margin-left: 20px;"></span>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red"></span>户号：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" id="houseNOId"
						name="houseNO" value="${cgq.houseNO}">
				</div>
				<span id="v6" style="float: left; margin-left: 20px;"></span>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red"></span>用户名：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" id="nameId"
						name="name" value="${cgq.name}">
				</div>
				<span id="v7" style="float: left; margin-left: 20px;"></span>
			</div>

			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red"></span>联系方式：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" id="telId"
						name="tel" value="${cgq.tel}">
				</div>
				<span id="v8" style="float: left; margin-left: 20px;"></span>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red"></span>备注信息：
				</label>
				<div style="float: left;" class="formControls col-5">
					<textarea id="beiZhuId" class="input-text" name="beiZhu" value="${cgq.beiZhu}"></textarea>

				</div>
				<span id="v9" style="float: left; margin-left: 20px;"></span>
			</div>
			<div class="row cl">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
					<input class="btn btn-primary radius" type="submit"
						value="&nbsp;&nbsp;修&nbsp;改&nbsp;&nbsp;">
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
</body>
</html>
