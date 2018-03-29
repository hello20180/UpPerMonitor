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

<title>修改用户</title>

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
			action="UserCon/Update.action" method="post">
			<input type="hidden" name="id" id="id" value="${u.id}" />
			
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>用户名：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input required autofocus type="text" class="input-text"
						name="name" id="nameId" value="${u.name}"/>
				</div>
			</div>
					<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>密码:
				</label>
				<div style="float: left;" class="formControls col-5">
					<input required autofocus type="password" class="input-text"
						name="pass" id="passId" value="${u.pass}"/>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>真实姓名:
				</label>
				<div style="float: left;" class="formControls col-5">
					<input required autofocus type="text" class="input-text" id="trueNameId" name="trueName" value="${u.trueName}"/>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>权限：
				</label>
				<div style="float: left;" class="formControls col-5">
				<c:if test="${u.role==0}">
					<input type="radio" value="0" id="roleId"	name="role" checked>管理员
					<input type="radio" value="1" id="roleId"	name="role" >工作人员
				</c:if>
				<c:if test="${u.role==1}">
					<input type="radio" value="0" id="roleId"	name="role" >管理员
					<input type="radio" value="1" id="roleId"	name="role" checked>工作人员
				</c:if>
				</div>
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
