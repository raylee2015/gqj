<%@page import="com.base.admin.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	User user = (User) request.getSession().getAttribute("user");
	String userName = user.getUserName();
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
	//刷新当前标签Tabs
	function refreshTab(currentTab) {
		var content = $(currentTab.panel('options')).attr('content');
		$('#tab').tabs('update', {
			tab : currentTab,
			options : {
				content : content
			}
		});
		currentTab.panel('refresh');
	}

	//添加tab页面
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
		closeCache();
		initTabs();
	});

	//初始tab页面
	function initTabs() {
		$('#tab').tabs({
			onSelect : function(title) {
				if ($('#tab').tabs('exists', title)) {
					//当选中的时候刷新
					refreshTab($('#tab').tabs('getSelected'));
				}
			}
		});
	}

	//初始化菜单
	function initMenu() {
		$('#menu').menu({
			onClick : function(item) {
				if (item.menuLevel == 3) {
					addTab(item.text, item.url);
				}
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
			var menuLevel = rows[i].MENU_LEVEL;
			var base =
<%="'" + contextPath + "'"%>
	;
			var menuUrl = base + rows[i].MENU_URL;
			if (menuLevel == 0) {
			} else if (menuLevel == 1) {
				$('#menu').menu('appendItem', {
					text : menuName,
					url : menuUrl,
					menuLevel : menuLevel
				});
			} else {
				var item = $('#menu').menu('findItem', upMenuName); // 查找“打开”项
				$('#menu').menu('appendItem', {
					parent : item.target, // 设置父菜单元素
					text : menuName,
					url : menuUrl,
					menuLevel : menuLevel
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
		<form id="logoutForm" action="<%=contextPath%>/logout.do"
			method="Post"></form>
		<div id="tab" data-options="tools:'#tab-tools',toolPosition:'left'"
			class="easyui-tabs" style="width: 100%; height: 100%;">
			<div title="首页" style="width: 100%; height: 100%;">欢迎使用综合管理平台</div>
		</div>
		<div id="tab-tools">
			<a href="#" class="easyui-menubutton"
				data-options="menu:'#menu',iconCls:'icon-person'"><b><%=userName%></b>，欢迎使用综合管理平台</a>
			<a href="#" class="easyui-linkbutton"
				data-options="iconCls:'icon-control_power'" onClick="logout()">注销</a>
			<div id="menu" class="easyui-menu" style="width: 206px;"></div>
		</div>
	</div>

</body>
</html>