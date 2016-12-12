<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/jquery-easyui-1.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/jquery-easyui-1.5/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/css/base.css">
<script type="text/javascript"
	src="<%=contextPath%>/jquery-easyui-1.5/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/jquery-easyui-1.5/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/base.js"></script>
<script type="text/javascript">
	function addTab(title, url) {
		if ($('#tab').tabs('exists', title)) {
			$('#tab').tabs('select', title);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="'
					+ url + '" style="width:100%;height:100%;"></iframe>';
			$('#tab').tabs('add', {
				title : title,
				content : content,
				closable : true
			});
		}
	}
</script>
<style>
</style>
</head>
<body id="layout" class="easyui-layout">
	<div id="content" region="center" style="width: 100%; height: 100%;">
		<div id="tab" data-options="tools:'#tab-tools',toolPosition:'left'"
			class="easyui-tabs" style="width: 100%; height: 100%;">
			<div title="首页" style="width: 100%; height: 100%;">首页</div>
		</div>
		<div id="tab-tools">
			<a href="#" class="easyui-menubutton"
				data-options="menu:'#menu',iconCls:'icon-person'">XXX，欢迎使用综合管理平台</a>
			<div id="menu" style="width: 206px;">
				<div style="width: 206px;" data-options="iconCls:'icon-medal-gold'">
					<span style="width: 206px;">XXX系统</span>
					<div style="width: 206px;">
						<div style="width: 206px;">
							<span style="width: 206px;">XXX管理模块</span>
							<div style="width: 206px;">
								<div style="width: 206px;">XXX管理</div>
								<div style="width: 206px;">XXX管理</div>
								<div style="width: 206px;">XXX管理</div>
							</div>
						</div>
						<div style="width: 206px;">
							<span style="width: 206px;">XXX管理模块</span>
							<div style="width: 206px;">
								<div style="width: 206px;">XXX管理</div>
								<div style="width: 206px;">XXX管理</div>
								<div style="width: 206px;">XXX管理</div>
							</div>
						</div>
						<div style="width: 206px;">
							<span style="width: 206px;">XXX管理模块</span>
							<div style="width: 206px;">
								<div style="width: 206px;">XXX管理</div>
								<div style="width: 206px;">XXX管理</div>
								<div style="width: 206px;">XXX管理</div>
							</div>
						</div>
					</div>
				</div>
				<div style="width: 206px;">
					<span style="width: 206px;">XXX系统</span>
					<div style="width: 206px;">
						<div style="width: 206px;">
							<span style="width: 206px;">XXX管理模块</span>
							<div style="width: 206px;">
								<div style="width: 206px;">XXX管理</div>
								<div style="width: 206px;">XXX管理</div>
								<div style="width: 206px;">XXX管理</div>
							</div>
						</div>
						<div style="width: 206px;">
							<span style="width: 206px;">XXX管理模块</span>
							<div style="width: 206px;">
								<div style="width: 206px;">XXX管理</div>
								<div style="width: 206px;">XXX管理</div>
								<div style="width: 206px;">XXX管理</div>
							</div>
						</div>
						<div style="width: 206px;">
							<span style="width: 206px;">XXX管理模块</span>
							<div style="width: 206px;">
								<div style="width: 206px;">XXX管理</div>
								<div style="width: 206px;">XXX管理</div>
								<div style="width: 206px;">XXX管理</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>