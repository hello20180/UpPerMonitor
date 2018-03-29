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

<title>添加小区</title>

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
			action="XqCon/Insert.action" method="post">
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>小区代码：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input required autofocus type="text" class="input-text"
						name="xqId" id="xqId">
				</div>
				<div id="v1" style="float: left; margin-left: 20px;"></div>
			</div>
				<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>小区名称：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text"
						name="xqName" id="xqNameId" required>
				</div>
				<div id="v2" style="float: left; margin-left: 20px;"></div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>小区地址:
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" id="xqAdId" name="xqAd"
						required>
				</div>
				<div id="v3" style="float: left; margin-left: 20px;"></div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>物业名称：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" id="wyNameId"
						name="wyName" required>
				</div>
				<span id="v4" style="float: left; margin-left: 20px;"></span>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>物业电话：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" id="wyTelId"
						name="wyTel" required>
				</div>
				<span id="v5" style="float: left; margin-left: 20px;"></span>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red"></span>备注信息：
				</label>
				<div style="float: left;" class="formControls col-5">
					<textarea id="beiZhuId" class="input-text" name="beiZhu"></textarea>
				</div>
				<span id="v6" style="float: left; margin-left: 20px;"></span>
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
		$(function() {
			$("#form-user-pwd").validate({
				rules : {
					'xqId' : {
						required : true,
						minlength : 1,
					},
					'xqName' : {
						required : true,
						minlength : 2,
					},
					'xqAd' : {
						required : true,
						minlength : 2,
					},
					'wyName' : {
						required : true,
						minlength : 2,
					},
					'wyTel' : {
						required : true,
						minlength : 11,
						maxlength:15,
					},
				},
				messages : {
					'xqId' : {
						required : "请输入小区编号 !",
						minlength : jQuery.format ("小区编号不能小于{0}个字 符!")
					},
					'xqName' : {
						required : "请输入小区名称!",
						minlength : jQuery.format("小区名称不能小于{0}个字 符!")
					},
					'xqAd' : {
						required : "请输入小区地址!",
						minlength : jQuery.format("小区地址不能空!")
					},	
					'wyName' : {
						required : "请输入物业名称!",
						minlength : jQuery.format("物业名称不能为空!")
					},
					'wyTel' : {
						required : "请输入物业电话!",
						minlength : jQuery.format("电话不能少于{0}个字 符!"),
						maxlength : jQuery.format("电话不能多于{0}个字 符!")
					}
				},
			});
		});
	</script>
</body>
</html>
