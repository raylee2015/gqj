<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-control" content="no-cache">
<meta http-equiv="Cache" content="no-cache">
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/jquery-easyui-1.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/jquery-easyui-1.5/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/jquery-easyui-1.5/demo/demo.css">
<script type="text/javascript"
	src="<%=contextPath%>/jquery-easyui-1.5/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/jquery-easyui-1.5/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/base.js"></script>
<script type="text/javascript">
	//关闭编辑窗口
	function closeEditUIForMenu() {
		closeEditUI('editUIForMenu')
	}

	//打开编辑窗口
	function openAddUIForMenu() {
		createModalDialog("editUIForMenu", "openEditUI.do?opType=add", "菜单设置",
				400, 300);
		openEditUI('editUIForMenu');
	}

	//打开编辑窗口
	function openEditUIForMenu(rowIndex) {
		var url = "openEditUI.do?opType=edit&rowIndex=" + rowIndex;
		createModalDialog("editUIForMenu", url, "菜单设置", 400, 300);
		openEditUI('editUIForMenu');
	}
	//删除
	function delMenus() {
		if (checkSelectedItems('datagridForMenu', '请选择菜单')) {
			var menuIds = getIdsOfSelectedItems('datagridForMenu',
					'MENU_INNER_CODE');
			if (menuIds != null && menuIds != '') {
				var params = {
					MENU_IDS : menuIds
				};
				showMessageBox(params, 'delMenus.do', '是否删除所选菜单及其子菜单?',
						successFunctionForOption);
			}
		}
	}

	//更新级联数据
	function updateInnerData() {
		var params = {};
		showMessageBox(params, 'updateInnerData.do', '是否更新级联数据?',
				successFunctionForOption);
	}

	//回调函数，删除或其他操作成功后调用
	function successFunctionForOption(result) {
		showMessage(result.msg, result.msg);
		reloadDataGrid('datagridForMenu');
		reloadTree("menuTree");
	}

	//用在点击查询按钮的时候
	function queryMenuPageForSearch() {
		queryMenus('');
	}

	//用在点击树的时候
	function queryMenuPageForTree(menuInnerCode) {
		queryMenus(menuInnerCode);
	}
	//查询
	function queryMenus(menuInnerCode) {
		var params = {
			menuInnerCode : menuInnerCode,
			keyWord : $('#keyWordForMenuTextInput').textbox('getValue'),
			page : 1,
			rows : getPageSizeOfDataGrid('datagridForMenu')
		};
		query(params, 'queryMenusPage.do', successFunctionForQuery);
	}

	//回调函数，查询成功后调用
	function successFunctionForQuery(result) {
		dataGridLoadData('datagridForMenu', result);
	}

	//页面加载完
	$(document).ready(
			function() {
				closeCache();
				initDataGridForMenu();
				initMentTree();
				registerKeyPressForTextInput('keyWordForMenuTextInput',
						queryMenuPageForSearch);
			});

	//初始化树
	function initMentTree() {
		$('#menuTree').tree({
			url : 'queryMenuTree.do',
			onClick : function(node) {
				if (typeof (node.menu_inner_code) == 'undefined') {
					alert('请关联菜单');
				} else {
					queryMenuPageForTree(node.menu_inner_code); // 在菜单点击的时候提示
				}
			},
			onLoadError : function(arguments) {
				errorFunctionForQuery()
			}
		});
	}

	//初始化列表元素
	function initDataGridForMenu() {
		$('#datagridForMenu')
				.datagrid(
						{
							url : 'queryMenusPage.do',
							idField : 'MENU_ID',
							rownumbers : true,
							toolbar : '#toolbarForMenu',
							pagination : true,
							pageSize : 30,
							pageNumber : 1,
							checkOnSelect : false,
							fit : true,
							method : 'get',
							columns : [ [
									{
										field : 'ck',
										checkbox : true
									},
									{
										field : 'op',
										title : '操作',
										formatter : function(fieldValue,
												rowData, rowIndex) {
											var btn = '<a class="easyui-linkbutton" '
													+ ' onclick="openEditUIForMenu(\''
													+ rowIndex
													+ '\')" href="javascript:void(0)">编辑</a>';
											return btn;
										}
									}, {
										field : 'MENU_NAME',
										title : '菜单名称',
										width : 100,
									}, {
										field : 'MENU_LEVEL_NAME',
										title : '菜单级别',
										width : 100,
									}, {
										field : 'MENU_URL',
										title : '菜单链接',
										width : 250,
									}, {
										field : 'MENU_EXT_CODE',
										title : '扩展权限代码',
										width : 100,
									}, {
										field : 'MENU_SORT',
										title : '排序号',
										width : 100,
									} ] ],
							onLoadError : function() {
								errorFunctionForQuery();
							}
						});
	}
</script>
</head>
<body>
	<!-- 列表页面 -->
	<div class="easyui-layout" data-options="fit:true">
		<div region="west" collapsible="false" style="width: 200px;">
			<ul id="menuTree" class="easyui-tree" method="get" animate="true"
				lines="true"></ul>
		</div>
		<div region="center">
			<table id="datagridForMenu" class="easyui-datagrid">
			</table>
			<div id="toolbarForMenu">
				<div>
					<a href="#" class="easyui-linkbutton" iconCls="icon-reload"
						plain="true" onclick="refreshDataGrid('datagridForMenu')">刷新</a> <a
						href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
						onclick="openAddUIForMenu()">添加</a> <a href="#"
						class="easyui-linkbutton" iconCls="icon-remove" plain="true"
						onclick="delMenus()">删除</a><a href="#" class="easyui-linkbutton"
						iconCls="icon-reload" plain="true" onclick="updateInnerData()">更新级联数据</a>
				</div>
				<div>
					<input id="keyWordForMenuTextInput" class="easyui-textbox"
						data-options="prompt:'菜单名称',validType:'length[0,10]'"
						style="width: 200px"> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="queryMenuPageForSearch()">查询</a>
				</div>

			</div>
		</div>
	</div>
</body>
</html>