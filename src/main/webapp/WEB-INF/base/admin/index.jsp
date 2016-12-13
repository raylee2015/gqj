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
		$('#menu').menu({
			onClick : function(item) {
				addTab(item.text, item.url);
			}
		});
		querySelectedMenusForList();
	}

	//查询已选菜单权限
	function querySelectedMenusForList() {
		var params = {};
		ajaxFunction(params, 'queryMenuList.do',
				successFunctionForQuerySelectedMenus, errorFunctionForQuery,
				false);
	}

	//成功查询已选菜单权限
	function successFunctionForQuerySelectedMenus(result, haveTree) {
		var rows = result.rows;
		for (var i = 0; i < rows.length; i++) {
			var upMenuName = rows[i].UP_MENU_NAME;
			var menuName = rows[i].MENU_NAME;
			var base =
<%="'" + contextPath + "'"%>
	;
			var menuUrl = base + rows[i].MENU_URL;
			if (typeof (upMenuName) == 'undefined') {
				$('#menu').menu('appendItem', {
					text : menuName,
					url : menuUrl
				});
			} else {
				var item = $('#menu').menu('findItem', upMenuName); // 查找“打开”项
				$('#menu').menu('appendItem', {
					parent : item.target, // 设置父菜单元素
					text : menuName,
					url : menuUrl
				});
			}
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