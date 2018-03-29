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

<title>添加区管</title>

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
<script type="text/javascript" src="page/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
/*页面加载就开始执行js*/
$(document).ready(function() {
	debugger;
	$("#xqNameId").change(
	function(){	
	  $.get("QgCon/findxqId.action?xqName="+ $("#xqNameId").val(),function(data) {
		var dd=data;
		var d=dd.xqI;
		$("#xqId option:gt(0)").remove();
		for(var i=0;i<d.length;i++){
			var xqId=d[i].xqId;
			var opt="<option value='"+xqId+"'>"+xqId+"</option>";
			$("#xqId").append(opt);
		}
		});
	});
});
</script>
<body>
	<div class="page-container">
		<form class="form form-horizontal" id="form-user-pwd"
			action="QgCon/Insert.action" method="post">
			
					<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>所属小区:
				</label>
				<div style="float: left;" class="formControls col-5">
					<select id="xqNameId" name="xqName" class="input-text">
					<option>--选择小区--</option>
					<c:forEach items="${xq}" var="xq">
						<option value="${xq.xqName}">${xq.xqName}</option>
					</c:forEach>
			</select> 
				</div>
				<div id="v3" style="float: left; margin-left: 20px;"></div>
			</div>
				<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>小区Id:
				</label>
				<div style="float: left;" class="formControls col-5">
					<select id="xqId" name="xqId" class="input-text">
					<option>--选择小区--</option>
					<c:forEach items="${xqI}" var="xqI">
						<option value="${xqI.xqId}">${xqI.xqId}</option>
					</c:forEach>
			</select> 
				</div>
				<div id="v2" style="float: left; margin-left: 20px;"></div>
			</div>
			
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>楼栋号:
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" id="buildNOId" name="buildNO"
						required>
				</div>
				<div id="v3" style="float: left; margin-left: 20px;"></div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>区管编码：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input required autofocus type="text" class="input-text"
						name="qgId" id="qgId">
				</div>
				<div id="v1" style="float: left; margin-left: 20px;"></div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3"> <span
					class="c-red">*</span>安装位置：
				</label>
				<div style="float: left;" class="formControls col-5">
					<input type="text" class="input-text" id="qgAdId"
						name="qgAd" required>
				</div>
				<span id="v4" style="float: left; margin-left: 20px;"></span>
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
					'qgId' : {
						required : true,
						minlength : 1,
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
