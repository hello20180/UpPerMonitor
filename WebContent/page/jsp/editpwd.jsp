<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>重置密码</title>

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
				action="UserCon/updapwd.action" method="post">
				<input type="hidden" name="id" id="id" value="${userpass.id}" />
				<input type="hidden"  name="oldpwd" id="oldpwd" value="${userpass.pass}" />
		
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-3">
						<span class="c-red">*</span>新密码:
					</label>
					<div style="float: left;" class="formControls col-5">
						<input type="password" class="input-text" id="pass"
							name="pass" required>
					</div>
					<div id="v2" style="float: left; margin-left: 20px;">

					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-3">
						<span class="c-red">*</span>确认密码：
					</label>
					<div style="float: left;" class="formControls col-5">
						<input type="password" class="input-text" id="passnew"
							name="passnew" required>
					</div>
					<span id="v3" style="float: left; margin-left: 20px;"></span>
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
$(function(){
	debugger;
	$("#form-user-pwd").validate({ 
		rules:{
			'pass':{
				required:true,
				minlength: 8,
			},
			'passnew':{
				required:true,
				equalTo:'#pass',
			},
		},
	 messages:{
          'pass':{
              required:"请输入新密码!",
              minlength:jQuery.format("密码不能小于{0}个字 符!")
          },
          'passnew':{
              required:"请重复密码!",
              equalTo: "两次输入密码不一致!"
          }
    },
  });
});

</script>
	</body>
</html>
