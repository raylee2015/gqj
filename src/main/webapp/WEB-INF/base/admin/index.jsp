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
	href="<%=contextPath%>/jquery-easyui-1.5/demo/demo.css">
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
				data-options="menu:'#mm1',iconCls:'icon-person'">XXX，欢迎使用综合管理平台</a>
			<div id="mm1" style="width: 150px;">
				<div data-options="iconCls:'icon-undo'">Undo</div>
				<div data-options="iconCls:'icon-redo'">Redo</div>
				<div class="menu-sep"></div>
				<div>Cut</div>
				<div>Copy</div>
				<div>Paste</div>
				<div class="menu-sep"></div>
				<div>
					<span>Toolbar</span>
					<div>
						<div>Address</div>
						<div>Link</div>
						<div>Navigation Toolbar</div>
						<div>Bookmark Toolbar</div>
						<div class="menu-sep"></div>
						<div>New Toolbar...</div>
					</div>
				</div>
				<div data-options="iconCls:'icon-remove'">Delete</div>
				<div>Select All</div>
			</div>
		</div>
	</div>

</body>
</html>