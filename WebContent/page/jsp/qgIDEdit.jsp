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

<title>修改区管Id</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css"
	href="page/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css"
	href="page/static/h-ui.admin/css/H-ui.admin.css" />
<script type="text/javascript">
	//修改
	function upda() {
		debugger;
	var ids= $('#id').val();
	var qgIDs= $('#QGId').val();
		$.ajax({
			url : "QgTat/upqg.action",
			async : false,
			dataType : "json",
			data : {
				ids : ids,
				qgIDs : qgIDs,
			},
			success : function(data) {
				msg = data.js
				if (msg == "0") {
					alert("修改区管" + data.qgID + "Id为:"+data.qgIds);
					window.location.href ="page/jsp/qgStatesuccess.jsp"
				}
				if (msg == "1") {
					alert("修改区管" + data.qgIds + "Id失败!");
				}
				if (msg == "2") {
					alert("区管"+data.qgID + "Id数据超时");
				}
			}
		});
	}
</script>

</head>

<body>
	<div class="page-container">
		<form class="form form-horizontal" id="form-user-pwd">
			<input type="hidden" name="id" id="id" value="${qgI.id}" />
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>区管编码：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input required autofocus type="text" class="input-text"
						name="qgId" id="qgId" value="${qgI.qgId}" readonly="readonly">
				</div>
				<div id="v1" style="float: left; margin-left: 20px;"></div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>新区管ID：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" name="qgId" id="QGId" required >
				</div>
				<div id="v2" style="float: left; margin-left: 20px;"></div>
			</div>
			<div class="row cl">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
					<button type="button" class="btn btn-success" onclick="upda()">修改</button>
					
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
					'wxAd' : {
						required : true,
						minlength : 1,
					},

				},
				messages : {
					'wxAd' : {
						required : "请输入无线地址 !",
						minlength : jQuery.format("区管无线地址不能小于{0}个字 符!")
					},

				},
			});
		});
	</script>
</body>
</html>
