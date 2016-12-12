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

	//页面加载完
	$(document).ready(function() {
		initMenu();
	});

	function initMenu() {
		// 追加一个顶部菜单
		$('#menu').menu('appendItem', {
			text : '打开',
			minWidth : 206,
			onclick : function() {
				alert('提示：新菜单项！')
			}
		});

		// 追加一个子菜单项
		var item = $('#menu').menu('findItem', '打开'); // 查找“打开”项
		$('#menu').menu('appendItem', {
			parent : item.target, // 设置父菜单元素
			text : '打开Excel文档',
			minWidth : 206,
			onclick : function() {
				alert('提示：打开Excel文档！')
			}
		});
	}
</script>
<style>
</style>
</head>
<body id="layout" class="easyui-layout">
	<div id="content" region="center" style="width: 100%; height: 100%;">
		<div id="tab" data-options="tools:'#tab-tools',toolPosition:'left'"
			class="easyui-tabs" style="width: 100%; height: 100%;">
			<div title="首页" style="width: 100%; height: 100%;">欢迎使用综合管理平台</div>
		</div>
		<div id="tab-tools">
			<a href="#" class="easyui-menubutton"
				data-options="menu:'#menu',iconCls:'icon-person'">XXX，欢迎使用综合管理平台</a>
			<div id="menu" class="easyui-menu" style="width: 206px;"></div>
		</div>
	</div>

</body>
</html>