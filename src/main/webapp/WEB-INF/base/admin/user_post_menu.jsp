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
	//查询岗位
	function queryPostForPage(userId) {
		var params = {
			USER_ID : userId,
			page : 1,
			rows : getPageSizeOfDataGrid('datagridOfPost')
		};
		query(params, 'querySelectedPostsForPage.do',
				successFunctionForQueryPost);
	}

	//岗位查询成功
	function successFunctionForQueryPost(result) {
		dataGridLoadData('datagridOfPost', result);
	}

	//查询人员
	function queryUsersForPage(deptId) {
		var params = {
			USER_DEPT_ID : deptId,
			page : 1,
			rows : getPageSizeOfDataGrid('datagridOfUser')
		};
		query(params, 'queryUsersPage.do', successFunctionForQueryUser);
	}

	//成功查询已选人员
	function successFunctionForQueryUser(result) {
		dataGridLoadData('datagridOfUser', result);
	}

	//页面加载完
	$(document).ready(function() {
		closeCache();
		initDeptTree();
		initDataGridOfUser();
		initDataGridOfPost();
	});

	//初始化树
	function initDeptTree() {
		$('#deptTree').tree({
			url : 'queryDeptTree.do',
			onClick : function(node) {
				queryUsersForPage(node.id);
				$('#datagridOfPost').datagrid('loadData', {
					total : 0,
					rows : []
				});
				$('#datagridOfPost').datagrid('unselectAll');
				$('#datagridOfPost').datagrid('uncheckAll');
				$('#treeOfSelectedMenus').tree('loadData', {});
			},
			onLoadError : function(arguments) {
				errorFunctionForQuery();
			}
		});
	}

	//初始化岗位列表
	function initDataGridOfUser() {
		$('#datagridOfUser').datagrid({
			idField : 'USER_ID',
			columns : [ [ {
				field : 'ck',
				title : '操作',
				checkbox : true,
			}, {
				field : 'USER_NAME',
				title : '用户名称',
				width : 100
			} ] ],
			onSelect : function(rowIndex, rowData) {
				queryPostForPage(rowData.USER_ID);
				$('#treeOfSelectedMenus').tree('loadData', {});
			}
		});
	}

	//初始化岗位列表
	function initDataGridOfPost() {
		$('#datagridOfPost').datagrid({
			idField : 'POST_ID',
			columns : [ [ {
				field : 'ck',
				title : '操作',
				checkbox : true,
			}, {
				field : 'POST_NAME',
				title : '岗位名称',
				width : 250
			} ] ],
			onSelect : function(rowIndex, rowData) {
				queryMenusForTree(rowData.POST_ID);
			}
		});
	}

	//查询已选菜单权限
	function queryMenusForTree(postId) {
		var params = {
			POST_ID : postId,
		};
		query(params, 'queryMenusForTree.do', successFunctionForQueryMenus);
	}

	//成功查询已选菜单权限
	function successFunctionForQueryMenus(result, haveTree) {
		$('#treeOfSelectedMenus').tree('loadData', result);
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div region="west" class="easyui-panel" title="第一步：选择部门"
			style="width: 25%;" collapsible="false">
			<ul id="deptTree" class="easyui-tree" method="get" animate="true"
				lines="true"></ul>
		</div>
		<div class="easyui-panel" region="center" style="width: 50%;">
			<div class="easyui-layout" data-options="fit:true">
				<div region="west" class="easyui-panel" collapsible="false"
					style="width: 50%;" title="第二步：选择人员">
					<table id="datagridOfUser" class="easyui-datagrid"
						checkOnSelect="true" pagination="true" pageSize="30"
						singleSelect="true" pageNumber="1" method="get" fit="true">
					</table>
				</div>
				<div region="center" class="easyui-panel" collapsible="false"
					style="width: 50%;" title="第三步：选择岗位">
					<table id="datagridOfPost" class="easyui-datagrid"
						singleSelect="true" singleSelect="true" pagination="true"
						pageSize="30" pageNumber="1" method="get" fit="true">
					</table>
				</div>
			</div>
		</div>
		<div class="easyui-panel" region="east"
			style="width: 25%; height: 100%">
			<div class="easyui-panel" collapsible="false" title="已选菜单权限"
				style="height: 100%">
				<ul id="treeOfSelectedMenus" class="easyui-tree" method="get"
					animate="true" lines="true"></ul>
			</div>
		</div>
	</div>
</body>
</html>