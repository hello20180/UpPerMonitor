<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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

		<title>无线测温管理系统</title>

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
	</head>

	<body>
		<header class="navbar-wrapper">
		<div class="navbar navbar-fixed-top">
			<div class="container-fluid cl">
				<a class="logo navbar-logo f-l mr-10 hidden-xs"><strong>义马市热力有限责任公司-</strong> 无线测温监控平台</a>
				<a class="logo navbar-logo-m f-l mr-10 visible-xs"
					href="/aboutHNZY.html"></a>
				<span class="logo navbar-slogan f-l mr-10 hidden-xs">v1.0</span>
				<a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs"
					href="javascript:;">&#xe667;</a>
			
				<nav id="Hui-userbar"
					class="nav navbar-nav navbar-userbar hidden-xs">
				<ul class="cl">
					<li>
						您好，
					</li>
					<li class="dropDown dropDown_hover" style="padding-right: 30px;">
						${admins.trueName} <i class="Hui-iconfont">&#xe6d5;</i> 
						<ul class="dropDown-menu menu radius box-shadow">
							<li>
								<a href="javascript:void(0)"
									onclick="edit_pwd('重置密码','UserCon/changepwd.action?id=${admins.id}','800','350')">重置密码</a>
							</li>
							<li>
								<a href="javascript:void(0)" onclick="user_exit();">退出</a>
							</li>
						</ul>
					</li>
				</ul>
				</nav>
			</div>
		</div>
		</header>
		<aside class="Hui-aside">
		<div class="menu_dropdown bk_2">
			<c:if test="${admins.role==0}">
				<dl>
					<dt>
						<strong>用户信息</strong>
						<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
					</dt>
					<dd style="display: block;">
						<ul>
							<li>
								<a data-href="UserCon/find.action?name=${admins.name}&part=${admins.role}"
									data-title="用户信息" href="javascript:void(0)">用户信息</a>
							</li>
						</ul>
					</dd>
				</dl>
			</c:if>
			
				<dl>
					<dt>
						<strong>地图显示</strong>
						<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
					</dt>
					<dd style="display: block;">
						<ul>
							<li>
								<a data-href="map/main.action?name=${admins.name}&part=${admins.role}" data-title="地图显示" href="javascript:void(0)">地图显示</a>
							</li>
						</ul>
					</dd>
				</dl>
			
			<dl>
				<dt>
					<strong>信息管理</strong>
					<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd style="display: block;">
					<ul>
						<li>
							<a data-href="CGQCon/find.action?name=${admins.name}&part=${admins.role}"
									data-title="传感器信息" href="javascript:void(0)">传感器信息</a>
						</li>
						<li>
							<a data-href="QgCon/find.action?name=${admins.name}&part=${admins.role}"
								data-title="区管信息" href="javascript:void(0)">区管信息</a>
							
						</li>
							<li>
							<a data-href="JzqCon/find.action?name=${admins.name}&part=${admins.role}"
								data-title="集中器信息" href="javascript:void(0)">集中器信息</a>
							
						</li>
						<li>
							<a data-href="XqCon/find.action?name=${admins.name}&part=${admins.role}"
								data-title="小区信息" href="javascript:void(0)">小区信息</a>
						</li>
					</ul>
				</dd>
			</dl>
			<dl>
				<dt>
					<strong>设备监控</strong>
					<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd style="display: block;">
					<ul>
						<li>
							<a data-href="WDInfo/find.action" data-title="实时温度" href="javascript:void(0)">实时温度</a>
						</li>
						<li>
							<a data-href="WDHis/find.action?name=${admins.name}&part=${admins.role}"
								data-title="历史记录" href="javascript:void(0)">历史记录</a>
						</li>
						<li>
							<a data-href="QgTat/find.action?name=${admins.name}&part=${admins.role}"
								data-title="区管状态" href="javascript:void(0)">区管状态</a>
						</li>
					</ul>
				</dd>
			</dl>

			<dl>
				<dt>
					<strong>图表分析</strong>
					<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd style="display: block;">
					<ul>
					    <li>
							<a data-href="CgqChart/find.action?name=${admins.name}&part=${admins.role}"
									data-title="温度折线图" href="javascript:void(0)">温度折线图</a>
						</li>
				 		<li>
							<a data-href="Chart/find.action?name=${admins.name}&part=${admins.role}"
									data-title="温度饼图" href="javascript:void(0)">温度饼图</a>
						</li>
						<li>
							<a data-href="WDSD/find.action?name=${admins.name}&part=${admins.role}"
									data-title="温度散点图" href="javascript:void(0)">温度散点图</a>
						</li>
					</ul>
				</dd>
			</dl>
			<dl>
				<dt>
					<strong>系统日志</strong>
					<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
				</dt>
				<dd style="display: block;">
					<ul>
						<li>
							<c:if test="${admins.role==0}">
								<a data-href="RzController/findAll.action?name=${admins.name}"
									data-title="操作记录" href="javascript:void(0)">操作记录</a>
							</c:if>
							<c:if test="${admins.role==1}">
								<a data-href="RzController/findAll.action?name=${admins.name}"
									data-title="操作记录" href="javascript:void(0)">操作记录</a>
							</c:if>
						</li>
					</ul>
				</dd>
			</dl>
		</div>
		</aside>
		<div class="dislpayArrow hidden-xs">
			<a class="pngfix" href="javascript:void(0);"
				onClick="displaynavbar(this);"></a>
		</div>
		<section class="Hui-article-box">
		<div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
			<div class="Hui-tabNav-wp">
				<ul id="min_title_list" class="acrossTab cl">
					<li class="active">
						<span title="我的桌面" data-href="sys/welcome.action">首页</span><em></em>
					</li>
				</ul>
			</div>
			<div class="Hui-tabNav-more btn-group">
				<a id="js-tabNav-prev" class="btn radius btn-default size-S"
					href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i> </a><a
					id="js-tabNav-next" class="btn radius btn-default size-S"
					href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i> </a>
			</div>
		</div>
		<div id="iframe_box" class="Hui-article">
			<div class="show_iframe">
				<div style="display: none" class="loading"></div>
				<iframe scrolling="yes" frameborder="0" src="sys/welcome.action"></iframe>
			</div>
		</div>
		</section>

		<div class="contextMenu" id="Huiadminmenu">
			<ul>
				<li id="closethis">
					关闭当前
				</li>
				<li id="closeall">
					关闭全部
				</li>
			</ul>
		</div>
	</body>
	<script type="text/javascript"
		src="page/lib/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript" src="page/lib/layer/2.4/layer.js"></script>
	<script type="text/javascript" src="page/static/h-ui/js/H-ui.min.js"></script>
	<script type="text/javascript"
		src="page/static/h-ui.admin/js/H-ui.admin.js"></script>
	<script type="text/javascript"
		src="page/lib/jquery.contextmenu/jquery.contextmenu.r2.js"></script>

	<script type="text/javascript">
	/*密码修改*/
	function edit_pwd(title, url, w, h) {
		layer_show(title, url, w, h);
	}
	function user_exit() {
		layer.confirm('您确定要退出吗？',
		function(index) {
			location.replace("UserCon/signout.action?name=${admins.name}");
						});
	}
	/*快捷-添加*/
	function quilk_add(title, url) {
		var index = layer.open( {
			type : 2,
			title : title,
			content : url
		});
		layer.full(index);
	}
</script>
</html>
