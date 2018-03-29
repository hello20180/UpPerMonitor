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

<title>修改区管</title>

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
			action="QgCon/Update.action" method="post">
			<input type="hidden" name="id" id="id" value="${qg.id}" />
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>区管编码：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input required autofocus type="text" class="input-text" readonly="readonly"
						name="qgId" id="qgId" value="${qg.qgId}" >
				</div>
				<div id="v1" style="float: left; margin-left: 20px;"></div>
			</div>
			
				<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>小区名称：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" readonly="readonly"
						name="xqName" id="xqNameId" required value="${qg.xqName}">
				</div>
				<div id="v2" style="float: left; margin-left: 20px;"></div>
			</div>
			
				<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>小区Id：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" readonly="readonly"
						name="xqId" id="xqId" required value="${qg.xqId}">
				</div>
				<div id="v2" style="float: left; margin-left: 20px;"></div>
			</div>
			
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>楼栋号:
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" id="buildNOId" name="buildNO"
						required value="${qg.buildNO}">
				</div>
				<div id="v3" style="float: left; margin-left: 20px;"></div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>安装位置：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" id="qgAdId"
						name="qgAd" required value="${qg.qgAd}">
				</div>
				<span id="v4" style="float: left; margin-left: 20px;"></span>
			</div>
		
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red"></span>备注信息：
				</label>
				<div style="float: left;" class="formControls col-5">
				<textarea id="beiZhuId" class="input-text" name="beiZhu" value="${qg.beiZhu}"></textarea>
				</div>
				<span id="v6" style="float: left; margin-left: 20px;"></span>
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
	<script type="text/javascript">
	$(function() {
		$("#form-user-pwd").validate({
			rules : {
				'qgId' : {
					required : true,
					minlength : 1,
				},
				'xqName' : {
					required : true,
					minlength : 2,
				},
				'buildNO' : {
					required : true,
					minlength : 1,
				},
				'qgAd' : {
					required : true,
					minlength : 2,
				},
			},
			messages : {
				'qgId' : {
					required : "请输入区管编号 !",
					minlength : jQuery.format ("区管编号不能小于{0}个字 符!")
				},
				'xqName' : {
					required : "请输入小区名称!",
					minlength : jQuery.format("小区名称不能小于{0}个字 符!")
				},
				'buildNO' : {
					required : "请输入楼栋号!",
					minlength : jQuery.format("楼栋号不能小于{0}个字 符!")
				},	
				'qgAd' : {
					required : "请输入安装位置!",
					minlength : jQuery.format("安装位置不能小于{0}个字 符!")
				},
			},
		});
	});
	</script>
</body>
</html>
